import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SheetSetting {

    String sheetName();

    int defaultColumnWidth() default 0;

    boolean header() default false;

    FontSetting fontSetting();

    boolean isLock() default true;

    HorizontalAlignment HorizontalAlignment() default HorizontalAlignment.CENTER;

    VerticalAlignment VerticalAlignment() default VerticalAlignment.CENTER;

    IndexedColors indexedColors() default IndexedColors.GREY_25_PERCENT;

    FillPatternType fillPatternType() default FillPatternType.SOLID_FOREGROUND;

}
