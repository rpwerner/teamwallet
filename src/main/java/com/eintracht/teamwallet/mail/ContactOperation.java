package com.eintracht.teamwallet.mail;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;


@SpringComponent
@UIScope
public class ContactOperation implements Runnable
{
    private Window dialog = new Window("Contact / Help");

    @Override
    public void run()
    {
        dialog.setModal(true);
        dialog.setWidth("450px");

        HorizontalLayout verticalLayout = new HorizontalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        Label label = new Label("Do you have any questions or suggestions?");
        verticalLayout.addComponent(label);
        Link link = new Link("Let us know!", new ExternalResource("mailto:rafael.werner@ericsson.com"));
        verticalLayout.addComponent(link);

        dialog.setContent(verticalLayout);
        UI.getCurrent().addWindow(dialog);
    }
}
