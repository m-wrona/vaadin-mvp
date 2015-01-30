package com.mwronski.vaadinmvp.web.components;

import com.mwronski.vaadinmvp.ui.presenter.EntitiesPresenter;
import com.mwronski.vaadinmvp.ui.view.ListView;
import com.vaadin.ui.*;

/**
 * Table displays products and allows simple paging
 *
 * @param <P> type of presenter that controls view
 * @author Michal Wronski
 * @date 04-03-2014
 */
public class ProductTable<P extends EntitiesPresenter> extends VerticalLayout implements ListView<P> {

    private final String viewTitle;
    private final Table table = new Table();
    private final Button buttonNext = new Button(">>");
    private final Button buttonPrevious = new Button("<<");
    private P presenter;


    public ProductTable(String title) {
        viewTitle = title;
    }

    protected void initActions() {
        buttonNext.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (presenter != null) {
                    presenter.nextPage();
                }
            }
        });
        buttonPrevious.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (presenter != null) {
                    presenter.previousPage();
                }
            }
        });
    }

    protected void initTable(Table table) {
        table.setSortDisabled(true);
        table.setSelectable(false);
        table.setMultiSelect(false);
        table.setColumnCollapsingAllowed(true);
        table.setWidth(500, UNITS_PIXELS);
    }

    @Override
    public final void init() {
        addComponent(new Label(viewTitle));
        addComponent(table);
        HorizontalLayout buttonRow = new HorizontalLayout();
        addComponent(buttonRow);
        initTable(table);
        initButtons(buttonRow);
        initActions();
    }

    protected void initButtons(Layout layout) {
        layout.addComponent(buttonPrevious);
        layout.addComponent(buttonNext);
    }

    @Override
    public final void setNextPageEnabled(boolean enabled) {
        buttonNext.setEnabled(enabled);
    }

    @Override
    public final void setPreviousPageEnabled(boolean enabled) {
        buttonPrevious.setEnabled(enabled);
    }

    @Override
    public final void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public final void clear() {
        table.removeAllItems();
    }

    protected final Table getTable() {
        return table;
    }

    @Override
    public final void showInfo(String msg) {
        UIMessages.showInfo(msg);
    }

    @Override
    public final void showWarning(String msg) {
        UIMessages.showWarning(msg);
    }

    @Override
    public final void showError(String msg) {
        UIMessages.showError(msg);
    }
}

