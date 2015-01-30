package com.mwronski.vaadinmvp.web.validation;

/**
 * Interface for formatting generic values into proper type
 *
 * @param <T> type of formatted value
 * @author Michal Wronski
 * @date 05-03-2014
 */
public interface ValueFormatter<T> {

    T format(Object value);
}
