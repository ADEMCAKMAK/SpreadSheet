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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Tag("TimeReference_types_testing")
@DisplayName("TimeReference types testing")
public class TimeReferencesTest {
    @BeforeAll
    static void beforeAll() {
        File spreadSheetFolder = new File(TestConstants.spreadSheetFolderPath);

        if( spreadSheetFolder.exists() ){
            if( spreadSheetFolder.isDirectory() ){
                File[] files = spreadSheetFolder.listFiles((dir, name) -> name.contains("Time"));
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
    @Tag("Non_initialize_time_reference_types_testing")
    @CsvFileSource(resources = "NonInitializeTimeReferenceTypesTest.csv")
    @DisplayName("Non initialize time_reference types testing")
    public void nonInitializeTimeReferenceTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExampleTimeReferences> exampleTimeReferencesList = new ArrayList<>();
        exampleTimeReferencesList.add(new ExampleTimeReferences());

        workbook = spreadSheet.toSpreadSheet(workbook, exampleTimeReferencesList, ExampleTimeReferences.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    @ParameterizedTest
    @Tag("Initialize_time_reference_types_testing")
    @CsvFileSource(resources = "InitializeTimeReferenceTypesTest.csv")
    @DisplayName("Initialize time_reference types testing")
    public void initializeTimeReferenceTypeTest(boolean workbookType, String path)
            throws IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {

        //System.out.println("workbookType: "+workbookType+ "\n path: "+path);
        Workbook workbook = WorkbookFactory.create(workbookType);

        SpreadSheet spreadSheet = new SpreadSheetBender();

        List<ExampleTimeReferences> exampleTimeReferencesList = exampleTimeReferencesList();

        workbook = spreadSheet.toSpreadSheet(workbook, exampleTimeReferencesList, ExampleTimeReferences.class);

        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }

    }

    private List<ExampleTimeReferences> exampleTimeReferencesList(){
        List<ExampleTimeReferences> exampleTimeReferencesList = new ArrayList<>();
        ExampleTimeReferences timeReferences = new ExampleTimeReferences();
        timeReferences.setDate(new Date());
        timeReferences.setLocalDate(LocalDate.now());
        timeReferences.setLocalDateTime(LocalDateTime.now());
        timeReferences.setCalendar(Calendar.getInstance());
        exampleTimeReferencesList.add(timeReferences);
    return exampleTimeReferencesList;
    }

}
