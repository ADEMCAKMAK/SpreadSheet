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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag("JavaMathReference_types_testing")
@DisplayName("JavaMathReference types testing")
public class JavaMathReferencesTest {

    @BeforeAll
    static void beforeAll() {
        File spreadSheetFolder = new File(TestConstants.spreadSheetFolderPath);

        if( spreadSheetFolder.exists() ){
            if( spreadSheetFolder.isDirectory() ){
                File[] files = spreadSheetFolder.listFiles((dir, name) -> name.contains("JavaMath"));
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
    @Tag("Non_initialize_java_math_reference_types_testing")
    @CsvFileSource(resources = "NonInitializeJavaMathReferenceTypesTest.csv")
    @DisplayName("Non initialize java_math_reference types testing")
    public void nonInitializeJavaMathReferenceTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExampleJavaMathReferences> exampleJavaMathReferencesList = new ArrayList<>();
        exampleJavaMathReferencesList.add(new ExampleJavaMathReferences());

        workbook = spreadSheet.toSpreadSheet(workbook, exampleJavaMathReferencesList, ExampleJavaMathReferences.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    @ParameterizedTest
    @Tag("Initialize_java_math_reference_types_testing")
    @CsvFileSource(resources = "InitializeJavaMathReferenceTypesTest.csv")
    @DisplayName("Initialize java_math_reference types testing")
    public void initializeJavaMathReferenceTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExampleJavaMathReferences> exampleJavaMathReferencesList = exampleJavaMathReferencesList();

        workbook = spreadSheet.toSpreadSheet(workbook, exampleJavaMathReferencesList, ExampleJavaMathReferences.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    private List<ExampleJavaMathReferences> exampleJavaMathReferencesList(){
        List<ExampleJavaMathReferences> exampleJavaMathReferencesList = new ArrayList<>();
        exampleJavaMathReferencesList.add(new ExampleJavaMathReferences(new BigDecimal("0.0"), new BigInteger("0")));
        exampleJavaMathReferencesList.add(new ExampleJavaMathReferences(new BigDecimal("1.11"), new BigInteger("11")));
        exampleJavaMathReferencesList.add(new ExampleJavaMathReferences(new BigDecimal("22.333"), new BigInteger("234")));
        exampleJavaMathReferencesList.add(new ExampleJavaMathReferences(new BigDecimal("1000000.0"), new BigInteger("10000000")));
        exampleJavaMathReferencesList.add(new ExampleJavaMathReferences(new BigDecimal("123450.0"), new BigInteger("123450")));
        return exampleJavaMathReferencesList;
    }
}
