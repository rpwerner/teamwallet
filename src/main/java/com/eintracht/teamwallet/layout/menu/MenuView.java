package com.eintracht.teamwallet.layout.menu;

import com.eintracht.teamwallet.layout.TeamWalletView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum MenuView
{
    HOME("1DCL Monitor",TeamWalletView.class, VaadinIcons.HOME, true);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private MenuView(final String viewName, final Class<? extends View> viewClass, final Resource icon, final boolean stateful)
    {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }
    public boolean isStateful()
    {
        return stateful;
    }

    public String getViewName()
    {
        return viewName;
    }


    public Class<? extends View> getViewClass()
    {
        return viewClass;
    }

    public Resource getIcon()
    {
        return icon;
    }

    public static MenuView getByViewName(final String viewName)
    {
        MenuView result = null;
        for (MenuView viewType : values())
        {
            if (viewType.getViewName().equals(viewName))
            {
                result = viewType;
                break;
            }
        }
        return result;
    }
}
