package com.mwronski.vaadinmvp.services;

import com.mwronski.vaadinmvp.model.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Event provides information about changes made in products
 *
 * @author Michal Wronski
 * @date 11-03-2014
 */
public final class ProductsChangedEvent implements Iterable<Product> {

    private final List<Product> changedProducts = new ArrayList<>();

    /**
     * Add all updated products
     *
     * @param products that have been changed
     */
    void addChangedProducts(Product... products) {
        for (Product product : products) {
            changedProducts.add(product);
        }
    }

    @Override
    public Iterator<Product> iterator() {
        return changedProducts.iterator();
    }

    ProductsChangedEvent() {
        //from this package only
    }
}
