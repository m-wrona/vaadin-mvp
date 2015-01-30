package com.mwronski.vaadinmvp.web.validation;

import com.mwronski.vaadinmvp.validation.ValidationException;
import com.mwronski.vaadinmvp.validation.Validator;

/**
 * Wrapper for service validators to help displaying validations warnings in a simple and smooth way.
 *
 * @author Michal Wronski
 * @date 05-03-2014
 */
public final class UIFieldValidator<T> implements com.vaadin.data.Validator, Validator<T> {

    private final Validator<T> validator;
    private final ValueFormatter<T> formatter;

    @Override
    public boolean isRequired() {
        return validator.isRequired();
    }

    @Override
    public void validate(Object o) throws InvalidValueException {
        try {
            validator.validate(formatter.format(o));
        } catch (ValidationException e) {
            throw new InvalidValueException(e.getInfoMessage());
        }
    }

    @Override
    public boolean isValid(Object o) {
        try {
            validator.validate(formatter.format(o));
            return true;
        } catch (ValidationException e) {
            return false;
        }
    }

    /**
     * Create instance
     *
     * @param validator validator to be wrapped on UI layer
     * @param formatter converted for changing values from UI into proper type
     */
    public UIFieldValidator(Validator<T> validator, ValueFormatter<T> formatter) {
        this.validator = validator;
        this.formatter = formatter;
    }
}
