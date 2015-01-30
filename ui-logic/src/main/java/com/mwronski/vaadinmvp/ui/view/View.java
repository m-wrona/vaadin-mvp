package com.mwronski.vaadinmvp.ui.view;

/**
 * Basic view that can be controlled by single presenter
 *
 * @param <P> type of presenter that controls view
 * @author Michal Wronski
 * @date 04-03-2014
 */
public interface View<P> {

    /**
     * Initialize view
     */
    void init();

    /**
     * Clear data on view
     */
    void clear();

    /**
     * Set presenter where UI actions should be redirect to
     *
     * @param presenter
     */
    void setPresenter(final P presenter);

    /**
     * Show generic information
     * @param msg message
     */
    void showInfo(String msg);

    /**
     * Show warning information
     * @param msg message
     */
    void showWarning(String msg);

    /**
     * Show error information
     * @param msg message
     */
    void showError(String msg);

}
