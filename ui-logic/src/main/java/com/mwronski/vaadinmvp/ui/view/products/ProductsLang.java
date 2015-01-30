package com.mwronski.vaadinmvp.ui.view.products;

import com.mwronski.vaadinmvp.lang.LangBundle;

/**
 * Language support for product related messages.
 * <p/>
 * TODO convert into interface
 *
 * @author Michal Wronski
 * @date 01-03-2014
 * @see com.mwronski.vaadinmvp.lang.Lang
 */
public final class ProductsLang implements LangBundle {

    public String productNotFound(String productName) {
        return String.format("Product not found: %s", productName);
    }

    public String notEnoughProduct(String productName, int productsCount, int orderCount) {
        return String.format("Not enough items available - product: %s, count: %s, order count: %s", productName, productsCount, orderCount);
    }

    public String orderSavedSuccessfully() {
        return "Order has been created";
    }


}
