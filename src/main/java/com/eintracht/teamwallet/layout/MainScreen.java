package com.eintracht.teamwallet.layout;

import com.eintracht.teamwallet.layout.menu.MainMenu;
import com.vaadin.navigator.Navigator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class MainScreen extends CustomComponent
{

    /**
     * @param springViewProvider
     * @param sideBar
     */
    @Autowired
    public MainScreen(SpringViewProvider springViewProvider, MainMenu sideBar)
    {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Panel mainPanel = new Panel();
        mainPanel.setStyleName("mainArea");
        horizontalLayout.addComponent(mainPanel);
        mainPanel.setSizeFull();

        setCompositionRoot(layout);

        setSizeFull();

        layout.addComponent(sideBar);
        layout.addComponent(mainPanel);
        layout.setExpandRatio(mainPanel, 1f);

        Navigator navigator = new Navigator(UI.getCurrent(), mainPanel);
        navigator.addProvider(springViewProvider);

    }

}
