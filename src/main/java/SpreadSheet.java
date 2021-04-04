import org.apache.poi.ss.usermodel.Workbook;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

public interface SpreadSheet {

    // export
    <R extends Serializable> Workbook toSpreadSheet(Workbook workbook, Iterable<R> rows, Class<R> rClass) throws IllegalAccessException, IntrospectionException, InvocationTargetException;

    // import
    <R extends Serializable> List<R> fromSpreadSheet(Workbook workbook, List<R> rows, Class<R> rClass) throws IntrospectionException, InvocationTargetException, IllegalAccessException;

    // import
    <R extends Serializable> List<R> fromSpreadSheet(Workbook workbook, Class<R> rClass) throws IntrospectionException, InvocationTargetException, IllegalAccessException;
}
