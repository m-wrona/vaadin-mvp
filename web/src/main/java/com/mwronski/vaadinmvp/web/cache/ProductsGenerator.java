package com.mwronski.vaadinmvp.web.cache;

import com.google.common.eventbus.EventBus;
import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.services.ProductsService;

import java.util.Random;

/**
 * Class generates products for current user session for DEMO purposes.
 * <p/>
 * TODO remove ASAP
 *
 * @author Michal Wronski
 * @date 04-03-2014
 * @deprecated for DEMO purposes
 */
@Deprecated
public final class ProductsGenerator {

    private static Random random;

    public static synchronized void generate() {
        if (random != null) {
            //already generated
            return;
        }
        random = new Random();
        ProductsService products = new ProductsService(new EventBus());
        int productsCount = 30 + random.nextInt(20);
        for (int i = 0; i < productsCount; i++) {
            Product product = new Product();
            product.setName("Product " + (i + 1));
            product.setCount(5 + random.nextInt(15));
            products.save(product);
        }
    }


}
