package com.mwronski.vaadinmvp.services;

import com.mwronski.vaadinmvp.exceptions.CoreRuntimeException;

/**
 * Basic exception for services.
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class ServiceException extends CoreRuntimeException {

    /**
     * Create instance
     *
     * @param message message to be included in the stack
     * @param userMessage "pretty" message for end user
     */
    ServiceException(String message, String userMessage) {
        super(message, userMessage);
    }

    /**
     * Create instance
     *
     * @param message message to be included in the stack
     * @param userMessage "pretty" message for end user
     * @param cause cause of issue to be included in the stack
     */
    ServiceException(String message, String userMessage, Throwable cause) {
        super(message, userMessage, cause);
    }
}
