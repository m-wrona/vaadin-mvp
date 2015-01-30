package com.mwronski.vaadinmvp.validation;

import com.mwronski.vaadinmvp.exceptions.CoreRuntimeException;

/**
 * Basic exception for validation.
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class ValidationException extends CoreRuntimeException {

    /**
     * Create instance
     *
     * @param message message to be included in the stack
     * @param userMessage "pretty" message for end user
     */
    public ValidationException(String message, String userMessage) {
        super(message, userMessage);
    }

    /**
     * Create instance
     *
     * @param message message to be included in the stack
     * @param userMessage "pretty" message for end user
     * @param cause cause of issue to be included in the stack
     */
    public ValidationException(String message, String userMessage, Throwable cause) {
        super(message, userMessage, cause);
    }
}
