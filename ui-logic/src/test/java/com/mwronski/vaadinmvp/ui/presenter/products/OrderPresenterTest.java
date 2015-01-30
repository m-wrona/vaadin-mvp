package com.mwronski.vaadinmvp.ui.presenter.products;


import com.google.common.eventbus.EventBus;
import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.services.Service;
import com.mwronski.vaadinmvp.ui.presenter.EntitiesPresenter;
import com.mwronski.vaadinmvp.ui.presenter.EntitiesPresenterTest;
import com.mwronski.vaadinmvp.ui.view.products.OrderView;
import com.mwronski.vaadinmvp.validation.ValidationException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


/**
 * Test case for checking logic related with ordering products by single user
 *
 * @author Michal Wronski
 * @date 16-02-2014
 * @see OrderPresenter
 */
public class OrderPresenterTest extends EntitiesPresenterTest<String, Product, OrderView> {

    private Service<?, Product> ordersService = mock(Service.class);
    private Service<String, Product> userOrders = mock(Service.class);
    private OrderPresenter presenter;

    @Override
    protected OrderView createMockView() {
        return mock(OrderView.class);
    }

    @Override
    protected Service<String, Product> createMockService() {
        return mock(Service.class);
    }

    @Override
    protected EntitiesPresenter<Product, OrderView> createPresenter(Service<String, Product> mockReadService, OrderView mockView) {
        presenter = new OrderPresenter(mockView, mockReadService, ordersService, userOrders, new EventBus());
        return presenter;
    }

    @Override
    public void testViewInitialization() {
        verify(getMockView()).setOrderPresenter(presenter);
        super.testViewInitialization();
    }

    @Test
    @Override
    public void testDisplayData() {
        //given
        when(getMockReadService().size()).thenReturn(1);
        List<Product> testProducts = new ArrayList<>();
        when(getMockReadService().get(anyInt(), anyInt())).thenReturn(testProducts);
        Product sampleProduct = new Product();
        sampleProduct.setName("Test Product");
        sampleProduct.setCount(666);
        testProducts.add(sampleProduct);
        Product orderedProducts = new Product();
        orderedProducts.setName("Test Product");
        orderedProducts.setCount(22);
        when(userOrders.find("Test Product")).thenReturn(orderedProducts);
        //when
        getPresenter().display();
        //then
        verify(getMockView()).clear();
        verify(getMockView()).displayProduct("Test Product", 666, 22, 0);
    }

    /**
     * Display data where user has already bought some items
     */
    @Test
    public void testDisplayBoughtProduct() {
        //given
        when(getMockReadService().size()).thenReturn(1);
        List<Product> testProducts = new ArrayList<>();
        when(getMockReadService().get(anyInt(), anyInt())).thenReturn(testProducts);
        Product sampleProduct = new Product();
        sampleProduct.setName("Test Product");
        sampleProduct.setCount(666);
        testProducts.add(sampleProduct);
        when(getMockReadService().find("Test Product")).thenReturn(sampleProduct);

        Product orderedProducts = new Product();
        orderedProducts.setName("Test Product");
        orderedProducts.setCount(22);
        when(userOrders.find("Test Product")).thenReturn(orderedProducts);
        //when
        presenter.handleProductOrder("Test Product", 11);
        presenter.display();
        //then
        verify(getMockView()).displayProduct("Test Product", 666, 22, 11);
    }

    @Test
    public void testHandleNewOrder() {
        //given
        when(getMockReadService().size()).thenReturn(1);
        List<Product> testProducts = new ArrayList<>();
        when(getMockReadService().get(anyInt(), anyInt())).thenReturn(testProducts);
        Product sampleProduct = new Product();
        sampleProduct.setName("Test Product");
        sampleProduct.setCount(666);
        testProducts.add(sampleProduct);
        when(getMockReadService().find("Test Product")).thenReturn(sampleProduct);

        Product orderedProducts = new Product();
        orderedProducts.setName("Test Product");
        orderedProducts.setCount(22);
        when(userOrders.find("Test Product")).thenReturn(orderedProducts);
        reset(getMockView());
        //when
        presenter.handleProductOrder("Test Product", 11);
        //then order is accepted
        verify(getMockView()).setSaveEnabled(true);
        verify(getMockView()).setNextPageEnabled(false);
        verify(getMockView()).setPreviousPageEnabled(false);
        verifyNoMoreInteractions(getMockView());
    }

