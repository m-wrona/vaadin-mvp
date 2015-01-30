package com.mwronski.vaadinmvp.validation;

import com.mwronski.vaadinmvp.lang.Lang;

/**
 * Validator checks whether given string value is not null and has proper length
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class StringValidator implements Validator<String> {

    private final boolean nullable;
    private final Integer minLength;
    private final Integer maxLength;
    private final boolean isRequired;

    /**
     * Create instance
     *
     * @param nullable flag indicates whether value can be null
     * @param minLength min allowed length (optional)
     * @param maxLength max allowed length (optional)
     * @param isRequired flag indicates whether field is mandatory
     */
    public StringValidator(final boolean nullable, final Integer minLength, final Integer maxLength,
                           final boolean isRequired) {
        this.nullable = nullable;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.isRequired = isRequired;
    }

    /**
     * Create instance for mandatory field
     *
     * @param nullable flag indicates whether value can be null
     * @param minLength min allowed length (optional)
     * @param maxLength max allowed length (optional)
     */
    public StringValidator(final boolean nullable, final Integer minLength, final Integer maxLength) {
        this(nullable, minLength, maxLength, true);
    }

    @Override
    public void validate(final String value) {
        if (value == null) {
            if (!nullable) {
                throw new ValidationException(Lang.system(ValidationMessages.class).valueIsNull(String.class.getSimpleName()),
                        Lang.user(ValidationMessages.class).valueIsNull(String.class.getSimpleName()));
            }
        }
        else if (minLength != null && value.length() < minLength) {
            throw new ValidationException(Lang.system(ValidationMessages.class).stringShorterThan(value, minLength),
                    Lang.user(ValidationMessages.class).stringShorterThan(value, minLength));
        }
        else if (maxLength != null && value.length() > maxLength) {
            throw new ValidationException(Lang.system(ValidationMessages.class).stringLongerThan(value, maxLength),
                    Lang.user(ValidationMessages.class).stringLongerThan(value, maxLength));
        }
    }

    @Override
    public boolean isRequired() {
        return isRequired;
    }

}
