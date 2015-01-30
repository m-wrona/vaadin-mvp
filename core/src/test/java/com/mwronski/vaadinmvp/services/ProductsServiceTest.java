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
 * Test cases related with managing products
 *
 * @author Michal Wronski
 * @date 16-02-2014
 * @see ProductsService
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductsServiceTest extends ServiceTest<String, Product> {

    private final Product productA = new Product();
    @Mock
    private EventBus mockEventBus;

    @Override
    public void setUp() {
        new ProductsService(mockEventBus).clear();
        super.setUp();
        reset(mockEventBus);
    }


    @Override
    protected Service<String, Product> createService() {
        ProductsService service = new ProductsService(mockEventBus);
        productA.setName("Product A");
        productA.setCount(5);
        service.save(productA);
        Product productB = productA.clone();
        productB.setName("Product B");
        service.save(productB);
        return service;
    }

    @Override
    protected int getEntitiesCount() {
        return 2;
    }

    @Override
    public void testSaveEntity() {
        //given
        Product changedEntity = productA.clone();
        changedEntity.setCount(1);
        //when
        getService().save(changedEntity);
        //then
        Product serviceProduct = getService().get(0, 1).get(0);
        assertNotNull(serviceProduct);
        assertEquals("Product A", serviceProduct.getName());
        assertEquals(1, serviceProduct.getCount());
        verify(mockEventBus).post(any(ProductsChangedEvent.class));
        verifyNoMoreInteractions(mockEventBus);
    }

    @Test
    public void testCreateNewProduct() {
        assertEquals(getEntitiesCount(), getService().size());
        //given
        Product newProduct = new Product();
        newProduct.setName("New Product");
        newProduct.setCount(666);
        //when
        getService().save(newProduct);
        //then
        Product serviceProduct = getService().find(newProduct.getName());
        assertNotNull(serviceProduct);
        assertEquals(newProduct.getName(), serviceProduct.getName());
        assertEquals(newProduct.getCount(), serviceProduct.getCount());
        assertEquals(getEntitiesCount() + 1, getService().size());
        verify(mockEventBus).post(any(ProductsChangedEvent.class));
        verifyNoMoreInteractions(mockEventBus);
    }

    @Override
    public void testFindEntity() {
        Product productA = getService().find("Product A");
        assertNotNull(productA);
        assertEquals("Product A", productA.getName());
    }

    @Test
    public void testFindNotExistingEntity() {
        assertNull(getService().find("not existing"));
    }
}
