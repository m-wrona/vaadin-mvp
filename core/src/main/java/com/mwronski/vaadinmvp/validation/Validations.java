package com.mwronski.vaadinmvp.validation;

import com.mwronski.vaadinmvp.lang.Lang;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generic functions related with validation
 *
 * @author Michal Wronski
 * @date 03-03-2014
 */
public final class Validations {

    /**
     * Check whether given value is not null
     *
     * @param value to be checked
     * @param valueName name of value that will appear in error message
     * @param <T> type of checked value
     * @throws ValidationException for null values
     */
    public static <T> void validateNotNull(T value, String valueName) throws ValidationException {
        if (value == null) {
            throw new ValidationException(Lang.system(ValidationMessages.class).valueIsNull(valueName),
                    Lang.user(ValidationMessages.class).valueIsNull(valueName));
        }
    }

    /**
     * Check whether given array is not null and contains at least one element.
     *
     * @param objects array to be checked
     * @param <T> type of array
     * @throws NullPointerException for null array
     * @throws IllegalArgumentException for empty array
     */
    public static <T> void nonEmpty(T[] objects) {
        checkNotNull(objects, "Array cannot be null");
        checkArgument(objects.length > 0, "Array is empty");
    }

    private Validations() {
        //no instances
    }
}
