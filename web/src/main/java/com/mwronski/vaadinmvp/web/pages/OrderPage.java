package com.mwronski.vaadinmvp.web.pages;

import com.google.common.eventbus.EventBus;
import com.mwronski.vaadinmvp.services.ProductsOrderService;
import com.mwronski.vaadinmvp.services.ProductsService;
import com.mwronski.vaadinmvp.ui.presenter.products.OrderPresenter;
import com.mwronski.vaadinmvp.ui.presenter.products.ProductsPresenter;
import com.mwronski.vaadinmvp.web.cache.ProductsGenerator;
import com.mwronski.vaadinmvp.web.cache.UserOrders;
import com.mwronski.vaadinmvp.web.view.products.ProductsOrderWebView;
import com.mwronski.vaadinmvp.web.view.products.ProductsWebView;
import com.vaadin.ui.HorizontalLayout;
import org.vaadin.navigator7.Page;

/**
 * Simple page for creating new orders
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
@Page
public final class OrderPage extends HorizontalLayout {

    static {
        //TODO remove ASAP
        ProductsGenerator.generate();
    }

    public OrderPage() {
        init();
    }

    private void init() {
        EventBus eventBus = new EventBus();
        //widget with currently available products
        ProductsWebView availableProductsView = new ProductsWebView();
        ProductsPresenter productsPresenter =
                new ProductsPresenter(availableProductsView, new ProductsService(eventBus), eventBus);
        productsPresenter.display();
        addComponent(availableProductsView);
        //widget for creating new orders
        ProductsOrderWebView productsOrderView = new ProductsOrderWebView();
        OrderPresenter productsOrderPresenter =
                new OrderPresenter(productsOrderView, new ProductsService(eventBus), new ProductsOrderService(eventBus), UserOrders.instance(), eventBus);
        productsOrderPresenter.display();
        addComponent(productsOrderView);
    }


}
