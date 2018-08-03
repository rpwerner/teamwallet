package com.eintracht.teamwallet.layout;

import com.eintracht.teamwallet.mail.Report;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@SpringView(name = "")
public class TeamWalletView extends VerticalLayout implements View
{
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    Report report;

    private VerticalLayout onedclMonitorLayout;

    @Value("${spring.mail.username}")
    String fromEmail;

    /**
     * Builds up the wizard for soap test case creation
     */
    public TeamWalletView(){}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
        //report.sendMail();


        onedclMonitorLayout = new VerticalLayout();
        onedclMonitorLayout.setWidth("100%");
        onedclMonitorLayout.setSpacing(true);
        onedclMonitorLayout.setMargin(true);

        Label text = new Label("1DCL Monitor");
        text.setStyleName("v-label-title");
        onedclMonitorLayout.addComponent(text);

        addComponent(onedclMonitorLayout);
        setComponentAlignment(onedclMonitorLayout, Alignment.TOP_CENTER);
    }

}
