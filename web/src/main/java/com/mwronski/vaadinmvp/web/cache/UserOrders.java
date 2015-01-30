package com.mwronski.vaadinmvp.web.cache;

import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.services.Service;
import com.mwronski.vaadinmvp.services.ServiceException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Orders done by current user.
 * Orders are kept in-memory as long as session is valid.
 * <p/>
 * Note: Class is for DEMO purposes only. MUSTN'T be used on PROD.
 * TODO remove ASAP.
 *
 * @author Michal Wronski
 * @date 16-02-2014
 * @see ThreadLocal
 * @deprecated for DEMO purpose only
 */
@Deprecated
public final class UserOrders implements Service<String, Product> {

    private static final ThreadLocal<UserOrders> CACHE = new ThreadLocal<UserOrders>();
    private final Map<String, Product> orderedProducts = new LinkedHashMap<>();

    @Override
    public Product find(String productName) throws ServiceException {
        return orderedProducts.get(productName);
    }

    @Override
    public void save(Product... products) throws ServiceException {
        for (Product product : products) {
            Product ordered = orderedProducts.get(product.getName());
            if (ordered != null) {
                //increase number of ordered items
                ordered.setCount(ordered.getCount() + product.getCount());
            }
            else {
                orderedProducts.put(product.getName(), product);
            }
        }
    }

    @Override
    public List<Product> get(int startIndex, int size) throws ServiceException {
        List<Product> orders = new ArrayList<>(orderedProducts.values());
        return orders.subList(startIndex, startIndex + size);
    }

    @Override
    public int size() throws ServiceException {
        return orderedProducts.size();
    }

    /**
     * Get user's orders related with current session
     *
     * @return non-nullable instance
     */
    public static UserOrders instance() {
        return CACHE.get();
    }

    /**
     * Initialize user's orders for current session
     *
     * @param instance
     */
    public static void init(UserOrders instance) {
        CACHE.set(instance);
    }

    /**
     * Clear user's orders in current session
     */
    public static void clear() {
        CACHE.set(null);
    }

}
