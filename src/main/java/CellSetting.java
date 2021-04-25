import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface CellSetting {

    int cellNumber();
    String headerName();
    String dataFormatString() default "";
    short dataFormatIndex() default -1;

    FontSetting fontSetting() default @FontSetting(fontName = "headerFontName", isBold = true);

    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.CENTER;
    VerticalAlignment verticalAlignment() default VerticalAlignment.JUSTIFY;
}
