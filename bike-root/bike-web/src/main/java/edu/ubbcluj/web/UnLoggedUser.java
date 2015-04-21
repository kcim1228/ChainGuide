package edu.ubbcluj.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

import edu.ubbcluj.backend.model.Places;
import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Type;
import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.DAOFactory;
import edu.ubbcluj.backend.repository.PlacesDAO;
import edu.ubbcluj.backend.repository.ServicesDAO;
import edu.ubbcluj.backend.repository.TypeDAO;
import edu.ubbcluj.backend.repository.UsersDAO;


public class UnLoggedUser extends VerticalLayout implements View {
	
	
	 boolean loginAdded;
	 boolean registerAdded;
	 UI myUIClass;
	 
	// public static final String NAME = "unloggedUser";
	private GridLayout topgrid = new GridLayout(3, 1);
	private GridLayout logingrid = new GridLayout(2,1);
	private GridLayout maingrid = new GridLayout(2,1);
	private GridLayout actiongrid = new GridLayout(1,13);
	private TextArea search = new TextArea("Search: ");
	private TextArea aPoint = new TextArea("A:");
	private TextArea bPoint = new TextArea("B:");
	private TextArea start = new TextArea("Select a Start point: ");
	private Button login = new Button("Login");
	private Button register = new Button("Register");
	private Button getDirection = new Button("GO");
	private Button getNearest = new Button("GO");
	private Label direction = new Label("Get directions: ");
	private ComboBox nearestSelect = new ComboBox ("Get nearest: ");
	private ComboBox showAll = new ComboBox("Show all: ");
	private CheckBox nearestCb = new CheckBox("open now");
	private CheckBox showAllCb = new CheckBox("open now");
	private ComboBox routeType = new ComboBox();
	private Button topsearchButton = new Button("GO");
	private Panel mapPanel = new Panel();
	private Panel narrative = new Panel();
	private Label rtype = new Label("bicycle");
	private VerticalLayout mapLayout = new VerticalLayout();
	
	public UnLoggedUser(UI UIClass){
		myUIClass = UIClass;
		
		//this.setCaption("alma");
	}

	public void enter(ViewChangeEvent event) {
		//System.out.println("unluser: "+this.getSession().getAttribute("userType"));
		
		
		if(this.getSession().getAttribute("userType").equals("user")){
			getUI().getNavigator().navigateTo("loggedUser");
		}
		if(this.getSession().getAttribute("userType").equals("admin")){
			System.out.println("hiba");
			getUI().getNavigator().navigateTo("admin");
		}
		
	
		search.setValue("strada horea, cluj napoca");
		//search.setInputPrompt("blabla");	
		rtype.setStyleName("notVisible");
		routeType.addItem("bicycle");
		routeType.addItem("fastest");
		routeType.setValue("bicycle");
		routeType.addListener( new Property.ValueChangeListener() {
		    public void valueChange(ValueChangeEvent event) {
		    		rtype.setValue((String) routeType.getValue());
		    	}
		    }
		       );
		
		routeType.setRequired(true);
		rtype.setId("rtype");
		
		routeType.setDescription("Choose a travel-mode");		
		nearestSelect.setDescription("What are you looking for?");
		topsearchButton.setId("topsearchButton");
		getDirection.setId("getDirection");
		getNearest.setId("getNearest");		
		topgrid.setMargin(true);
		search.setWidth("70%");
		search.setHeight("1.7em");
		topgrid.setWidth("100%");
		topgrid.setHeight("10%");
		search.setRows(1);
		search.setDescription("Here you can serch for places, services, etc.");
		search.setId("searchTextField");
		maingrid.setWidth("100%");
		maingrid.setHeight("70%");

		logingrid.addComponent(login,0,0);
		logingrid.addComponent(register,1,0);
		topgrid.addComponent(search,0,0);
		topgrid.addComponent(topsearchButton,1,0);
		topgrid.addComponent(logingrid, 2,0);
		topgrid.setComponentAlignment(logingrid,  Alignment.TOP_RIGHT);
		topgrid.setComponentAlignment(topsearchButton,  Alignment.TOP_LEFT);
		
		logingrid.setComponentAlignment(login, Alignment.TOP_RIGHT);
		logingrid.setComponentAlignment(register, Alignment.TOP_RIGHT);
		logingrid.setWidth("100%");
		logingrid.setColumnExpandRatio(0, 5);
		logingrid.setColumnExpandRatio(1, 1);
		topgrid.setColumnExpandRatio(0, 5);
		topgrid.setColumnExpandRatio(1, 1);
		topgrid.setColumnExpandRatio(2, 3);
		actiongrid.addComponent(direction,0,0);
		actiongrid.setWidth("100%");
		actiongrid.setHeight("100%");
		aPoint.setRows(1);
		aPoint.setId("aPoint");
		bPoint.setId("bPoint");
		bPoint.setRows(1);
		start.setRows(1);
		aPoint.setWidth("90%");
		aPoint.setRows(2);
		bPoint.setRows(2);
		start.setRows(2);
		bPoint.setWidth("90%");
		aPoint.setDescription("Type or choose from the map a Start point for your trip .");
		bPoint.setDescription("Type or choose from the map an End point for your trip .");
		actiongrid.addComponent(aPoint,0,1);
		actiongrid.addComponent(bPoint,0,2);
		actiongrid.addComponent(routeType,0,3);
		actiongrid.addComponent(getDirection,0,4);
		actiongrid.addComponent(nearestSelect,0,5);
		actiongrid.addComponent(nearestCb,0,6);
		actiongrid.addComponent(start,0,7);
		actiongrid.addComponent(getNearest,0,8);
		actiongrid.addComponent(showAll,0,9);
		actiongrid.addComponent(showAllCb,0,10);
		actiongrid.addComponent(rtype,0,11);
		start.setId("nearestStart");
		
		
		actiongrid.setComponentAlignment(aPoint, Alignment.MIDDLE_LEFT);
		actiongrid.setComponentAlignment(bPoint, Alignment.MIDDLE_LEFT);
		actiongrid.setComponentAlignment(getDirection, Alignment.MIDDLE_CENTER);
		actiongrid.setComponentAlignment(nearestSelect, Alignment.MIDDLE_LEFT);
		actiongrid.setComponentAlignment(getNearest, Alignment.MIDDLE_CENTER);
		
		actiongrid.setRowExpandRatio(4, 2);
		actiongrid.setMargin(true);
		
		//Label map = new Label("a map itt lesz");

		
		mapLayout.setSizeFull();
		mapPanel.setId("map");
		mapPanel.setStyleName("mapPanel");
		narrative.setId("route-results");
		mapPanel.setSizeFull();	
		
	
	
		
		mapLayout.addComponent(mapPanel);
		mapLayout.addComponent(narrative);
		
		this.addComponent(new Label("ChainGuide"));
		this.addComponent(topgrid);
		this.addComponent(maingrid);
		
		maingrid.addComponent(actiongrid,1,0);
		maingrid.addComponent(mapLayout, 0, 0);
		maingrid.setColumnExpandRatio(0, 4);
		maingrid.setColumnExpandRatio(1, 1);
		
		//PointLocalizer alert = new PointLocalizer();
		//mapLayout.addComponent(alert);
		
		fillSelectAreas(nearestSelect,showAll);
		createLogin(login);	
		createRegister(register);
		
		
		MapLoader mp = new MapLoader();
		mapPanel.setContent(mp);
		
		
		
		
	}

