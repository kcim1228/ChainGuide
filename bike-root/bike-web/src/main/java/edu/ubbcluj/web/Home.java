package edu.ubbcluj.web;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.DAOFactory;
import edu.ubbcluj.backend.repository.UsersDAO;
import edu.ubbcluj.web.admin.Admin;
import edu.ubbcluj.web.user.LoggedUser;
import edu.ubbcluj.web.user.UnLoggedUser;

@Theme("myThemes")
public class Home extends UI {

	
	private static final long serialVersionUID = 1L;
	private DAOFactory daoFactory;

	@Override
	protected void init(VaadinRequest request) {
		 daoFactory = DAOFactory.getInstance();
		System.out.println("regi ertek: "+ this.getSession().getAttribute("userName"));
		VerticalLayout view = new VerticalLayout();
		setContent(view);
		
		Navigator navigator = new Navigator(this, view);
		String oldSessionValue = (String) this.getSession().getAttribute("userName");
		if(oldSessionValue == null){
			this.getSession().setAttribute("userName", "");
			navigator.addView("", new UnLoggedUser(this));
		}
		else{
			if(oldSessionValue.equals("")){
				navigator.addView("", new UnLoggedUser(this));
			}
			else{
				UsersDAO udao = daoFactory.getUsersDAO();
				Users user = udao.getUserByName(oldSessionValue);
				String userType = user.getUsertype();
				if(userType.equals("user")){
					navigator.addView("", new LoggedUser(user));
				}
				if(userType.equals("admin")){
					navigator.addView("", new Admin(user));
				}
			}
		}
		
		
		
		
		
	}
		
	

}
