package com.eintracht.teamwallet.layout;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringUI(path="/teamWallet")
@Theme("onedclmonitor")
@Push
public class TeamWalletUI extends UI
{
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private SpringViewProvider springViewProvider;


    /**
     * Initialize themes
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException
    {

        Responsive.makeResponsive(this);
        addStyleName("my-valo-menu-responsive");

        String root = servletContext.getRealPath("/");
        if (root != null && Files.isDirectory(Paths.get(root)))
        {
            Files.createDirectories(Paths.get(servletContext.getRealPath("/VAADIN/themes/onedclmonitor")));
        }
    }

    @Override
    protected void init(VaadinRequest request)
    {
        setContent(applicationContext.getBean(MainScreen.class));
    }
}
