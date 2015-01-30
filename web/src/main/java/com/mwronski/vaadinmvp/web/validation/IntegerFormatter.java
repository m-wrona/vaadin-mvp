package com.mwronski.vaadinmvp.web.validation;

import com.google.common.base.Strings;
import com.mwronski.vaadinmvp.lang.Lang;
import com.mwronski.vaadinmvp.validation.ValidationException;
import com.mwronski.vaadinmvp.validation.ValidationMessages;

import static com.mwronski.vaadinmvp.logging.Tracer.tracer;

/**
 * Formatter for numeric values
 *
 * @author Michal Wronski
 * @date 05-03-2014
 */
public final class IntegerFormatter implements ValueFormatter<Integer> {

    @Override
    public Integer format(Object value) {
        try {
            String sValue = value == null ? null : value.toString();
            return Strings.isNullOrEmpty(sValue) ? null : Integer.valueOf(sValue);
        } catch (Exception e) {
            tracer(this).debug("Cannot format '%s' into integer value", e, value);
            throw new ValidationException(Lang.system(ValidationMessages.class).notNumber(),
                    Lang.user(ValidationMessages.class).notNumber());
        }
    }
}
