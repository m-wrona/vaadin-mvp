package com.mwronski.vaadinmvp.ui.view.products;

import com.mwronski.vaadinmvp.ui.presenter.products.ProductsPresenter;
import com.mwronski.vaadinmvp.ui.view.ListView;

/**
 * View for displaying products
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public interface ProductsView extends ListView<ProductsPresenter> {

    /**
     * Display single product on view
     *
     * @param name name of product
     * @param count number of products
     */
    void displayProduct(String name, int count);

}
