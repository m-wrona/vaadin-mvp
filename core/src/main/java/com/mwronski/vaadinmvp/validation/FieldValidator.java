package com.mwronski.vaadinmvp.validation;

import com.mwronski.vaadinmvp.lang.Lang;

/**
 * Field validator checks whether given field has correct value.
 * If field has incorrect value then proper validation message about value is displayed with field's name.
 * Field validator wraps validation errors coming from value validator and just adds field's name there.
 *
 * @param <T> type of field
 * @author Michal Wronski
 * @date 05-03-2014
 * @see Validator
 */
public final class FieldValidator<T> implements Validator<T> {

    private final Validator<T> validator;
    private final String fieldName;

    @Override
    public void validate(T value) throws ValidationException {
        try {
            validator.validate(value);
        } catch (ValidationException e) {
            String systemError = Lang.system(ValidationMessages.class).wrongField(fieldName) + ":" + e.getMessage();
            String userError = Lang.user(ValidationMessages.class).wrongField(fieldName) + ":" + e.getInfoMessage();
            throw new ValidationException(systemError, userError);
        }
    }

    @Override
    public boolean isRequired() {
        return validator.isRequired();
    }

    public FieldValidator(String fieldName, Validator<T> validator) {
        this.validator = validator;
        this.fieldName = fieldName;
    }
}
