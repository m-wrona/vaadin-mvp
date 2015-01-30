package com.mwronski.vaadinmvp.web.components;

import com.mwronski.vaadinmvp.web.WannabeWindow;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * Generic UI messages for notification purposes
 *
 * @author Michal Wronski
 * @date 11-03-2014
 */
public final class UIMessages {

    /**
     * Time in msec how long message should be displayed on screen
     */
    private static final int MESSAGE_DISPLAY_TIME_MS = 1000;

    /**
     * Show error message on screen
     *
     * @param msg
     */
    public static void showError(final String msg) {
        showMessage(msg, Window.Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * Show warning message on screen
     *
     * @param msg
     */
    public static void showWarning(final String msg) {
        showMessage(msg, Window.Notification.TYPE_WARNING_MESSAGE);
    }

    /**
     * Show information message on screen
     *
     * @param msg
     */
    public static void showInfo(final String msg) {
        showMessage(msg, Window.Notification.TYPE_HUMANIZED_MESSAGE);
    }

    /**
     * Show small notification message on screen
     *
     * @param msg
     */
    public static void showNotification(final String msg) {
        showMessage(msg, Window.Notification.TYPE_TRAY_NOTIFICATION);
    }

    /**
     * Show message on screen
     *
     * @param msg
     * @param type type of {@link Notification} window
     * @see Notification
     */
    private static void showMessage(final String msg, final int type) {
        Window.Notification notification = new Window.Notification(msg, type);
        notification.setDelayMsec(MESSAGE_DISPLAY_TIME_MS);
        WannabeWindow.getCurrentNavigableAppLevelWindow().showNotification(notification);
    }


    private UIMessages() {
        // no instances
    }

}
