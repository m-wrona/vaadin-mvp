package com.mwronski.vaadinmvp.ui.presenter.products;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.mwronski.vaadinmvp.lang.Lang;
import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.services.ProductsChangedEvent;
import com.mwronski.vaadinmvp.services.Service;
import com.mwronski.vaadinmvp.ui.presenter.EntitiesPresenter;
import com.mwronski.vaadinmvp.ui.view.products.OrderView;
import com.mwronski.vaadinmvp.ui.view.products.ProductsLang;
import com.mwronski.vaadinmvp.validation.ValidationException;
import com.mwronski.vaadinmvp.validation.model.ProductValidator;

import java.util.HashMap;
import java.util.Map;

import static com.mwronski.vaadinmvp.logging.Tracer.tracer;

/**
 * Presenter controls ordering of new products.
 *
 * @author Michal Wronski
 * @date 16-02-2014
 */
public final class OrderPresenter extends EntitiesPresenter<Product, OrderView> {

    private final OrderView view;
    private final Service<String, Product> productsService;
    private final Service<?, Product> ordersService;
    private final Service<String, Product> userOrders;
    private final Map<String, Product> newOrdersMap = new HashMap<>();
    private ProductsPresenter productsPresenter;


    /**
     * Buy products chosen by the user
     */
    public void order() {
        Product[] orders = newOrdersMap.values().toArray(new Product[newOrdersMap.size()]);
        ordersService.save(orders);
        userOrders.save(orders);
        newOrdersMap.clear();
        display(); //refresh screen
        view.showInfo(Lang.user(ProductsLang.class).orderSavedSuccessfully());
    }

    /**
     * Handle action related with ordering product
     *
     * @param productName name of ordered product
     * @param count number of orders items (0/null for order's cancellation)
     * @throws ValidationException for not deliverable orders
     */
    public void handleProductOrder(String productName, Integer count) throws ValidationException {
        tracer(this).debug("Handling order - product: %s, count: %d", productName, count);
        Product newOrder = new Product();
        newOrder.setName(productName);
        count = count == null ? 0 : count;
        newOrder.setCount(count);
        getValidator().validate(newOrder);
        //cancel first and then check product;s availability
        newOrdersMap.remove(productName);
        if (count > 0) {
            checkOrder(newOrder);
            //order can be managed
            tracer(this).debug("Order created - product: %s, count: %d", productName, count);
            newOrdersMap.put(productName, newOrder);
        }
        refreshNavigation(); //refresh after potential blocking
    }

    /**
     * Check whether given order can be realized
     *
     * @param newOrder order to be checked
     * @throws ValidationException for not deliverable orders
     */
    private void checkOrder(Product newOrder) throws ValidationException {
        Product serviceProduct = productsService.find(newOrder.getName());
        if (serviceProduct == null) {
            throw new ValidationException(Lang.system(ProductsLang.class).productNotFound(newOrder.getName()),
                    Lang.user(ProductsLang.class).productNotFound(newOrder.getName()));
        }
        else if (newOrder.getCount() > serviceProduct.getCount()) {
            throw new ValidationException(Lang.system(ProductsLang.class).notEnoughProduct(serviceProduct.getName(), serviceProduct.getCount(), newOrder.getCount()),
                    Lang.user(ProductsLang.class).notEnoughProduct(serviceProduct.getName(), serviceProduct.getCount(), newOrder.getCount()));
        }
    }

    /**
     * Perform proper actions when one of the orders is not valid
     *
     * @param productName product with wrong order
     */
    public void handleNotValidOrder(String productName) {
        tracer(this).debug("Not valid order for product '%s' - blocking screen", productName);
        view.setSaveEnabled(false);
        view.setNextPageEnabled(false);
        view.setPreviousPageEnabled(false);
    }

    /**
     * Get validator for checking orders
     *
     * @return orders validator
     */
    public ProductValidator getValidator() {
        return new ProductValidator();
    }

    @Override
    protected void displayEntityOnView(Product entity, OrderView view) {
        Product newOrder = newOrdersMap.get(entity.getName());
        Product ordered = userOrders.find(entity.getName());
        view.displayProduct(entity.getName(), entity.getCount(),
                (ordered != null ? ordered.getCount() : 0),
                (newOrder != null ? newOrder.getCount() : 0));
    }

    @Override
    protected void refreshNavigation() {
        super.refreshNavigation();
        view.setSaveEnabled(!newOrdersMap.isEmpty());
    }

    @Subscribe
    public void handleProductsChangedEvent(ProductsChangedEvent event) {
        display(); //refresh
    }

    /**
     * Create instance
     *
     * @param view controlled by presenter
     * @param productsService all products available in application
     * @param ordersService service for orders management
     * @param userOrders service with already ordered products by current user
     * @param eventBus bus for messaging exchange
     */
    public OrderPresenter(OrderView view,
                          Service<String, Product> productsService,
                          Service<?, Product> ordersService,
                          Service<String, Product> userOrders,
                          EventBus eventBus) {
        super(view, productsService);
        this.view = view;
        this.productsService = productsService;
        this.ordersService = ordersService;
        this.userOrders = userOrders;
        view.setOrderPresenter(this);
        eventBus.register(this);
    }

}
