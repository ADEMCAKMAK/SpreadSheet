import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SpreadSheetBender implements SpreadSheet {

    @Override
    public <R extends Serializable> Workbook toSpreadSheet(Workbook workbook, Iterable<R> rows, Class<R> rClass) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        Sheet sheet = createSheet(workbook, rClass);
        int rowNumber = 0;

        if (getHeaderStatus(rClass)) {
            Row row = sheet.createRow(rowNumber++);
            fillHeaders(workbook, sheet, row, rClass);
        }

        fillSheet(workbook, sheet, rows, rowNumber);

        return workbook;
    }

    private <R extends Serializable> Sheet createSheet(Workbook workbook, Class<R> rClass) {
        String safeName = WorkbookUtil.createSafeSheetName(getSheetName(rClass));
        return workbook.createSheet(safeName);
    }

    private <R extends Serializable> String getSheetName(Class<R> rClass) {
        SheetSetting sheetSetting = rClass.getAnnotation(SheetSetting.class);
        return Objects.nonNull(sheetSetting) && StringUtils.isBlank(sheetSetting.sheetName()) ? sheetSetting.sheetName() : rClass.getSimpleName();
    }

    private <R extends Serializable> boolean getHeaderStatus(Class<R> rClass) {
        SheetSetting sheetSetting = rClass.getAnnotation(SheetSetting.class);
        return Objects.nonNull(sheetSetting) ? sheetSetting.header() : Boolean.TRUE;
    }

    private <R extends Serializable> void fillHeaders(Workbook workbook, Sheet sheet, Row row, Class<R> rClass) {

        Field[] declaredFields = Arrays.stream(rClass.getDeclaredFields())
                .filter(this::ignoreField)
                .toArray(Field[]::new);
        CellStyle headerCellStyle = createHeaderCellStyle(workbook, rClass);
        SheetSetting sheetSetting = rClass.getAnnotation(SheetSetting.class);
        for (int fieldNumber = 0, declaredFieldsLength = declaredFields.length; fieldNumber < declaredFieldsLength; fieldNumber++) {
            Field field = declaredFields[fieldNumber];
            CellSetting cellSetting = field.getAnnotation(CellSetting.class);
            Cell cell;
            if (Objects.nonNull(cellSetting)) {
                cell = row.createCell(cellSetting.cellNumber());
                cell.setCellValue(cellSetting.headerName());
            } else {
                cell = row.createCell(fieldNumber);
                cell.setCellValue(field.getName());
            }
            cell.setCellStyle(headerCellStyle);
            if (Objects.nonNull(sheetSetting)) {
                if (Objects.nonNull(cellSetting) && Objects.equals(sheetSetting.defaultColumnWidth(), 0))
                    sheet.autoSizeColumn(cellSetting.cellNumber());
                else
                    sheet.setDefaultColumnWidth(sheetSetting.defaultColumnWidth());
            } else
                sheet.autoSizeColumn(fieldNumber);
        }
    }

    private <R extends Serializable> CellStyle createHeaderCellStyle(Workbook workbook, Class<R> rClass) {

        SheetSetting sheetSetting = rClass.getAnnotation(SheetSetting.class);
        Font headerFont = createHeaderFont(workbook, rClass);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setHidden(false);
        headerCellStyle.setQuotePrefixed(false);
        headerCellStyle.setWrapText(false);
        if (Objects.nonNull(sheetSetting)) {
            headerCellStyle.setLocked(sheetSetting.isLock());
            headerCellStyle.setAlignment(sheetSetting.HorizontalAlignment());
            headerCellStyle.setVerticalAlignment(sheetSetting.VerticalAlignment());
            headerCellStyle.setFillForegroundColor(sheetSetting.indexedColors().getIndex());
            headerCellStyle.setFillPattern(sheetSetting.fillPatternType());
        } else {
            headerCellStyle.setLocked(true);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        return headerCellStyle;
    }

    private <R extends Serializable> Font createHeaderFont(Workbook workbook, Class<R> rClass) {

        SheetSetting sheetSetting = rClass.getAnnotation(SheetSetting.class);
        Font headerFont = workbook.createFont();
        if (Objects.nonNull(sheetSetting)) {
            headerFont.setBold(sheetSetting.fontSetting().isBold());
            headerFont.setItalic(sheetSetting.fontSetting().isItalic());
        } else {
            headerFont.setBold(true);
            headerFont.setItalic(false);
        }

        return headerFont;
    }

    private <R extends Serializable> void fillSheet(Workbook workbook, Sheet sheet, Iterable<R> rows, int rowNumber) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        CreationHelper createHelper = workbook.getCreationHelper();
        for (R item : rows) {
            Row row = sheet.createRow(rowNumber++);
            Field[] declaredFields = Arrays.stream(item.getClass().getDeclaredFields())
                    .filter(this::ignoreField)
                    .toArray(Field[]::new);
            for (int fieldNumber = 0, declaredFieldsLength = declaredFields.length; fieldNumber < declaredFieldsLength; fieldNumber++) {
                Field field = declaredFields[fieldNumber];
                CellSetting cellSetting = field.getAnnotation(CellSetting.class);
                Cell cell = Objects.nonNull(cellSetting)
                        ? row.createCell(cellSetting.cellNumber())
                        : row.createCell(fieldNumber);
                CellStyle cellStyle = createCellStyle(workbook, createHelper, field);
                cell.setCellStyle(cellStyle);
                setCellValue(item, cell, field);
            }
        }
    }

    private CellStyle createCellStyle(Workbook workbook, CreationHelper createHelper, Field field) {

        CellSetting cellSetting = field.getAnnotation(CellSetting.class);
        Font font = createFont(workbook, field);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setHidden(false);
        cellStyle.setQuotePrefixed(false);
        cellStyle.setWrapText(false);
        if (Objects.nonNull(cellSetting)) {
            cellStyle.setAlignment(cellSetting.horizontalAlignment());
            cellStyle.setVerticalAlignment(cellSetting.verticalAlignment());
            if( cellSetting.dataFormatIndex() >= 0 && cellSetting.dataFormatIndex() < BuiltinFormats.getAll().length )
                cellStyle.setDataFormat(cellSetting.dataFormatIndex());
            else if (!cellSetting.dataFormatString().isEmpty() || !cellSetting.dataFormatString().isBlank())
                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(cellSetting.dataFormatString()));
            else{
                cellStyle.setDataFormat(getDefaultDataFormat(field));
            }
        } else {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setDataFormat(getDefaultDataFormat(field));
        }

        return cellStyle;
    }

    private boolean ignoreField(Field field){
        return  Objects.isNull(field) || Modifier.isStatic(field.getModifiers());
    }

    private short getDefaultDataFormat(Field field) {

        Type fieldType = field.getGenericType();
        if( isDateTime(fieldType) ){
            return (short) BuiltinFormats.getBuiltinFormat("m/d/yy h:mm");
        }
        else if( isFloatingPoint(fieldType) ){
            return (short) BuiltinFormats.getBuiltinFormat("0.00");
        }
        return 0;
    }

    private Font createFont(Workbook workbook, Field field) {

        CellSetting cellSetting = field.getAnnotation(CellSetting.class);

        Font font = workbook.createFont();
        if(Objects.nonNull(cellSetting)){
            font.setBold(cellSetting.fontSetting().isBold());
            font.setItalic(cellSetting.fontSetting().isItalic());
        }
        else {
            font.setBold(false);
            font.setItalic(false);
        }

        return font;
    }

    private <R extends Serializable> void setCellValue(R item, Cell cell, Field field) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        Type fieldType = field.getGenericType();
        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), item.getClass());
        Method getter = descriptor.getReadMethod();

        if (Objects.isNull(getter) || Objects.isNull(getter.invoke(item))) {
            cell.setBlank();
        } else if (Objects.equals(fieldType, Date.class)) {
            cell.setCellValue((Date) getter.invoke(item));
        } else if (Objects.equals(fieldType, LocalDate.class)) {
            cell.setCellValue((LocalDate) getter.invoke(item));
        } else if (Objects.equals(fieldType, LocalDateTime.class)) {
            cell.setCellValue((LocalDateTime) getter.invoke(item));
        } else if (Objects.equals(fieldType, Calendar.class)) {
            cell.setCellValue((Calendar) getter.invoke(item));
        } else if (Objects.equals(fieldType, Character.class) || Objects.equals(fieldType, char.class)) {
            cell.setCellValue(String.valueOf((char) getter.invoke(item)));
        } else if (Objects.equals(fieldType, String.class)) {
            cell.setCellValue((String) getter.invoke(item));
        } else if (isNumeric(fieldType)) {
            cell.setCellValue(getNumericValue(fieldType, getter.invoke(item)));
        } else if (Objects.equals(fieldType, Boolean.class) || Objects.equals(fieldType, boolean.class)) {
            cell.setCellValue((Boolean) getter.invoke(item));
        } else {
            cell.setCellValue(fieldType.getTypeName());
        }

    }

    private double getNumericValue(Type fieldType, Object value) {

        if (Objects.equals(fieldType, Byte.class) || Objects.equals(fieldType, byte.class)) {
            return (Byte) value;
        }
        if (Objects.equals(fieldType, Short.class) || Objects.equals(fieldType, short.class)) {
            return (Short) value;
        }
        if (Objects.equals(fieldType, Integer.class) || Objects.equals(fieldType, int.class)) {
            return (Integer) value;
        }
        if (Objects.equals(fieldType, Long.class) || Objects.equals(fieldType, long.class)) {
            return (Long) value;
        }
        if (Objects.equals(fieldType, Double.class) || Objects.equals(fieldType, double.class)) {
            return (Double) value;
        }
        if (Objects.equals(fieldType, Float.class) || Objects.equals(fieldType, float.class)) {
            return (Float) value;
        }
        if (Objects.equals(fieldType, BigDecimal.class)) {
            BigDecimal bigDecimal = (BigDecimal) value;
            return bigDecimal.doubleValue();
        }
        if (Objects.equals(fieldType, BigInteger.class)) {
            BigInteger bigInteger = (BigInteger) value;
            return bigInteger.doubleValue();
        }

        return 0.0;
    }

    private List<Type> getListOfNumericTypes() {
        return Arrays.asList(byte.class, short.class, int.class, long.class, double.class, float.class,
                Byte.class, Short.class, Integer.class, Long.class, Double.class, Float.class, BigDecimal.class, BigInteger.class);
    }

    private List<Type> getListOfDateTimeTypes() {
        return Arrays.asList(Date.class, LocalDate.class, LocalDateTime.class, Calendar.class);
    }

    private List<Type> getListOfFloatingPointsTypes() {
        return Arrays.asList(float.class,double.class,Float.class,Double.class,BigDecimal.class);
    }

    private boolean isFloatingPoint(Type fieldType) {
        return getListOfFloatingPointsTypes().contains(fieldType);
    }

    private boolean isDateTime(Type fieldType){
        return getListOfDateTimeTypes().contains(fieldType);
    }

    private boolean isNumeric(Type fieldType) {
        return getListOfNumericTypes().contains(fieldType);
    }

    @Override
    public <R extends Serializable> List<R> fromSpreadSheet(Workbook workbook, Class<R> rClass) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        return fromSpreadSheet(workbook, null, rClass);
    }

    @Override
    public <R extends Serializable> List<R> fromSpreadSheet(Workbook workbook, List<R> rows, Class<R> rClass) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        Sheet sheet = getSheet(workbook, rClass);
        List<R> lists = readSheet(sheet, rClass);

        if( Objects.nonNull(rows) ) lists.addAll(rows);

        return lists;
    }

    private <R extends Serializable> List<R> readSheet(Sheet sheet, Class<R> rClass) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        List<R> lists = new ArrayList<>();
        if( Objects.isNull(sheet) ){
            return lists;
        }

        Iterator<Row> rowIterator = sheet.rowIterator();
        Field[] declaredFields = rClass.getDeclaredFields();

        while(rowIterator.hasNext()){

            Row row = rowIterator.next();
            R item = getNewInstance(rClass);

            for (int fieldNumber = 0, declaredFieldsLength = declaredFields.length; fieldNumber < declaredFieldsLength; fieldNumber++) {
                Field field = declaredFields[fieldNumber];
                if (ignoreField(field)) continue;
                CellSetting cellSetting = field.getAnnotation(CellSetting.class);
                Cell cell = Objects.nonNull(cellSetting)
                        ? row.getCell(cellSetting.cellNumber())
                        : row.getCell(fieldNumber);
                setFieldValue(item, cell, field);
            }

            lists.add(item);
        }

        return lists;
    }

    private <R extends Serializable> void setFieldValue(R item, Cell cell, Field field) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        Type fieldType = field.getGenericType();
        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), item.getClass());
        Method setter = descriptor.getWriteMethod();

        if (Objects.nonNull(setter)) {

            if( Objects.isNull(cell) ) {
                setter.invoke(item, (Object) null);
            } else if (Objects.equals(fieldType, Date.class)) {
                setter.invoke(item, cell.getDateCellValue());
            } else if (Objects.equals(fieldType, LocalDate.class)) {
                setter.invoke(item, cell.getLocalDateTimeCellValue());
            } else if (Objects.equals(fieldType, LocalDateTime.class)) {
                setter.invoke(item, cell.getLocalDateTimeCellValue());
            } else if (Objects.equals(fieldType, Calendar.class)) {
                setter.invoke(item, cell.getLocalDateTimeCellValue());
            } else if (Objects.equals(fieldType, Character.class) || Objects.equals(fieldType, char.class)) {
                setter.invoke(item, cell.getStringCellValue());
            } else if (Objects.equals(fieldType, String.class)) {
                setter.invoke(item, cell.getStringCellValue());
            } else if (isNumeric(fieldType)) {
                setter.invoke(item, cell.getNumericCellValue());
            } else if (Objects.equals(fieldType, Boolean.class) || Objects.equals(fieldType, boolean.class)) {
                setter.invoke(item, cell.getBooleanCellValue());
            }
            else{
                setter.invoke(item, (Object) null);
            }
        }

    }

    private <R extends Serializable> R getNewInstance(Class<R> rClass) {

        R item;
        try {
            Constructor<R> constructor = rClass.getConstructor();
            item = constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new ThereIsNoPublicNonArgConstructorException("Need a public non-arg constructor", e);
        }

        return item;
    }

    private <R extends Serializable> Sheet getSheet(Workbook workbook, Class<R> rClass) {

        SheetSetting sheetSetting = rClass.getAnnotation(SheetSetting.class);
        Sheet sheet;

        if( Objects.nonNull(sheetSetting)){
            if( StringUtils.isBlank(sheetSetting.sheetName()) )
                sheet = workbook.getSheetAt(0);
            else
                sheet = workbook.getSheet(sheetSetting.sheetName());
        }
        else{
            sheet = workbook.getSheetAt(0);
        }

        return sheet;
    }

}
