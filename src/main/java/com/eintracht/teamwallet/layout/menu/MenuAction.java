package com.eintracht.teamwallet.layout.menu;

import com.eintracht.teamwallet.mail.ContactOperation;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;

public enum MenuAction
{

    LOGOUT("Contact / Help", ContactOperation.class, VaadinIcons.MAILBOX, true);

    private final String viewName;
    private final Class<? extends Runnable> menuAction;
    private final Resource icon;
    private final boolean stateful;

    private MenuAction(final String viewName, final Class<? extends Runnable> viewClass, final Resource icon, final boolean stateful)
    {
        this.viewName = viewName;
        this.menuAction = viewClass;
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

    public Class<? extends Runnable> getActionClass()
    {
        return menuAction;
    }

    public Resource getIcon()
    {
        return icon;
    }

    public static MenuAction getByViewName(final String viewName)
    {
        MenuAction result = null;
        for (MenuAction menuAction : values())
        {
            if (menuAction.getViewName().equals(viewName))
            {
                result = menuAction;
                break;
            }
        }
        return result;
    }
}

