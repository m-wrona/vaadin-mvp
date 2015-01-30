package com.mwronski.vaadinmvp.validation;

/**
 * Validator checks whether value is correct
 *
 * @param <T> type of checked values
 * @author Michal Wronski
 * @date 04-03-2014
 */
public interface Validator<T> {

    /**
     * Check whether value is valid
     *
     * @param value value to be validated
     * @throws ValidationException cause of validation issue with detail information
     */
    void validate(T value) throws ValidationException;

    /**
     * Check whether validation is required  (mandatory)
     *
     * @return true for mandatory values, false otherwise
     */
    boolean isRequired();
}
