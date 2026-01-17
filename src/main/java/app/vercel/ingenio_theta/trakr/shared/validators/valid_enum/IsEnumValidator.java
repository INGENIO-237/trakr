package app.vercel.ingenio_theta.trakr.shared.validators.valid_enum;

import java.util.List;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsEnumValidator implements ConstraintValidator<IsEnum, String> {
    private List<String> allowedValues;

    @Override
    public void initialize(IsEnum constraintAnnotation) {
        allowedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(enumValue -> enumValue.name()).toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty())
            return true;

        return allowedValues.contains(value);
    }

}
