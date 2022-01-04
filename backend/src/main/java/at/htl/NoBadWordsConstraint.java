package at.htl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoBadWordsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoBadWordsConstraint {
    String message() default "Bad Word";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