	private void fillSelectAreas(final ComboBox a, ComboBox b){
		final DAOFactory daoFactory = DAOFactory.getInstance();
		PlacesDAO placesDAO = daoFactory.getPlacesDAO();
		TypeDAO typeDAO = daoFactory.getTypeDAO();
		List<Places> places = null;
		List<Type> types = null;
		try{	
			types = typeDAO.getAllType();
			places = placesDAO.getAllPlaces();
		} catch(RuntimeException ex) {
			System.out.println(ex.getMessage());
		}
		for (Type p:types) {
			a.addItem(p.getName());
			b.addItem(p.getName());
		}
		for (Places p:places) {
			a.addItem(p.getType());
			b.addItem(p.getType());
		}
		
		a.addValueChangeListener(new Property.ValueChangeListener() {			
			public void valueChange(ValueChangeEvent event) {
				System.out.println(a.getValue());
				TypeDAO typeDAO = daoFactory.getTypeDAO();
				Type type = typeDAO.getTypeByName(a.getValue().toString());
				System.out.println(type.toString());
				ServicesDAO servicesDAO = daoFactory.getServicesDAO();
				List<Services> services = servicesDAO.getAllServicesByType(type);
				
				for(Services s:services){
					System.out.println(s.toString());
				}
				
				
			}
		});
		
	}
	
