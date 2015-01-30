package com.mwronski.vaadinmvp.ui.presenter;

import com.mwronski.vaadinmvp.services.Service;
import com.mwronski.vaadinmvp.ui.view.ListView;

import java.util.List;

import static com.mwronski.vaadinmvp.logging.Tracer.tracer;

/**
 * Basic presenter for displaying entities provided by proper service
 *
 * @param <T> type of displayed entities
 * @param <V> type of view controlled by presenter
 * @author Michal Wronski
 * @date 04-03-2014
 */
public abstract class EntitiesPresenter<T, V extends ListView> {

    private final V view;
    private final Service<?, T> service;
    private int pageSize = 10;
    private int currentStartIndex = 0;

    /**
     * Display current page (refresh)
     */
    public final void display() {
        display(currentStartIndex, pageSize);
    }

    /**
     * Check whether last page is displayed
     *
     * @return true if last page is displayed, false otherwise
     */
    public final boolean hasNextPage() {
        return currentStartIndex + pageSize < service.size();
    }

    /**
     * Display next available page
     */
    public final void nextPage() {
        if (!hasNextPage()) {
            //already on last page
            return;
        }
        currentStartIndex += pageSize;
        display(currentStartIndex, pageSize);
    }

    /**
     * Check whether first page is displayed
     *
     * @return true if first page is displayed, false otherwise
     */
    public final boolean hasPreviousPage() {
        return currentStartIndex > 0;
    }

    /**
     * Display previous available page
     */
    public final void previousPage() {
        if (!hasPreviousPage()) {
            //already on first page
            return;
        }
        currentStartIndex -= pageSize;
        display(currentStartIndex, pageSize);

    }

    /**
     * Display entities from given range
     *
     * @param startIndex index of first entity to be displayed
     * @param size number of entities to be displayed
     */
    private void display(int startIndex, int size) {
        tracer(this).debug("Displaying page - start index: %d, size: %d", startIndex, size);
        List<T> entities = service.get(startIndex, size);
        view.clear();
        for (T entity : entities) {
            displayEntityOnView(entity, view);
        }
        refreshNavigation();
    }

    /**
     * Refresh navigation area on view
     */
    protected void refreshNavigation() {
        view.setNextPageEnabled(hasNextPage());
        view.setPreviousPageEnabled(hasPreviousPage());
    }

    /**
     * Display entity on view
     *
     * @param entity to be displayed
     * @param view view where data of entity can be displayed on
     */
    protected abstract void displayEntityOnView(T entity, V view);

    /**
     * Set number of elements display on view.
     * Note: change resets currently displayed page.
     *
     * @param pageSize number of visible elements on view
     */
    public final void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        currentStartIndex = 0;
    }

    /**
     * Get view controlled by this instance
     *
     * @return non-null instance of view
     */
    public final V getView() {
        return view;
    }

    /**
     * Create instance
     *
     * @param view where entities will be displayed
     * @param service entities' provider
     */
    public EntitiesPresenter(V view, Service<?, T> service) {
        this.view = view;
        this.service = service;
        view.setPresenter(this);
        view.init();
    }
}
