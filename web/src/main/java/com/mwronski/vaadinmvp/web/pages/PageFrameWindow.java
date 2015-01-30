package com.mwronski.vaadinmvp.web.pages;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.navigator7.Navigator;
import org.vaadin.navigator7.window.NavigableAppLevelWindow;

/**
 * Frame of each page that sets main layout for the page
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class PageFrameWindow extends NavigableAppLevelWindow {

    public PageFrameWindow() {
        // this window should always be immediate
        // otherwise refresh does not work
        setImmediate(true);
    }

    @Override
    public final void attach() {
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        pageContainer = new VerticalLayout();
        content.addComponent(pageContainer);
        navigator = new Navigator();
        addComponent(navigator);
    }

    @Override
    protected final Layout createMainLayout() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected final ComponentContainer createComponents() {
        throw new UnsupportedOperationException();
    }

}
