package com.mwronski.vaadinmvp.validation;

import com.mwronski.vaadinmvp.lang.Lang;

/**
 * Validator checks whether number is not null and is in given range
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class NumberValidator<N extends Number> implements Validator<N> {

    private final boolean nullable;
    private final N minValue;
    private final N maxValue;
    private final boolean isRequired;

    /**
     * Create instance for mandatory field
     *
     * @param nullable flag indicates whether value can be null
     * @param minValue min allowed value (optional)
     * @param maxValue max allowed value (optional)
     */
    public NumberValidator(final boolean nullable, final N minValue, final N maxValue) {
        this(nullable, minValue, maxValue, true);
    }

    /**
     * Create instance
     *
     * @param nullable flag indicates whether value can be null
     * @param minValue min allowed value (optional)
     * @param maxValue max allowed value (optional)
     * @param isRequired flag indicates whether field is mandatory
     */
    public NumberValidator(final boolean nullable, final N minValue, final N maxValue, final boolean isRequired) {
        this.nullable = nullable;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.isRequired = isRequired;
    }

    @Override
    public void validate(final N value) {
        if (value == null) {
            if (!nullable) {
                throw new ValidationException(Lang.system(ValidationMessages.class).valueIsNull(Number.class.getSimpleName()),
                        Lang.user(ValidationMessages.class).valueIsNull(Number.class.getSimpleName()));
            }
        }
        else if (minValue != null && value.doubleValue() < minValue.doubleValue()) {
            throw new ValidationException(Lang.system(ValidationMessages.class).valueIsLesserThan(value, minValue),
                    Lang.user(ValidationMessages.class).valueIsLesserThan(value, minValue));
        }
        else if (maxValue != null && value.doubleValue() > maxValue.doubleValue()) {
            throw new ValidationException(Lang.system(ValidationMessages.class).valueIsBiggerThan(value, maxValue),
                    Lang.user(ValidationMessages.class).valueIsBiggerThan(value, maxValue));
        }
    }

    @Override
    public boolean isRequired() {
        return isRequired;
    }
}
