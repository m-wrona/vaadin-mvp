package com.mwronski.vaadinmvp.web.view.products;

import com.mwronski.vaadinmvp.lang.Lang;
import com.mwronski.vaadinmvp.ui.presenter.products.ProductsPresenter;
import com.mwronski.vaadinmvp.ui.view.products.ProductsView;
import com.mwronski.vaadinmvp.web.components.ProductTable;
import com.vaadin.ui.Table;

/**
 * Basic web form that displays list of products
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class ProductsWebView extends ProductTable<ProductsPresenter> implements ProductsView {

    public ProductsWebView() {
        super(Lang.user(UIProductsLang.class).availableProducts());
    }

    public ProductsWebView(String title) {
        super(title);
    }

    @Override
    protected void initTable(Table table) {
        super.initTable(table);
        // define columns
        UIProductsLang lang = Lang.user(UIProductsLang.class);
        table.addContainerProperty(lang.name(), String.class, null);
        table.setColumnExpandRatio(lang.name(), 0.8f);
        table.addContainerProperty(lang.count(), String.class, null);
        table.setColumnExpandRatio(lang.count(), 0.2f);
    }

    @Override
    public void displayProduct(String name, int count) {
        getTable().addItem(new Object[]{name, count}, name);
    }

}
