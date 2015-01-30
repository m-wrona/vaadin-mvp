package com.mwronski.vaadinmvp.web.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;

/**
 * Generic functions related with views
 *
 * @author Michal Wronski
 * @date 16-02-2014
 */
public final class ViewUtil {

    /**
     * Add component to layout and mark it with proper label
     *
     * @param layout where component should be add to
     * @param name name of component to be displayed on layout
     * @param component non-null component to be added
     */
    public static void addRow(Layout layout, String name, Component component) {
        HorizontalLayout row = new HorizontalLayout();
        row.addComponent(new Label(name));
        row.addComponent(component);
        layout.addComponent(row);
    }

    private ViewUtil() {
        //no instances
    }
}