	private void createLogin(Button login){
		  	final Window subWindow = new Window("Login");
	        FormLayout flayout = new FormLayout();
	        flayout.setMargin(true);
	        subWindow.setContent(flayout);  
	        final TextField username = new TextField("Username: ");
	        username.focus();
	        flayout.addComponent(username);
	        final PasswordField pass = new PasswordField("Password: ");
	        flayout.addComponent(pass);
	        Button log = new Button("Login");
	        log.setWidth("70%");
	        flayout.addComponent(log);
	        flayout.setHeight("20em");
	        flayout.setWidth("25em"); 
	        subWindow.center();
	        subWindow.setResizable(false);
	        username.setRequired(true);
	        pass.setRequired(true);
	        
	        login.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
			    	if(loginAdded==false){
			    		//addWindow(subWindow);
			    		loginAdded=true;
			    		myUIClass.addWindow(subWindow);
			    		
			    	}
			    }
			});
	        
	        subWindow.addCloseListener(new Window.CloseListener() {
				private static final long serialVersionUID = 1L;

				public void windowClose(CloseEvent e) {					
					subWindow.close();
					loginAdded=false;
				}
			});
	        
	        log.addClickListener(new Button.ClickListener() {
			    public void buttonClick(ClickEvent event) {
			    	DAOFactory daoFactory = DAOFactory.getInstance();
		    		UsersDAO usersDao = daoFactory.getUsersDAO();
		    		try{
		    			Users user = usersDao.getUserByName(username.getValue());
		    			String hashed = passwordHasher(pass.getValue());
		    			if(hashed.equals(user.getPassword())){
		    				subWindow.close();
				    		loginAdded=false;
				    		//getUI().getNavigator().navigateTo(UnLoggedUser.NAME);
				    		if(user.getUsertype().equals("user")){
				    			myUIClass.getSession().setAttribute("userType", "user");
			    				getUI().getNavigator().navigateTo("loggedUser");
			    				
			    				// Save to VaadinServiceSession
			    		       
			    		        // Save to HttpSession
			    		       
			    				
			    			}else{
			    				myUIClass.getSession().setAttribute("userType", "admin");
			    				getUI().getNavigator().navigateTo("admin");
			    				
			    			}
				    		
		    			}else{
		    				pass.setComponentError(new UserError("Wrong password!"));
		    			}
		    			
		    		}catch(RuntimeException ex){
		    			username.setComponentError( new UserError("No such user with this username!"));
		    		}
		    		
		    		
			    }
			});
	}
	
	private void createRegister(Button register){
		final Window subWindow = new Window("Regiter");
        FormLayout flayout = new FormLayout();
        flayout.setMargin(true);
        subWindow.setContent(flayout);
        final TextField fname = new TextField("First Name: ");
        flayout.addComponent(fname);
        final TextField lname = new TextField("Last Name: ");
        flayout.addComponent(lname);
        final TextField username = new TextField("Username: ");
        flayout.addComponent(username);
        username.setRequired(true);
        username.setInvalidAllowed(false);
        username.setDescription("Username must contain only alphabetic characters and numbers");
        final PasswordField pass1 = new PasswordField("Password: ");
        flayout.addComponent(pass1);
        pass1.setRequired(true);
        pass1.setInvalidAllowed(false);
        final PasswordField pass2 = new PasswordField("Re-type: ");
        flayout.addComponent(pass2);
        pass2.setRequired(true);
        pass2.setInvalidAllowed(false);
        final TextField email = new TextField("Email: ");
        flayout.addComponent(email);
        Button reg = new Button("Register");
        flayout.addComponent(reg);
        reg.setWidth("70%");
        flayout.setHeight("30em");
        flayout.setWidth("25em"); 
        subWindow.center();
        subWindow.setResizable(false);
        
        
        register.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	if(registerAdded==false){
		    		myUIClass.addWindow(subWindow);
		    		registerAdded=true;
		    	}
		    }
		});
        
        subWindow.addListener(new Window.CloseListener() {			
			public void windowClose(CloseEvent e) {					
				registerAdded=false;
			}
		});
        
        reg.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	boolean okay=true;
		    	DAOFactory daoFactory = DAOFactory.getInstance();
	    		UsersDAO usersDao = daoFactory.getUsersDAO();
	    		List<Users> existingUser = null;
		    	if(!username.isValid()){
		    		username.setComponentError(new UserError("Please pick a username!"));
		    		okay=false;
		    	}else{
		    			 existingUser = usersDao.getUsersByName("");
		    	}
		    	
		    	if(!pass1.isValid()){
		    		pass1.setComponentError(new UserError("Please type in your password!"));
		    		okay=false;
		    	}
		    	if(!pass2.isValid()){
		    		pass2.setComponentError(new UserError("Please re-type your password!"));
		    		okay=false;
		    	}
		    	if(!pass1.getValue().equals(pass2.getValue())){
		    	
		    		pass2.setComponentError(new UserError("The passwords are not the same!"));
		    		okay=false;
		    	}
		    	if((email!=null)||(!email.equals(""))){
		    		if(!email.getValue().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		    				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
		    			okay=false;
		    			email.setComponentError(new UserError("This is not a valid email adress!"));
		    		}
		    	}
		    	if(okay){
		    		boolean exist = false;
		    		
		    		for(Users u:existingUser){
		    			System.out.println(u.getUsername());
		    			if(u.getUsername().equals(username.getValue())){
		    				
		    				exist = true;
		    			}
		    		}
		    		
		    		if(exist){
		    			Notification.show("Username alredy i use", "Pick up another", com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
		    		}
		    		else{
		    			
		    			subWindow.close();
			    		//System.out.println(fname.getValue()+","+lname.getValue()+","+username.getValue()+","+pass1.getValue()+","+email.getValue());
			    		
			    		String hashed = passwordHasher(pass1.getValue());
			    		Users user = new Users("user",username.getValue(),hashed,fname.getValue(),lname.getValue(),email.getValue());
			    		usersDao.insertUser(user);
			    		getUI().getNavigator().navigateTo("loggedUser");
		    		}
		    		
		    		
		    		
		    	}
		    	
		    	
		    }
		});
        
	}
	
	protected void addWindow(Window subWindow) {
		this.addComponent(subWindow);
		
	}

	private String  passwordHasher(String password){
		String passwordToHash = password;
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(passwordToHash.getBytes());
			//Get the hash's bytes 
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return generatedPassword;
	
	}
	

}