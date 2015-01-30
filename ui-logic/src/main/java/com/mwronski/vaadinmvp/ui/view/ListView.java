package com.mwronski.vaadinmvp.ui.view;

import com.mwronski.vaadinmvp.ui.presenter.EntitiesPresenter;

/**
 * Contract for view that displays many entities at the same time.
 *
 * @param <P> type of presenter that controls view
 * @author Michal Wronski
 * @date 04-03-2014
 */
public interface ListView<P extends EntitiesPresenter> extends View<P> {

    /**
     * Set whether next page is available
     *
     * @param enabled if next is available, false otherwise
     */
    void setNextPageEnabled(boolean enabled);

    /**
     * Set whether previous page is available
     *
     * @param enabled if previous is available, false otherwise
     */
    void setPreviousPageEnabled(boolean enabled);

}
