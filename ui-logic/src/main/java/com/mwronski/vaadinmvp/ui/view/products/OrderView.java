package com.mwronski.vaadinmvp.ui.view.products;

import com.mwronski.vaadinmvp.ui.presenter.products.OrderPresenter;
import com.mwronski.vaadinmvp.ui.view.ListView;

/**
 * View for ordering new products
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public interface OrderView extends ListView<OrderPresenter> {

    /**
     * Display product
     *
     * @param name name of the product
     * @param count number of available products
     * @param bought number of already bought elements
     * @param order number of elements in current basket
     */
    void displayProduct(String name, int count, int bought, int order);

    void setSaveEnabled(boolean enabled);

    void setOrderPresenter(OrderPresenter presenter);

}
