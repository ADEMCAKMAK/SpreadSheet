import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Tag("PrimitiveArrayReference_types_testing")
@DisplayName("PrimitiveArrayReference types testing")
public class PrimitiveArrayReferencesTest {

    @BeforeAll
    static void beforeAll() {
        File spreadSheetFolder = new File(TestConstants.spreadSheetFolderPath);

        if( spreadSheetFolder.exists() ){
            if( spreadSheetFolder.isDirectory() ){
                File[] files = spreadSheetFolder.listFiles((dir, name) -> name.contains("PrimitiveArrayReferenceType"));
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
    @Tag("Non_initialize_primitive_array_reference_types_testing")
    @CsvFileSource(resources = "NonInitializePrimitiveArrayReferenceTypesTest.csv")
    @DisplayName("Non initialize primitive_array_reference types testing")
    public void nonInitializePrimitiveArrayReferenceTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExamplePrimitiveArrayReferences> examplePrimitiveArrayReferencesList = new ArrayList<>();
        examplePrimitiveArrayReferencesList.add(new ExamplePrimitiveArrayReferences());

        workbook = spreadSheet.toSpreadSheet(workbook, examplePrimitiveArrayReferencesList, ExamplePrimitiveArrayReferences.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    @ParameterizedTest
    @Tag("Initialize_primitive_array_reference_types_testing")
    @CsvFileSource(resources = "InitializePrimitiveArrayReferenceTypesTest.csv")
    @DisplayName("Initialize primitive_array_reference types testing")
    public void initializePrimitiveArrayReferenceTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExamplePrimitiveArrayReferences> examplePrimitiveArrayReferencesList = examplePrimitiveArrayReferencesList();

        workbook = spreadSheet.toSpreadSheet(workbook, examplePrimitiveArrayReferencesList, ExamplePrimitiveArrayReferences.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    private List<ExamplePrimitiveArrayReferences> examplePrimitiveArrayReferencesList(){
        List<ExamplePrimitiveArrayReferences> examplePrimitiveArrayReferencesList = new ArrayList<>();
        ExamplePrimitiveArrayReferences primitiveArrayReferences = new ExamplePrimitiveArrayReferences();
        primitiveArrayReferences.setByteValue(new byte[1]);
        primitiveArrayReferences.setShortValue(new short[1]);
        primitiveArrayReferences.setIntValue(new int[1]);
        primitiveArrayReferences.setLongValue(new long[1]);
        primitiveArrayReferences.setFloatValue(new float[1]);
        primitiveArrayReferences.setDoubleValue(new double[1]);
        primitiveArrayReferences.setCharValue(new char[1]);
        primitiveArrayReferences.setBooleanValue(new boolean[1]);
        examplePrimitiveArrayReferencesList.add(primitiveArrayReferences);
        return examplePrimitiveArrayReferencesList;
    }

}
