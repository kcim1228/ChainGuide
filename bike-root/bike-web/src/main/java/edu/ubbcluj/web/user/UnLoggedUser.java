package edu.ubbcluj.web.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.Accordion;
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

import edu.ubbcluj.backend.model.Openhours;
import edu.ubbcluj.backend.model.Places;
import edu.ubbcluj.backend.model.Rating;
import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Type;
import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.DAOFactory;
import edu.ubbcluj.backend.repository.OpenhoursDAO;
import edu.ubbcluj.backend.repository.PlacesDAO;
import edu.ubbcluj.backend.repository.RatingDAO;
import edu.ubbcluj.backend.repository.ServicesDAO;
import edu.ubbcluj.backend.repository.TypeDAO;
import edu.ubbcluj.backend.repository.UsersDAO;
import edu.ubbcluj.backend.util.PropertyProvider;
import edu.ubbcluj.web.admin.Admin;

public class UnLoggedUser extends VerticalLayout implements View {


	/**
	 * Kátay Csilla
	 */
	private static final long serialVersionUID = 1L;
	private boolean loginAdded;
	private boolean registerAdded;
	private UI myUIClass;
	private GridLayout topgrid = new GridLayout(4, 1);
	private GridLayout logingrid = new GridLayout(2,1);
	private GridLayout maingrid = new GridLayout(2,1);
	private GridLayout actiongrid = new GridLayout(1,15);
	private TextArea search = new TextArea();
	private TextArea aPoint = new TextArea("Start:");
	private TextArea bPoint = new TextArea("End:");
	private TextArea startPointForNearest = new TextArea("Select a Start point: ");
	private Button login = new Button("Login");
	private Button register = new Button("Register");
	private Button getDirection = new Button("GO");
	private Button getNearest = new Button("GO");
	private ComboBox nearestSelect = new ComboBox ("Get nearest: ");
	private ComboBox showAll = new ComboBox("Show all: ");
	private CheckBox nearestCb = new CheckBox("open now");
	private CheckBox showAllCb = new CheckBox("open now");
	private ComboBox routeType = new ComboBox();
	private Button topsearchButton = new Button("GO");
	private Panel mapPanel = new Panel();
	private Panel narrative = new Panel();
	private VerticalLayout mapLayout = new VerticalLayout();
	private ComboBox searchType = new ComboBox();
	Panel jsPanel = new Panel();
	private Services actualSelectedService;
	private Places actualSelectedPlace;
	private String actualSelectedName;
	private Float actualLat;
	private Float actualLng;
	private List<String> allNames =  new ArrayList<String>();
	private List<Float> allLat = new ArrayList<Float>();
	private List<Float> allLng = new ArrayList<Float>();
	private int allSize = -1;
	private String actionState = "searchAction";
	private DAOFactory daoFactory;
	private Button showAllButton = new Button("GO");
	private ComboBox topRated = new ComboBox("Get top rated: ");
	private static final Logger LOG = LoggerFactory.getLogger(UnLoggedUser.class);

	public UnLoggedUser(UI UIClass){
		myUIClass = UI.getCurrent();
	}

