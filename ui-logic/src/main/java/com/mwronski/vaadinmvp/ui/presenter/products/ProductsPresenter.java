package com.mwronski.vaadinmvp.ui.presenter.products;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.services.ProductsChangedEvent;
import com.mwronski.vaadinmvp.services.Service;
import com.mwronski.vaadinmvp.ui.presenter.EntitiesPresenter;
import com.mwronski.vaadinmvp.ui.view.products.ProductsView;

/**
 * Presentation logic for displaying products
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class ProductsPresenter extends EntitiesPresenter<Product, ProductsView> {

    @Subscribe
    public void handleProductsChangedEvent(ProductsChangedEvent event) {
        display(); //refresh
    }

    @Override
    protected void displayEntityOnView(Product entity, ProductsView view) {
        view.displayProduct(entity.getName(), entity.getCount());
    }

    /**
     * Create instance
     *
     * @param view controlled by presenter
     * @param service source of displayed data
     * @param eventBus bus for messaging exchange
     */
    public ProductsPresenter(ProductsView view, Service<?, Product> service, EventBus eventBus) {
        super(view, service);
        eventBus.register(this);
    }
}
