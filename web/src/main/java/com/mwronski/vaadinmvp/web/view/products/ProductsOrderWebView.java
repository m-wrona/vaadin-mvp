package com.mwronski.vaadinmvp.web.view.products;

import com.mwronski.vaadinmvp.lang.Lang;
import com.mwronski.vaadinmvp.ui.presenter.products.OrderPresenter;
import com.mwronski.vaadinmvp.ui.view.products.OrderView;
import com.mwronski.vaadinmvp.validation.ValidationException;
import com.mwronski.vaadinmvp.web.components.ProductTable;
import com.mwronski.vaadinmvp.web.validation.IntegerFormatter;
import com.mwronski.vaadinmvp.web.validation.UIFieldValidator;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

/**
 * Web view for making single order with products.
 *
 * @author Michal Wronski
 * @date 16-02-2014
 */
public final class ProductsOrderWebView extends ProductTable<OrderPresenter> implements OrderView {

    private final UIProductsLang lang = Lang.user(UIProductsLang.class);
    private final Button buttonBuy = new Button(lang.buy());
    private OrderPresenter presenter;

    public ProductsOrderWebView(String title) {
        super(title);
    }

    public ProductsOrderWebView() {
        super(Lang.user(UIProductsLang.class).buyProducts());
    }

    @Override
    protected void initTable(Table table) {
        super.initTable(table);
        // define columns

        table.setWidth(700, UNITS_PIXELS);
        table.addContainerProperty(lang.name(), String.class, null);
        table.setColumnExpandRatio(lang.name(), 0.5f);
        table.setColumnCollapsible(lang.name(), false);
        table.addContainerProperty(lang.count(), Integer.class, null);
        table.setColumnExpandRatio(lang.count(), 0.15f);
        table.addContainerProperty(lang.bought(), Integer.class, null);
        table.setColumnExpandRatio(lang.bought(), 0.15f);
        table.addContainerProperty(lang.order(), TextField.class, null);
        table.setColumnExpandRatio(lang.order(), 0.2f);
        table.setColumnCollapsible(lang.order(), false);
    }

    @Override
    public void displayProduct(String name, int count, int bought, int order) {
        TextField tfOrder = new TextField();
        tfOrder.setValue("" + order);
        tfOrder.setWidth(100, UNITS_PERCENTAGE);
        getTable().addItem(new Object[]{name, count, bought, tfOrder}, name);
        initOrderActions(name, count, tfOrder, presenter);
    }

    private void initOrderActions(final String productName, int productCount, final TextField order, final OrderPresenter presenter) {
        if (presenter == null) {
            //nothing to do
            return;
        }
        order.setImmediate(true);
        order.addValidator(new UIFieldValidator<Integer>(presenter.getValidator().createCountValidator(productCount), new IntegerFormatter()));
        order.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                    if (presenter != null) {
                        if (order.isValid()) {
                            Integer orderValue = new IntegerFormatter().format(valueChangeEvent.getProperty().getValue());
                            presenter.handleProductOrder(productName, orderValue);
                        }
                        else {
                            presenter.handleNotValidOrder(productName);
                        }
                    }
                } catch (ValidationException e) {
                    showError(e.getInfoMessage());
                }
            }
        });
    }

    @Override
    public void setSaveEnabled(boolean enabled) {
        buttonBuy.setEnabled(enabled);
    }

    @Override
    protected void initButtons(Layout layout) {
        super.initButtons(layout);
        layout.addComponent(buttonBuy);
    }

    @Override
    protected void initActions() {
        super.initActions();
        buttonBuy.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (presenter != null) {
                    presenter.order();
                }
            }
        });
    }

    @Override
    public void setOrderPresenter(OrderPresenter presenter) {
        this.presenter = presenter;
    }
}
