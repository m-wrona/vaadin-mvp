package com.mwronski.vaadinmvp.ui.presenter.products;

import com.google.common.eventbus.EventBus;
import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.services.Service;
import com.mwronski.vaadinmvp.ui.presenter.EntitiesPresenter;
import com.mwronski.vaadinmvp.ui.presenter.EntitiesPresenterTest;
import com.mwronski.vaadinmvp.ui.view.products.ProductsView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Test cases for displaying products
 *
 * @author Michal Wronski
 * @date 23-03-2014
 * @see ProductsPresenter
 */
public class ProductsPresenterTest extends EntitiesPresenterTest<String, Product, ProductsView> {

    @Override
    protected ProductsView createMockView() {
        return mock(ProductsView.class);
    }

    @Override
    protected Service<String, Product> createMockService() {
        return mock(Service.class);
    }

    @Override
    protected EntitiesPresenter<Product, ProductsView> createPresenter(Service<String, Product> mockReadService, ProductsView mockView) {
        return new ProductsPresenter(mockView, mockReadService, new EventBus());
    }

    @Test
    @Override
    public void testDisplayData() {
        //given
        when(getMockReadService().size()).thenReturn(3);
        List<Product> testProducts = new ArrayList<>();
        when(getMockReadService().get(anyInt(), anyInt())).thenReturn(testProducts);
        Product sampleProduct = new Product();
        sampleProduct.setName("Test Product");
        sampleProduct.setCount(666);
        testProducts.add(sampleProduct);
        //when
        getPresenter().display();
        //then
        verify(getMockView()).clear();
        verify(getMockView()).displayProduct("Test Product", 666);
    }
}
