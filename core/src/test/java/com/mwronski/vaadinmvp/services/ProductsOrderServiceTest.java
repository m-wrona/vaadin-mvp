package com.mwronski.vaadinmvp.services;

import com.google.common.eventbus.EventBus;
import com.mwronski.vaadinmvp.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test cases for service responsible for products ordering
 *
 * @author Michal Wronski
 * @date 04-03-2014
 * @see ProductsOrderService
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductsOrderServiceTest extends ServiceTest<String, Product> {

    private final Product productA = new Product();
    @Mock
    private EventBus mockEventBus;
    private ProductsService productsService;

    @Override
    public void setUp() {
        super.setUp();
        productsService = new ProductsService(mockEventBus);
        productsService.clear();
        productA.setName("Product A");
        productA.setCount(5);
        productsService.save(productA);
        reset(mockEventBus);
    }

    @Override
    protected Service<String, Product> createService() {
        return new ProductsOrderService(mockEventBus);
    }

    @Override
    protected int getEntitiesCount() {
        return 1;
    }

    @Override
    public void testSaveEntity() {
        //given
        int originalCount = productA.getCount();
        Product order = productA.clone();
        order.setCount(1);
        //when
        getService().save(order);
        //then
        Product productAfterOrder = productsService.get(0, 1).get(0);
        assertNotNull(productAfterOrder);
        assertEquals("Product A", productAfterOrder.getName());
        assertEquals(originalCount - 1, productAfterOrder.getCount());
        verify(mockEventBus).post(any(ProductsChangedEvent.class));
        verifyNoMoreInteractions(mockEventBus);
    }


    @Test
    public void testTryOrderTooManyProducts() {
        //given
        Product order = productA.clone();
        order.setCount(productA.getCount() + 1);
        try {
            //when
            getService().save(order);
            fail("Should fail as too many products have been bought");
        } catch (Exception e) {
            //then
            assertEquals("Order '6' is bigger than current state '5' of products 'Product A'", e.getMessage());
        }
    }

    @Override
    public void testGetOneByOne() {
        try {
            getService().get(0, 1);
            fail("Service method implemented but test case not updated");
        } catch (UnsupportedOperationException e) {
            //TODO test case to be implemented
        }
    }

    @Override
    public void testGetLast() {
        try {
            getService().get(0, 1);
            fail("Service method implemented but test case not updated");
        } catch (UnsupportedOperationException e) {
            //TODO test case to be implemented
        }
    }

    @Override
    public void testFindEntity() {
        try {
            getService().find(null);
            fail("Service method implemented but test case not updated");
        } catch (UnsupportedOperationException e) {
            //TODO test case to be implemented
        }
    }


}
