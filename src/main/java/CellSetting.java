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
}
