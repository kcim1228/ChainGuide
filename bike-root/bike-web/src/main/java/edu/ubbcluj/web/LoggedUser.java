package edu.ubbcluj.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

import edu.ubbcluj.backend.model.Messages;
import edu.ubbcluj.backend.model.Openhours;
import edu.ubbcluj.backend.model.Places;
import edu.ubbcluj.backend.model.Rating;
import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Type;
import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.DAOFactory;
import edu.ubbcluj.backend.repository.MessagesDAO;
import edu.ubbcluj.backend.repository.OpenhoursDAO;
import edu.ubbcluj.backend.repository.PlacesDAO;
import edu.ubbcluj.backend.repository.RatingDAO;
import edu.ubbcluj.backend.repository.ServicesDAO;
import edu.ubbcluj.backend.repository.TypeDAO;
import edu.ubbcluj.backend.repository.UsersDAO;


public class LoggedUser extends VerticalLayout implements View {
	
	
	 boolean rateAdded;
	 boolean registerAdded;
	 UI myUIClass;
	 
	// public static final String NAME = "unloggedUser";
	private GridLayout topgrid = new GridLayout(4, 1);
	private GridLayout logingrid = new GridLayout(2,1);
	private GridLayout maingrid = new GridLayout(2,1);
	private GridLayout actiongrid = new GridLayout(1,14);
	private TextArea search = new TextArea("Search: ");
	private TextArea aPoint = new TextArea("A:");
	private TextArea bPoint = new TextArea("B:");
	private TextArea startPointForNearest = new TextArea("Select a Start point: ");
	private Button logout = new Button("Logout");

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
	private VerticalLayout mapLayout = new VerticalLayout();
	private ComboBox searchType = new ComboBox("serch for");
	private UserMessageState mState = new UserMessageState();
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
	private Button rate = new Button("Service Rating");
	private int rowIndex;
	private Users thisUser;
	private Button message = new Button("Contact");
	private boolean messageAdded = false;
	
	
	public LoggedUser(UI UIClass,Users us){
		myUIClass = UIClass;
		thisUser = us;
		
		//this.setCaption("alma");
	}

