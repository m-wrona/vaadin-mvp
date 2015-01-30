package com.mwronski.vaadinmvp.web;

import com.mwronski.vaadinmvp.web.pages.OrderPage;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.WebApplication;

import java.util.Set;

/**
 * Entry class for web application.
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class WannabeWebApp extends WebApplication {

    public WannabeWebApp() {
        registerPages(findPages());
        getNavigatorConfig().setHomePageClass(OrderPage.class);
    }

    /**
     * Find pages available in application. Pages are searched in current
     * application package and its sub-packages.
     *
     * @return found pages
     */
    private Class<?>[] findPages() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().addUrls(ClasspathHelper
                .forPackage(getClass().getPackage().getName())));
        Set<Class<?>> pageClasses = reflections.getTypesAnnotatedWith(Page.class);
        if (pageClasses.isEmpty()) {
            throw new RuntimeException("No pages found");
        }
        return pageClasses.toArray(new Class<?>[pageClasses.size()]);
    }
}