	@SuppressWarnings("deprecation")
	public void enter(ViewChangeEvent event) {
		
		daoFactory = DAOFactory.getInstance();
		Float latCoord = Float.parseFloat( PropertyProvider.getProperty("lat"));
		Float lngCoord = Float.parseFloat(PropertyProvider.getProperty("lng"));
		
		
		//Appearance of View
		search.setValue("strada horea, cluj napoca");	
		routeType.addItem("bicycle");
		routeType.addItem("shortest");
		routeType.setValue("bicycle");
		searchType.addItem("adress");
		searchType.addItem("service");
		searchType.addItem("place");
		searchType.setValue("adress");  
		search.setStyleName("textFieldColor");
		aPoint.setStyleName("textFieldColor");
		bPoint.setStyleName("textFieldColor");
		jsPanel.setStyleName("notVisible");
		startPointForNearest.setStyleName("textFieldColor");
		startPointForNearest.setDescription("Select a Start point from the map");
		showAll.setDescription("Choose a type, and list that type of services or places");
		topRated.setDescription("Choose a service type, and you get the top rated service of that type");
		searchType.setDescription("What do you want to search for?");
		routeType.setRequired(true);
		routeType.setDescription("Choose a travel-mode");		
		nearestSelect.setDescription("What are you looking for?");
		topsearchButton.setId("topsearchButton");
		searchType.setId("searchTypeCheck");
		searchType.setStyleName("check");
		getDirection.setId("getDirection");
		getNearest.setId("getNearest");		
		topgrid.setMargin(true);
		search.setWidth("70%");
		search.setRows(1);
		topgrid.setWidth("100%");
		topgrid.setHeight("10%");
		topgrid.setStyleName("topGrid");
		search.setRows(1);
		search.setDescription("Here you can serch for places, services, etc.");
		search.setId("searchTextField");
		maingrid.setWidth("100%");
		maingrid.setHeight("85%");
		
		logingrid.addComponent(login,0,0);
		logingrid.addComponent(register,1,0);
		topgrid.addComponent(search,0,0);
		topgrid.addComponent(topsearchButton,2,0);
		topgrid.addComponent(searchType,1,0);
		topgrid.addComponent(logingrid, 3,0);
		topgrid.setComponentAlignment(search, Alignment.BOTTOM_LEFT);
		topgrid.setComponentAlignment(logingrid,  Alignment.TOP_RIGHT);
		topgrid.setComponentAlignment(topsearchButton,  Alignment.BOTTOM_LEFT);
		topgrid.setComponentAlignment(searchType,  Alignment.BOTTOM_LEFT);

		logingrid.setComponentAlignment(login, Alignment.TOP_RIGHT);
		logingrid.setComponentAlignment(register, Alignment.TOP_RIGHT);
		logingrid.setWidth("100%");
		logingrid.setColumnExpandRatio(0, 5);
		logingrid.setColumnExpandRatio(1, 1);

		topgrid.setColumnExpandRatio(0, 6);
		topgrid.setColumnExpandRatio(1, 1);
		topgrid.setColumnExpandRatio(2, 1);
		topgrid.setColumnExpandRatio(3, 5);

		actiongrid.setWidth("100%");
		actiongrid.setHeight("100%");
		aPoint.setRows(1);
		aPoint.setId("aPoint");
		bPoint.setId("bPoint");
		bPoint.setRows(1);
		aPoint.setWidth("90%");
		aPoint.setRows(2);
		bPoint.setRows(2);
		startPointForNearest.setRows(2);
		bPoint.setWidth("90%");
		aPoint.setDescription("Choose from the map a Start point for your trip .");
		bPoint.setDescription("Choose from the map an End point for your trip .");
		startPointForNearest.setId("nearestStart");
		
		// Create the Accordion.
		Accordion accordion = new Accordion();
		accordion.setHeight("100%");
		final VerticalLayout dirlayout = new VerticalLayout();
		dirlayout.setStyleName("accTab");
		dirlayout.addComponent(aPoint);
		dirlayout.addComponent(bPoint);
		dirlayout.addComponent(routeType);
		dirlayout.addComponent(getDirection);
		dirlayout.setComponentAlignment(getDirection, Alignment.BOTTOM_CENTER);
		dirlayout.setMargin(true);
		accordion.addTab(dirlayout,"Get direction");

		final VerticalLayout nearlayout = new VerticalLayout();
		nearlayout.setStyleName("accTab");
		nearlayout.addComponent(nearestSelect);
		nearlayout.addComponent(nearestCb);
		nearlayout.addComponent(startPointForNearest);
		nearlayout.addComponent(getNearest);
		nearlayout.setComponentAlignment(getNearest, Alignment.BOTTOM_CENTER);
		nearlayout.setMargin(true);
		accordion.addTab(nearlayout, "Get nearest");

		final VerticalLayout alllayout = new VerticalLayout();
		alllayout.setStyleName("accTab");
		alllayout.addComponent(showAll);
		alllayout.addComponent(showAllCb);
		alllayout.addComponent(showAllButton);
		alllayout.setComponentAlignment(showAllButton, Alignment.BOTTOM_CENTER);
		alllayout.setMargin(true);
		accordion.addTab(alllayout, "Get all");

		final VerticalLayout ratelayout = new VerticalLayout();
		ratelayout.setStyleName("accTab");
		ratelayout.addComponent(topRated);
		ratelayout.setMargin(true);
		accordion.addTab(ratelayout, "Get top rated");

		actiongrid.addComponent(accordion,0,13);
		actiongrid.addComponent(jsPanel,0,14);
		mapLayout.setSizeFull();
		mapPanel.setId("map");
		mapPanel.setStyleName("mapPanel");
		narrative.setId("route-results");
		mapPanel.setSizeFull();	
		mapLayout.addComponent(mapPanel);

		Label top = new Label("");
		top.setStyleName("logo");
		top.setWidth("100%");
		top.setHeight("4%");
		this.addComponent(top);
		this.addComponent(topgrid);
		Label div = new Label(".");
		this.addComponent(div);
		div.setHeight("1%");
		this.addComponent(maingrid);
		this.setStyleName("mainColor");
		maingrid.addComponent(actiongrid,1,0);
		maingrid.addComponent(mapLayout, 0, 0);
		maingrid.setColumnExpandRatio(0, 5);
		maingrid.setColumnExpandRatio(1, 1);
		fillSelectAreas(nearestSelect,showAll,topRated);
		createLogin(login);	
		createRegister(register);	
		MapLoader mp = new MapLoader(latCoord,lngCoord);
		mapPanel.setContent(mp);
		
		
		//action-listeners:
		getDirection.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				actionState = "routeAction";
				if((aPoint.getValue().equals(""))||(aPoint.getValue()==null)||
					(bPoint.getValue().equals(""))||(bPoint.getValue()==null)){
					Notification.show("Please select a START and an END point for your route!",
							"",
							Notification.Type.WARNING_MESSAGE);
				}else{
					JsConnecter js = new JsConnecter((String) routeType.getValue(),
							(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,allNames,
							allLat,allLng,allSize,actionState);
					jsPanel.setContent(js);
				}
			}
		});

		getNearest.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				if((startPointForNearest.getValue().equals(""))||(nearestSelect.getValue()==null)){
					Notification.show("Please select a START point and a Service-type!",
							"",
							Notification.Type.WARNING_MESSAGE);
				}else{
					allNames.clear();
					allLat.clear();
					allLng.clear();   	
					setAllParams(nearestCb.getValue());
				}
			}
		});


		showAllButton.addListener(new Button.ClickListener() {		
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				showAll();
			}
		});

		topRated.addListener(new Property.ValueChangeListener() {		
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				actionState = "rateAction";
				String typeName = (String) topRated.getValue();
				TypeDAO tdao = daoFactory.getTypeDAO();
				Type type = tdao.getTypeByName(typeName);
				ServicesDAO sdao = daoFactory.getServicesDAO();
				RatingDAO rdao = daoFactory.getRatingDAO();
				Float maxRate = (float) 0.0;
				if(type!=null){
					List<Services> slist = sdao.getAllServicesByType(type);
					for(Services s:slist){
						List<Rating> ratings = rdao.getAllRatingByService(s);
						Float avg = (float) 0.0;
						int count=0;
						int sum =0;
						for(Rating r:ratings){
							sum = sum+ r.getRate();
							count++;
						}
						if(count>0){
							avg = (float) ((float)sum/(float)count);
							if(maxRate<avg){
								maxRate = avg;
								actualSelectedName = s.getName();
								actualLat = s.getCoordX();
								actualLng = s.getCoordY();
							}
						}
					}
					JsConnecter js = new JsConnecter((String) routeType.getValue(),
							(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,
							allNames,allLat,allLng,allSize,actionState);
					jsPanel.setContent(js);
				}
			}
		});

		topsearchButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				actionState = "searchAction";
				if(searchType.getValue().equals("service")){
					ServicesDAO sdao = daoFactory.getServicesDAO();
					List<Services> sv = sdao.getAllServicesByName(search.getValue());
					if(sv.size()>0){
						actualSelectedService = sv.get(0);
						actualSelectedName = actualSelectedService.getName();
						actualLat = actualSelectedService.getCoordX();
						actualLng = actualSelectedService.getCoordY();
						JsConnecter js = new JsConnecter((String) routeType.getValue(),
								(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,
								allNames,allLat,allLng,allSize,actionState);
						jsPanel.setContent(js);
					}else{
						Notification.show("No such service found!",
								"",
								Notification.Type.WARNING_MESSAGE);
					}

				}
				if(searchType.getValue().equals("adress")){
					JsConnecter js = new JsConnecter((String) routeType.getValue(),
							(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,
							allNames,allLat,allLng,allSize,actionState);
					jsPanel.setContent(js);
				}
				if(searchType.getValue().equals("place")){
					PlacesDAO pdao = daoFactory.getPlacesDAO();
					List<Places> plist = pdao.getPlacesByName(search.getValue());
					if(plist.size()>0){
						actualSelectedPlace = plist.get(0);
						actualSelectedName = actualSelectedPlace.getName();
						actualLat = actualSelectedPlace.getCoordX();
						actualLng = actualSelectedPlace.getCoordY();
						JsConnecter js = new JsConnecter((String) routeType.getValue(),
								(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,
								allNames,allLat,allLng,allSize,actionState);
						jsPanel.setContent(js);
					}else{
						Notification.show("No such place found!",
								"",
								Notification.Type.WARNING_MESSAGE);
					}
				}
			}
		});

	}

	private void fillSelectAreas(final ComboBox a, ComboBox b, ComboBox c){
		final DAOFactory daoFactory = DAOFactory.getInstance();
		PlacesDAO placesDAO = daoFactory.getPlacesDAO();
		TypeDAO typeDAO = daoFactory.getTypeDAO();
		List<Places> places = null;
		List<Type> types = null;
		try{	
			types = typeDAO.getAllType();
			places = placesDAO.getAllPlaces();
		} catch(RuntimeException ex) {
			LOG.error("no place, no type found");
		}
		for (Type p:types) {
			a.addItem(p.getName());
			b.addItem(p.getName());
			c.addItem(p.getName());
		}
		for (Places p:places) {
			a.addItem(p.getType());
			b.addItem(p.getType());
		}
	}

	private void createLogin(Button login){
		final Window subWindow = new Window("Login");
		subWindow.setStyleName("subWindow");
		FormLayout flayout = new FormLayout();
		flayout.setStyleName("subWindow");
		flayout.setMargin(true);
		subWindow.setContent(flayout);  
		final TextField username = new TextField("Username: ");
		username.setStyleName("textFieldColor");
		username.focus();
		flayout.addComponent(username);
		final PasswordField pass = new PasswordField("Password: ");
		pass.setStyleName("textFieldColor");
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
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				UsersDAO usersDao = daoFactory.getUsersDAO();
				try{
					Users user = usersDao.getUserByName(username.getValue());
					username.setComponentError(null);	
					String hashed = passwordHasher(pass.getValue());
					if(hashed.equals(user.getPassword())){
						subWindow.close();
						loginAdded=false;
						myUIClass.getSession().setAttribute("userName", user.getUsername());
						if(user.getUsertype().equals("user")){
							getUI().getNavigator().removeView("");
							getUI().getNavigator().addView("", new LoggedUser(user));
							getUI().getNavigator().navigateTo("");
						}
						else{
							if(user.getUsertype().equals("admin")){
								getUI().getNavigator().removeView("");
								getUI().getNavigator().addView("", new Admin(user));
								getUI().getNavigator().navigateTo("");
							}else{
								Notification.show("Unknown type of user!",
										"",
										Notification.Type.ERROR_MESSAGE);
							}
						}

					}else{
						pass.setComponentError(new UserError("Wrong password!"));
						Notification.show("Wrong password!",
								"",
								Notification.Type.ERROR_MESSAGE);
					}

				}catch(RuntimeException ex){
					username.setComponentError( new UserError("No such user with this username!"));
					pass.setComponentError(null);
					Notification.show("Wrong username!",
							"No such user found with this username",
							Notification.Type.ERROR_MESSAGE);
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void createRegister(Button register){
		final Window subWindow = new Window("Register");
		FormLayout flayout = new FormLayout();
		flayout.setStyleName("subWindow");
		flayout.setMargin(true);
		subWindow.setContent(flayout);
		final TextField fname = new TextField("First Name: ");
		fname.setStyleName("textFieldColor");
		flayout.addComponent(fname);
		final TextField lname = new TextField("Last Name: ");
		lname.setStyleName("textFieldColor");
		flayout.addComponent(lname);
		final TextField username = new TextField("Username: ");
		username.setStyleName("textFieldColor");
		flayout.addComponent(username);
		username.setRequired(true);
		username.setInvalidAllowed(false);
		username.setDescription("Username must contain only alphabetic characters and numbers");
		final PasswordField pass1 = new PasswordField("Password: ");
		pass1.setStyleName("textFieldColor");
		flayout.addComponent(pass1);
		pass1.setRequired(true);
		pass1.setInvalidAllowed(false);
		final PasswordField pass2 = new PasswordField("Re-type: ");
		pass2.setStyleName("textFieldColor");
		flayout.addComponent(pass2);
		pass2.setRequired(true);
		pass2.setInvalidAllowed(false);
		final TextField email = new TextField("Email: ");
		email.setStyleName("textFieldColor");
		flayout.addComponent(email);
		Button reg = new Button("Register");
		flayout.addComponent(reg);
		reg.setWidth("70%");
		flayout.setHeight("30em");
		flayout.setWidth("25em"); 
		subWindow.center();
		subWindow.setResizable(false);
		register.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				if(registerAdded==false){
					myUIClass.addWindow(subWindow);
					registerAdded=true;
				}
			}
		});

		subWindow.addListener(new Window.CloseListener() {			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void windowClose(CloseEvent e) {					
				registerAdded=false;
			}
		});
		reg.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
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
					Notification.show("Passwords do not match", "", Notification.Type.WARNING_MESSAGE);
					okay=false;
				}
				if((email.getValue()!=null)&&(!email.getValue().equals(""))){
					if(!email.getValue().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
							+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
						okay=false;
						email.setComponentError(new UserError("This is an incorrect email adress!"));
						Notification.show("Incorrect email format", "", Notification.Type.WARNING_MESSAGE);
					}
				}
				if(okay){
					boolean exist = false;
					for(Users u:existingUser){
						if(u.getUsername().equals(username.getValue())){
							exist = true;
						}
					}

					if(exist){
						Notification.show("Username alredy in use", "Pick up another", com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
					}
					else{

						subWindow.close();
						String hashed = passwordHasher(pass1.getValue());
						Users user = new Users("user",username.getValue(),hashed,fname.getValue(),lname.getValue(),email.getValue());
						usersDao.insertUser(user);
						Notification.show("Your registration went successfully", "", Notification.Type.HUMANIZED_MESSAGE);
						myUIClass.getSession().setAttribute("userName", user.getUsername());
						getUI().getNavigator().removeView("");
						getUI().getNavigator().addView("", new LoggedUser(user));
						getUI().getNavigator().navigateTo("");
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

	public  List<Services> intersection(List<Services> list1, List<Services> list2) {
		List<Services> list = new ArrayList<Services>();

		for (Services t : list1) {
			if(list2.contains(t)) {
				list.add(t);
			}
		}
		return list;
	}

	private void setAllParams(boolean value){
		actionState = "nearestAction";
		String nearestType = "place";
		TypeDAO tdao = daoFactory.getTypeDAO();
		List<Type> tlist = tdao.getAllType();
		for(Type t:tlist){
			if(t.getName().equals(nearestSelect.getValue())){
				nearestType = "service";
			}
		}
		if(nearestType.equals("service")){
			ServicesDAO sdao = daoFactory.getServicesDAO();
			Type tp = tdao.getTypeByName(nearestSelect.getValue().toString());
			List<Services> slist = sdao.getAllServicesByType(tp);

			if(value==false){
				allSize = slist.size();
				for(int i=0;i<slist.size();i++){
					allNames.add(slist.get(i).getName().toString());
					allLat.add(slist.get(i).getCoordX());
					allLng.add(slist.get(i).getCoordY());
				}

			}else{
				OpenhoursDAO openDao = daoFactory.getOpenhoursDAO();
				List<Openhours> ohs =  openDao.getAllOpenNow();
				List<Services> openServ = new ArrayList<Services>();
				for(int i=0;i<ohs.size();i++){
					openServ.add(ohs.get(i).getServices());
				}
				List<Integer> openlist = new ArrayList<Integer>();
				List<Integer> alllist = new ArrayList<Integer>();	
				for(Services s:openServ){
					openlist.add(s.getId());
				}
				for(Services s:slist){
					alllist.add(s.getId());
				}
				openlist.retainAll(alllist);
				allSize = openlist.size();
				for(int i=0;i<allSize;i++){
					allNames.add(sdao.getServiceById(openlist.get(i)).getName());
					allLat.add(sdao.getServiceById(openlist.get(i)).getCoordX());
					allLng.add(sdao.getServiceById(openlist.get(i)).getCoordY());
				}
			}
		}else{
			PlacesDAO pdao = daoFactory.getPlacesDAO();
			List<Places> plist = pdao.getAllPlaces();
			List<Places> sameType = new ArrayList<Places>();
			for(Places p:plist){
				if(p.getType().equals(nearestSelect.getValue())){
					sameType.add(p);
				}
			}
			if(sameType.size()>0){
				allSize = sameType.size();
				for(Places p:sameType){
					allNames.add(p.getName());
					allLat.add(p.getCoordX());
					allLng.add(p.getCoordY());
				}
			}
		}

		
		if (allSize>0){
			JsConnecter js = new JsConnecter((String) routeType.getValue(),
					(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,allNames,
					allLat,allLng,allSize,actionState);
			jsPanel.setContent(js);
		}else{
			Notification.show("There is no one of that type. ",
					"",
					Notification.Type.WARNING_MESSAGE);
		}
		allSize = -1;
	}

	private void showAll(){
		if((showAll.getValue()==null)||(showAll.getValue().equals(""))){
			Notification.show("Please select a service or a place type! ",
					"",
					Notification.Type.WARNING_MESSAGE);
		}else{
			String allType="place";
			boolean show = false;
			actionState = "placeListAction";
			allNames.clear();
			allLat.clear();
			allLng.clear();
			TypeDAO tdao = daoFactory.getTypeDAO();
			List<Type> typeList = tdao.getAllType();
			for(Type t:typeList){
				if(t.getName().equals(showAll.getValue())){
					allType = "service";
					actionState = "serviceListAction";
				}
			}
			if(allType.equals("service")){
				Type tp = tdao.getTypeByName(showAll.getValue().toString());
				ServicesDAO sdao = daoFactory.getServicesDAO();
				List<Services> slist = sdao.getAllServicesByType(tp);
				if(slist.size()>0){
					if(showAllCb.getValue()==false){
						allSize = slist.size();
						for(int i=0;i<slist.size();i++){
							allNames.add(slist.get(i).getName().toString());
							allLat.add(slist.get(i).getCoordX());
							allLng.add(slist.get(i).getCoordY());
						}
						show = true;
					}else{
						OpenhoursDAO openDao = daoFactory.getOpenhoursDAO();
						List<Openhours> ohs =  openDao.getAllOpenNow();
						if(ohs.size()>0){
							List<Services> openServ = new ArrayList<Services>();
							for(int i=0;i<ohs.size();i++){
								openServ.add(ohs.get(i).getServices());
							}
							List<Integer> openlist = new ArrayList<Integer>();
							List<Integer> alllist = new ArrayList<Integer>();	
							for(Services s:openServ){
								openlist.add(s.getId());
							}
							for(Services s:slist){
								alllist.add(s.getId());
							}
							openlist.retainAll(alllist);
							allSize = openlist.size();
							for(int i=0;i<allSize;i++){
								allNames.add(sdao.getServiceById(openlist.get(i)).getName());
								allLat.add(sdao.getServiceById(openlist.get(i)).getCoordX());
								allLng.add(sdao.getServiceById(openlist.get(i)).getCoordY());
							}
							show = true;
						}
					}
					if(show){
				
						if (allSize>0){
							JsConnecter js = new JsConnecter((String) routeType.getValue(),
									(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,allNames,
									allLat,allLng,allSize,actionState);
							jsPanel.setContent(js);
						}else{
							Notification.show("There is no one of that type. ",
									"",
									Notification.Type.WARNING_MESSAGE);
						}
						allSize = -1;
					}
				}
			}else{
				PlacesDAO pdao = daoFactory.getPlacesDAO();
				List<Places> plist = pdao.getAllPlaces();
				List<Places> sameType = new ArrayList<Places>();
				for(Places p:plist){
					if(p.getType().equals(showAll.getValue())){
						sameType.add(p);
					}
				}
				if(sameType.size()>0){
					allSize = sameType.size();
					for(Places p:sameType){
						allNames.add(p.getName());
						allLat.add(p.getCoordX());
						allLng.add(p.getCoordY());
					}
					if (allSize>0){
						JsConnecter js = new JsConnecter((String) routeType.getValue(),
								(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,allNames,
								allLat,allLng,allSize,actionState);
						jsPanel.setContent(js);
					}else{
						Notification.show("There is no one of that type. ",
								"",
								Notification.Type.WARNING_MESSAGE);
					}
					
					allSize = -1;

				}
			}
		}
	}


}
