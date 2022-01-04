package at.htl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoBadWordsValidator implements ConstraintValidator<NoBadWordsConstraint, String> {

    @Override
    public void initialize(NoBadWordsConstraint words) {
    }

    @Override
    public boolean isValid(String text,
                           ConstraintValidatorContext cxt) {
        return text != null
                && !text.toLowerCase().contains("superbad")
                && !text.toLowerCase().contains("bad");
    }

}
