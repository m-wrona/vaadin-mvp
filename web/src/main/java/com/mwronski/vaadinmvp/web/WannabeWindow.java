package com.mwronski.vaadinmvp.web;

import com.mwronski.vaadinmvp.web.cache.UserOrders;
import com.mwronski.vaadinmvp.web.pages.PageFrameWindow;
import com.vaadin.Application;
import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.window.NavigableAppLevelWindow;

/**
 * Single window that can be opened for Wannabe web application.
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class WannabeWindow extends NavigableApplication {

    public WannabeWindow() {
        setTheme("mainTheme");
        initCurrentUserCache();
    }

    @SuppressWarnings("unchecked")
    private void initCurrentUserCache() {
        Object userData = getUser();
        if (userData == null) {
            userData = new UserOrders();
            setUser(userData);
        }
        else if (!(userData instanceof UserOrders)) {
            throw new RuntimeException(String.format("Wrong value found in cache - expected: %s, got: %s", UserOrders.class.getName(), userData.getClass().getName()));
        }
        UserOrders currentUserOrders = (UserOrders) userData;
        UserOrders.init(currentUserOrders);
    }

    @Override
    public void transactionStart(final Application application, final Object transactionData) {
        super.transactionStart(application, transactionData);
        if (this != application) {
            // It does not concern us.
            return;
        }
        initCurrentUserCache();
    }

    @Override
    public void transactionEnd(final Application application, final Object transactionData) {
        super.transactionEnd(application, transactionData);
        if (this != application) {
            // It does not concern us.
            return;
        }
        UserOrders.clear();
        super.transactionEnd(application, transactionData);
    }

    @Override
    public final NavigableAppLevelWindow createNewNavigableAppLevelWindow() {
        return new PageFrameWindow();
    }

}
