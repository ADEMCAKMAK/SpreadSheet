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

@Tag("Reference_types_testing")
@DisplayName("Reference types testing")
public class ReferenceTypesTest {

    @BeforeAll
    static void beforeAll() {
        File spreadSheetFolder = new File(TestConstants.spreadSheetFolderPath);

        if( spreadSheetFolder.exists() ){
            if( spreadSheetFolder.isDirectory() ){
                File[] files = spreadSheetFolder.listFiles((dir, name) -> name.contains("ReferenceType"));
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
    @Tag("Non_initialize_reference_types_testing")
    @CsvFileSource(resources = "NonInitializeReferenceTypesTest.csv")
    @DisplayName("Non initialize reference types testing")
    public void nonInitializeReferenceTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExampleReferences> exampleReferencesList = new ArrayList<>();
        exampleReferencesList.add(new ExampleReferences());

        workbook = spreadSheet.toSpreadSheet(workbook, exampleReferencesList, ExampleReferences.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    @ParameterizedTest
    @Tag("Initialize_reference_types_testing")
    @CsvFileSource(resources = "InitializeReferenceTypesTest.csv")
    @DisplayName("Initialize reference types testing")
    public void initializeReferenceTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExampleReferences> exampleReferencesList = exampleReferencesList();

        workbook = spreadSheet.toSpreadSheet(workbook, exampleReferencesList, ExampleReferences.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    private List<ExampleReferences> exampleReferencesList(){
        List<ExampleReferences> exampleReferencesList = new ArrayList<>();
        exampleReferencesList.add(new ExampleReferences(false,(byte) 0b00000000,(short)0,1.1D,1.5F,7,1L,'A',"lorem"));
        exampleReferencesList.add(new ExampleReferences(true,(byte) 0b00000001,(short)1,2.25D,1.1F,6,2L,'b',"ipsum"));
        exampleReferencesList.add(new ExampleReferences(false,(byte) 0b00000010,(short)2,3.99D,1.7F,5,3L,'c',"blablablabla"));
        exampleReferencesList.add(new ExampleReferences(true,(byte) 0b00000011,(short)3,4.333D,3.8F,4,0L,'d',"xdxdxdxdxdxdxd"));
        exampleReferencesList.add(new ExampleReferences(false,(byte) 0b00000100,(short)100,1000.0D,10000.0F,100000,0L,'ç',"asdasdasdasdasd"));
        return exampleReferencesList;
    }

}
