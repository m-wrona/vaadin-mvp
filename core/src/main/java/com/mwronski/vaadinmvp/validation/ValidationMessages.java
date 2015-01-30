package com.mwronski.vaadinmvp.validation;

import com.mwronski.vaadinmvp.lang.LangBundle;

/**
 * Translations of validation's messages
 * <p/>
 * TODO to be converted into interface. See notes in Lang for mode details.
 *
 * @author Michal Wronski
 * @date 05-03-2014
 * @see com.mwronski.vaadinmvp.lang.Lang
 */
public final class ValidationMessages implements LangBundle {

    public String valueIsNull(String valueName) {
        return String.format("Value is null '%s'", valueName);
    }

    public <T> String valueIsBiggerThan(T value1, T value2) {
        return String.format("Value %d is bigger than %d", value1, value2);
    }

    public <T> String valueIsLesserThan(T value1, T value2) {
        return String.format("Value %d is lesser than %d", value1, value2);
    }

    public <T> String stringLongerThan(String string, Integer length) {
        return String.format("String %s is longer than %d", string, length);
    }

    public <T> String stringShorterThan(String string, Integer length) {
        return String.format("String %s is shorten than %d", string, length);
    }

    public String wrongField(String fieldName) {
        return String.format("Wrong field '%s'", fieldName);
    }

    public String notNumber() {
        return "Not a number";
    }
}
