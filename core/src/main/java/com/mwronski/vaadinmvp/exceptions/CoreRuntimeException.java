package com.mwronski.vaadinmvp.exceptions;

/**
 * Core runtime exception for the whole application.
 * Exception allows to define "pretty" message that can be displayed for end user.
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public class CoreRuntimeException extends RuntimeException {

    private final String userMessage;

    /**
     * Create instance without defining "pretty" message
     *
     * @param message message to be included in the stack
     */
    public CoreRuntimeException(String message) {
        super(message);
        this.userMessage = null;
    }

    /**
     * Create instance
     *
     * @param message message to be included in the stack
     * @param userMessage "pretty" message for end user
     */
    public CoreRuntimeException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    /**
     * Create instance without defining "pretty" message
     *
     * @param message message to be included in the stack
     * @param cause cause of issue to be included in the stack
     */
    public CoreRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.userMessage = null;
    }

    /**
     * Create instance
     *
     * @param message message to be included in the stack
     * @param userMessage "pretty" message for end user
     * @param cause cause of issue to be included in the stack
     */
    public CoreRuntimeException(String message, String userMessage, Throwable cause) {
        super(message, cause);
        this.userMessage = userMessage;
    }

    /**
     * Get "pretty" message defined for the end user
     *
     * @return defined message,, null otherwise
     */
    public final String getUserMessage() {
        return userMessage;
    }

    /**
     * Get information about error.
     *
     * @return "pretty" message for user if defined, otherwise standard message from exception
     */
    public final String getInfoMessage() {
        return userMessage != null ? userMessage : getMessage();
    }
}
