package com.mwronski.vaadinmvp.services;

import com.google.common.eventbus.EventBus;
import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.validation.model.ProductValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.google.common.base.Preconditions.*;
import static com.mwronski.vaadinmvp.logging.Tracer.tracer;


/**
 * Simple service that manages available products in the shop.
 * Service keeps data only in-memory therefore shouldn't be used on PRODUCTION.
 * Access control is based on simple locking algorithm.
 * <p/>
 * TODO to be removed when real implementation is added for products
 *
 * @author Michal Wronski
 * @date 16-02-2014
 * @see ReentrantReadWriteLock for locking algorithm
 * @deprecated should be used for demo/testing purposes only. Do NOT use on PRODUCTION.
 */
@Deprecated
public final class ProductsService implements Service<String, Product> {

    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static final Map<String, Product> PRODUCTS_MAP = new HashMap<>();
    private static final List<Product> PRODUCTS = new ArrayList<>();

    private final EventBus eventBus;

    /**
     * Get lock of given service
     * <p/>
     * TODO remove ASAP
     *
     * @return non-null instance of lock
     * @deprecated for testing purposes only. Will be removed.
     */
    @Deprecated
    static ReadWriteLock getLock() {
        return LOCK;
    }

    private void validateProducts(Product[] products) {
        ProductValidator validator = new ProductValidator();
        for (Product product : products) {
            validator.validate(product);
        }
    }

    @Override
    public void save(Product... products) {
        tracer(this).debug("Saving products: %s", products);
        LOCK.writeLock().lock();
        try {
            validateProducts(products);
            for (Product product : products) {
                if (PRODUCTS_MAP.containsKey(product.getName())) {
                    //update
                    Product serviceProduct = PRODUCTS_MAP.get(product.getName());
                    serviceProduct.setCount(product.getCount());
                }
                else {
                    //create new product
                    PRODUCTS_MAP.put(product.getName(), product);
                    PRODUCTS.add(product);
                }
            }
            ProductsChangedEvent updateEvent = new ProductsChangedEvent();
            updateEvent.addChangedProducts(products);
            eventBus.post(updateEvent);
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    @Override
    public Product find(String key) throws ServiceException {
        tracer(this).debug("Searching product by key: ", key);
        LOCK.readLock().lock();
        try {
            Product product = PRODUCTS_MAP.get(key);
            return product == null ? null : product.clone();
        } finally {
            LOCK.readLock().unlock();
        }
    }

    @Override
    public List<Product> get(int startIndex, int size) {
        tracer(this).debug("Getting products - startIndex: %i, size: %i", startIndex, size);
        checkElementIndex(startIndex, PRODUCTS.size(), "Wrong start index");
        checkArgument(size > 0, "Wrong size: %s", size);
        LOCK.readLock().lock();
        try {
            List<Product> list = new ArrayList<>(size);
            for (int i = startIndex; i < PRODUCTS.size() && list.size() < size; i++) {
                list.add(PRODUCTS.get(i).clone());
            }
            return list;
        } finally {
            LOCK.readLock().unlock();
        }
    }

    @Override
    public int size() {
        tracer(this).debug("Getting count of products");
        LOCK.readLock().lock();
        try {
            return PRODUCTS_MAP.size();
        } finally {
            LOCK.readLock().unlock();
        }
    }

    /**
     * Remove all products
     * <p/>
     * TODO remove ASAP
     *
     * @deprecated for testing purposes only. Will be removed.
     */
    @Deprecated
    void clear() {
        tracer(this).warn("Deleting all available products");
        LOCK.writeLock().lock();
        try {
            PRODUCTS_MAP.clear();
            PRODUCTS.clear();
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    /**
     * Create instance
     *
     * @param eventBus for dispatching events about changes
     */
    public ProductsService(EventBus eventBus) {
        checkNotNull(eventBus, "Event bus cannot be null");
        this.eventBus = eventBus;
    }

}
