package com.mwronski.vaadinmvp.web.view.products;

import com.mwronski.vaadinmvp.lang.Lang;
import com.mwronski.vaadinmvp.lang.LangBundle;

/**
 * Language support for UI elements related with products
 * <p/>
 * TODO convert into interface
 *
 * @author Michal Wronski
 * @date 05-03-2014
 * @see Lang
 */
public final class UIProductsLang implements LangBundle {

    public String name() {
        return "Name";
    }

    public String count() {
        return "Count";
    }

    public String bought() {
        return "Bought";
    }

    public String order() {
        return "Order";
    }

    public String buy() {
        return "Buy";
    }

    public String availableProducts() {
        return "Available products";
    }

    public String buyProducts() {
        return "Buy products";
    }
}
