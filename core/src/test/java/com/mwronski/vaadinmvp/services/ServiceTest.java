package com.mwronski.vaadinmvp.services;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Common test cases for all types of services
 *
 * @param <K> type of primary key of entity
 * @param <T> type of entities taken by tested service
 * @author Michal Wronski
 * @date 04-03-2014
 */
public abstract class ServiceTest<K, T> {

    private Service<K, T> service;

    /**
     * Create sample instance of service
     *
     * @return non-null instance of service that can be used in tests
     */
    protected abstract Service<K, T> createService();

    /**
     * Get number of entities that should be found in service (min 2)
     *
     * @return number of entities
     */
    protected abstract int getEntitiesCount();

    @Before
    public void setUp() {
        service = createService();
    }

    /**
     * Get service under tests
     *
     * @return non-null instance of service
     */
    protected final Service<K, T> getService() {
        return service;
    }

    @Test
    public void testGetOneByOne() {
        assertEquals(getEntitiesCount(), service.size());
        for (int i = 0; i < service.size(); i++) {
            List<T> entities = service.get(i, 1);
            assertNotNull(entities);
            T entity = entities.get(0);
            assertNotNull(entity);
        }
    }

    @Test
    public void testGetLast() {
        assertEquals(getEntitiesCount(), service.size());
        List<T> entities = service.get(getEntitiesCount() - 1, 10);
        assertNotNull(entities);
        assertEquals(1, entities.size());
    }

    @Test
    public abstract void testSaveEntity();

    @Test
    public abstract void testFindEntity();

}