	@SuppressWarnings("deprecation")
	public void enter(ViewChangeEvent event) {
		System.out.println("luser: "+this.getSession().getAttribute("userType"));
		
		
		if(this.getSession().getAttribute("userType").equals("none")){
			getUI().getNavigator().navigateTo("unloggedUser");
		}
		if(this.getSession().getAttribute("userType").equals("admin")){
			getUI().getNavigator().navigateTo("admin");
		}
		
		//a javascipt vegrehajtasahoz szukseges panel
		
		jsPanel.setStyleName("notVisible");
		final DAOFactory daoFactory = DAOFactory.getInstance();
		search.setValue("strada horea, cluj napoca");	
		routeType.addItem("bicycle");
		routeType.addItem("shortest");
		routeType.setValue("bicycle");
		searchType.addItem("adress");
		searchType.addItem("service");
		searchType.addItem("place");
		searchType.setValue("adress");
		
		
		getDirection.addClickListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				actionState = "routeAction";
		    	JsConnecter js = new JsConnecter((String) routeType.getValue(),
		    			(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,allNames,
		    			allLat,allLng,allSize,actionState);
		    		jsPanel.setContent(js);
				
			}
		});
		
		getNearest.addClickListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				actionState = "nearestAction";
				System.out.println("nearestselect value: "+nearestSelect.getValue());
				System.out.println("text= "+startPointForNearest.getValue());
				if((startPointForNearest.getValue().equals(""))||(nearestSelect.getValue().equals(null))){
					Notification.show("Please select a start point and a service-type!",
			                  "",
			                  Notification.Type.WARNING_MESSAGE);
				}else{
					allNames.clear();
			    	allLat.clear();
			    	allLng.clear();
			    	System.out.println(nearestSelect.getValue());
			    	ServicesDAO sdao = daoFactory.getServicesDAO();
			    	TypeDAO tdao = daoFactory.getTypeDAO();
			    	Type tp = tdao.getTypeByName(nearestSelect.getValue().toString());
			    	List<Services> slist = sdao.getAllServicesByType(tp);
			    	allSize = slist.size();
			    	for(int i=0;i<slist.size();i++){
			    		allNames.add(slist.get(i).getName().toString());
			    		allLat.add(slist.get(i).getCoordX());
			    		allLng.add(slist.get(i).getCoordY());
			    		System.out.println(slist.get(i));
			    	}
					JsConnecter js = new JsConnecter((String) routeType.getValue(),
			    			(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,allNames,
			    			allLat,allLng,allSize,actionState);
			    		jsPanel.setContent(js);
			    	allSize = -1;
				}
				
			}
		});
		

		showAll.addListener( new Property.ValueChangeListener() {
		    public void valueChange(ValueChangeEvent event) {
		    	actionState = "listAction";
		    	allNames.clear();
		    	allLat.clear();
		    	allLng.clear();
		    	TypeDAO tdao = daoFactory.getTypeDAO();
		    	Type tp = tdao.getTypeByName(showAll.getValue().toString());
		    	ServicesDAO sdao = daoFactory.getServicesDAO();
		    	List<Services> slist = sdao.getAllServicesByType(tp);
		    	if(showAllCb.getValue()==false){
			    	System.out.println(showAll.getValue());
			    	allSize = slist.size();
			    	for(int i=0;i<slist.size();i++){
			    		allNames.add(slist.get(i).getName().toString());
			    		allLat.add(slist.get(i).getCoordX());
			    		allLng.add(slist.get(i).getCoordY());
			    		System.out.println(slist.get(i));
			    	}
			    	JsConnecter js = new JsConnecter((String) routeType.getValue(),
			    			(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,allNames,
			    			allLat,allLng,allSize,actionState);
			    		jsPanel.setContent(js);
			    		allSize = -1;
		    	}else{
		    		OpenhoursDAO openDao = daoFactory.getOpenhoursDAO();
		    		List<Openhours> ohs =  openDao.getAllOpenNow();
		    		List<Services> openServ = new ArrayList<Services>();
		    		for(int i=0;i<ohs.size();i++){
		    			openServ.add(ohs.get(i).getServices());
		    		}
		    		//slist.retainAll(openServ);//intersection(slist, openServ);
		    		
		    		System.out.println("slist: "+slist.toString());
		    		System.out.println("openserv"+openServ.toString());
		    		List<Integer> openlist = new ArrayList();
		    		List<Integer> alllist = new ArrayList();	
		    		for(Services s:openServ){
		    			openlist.add(s.getId());
		    		}
		    		for(Services s:slist){
		    			alllist.add(s.getId());
		    		}
		    		System.out.println("open: "+ openlist.toString());
		    		System.out.println("all: "+ alllist.toString());
		    		
		    		openlist.retainAll(alllist);
		    		System.out.println("utana: "+openlist);
		    		
		    		allSize = openlist.size();
		    		for(int i=0;i<allSize;i++){
			    		allNames.add(sdao.getServiceById(openlist.get(i)).getName());
			    		allLat.add(sdao.getServiceById(openlist.get(i)).getCoordX());
			    		allLng.add(sdao.getServiceById(openlist.get(i)).getCoordY());
			    	}
		    		JsConnecter js = new JsConnecter((String) routeType.getValue(),
			    			(String)searchType.getValue(),actualSelectedName,actualLat,actualLng,allNames,
			    			allLat,allLng,allSize,actionState);
			    		jsPanel.setContent(js);
			    		allSize = -1;
		    	}

		    	
		    	}
		    }
		       );
		
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
			    		
						System.out.println(actualSelectedService.toString());
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
		search.setHeight("1.7em");
		topgrid.setWidth("100%");
		topgrid.setHeight("10%");
		search.setRows(1);
		search.setDescription("Here you can serch for places, services, etc.");
		search.setId("searchTextField");
		maingrid.setWidth("100%");
		maingrid.setHeight("70%");

		logingrid.addComponent(logout,1,0);
		topgrid.addComponent(search,0,0);
		topgrid.addComponent(topsearchButton,2,0);
		topgrid.addComponent(searchType,1,0);
		topgrid.addComponent(logingrid, 3,0);
		topgrid.setComponentAlignment(logingrid,  Alignment.TOP_RIGHT);
		topgrid.setComponentAlignment(topsearchButton,  Alignment.TOP_LEFT);
		topgrid.setComponentAlignment(searchType,  Alignment.BOTTOM_LEFT);
		
		logingrid.setComponentAlignment(logout, Alignment.TOP_RIGHT);
		logingrid.setWidth("100%");
		logingrid.setColumnExpandRatio(0, 5);
		logingrid.setColumnExpandRatio(1, 1);
		topgrid.setColumnExpandRatio(0, 6);
		topgrid.setColumnExpandRatio(1, 1);
		topgrid.setColumnExpandRatio(2, 1);
		topgrid.setColumnExpandRatio(3, 5);
		
		actiongrid.addComponent(direction,0,0);
		actiongrid.setWidth("100%");
		actiongrid.setHeight("100%");
		aPoint.setRows(1);
		aPoint.setId("aPoint");
		bPoint.setId("bPoint");
		bPoint.setRows(1);
		startPointForNearest.setRows(1);
		aPoint.setWidth("90%");
		aPoint.setRows(2);
		bPoint.setRows(2);
		startPointForNearest.setRows(2);
		bPoint.setWidth("90%");
		aPoint.setDescription("Type or choose from the map a Start point for your trip .");
		bPoint.setDescription("Type or choose from the map an End point for your trip .");
		actiongrid.addComponent(aPoint,0,1);
		actiongrid.addComponent(bPoint,0,2);
		actiongrid.addComponent(routeType,0,3);
		actiongrid.addComponent(getDirection,0,4);
		actiongrid.addComponent(nearestSelect,0,5);
		actiongrid.addComponent(nearestCb,0,6);
		actiongrid.addComponent(startPointForNearest,0,7);
		actiongrid.addComponent(getNearest,0,8);
		actiongrid.addComponent(showAll,0,9);
		actiongrid.addComponent(showAllCb,0,10);
		actiongrid.addComponent(jsPanel,0,11);
		actiongrid.addComponent(rate,0,12);
		actiongrid.addComponent(message,0,13);
		startPointForNearest.setId("nearestStart");
		
		
		actiongrid.setComponentAlignment(aPoint, Alignment.MIDDLE_LEFT);
		actiongrid.setComponentAlignment(bPoint, Alignment.MIDDLE_LEFT);
		actiongrid.setComponentAlignment(getDirection, Alignment.MIDDLE_CENTER);
		actiongrid.setComponentAlignment(nearestSelect, Alignment.MIDDLE_LEFT);
		actiongrid.setComponentAlignment(getNearest, Alignment.MIDDLE_CENTER);
		
		actiongrid.setRowExpandRatio(4, 2);
		actiongrid.setMargin(true);
		
		
		mapLayout.setSizeFull();
		mapPanel.setId("map");
		mapPanel.setStyleName("mapPanel");
		narrative.setId("route-results");
		mapPanel.setSizeFull();	
		
		rate.addClickListener(new Button.ClickListener() {			
			public void buttonClick(ClickEvent event) {
				createRate();
			}
		});
	
		message.addClickListener(new Button.ClickListener() {			
			public void buttonClick(ClickEvent event) {
				createMessage();
				
			}
		});
		
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
		logout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				myUIClass.getSession().setAttribute("userType", "none");
				myUIClass.getNavigator().removeView("loggedUser");
				getUI().getNavigator().addView("unloggedUser", new UnLoggedUser(myUIClass));
				myUIClass.getSession().setAttribute("userType", "none");
				getUI().getNavigator().navigateTo("unloggedUser");
				 
		    }
		});

		
		
		MapLoader mp = new MapLoader();
		mapPanel.setContent(mp);
		
		
		
		
	}

	private void createRate(){
		final List<Integer> ids = new ArrayList<Integer>();
		final Window subWindow = new Window("Ratings");
        subWindow.center();
        subWindow.setResizable(false);
        subWindow.setWidth("30em");
        subWindow.setHeight("30em");
       FormLayout grid = new FormLayout();
       subWindow.setContent(grid);
       final TextField searching = new TextField();
       searching.setInputPrompt("Search by service name");
       grid.addComponent(searching);
       Button listButton = new Button("list");
       grid.addComponent(listButton);
       
       final Table table = new Table();
      // table.setSelectable(true);
       table.setWidth("95%");
       table.setPageLength(7);
       table.setColumnWidth("Average", 20);
       
       table.addContainerProperty("Service", String.class, null);
       table.addContainerProperty("Average",  String.class, null);
       table.addContainerProperty("Rate 	",  ComboBox.class, null);
       final DAOFactory daoFactory = DAOFactory.getInstance();
       
       grid.addComponent(table);
       listButton.addClickListener(new Button.ClickListener() {
		public void buttonClick(ClickEvent event) {
			ServicesDAO sdao = daoFactory.getServicesDAO();
			RatingDAO rdao = daoFactory.getRatingDAO();
			List<Services> servlist = sdao.getAllServicesByName(searching.getValue());
			rowIndex = 1;
			
			for(Services s:servlist){
				List<Rating> ratings = rdao.getAllRatingByService(s);
				System.out.println(ratings.toString());
				Float avg = (float) 0.0;
				int count=0;
				int sum =0;
				for(Rating r:ratings){
					sum = sum+ r.getRate();
					count++;
				}
				if(count>0){
					System.out.println("sum "+sum+" c: "+count);
					avg = (float) ((float)sum/(float)count);
					System.out.println("avg: "+avg);
				}
				
				ComboBox cb = new ComboBox();
				rowIndex++;
				cb.addItem("-");
				cb.addItem("1");
				cb.addItem("2");
				cb.addItem("3");
				cb.addItem("4");
				cb.addItem("5");
				
				cb.setValue("-");
				cb.setWidth("5em");
				String average = Float.toString(avg);
				table.addItem(new Object[]{s.getName(),average,cb},rowIndex);
				ids.add(s.getId());
			}
			
		}
	});
       
       
       Button rateButton = new Button("Done");
       grid.addComponent(rateButton);
       final ServicesDAO servdao = daoFactory.getServicesDAO();
       final RatingDAO rdao = daoFactory.getRatingDAO();
       rateButton.addClickListener(new Button.ClickListener() {
		public void buttonClick(ClickEvent event) {
			if(rowIndex>1){
				for(int i=2;i<=rowIndex;i++){
					ComboBox cb = (ComboBox) table.getItem(i).getItemProperty("Rate 	").getValue();
					String value = cb.getValue().toString();
					if (value!="-"){
						Services serv = servdao.getServiceById(ids.get(i-2 ));
						//ids.remove(0);
						Rating r = new Rating(serv, thisUser, Integer.parseInt(value));
					
						List<Rating> rates = rdao.getAllRatingByService(serv);
						boolean alredyRated = false;
						if(rates!=null){
							for(int j=0;j<rates.size();j++){
								int id = rates.get(j).getUsers().getId();
								if(id==thisUser.getId()){
									alredyRated=true;
								}
							}
						}
						if(alredyRated==false){
							rdao.insertRating(r);
							System.out.println("insert: "+r);
						}
						else{
							System.out.println("service: "+serv);
							Rating oldrate = rdao.getRatingByUserAndService(thisUser, serv);
							oldrate.setRate(Integer.parseInt(value));
							rdao.updateRating(oldrate);
							System.out.println("update: "+oldrate);
						}
					}
					
				}
			}
			subWindow.close();
			rateAdded=false;
			
		}
	});

        
        subWindow.addCloseListener(new Window.CloseListener() {
			private static final long serialVersionUID = 1L;

			public void windowClose(CloseEvent e) {					
				subWindow.close();
				rateAdded=false;
			}
		});
        
    	if(rateAdded==false){
    		//addWindow(subWindow);
    		rateAdded=true;
    		myUIClass.addWindow(subWindow);
    		
    	}
		
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
	private void createMessage(){
		final Window subWindow = new Window("Contact");
        subWindow.center();
        subWindow.setResizable(false);
        subWindow.setWidth("30em");
        subWindow.setHeight("30em");
       FormLayout form = new FormLayout();
       subWindow.setContent(form);
       
       if(messageAdded==false){
   		//addWindow(subWindow);
	   		messageAdded=true;
	   		myUIClass.addWindow(subWindow);  		
   		}
       subWindow.addCloseListener(new Window.CloseListener() {
			private static final long serialVersionUID = 1L;
			public void windowClose(CloseEvent e) {					
				subWindow.close();
				messageAdded=false;
			}
		});
       Button newMessage = new Button("Create new message");
       form.addComponent(newMessage);
       newMessage.addClickListener(new Button.ClickListener() {		
		public void buttonClick(ClickEvent event) {
			final Window newMessWindow = new Window("New message");
			newMessWindow.center();
			newMessWindow.setWidth("35em");
			newMessWindow.setHeight("30em");
			myUIClass.addWindow(newMessWindow);
			newMessWindow.addCloseListener(new Window.CloseListener() {
				private static final long serialVersionUID = 1L;
				public void windowClose(CloseEvent e) {					
					newMessWindow.close();
				}
			});
			FormLayout newMessForm = new FormLayout();
			newMessWindow.setContent(newMessForm);
			Label to = new Label("To: Admins");
			newMessForm.addComponent(to);
			final TextField body = new TextField("Body: ");
			newMessForm.addComponent(body);
			body.setHeight("10em");
			body.setWidth("90%");
			Button send = new Button("SEND");
			newMessForm.addComponent(send);
			send.addClickListener(new Button.ClickListener() {				
				public void buttonClick(ClickEvent event) {
					newMessWindow.close();
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					DAOFactory daoFactory = DAOFactory.getInstance();
					UsersDAO udao = daoFactory.getUsersDAO();
					MessagesDAO mdao = daoFactory.getMessagesDAO();
					List<Users> adminList = udao.getUsersByType("admin");
					for(Users u:adminList){
						Messages mess = new Messages(u,thisUser,body.getValue(),date,0);
						System.out.println(mess);
						mdao.insertMessage(mess);
					}
				}
			});
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
        System.out.println("lista:"+list);
        return list;
    }
	

}
