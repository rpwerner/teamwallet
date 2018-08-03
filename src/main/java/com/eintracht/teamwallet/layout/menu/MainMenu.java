package com.eintracht.teamwallet.layout.menu;

import com.eintracht.teamwallet.layout.menu.MenuAction;
import com.eintracht.teamwallet.layout.menu.MenuView;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.annotation.PrototypeScope;

@SpringComponent
@PrototypeScope
public class MainMenu extends CustomComponent
{
    public static final String ID = "oneDclMonitor-menu";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    @Autowired
    private ApplicationContext _applicationContext;

    public MainMenu()
    {
        setPrimaryStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();

        setCompositionRoot(buildContent());
    }

    public MainMenu(Component pCompositionRoot)
    {
        super(pCompositionRoot);
    }

    private Component buildContent()
    {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle()
    {
        Label logo = new Label("<center>Ericsson<br><strong>OneDCLMonitor</strong></center>", ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }


    private Component buildToggleButton()
    {
        Button valoMenuToggleButton = new Button("Menu", (Button.ClickListener) event ->
        {
            if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE))
            {
                getCompositionRoot().removeStyleName(STYLE_VISIBLE);
            }
            else
            {
                getCompositionRoot().addStyleName(STYLE_VISIBLE);
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems()
    {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (final MenuView view : MenuView.values())
        {
            Component menuItemComponent = new ValoMenuItemButton(view);

            menuItemsLayout.addComponent(menuItemComponent);
        }

        for (MenuAction action : MenuAction.values())
        {
            ValoMenuActionButton menuActionButton = new ValoMenuActionButton(action);
            menuItemsLayout.addComponent(menuActionButton);
        }

        return menuItemsLayout;

    }

    public final class ValoMenuItemButton extends Button
    {
        private static final String STYLE_SELECTED = "selected";

        private final MenuView view;

        public ValoMenuItemButton(final MenuView view)
        {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getViewName().substring(0, 1).toUpperCase() + view.getViewName().substring(1));
            addClickListener((ClickListener) event ->
            {
                SpringView annotation = view.getViewClass().getAnnotation(SpringView.class);
                String viewName = annotation.name();
                UI.getCurrent().getNavigator().navigateTo(viewName);
            });

        }

    }
    public final class ValoMenuActionButton extends Button
    {
        private final MenuAction action;

        public ValoMenuActionButton(final MenuAction pAction)
        {
            action = pAction;
            setPrimaryStyleName("valo-menu-item");
            setIcon(action.getIcon());
            setCaption(action.getViewName().substring(0, 1).toUpperCase() + action.getViewName().substring(1));
            addClickListener((ClickListener) event ->
            {
                Runnable bean = _applicationContext.getBean(action.getActionClass());
                bean.run();
            });

        }
    }
}
