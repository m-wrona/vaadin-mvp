package com.mwronski.vaadinmvp.services;

import com.google.common.eventbus.EventBus;
import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.validation.ValidationException;
import com.mwronski.vaadinmvp.validation.Validations;
import com.mwronski.vaadinmvp.validation.model.ProductValidator;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.mwronski.vaadinmvp.logging.Tracer.tracer;

/**
 * Simple service that allows ordering products.
 * Service keeps data only in-memory therefore shouldn't be used on PRODUCTION.
 * Access control is based on simple locking algorithm.
 * <p/>
 * TODO to be removed when real implementation is added for products
 *
 * @author Michal Wronski
 * @date 16-02-2014
 * @see java.util.concurrent.locks.ReentrantReadWriteLock for locking algorithm
 * @deprecated should be used for demo/testing purposes only. Do NOT use on PRODUCTION.
 */
@Deprecated
public final class ProductsOrderService implements Service<String, Product> {

    private final EventBus eventBus;

    private void validateOrder(ProductsService productsService, Product[] orderedProducts) {
        Validations.nonEmpty(orderedProducts);
        ProductValidator validator = new ProductValidator();
        for (Product orderedProduct : orderedProducts) {
            validator.validate(orderedProduct);
            Product serviceProduct = productsService.find(orderedProduct.getName());
            validator.validate(serviceProduct); //check whether exists
            if (orderedProduct.getCount() > serviceProduct.getCount()) {
                String errMsg = String.format("Order '%d' is bigger than current state '%d' of products '%s'",
                        orderedProduct.getCount(), serviceProduct.getCount(), serviceProduct.getName());
                throw new ValidationException(errMsg, errMsg);
            }
        }
    }

    @Override
    public void save(Product... orderedProducts) {
        tracer(this).debug("Saving orders: %s", orderedProducts);
        ProductsService.getLock().writeLock().lock();
        ProductsService productsService = new ProductsService(eventBus);
        try {
            validateOrder(productsService, orderedProducts);
            List<Product> changedEntities = new ArrayList<>();
            for (Product orderedProduct : orderedProducts) {
                Product product = productsService.find(orderedProduct.getName());
                checkNotNull(product, "Product not found: %s", orderedProduct.getName());
                product.setCount(product.getCount() - orderedProduct.getCount());
                changedEntities.add(product);
            }
            //TODO to be replaced with less memory consuming approach
            productsService.save(changedEntities.toArray(new Product[changedEntities.size()]));
        } finally {
            ProductsService.getLock().writeLock().unlock();
        }
    }

    @Override
    public Product find(String key) throws ServiceException {
        tracer(this).debug("Searching order by key: ", key);
        //TODO implement reading of existing orders
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Product> get(int startIndex, int size) throws ServiceException {
        tracer(this).debug("Getting orders - startIndex: %i, size: %i", startIndex, size);
        //TODO implement reading of existing orders
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() throws ServiceException {
        tracer(this).debug("Getting count of orders");
        //TODO implement reading of existing orders
        throw new UnsupportedOperationException();
    }

    /**
     * Create instance
     *
     * @param eventBus for dispatching events about changes
     */
    public ProductsOrderService(EventBus eventBus) {
        checkNotNull(eventBus, "Event bus cannot be null");
        this.eventBus = eventBus;
    }
}
