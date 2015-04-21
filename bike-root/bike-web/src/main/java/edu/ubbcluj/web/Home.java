package edu.ubbcluj.web;


import edu.ubbcluj.web.UnLoggedUser;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("myThemes")
public class Home extends UI {


	private static final long serialVersionUID = 1L;
	private String userType;

	@Override
	protected void init(VaadinRequest request) {
		
		 this.getSession().setAttribute("userType", "none");
		 
		 System.out.println("home: "+this.getSession().getAttribute("userType"));
		
		VerticalLayout view = new VerticalLayout();
		setContent(view);
		
		Navigator navigator = new Navigator(this, view);
		navigator.addView("", new UnLoggedUser(this));
		navigator.addView("unloggedUser", new UnLoggedUser(this));
		navigator.addView("loggedUser", new LoggedUser());
		navigator.addView("admin", new Admin(this));
		
		
		
	}
		
	

}
