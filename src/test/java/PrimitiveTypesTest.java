import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.beans.IntrospectionException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag("Primitive_types_testing")
@DisplayName("Primitive types testing")
public class PrimitiveTypesTest {

    @BeforeAll
    static void beforeAll() {
        File spreadSheetFolder = new File(TestConstants.spreadSheetFolderPath);

        if( spreadSheetFolder.exists() ){
            if( spreadSheetFolder.isDirectory() ){
                File[] files = spreadSheetFolder.listFiles((dir, name) -> name.contains("PrimitiveType"));
                if (Objects.nonNull(files)) {
                    for(File thatFile : files){
                        if(thatFile.isFile()) thatFile.delete();
                    }
                }
            }
        }
        else{
            spreadSheetFolder.mkdir();
        }
    }

    @AfterAll
    static void afterAll() {

    }

    @ParameterizedTest
    @Tag("Non_initialize_primitive_types_testing")
    @CsvFileSource(resources = "NonInitializePrimitiveTypesTest.csv")
    @DisplayName("Non initialize primitive types testing")
    public void nonInitializePrimitiveTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExamplePrimitives> examplePrimitivesList = new ArrayList<>();
        examplePrimitivesList.add(new ExamplePrimitives());

        workbook = spreadSheet.toSpreadSheet(workbook, examplePrimitivesList, ExamplePrimitives.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    @ParameterizedTest
    @Tag("Initialize_primitive_types_testing")
    @CsvFileSource(resources = "InitializePrimitiveTypesTest.csv")
    @DisplayName("Initialize primitive types testing")
    public void initializePrimitiveTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExamplePrimitives> examplePrimitivesList = examplePrimitivesList();

        workbook = spreadSheet.toSpreadSheet(workbook, examplePrimitivesList, ExamplePrimitives.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    private List<ExamplePrimitives> examplePrimitivesList(){
        List<ExamplePrimitives> examplePrimitivesList = new ArrayList<>();
        examplePrimitivesList.add(new ExamplePrimitives((byte) 0b00000000, (short) 0, 0, 0L, 0.0F, 0.0D, 'A', true));
        examplePrimitivesList.add(new ExamplePrimitives((byte) 0b00000001, (short) 1, 1, 1L, 1.0F, 1.0D, 'B', false));
        examplePrimitivesList.add(new ExamplePrimitives((byte) 0b00000010, (short) 2, 2, 2L, 1.2F, 1.3D, 'C', true));
        examplePrimitivesList.add(new ExamplePrimitives((byte) 0b00000011, (short) 3, 3, 3L, 2.3F, 3.45D, 'D', false));
        examplePrimitivesList.add(new ExamplePrimitives((byte) 0b00000100, (short) 4, 5, 6L, 6.25F, 35.002D, 'E', true));
        examplePrimitivesList.add(new ExamplePrimitives((byte) 0b00000101, (short) 100, 1000, 10000L, 10.0333F, 10.033D, 'F', false));
        return examplePrimitivesList;
    }

}
