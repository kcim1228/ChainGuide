package edu.ubbcluj.web;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LoggedUser extends VerticalLayout implements View{

	public void enter(ViewChangeEvent event) {
		Label a =  new Label("szia");
		this.addComponent(a);
		
	}

}
