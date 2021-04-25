import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface FontSetting {

    String fontName();
    boolean isBold() default false;
    boolean isItalic() default false;
}