    @Test
    public void testHandleNewOrder_ProductNotFound() {
        //given no orders are defined
        //when
        try {
            presenter.handleProductOrder("not exist", 11);
            fail("Should fail as product doesn't exist");
        } catch (ValidationException e) {
            //then user is informed
            assertEquals("Product not found: not exist", e.getMessage());
        }
    }

    @Test
    public void testHandleNewOrder_OrderTooBig() {
        //given
        when(getMockReadService().size()).thenReturn(1);
        List<Product> testProducts = new ArrayList<>();
        when(getMockReadService().get(anyInt(), anyInt())).thenReturn(testProducts);
        Product sampleProduct = new Product();
        sampleProduct.setName("Test Product");
        sampleProduct.setCount(666);
        testProducts.add(sampleProduct);
        when(getMockReadService().find("Test Product")).thenReturn(sampleProduct);

        Product orderedProducts = new Product();
        orderedProducts.setName("Test Product");
        orderedProducts.setCount(22);
        when(userOrders.find("Test Product")).thenReturn(orderedProducts);
        reset(getMockView());
        //when
        try {
            presenter.handleProductOrder("Test Product", 1000);
            fail("Should fail as too many items have been ordered");
        } catch (ValidationException e) {
            //then user is informed
            assertEquals("Not enough items available - product: Test Product, count: 666, order count: 1000", e.getMessage());
        }
    }

    @Test
    public void testHandleCancelOrder() {
        //given
        when(getMockReadService().size()).thenReturn(1);
        List<Product> testProducts = new ArrayList<>();
        when(getMockReadService().get(anyInt(), anyInt())).thenReturn(testProducts);
        Product sampleProduct = new Product();
        sampleProduct.setName("Test Product");
        sampleProduct.setCount(666);
        testProducts.add(sampleProduct);
        when(getMockReadService().find("Test Product")).thenReturn(sampleProduct);

        Product orderedProducts = new Product();
        orderedProducts.setName("Test Product");
        orderedProducts.setCount(22);
        when(userOrders.find("Test Product")).thenReturn(orderedProducts);

        reset(getMockView());
        //when order made
        presenter.handleProductOrder("Test Product", 11);
        presenter.display();
        //then order is cancelled
        verify(getMockView()).displayProduct("Test Product", 666, 22, 11);

        reset(getMockView());
        //when order cancelled
        presenter.handleProductOrder("Test Product", 0);
        presenter.display();
        //then
        verify(getMockView()).displayProduct("Test Product", 666, 22, 0);
    }

    @Test
    public void testHandleNotValidOrder() {
        //given products are already displayed
        reset(getMockView());
        //when not valid order is given
        presenter.handleNotValidOrder("not valid product");
        //then view is blocked
        verify(getMockView()).setSaveEnabled(false);
        verify(getMockView()).setNextPageEnabled(false);
        verify(getMockView()).setPreviousPageEnabled(false);
        verifyNoMoreInteractions(getMockView());
    }

    @Test
    public void testMakeOrder() {
        //given
        when(getMockReadService().size()).thenReturn(1);
        List<Product> testProducts = new ArrayList<>();
        when(getMockReadService().get(anyInt(), anyInt())).thenReturn(testProducts);
        Product sampleProduct = new Product();
        sampleProduct.setName("Test Product");
        sampleProduct.setCount(666);
        testProducts.add(sampleProduct);
        when(getMockReadService().find("Test Product")).thenReturn(sampleProduct);

        Product orderedProduct = new Product();
        orderedProduct.setName("Test Product");
        orderedProduct.setCount(22);
        when(userOrders.find("Test Product")).thenReturn(orderedProduct);
        reset(getMockView());
        //when items are chosen and order is made
        presenter.handleProductOrder("Test Product", 11);
        presenter.order();
        //then order is accepted
        verify(ordersService).save(any(Product.class));
        verify(userOrders).save(any(Product.class));
        verify(getMockView()).showInfo("Order has been created");
    }
}
