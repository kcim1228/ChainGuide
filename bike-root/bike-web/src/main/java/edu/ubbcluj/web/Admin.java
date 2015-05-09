package edu.ubbcluj.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;

import edu.ubbcluj.backend.model.Messages;
import edu.ubbcluj.backend.model.Openhours;
import edu.ubbcluj.backend.model.Places;
import edu.ubbcluj.backend.model.Rating;
import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Servicetype;
import edu.ubbcluj.backend.model.Type;
import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.DAOFactory;
import edu.ubbcluj.backend.repository.MessagesDAO;
import edu.ubbcluj.backend.repository.OpenhoursDAO;
import edu.ubbcluj.backend.repository.PlacesDAO;
import edu.ubbcluj.backend.repository.RatingDAO;
import edu.ubbcluj.backend.repository.ServicesDAO;
import edu.ubbcluj.backend.repository.ServicetypeDAO;
import edu.ubbcluj.backend.repository.TypeDAO;
import edu.ubbcluj.backend.repository.UsersDAO;

public class Admin extends VerticalLayout implements View{
	
	boolean saveAdded;
	boolean updateAdded;
	 boolean registerAdded;
	 boolean messageAdded;
	 boolean deleteAdded = false;
	 UI myUIClass;
	 
	// public static final String NAME = "unloggedUser";
	private GridLayout topgrid = new GridLayout(4, 1);
	private GridLayout logingrid = new GridLayout(2,1);
	private GridLayout maingrid = new GridLayout(2,1);
	private GridLayout actiongrid = new GridLayout(1,11);
	private TextArea search = new TextArea("Search: ");
	private Button logout = new Button("Logout");

	private Button save = new Button("SAVE / NEW");
	private Button delete = new Button("DELETE");
	private Button update = new Button("UPDATE");
	private TextField lat = new TextField("lat");
	private TextField lng = new TextField("lng");
	private TextField typeForSearch = new TextField();
	private Float actualLat;
	private Float actualLng;
	private String actionType = "none";
	 private int rowIndex ;
	 private List<Integer> days ;
	 private List<String> closeHours;
	 private List<String> openHours;
	 private ComboBox searchType  = new ComboBox();
	 private Services actualSelectedService;
	 private Places actualSelectedPlace;
	 private String actualSelectedName="none";
	 Panel jsPanel = new Panel();
	private Button topsearchButton = new Button("GO");
	private Panel mapPanel = new Panel();
	private VerticalLayout mapLayout = new VerticalLayout();
	 final DAOFactory daoFactory = DAOFactory.getInstance();
	 private int placeStamp=0;
	 private int serviceStamp=0;
	 private Button message = new Button("Messages");
	 private Users thisAdmin;
	
	public Admin(UI ui, Users u){
		myUIClass=ui;
		thisAdmin = u;
	}

	public void enter(ViewChangeEvent event) {
		//System.out.println("admin: "+this.getSession().getAttribute("userType"));
		
		if(this.getSession().getAttribute("userType").equals("user")){
			getUI().getNavigator().navigateTo("loggedUser");
		}
		
		if(this.getSession().getAttribute("userType").equals("none")){
			getUI().getNavigator().navigateTo("unloggedUser");
		}
		
		lat.setId("lat");
		lng.setId("lng");
	
		final DAOFactory daoFactory = DAOFactory.getInstance();
		jsPanel.setStyleName("notVisible");
		
		topsearchButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				actionType = "searchAction";
				System.out.println("kattintottunk");
				if(searchType.getValue().equals("service")){
					serviceStamp = placeStamp+1;
					ServicesDAO sdao = daoFactory.getServicesDAO();
		    		List<Services> sv = sdao.getAllServicesByName(search.getValue());
		    		if(sv.size()>0){
		    			actualSelectedService = sv.get(0);
			    		actualSelectedName = actualSelectedService.getName();
			    		actualLat = actualSelectedService.getCoordX();
			    		actualLng = actualSelectedService.getCoordY();
			    		
						System.out.println(actualSelectedService.toString());
						AdminJsConnecter js = new AdminJsConnecter((String)searchType.getValue(),actualSelectedName,actualLat,actualLng,actionType);
			    		jsPanel.setContent(js);
		    		}else{
    					Notification.show("No such service found!",
  			                  "",
  			                  Notification.Type.WARNING_MESSAGE);
		    		}
		    		
	    		}
				if(searchType.getValue().equals("adress")){
					AdminJsConnecter js = new AdminJsConnecter((String)searchType.getValue(),actualSelectedName,actualLat,actualLng,actionType);
		    		jsPanel.setContent(js);
				}
				if(searchType.getValue().equals("place")){
					placeStamp = serviceStamp+1;
					PlacesDAO pdao = daoFactory.getPlacesDAO();
					List<Places> plist = pdao.getPlacesByName(search.getValue());
					if(plist.size()>0){
						actualSelectedPlace = plist.get(0);
						actualSelectedName = actualSelectedPlace.getName();
						actualLat = actualSelectedPlace.getCoordX();
						actualLng = actualSelectedPlace.getCoordY();
						
						AdminJsConnecter js = new AdminJsConnecter((String)searchType.getValue(),actualSelectedName,actualLat,actualLng,actionType);
			    		jsPanel.setContent(js);
					}else{
						Notification.show("No such place found!",
	  			                  "",
	  			                  Notification.Type.WARNING_MESSAGE);
					}
				}
		    }
		});
		
		topsearchButton.setId("topsearchButton");
		searchType.addItem("adress");
		searchType.addItem("service");
		searchType.addItem("place");
		searchType.setValue("adress");
		topgrid.setMargin(true);
		search.setWidth("70%");
		search.setHeight("1.7em");
		search.setValue("strada horea, cluj napoca");
		topgrid.setWidth("100%");
		topgrid.setHeight("10%");
		search.setRows(1);
		search.setDescription("Here you can serch for places, services, etc.");
		search.setId("searchTextField");
		maingrid.setWidth("100%");
		maingrid.setHeight("70%");

		logingrid.addComponent(logout,1,0);

		topgrid.addComponent(search,0,0);
		topgrid.addComponent(searchType,1,0);
		topgrid.addComponent(topsearchButton,2,0);
		topgrid.addComponent(logingrid, 3,0);
		topgrid.setComponentAlignment(logingrid,  Alignment.TOP_RIGHT);
		topgrid.setComponentAlignment(topsearchButton,  Alignment.TOP_LEFT);		
		logingrid.setComponentAlignment(logout, Alignment.TOP_RIGHT);
		logingrid.setWidth("100%");
		logingrid.setColumnExpandRatio(0, 5);
		logingrid.setColumnExpandRatio(1, 1);
		topgrid.setColumnExpandRatio(0, 6);
		topgrid.setColumnExpandRatio(1, 1);
		topgrid.setColumnExpandRatio(2, 1);
		topgrid.setColumnExpandRatio(3, 5);
		actiongrid.setWidth("100%");
		actiongrid.setHeight("100%");
		actiongrid.addComponent(save,0,1);
		actiongrid.addComponent(delete,0,2);
		actiongrid.addComponent(update,0,3);
		actiongrid.addComponent(lat,0,4);
		actiongrid.addComponent(lng,0,5);
		actiongrid.addComponent(typeForSearch,0,7);
		actiongrid.addComponent(jsPanel,0,6);
		typeForSearch.setValue("adress");
		typeForSearch.setId("typeForSearch");
		actiongrid.addComponent(message,0,8);
	
		actiongrid.setComponentAlignment(save, Alignment.MIDDLE_CENTER);
		actiongrid.setComponentAlignment(delete, Alignment.MIDDLE_CENTER);
		actiongrid.setComponentAlignment(update, Alignment.MIDDLE_CENTER);		
		actiongrid.setRowExpandRatio(4, 2);
		actiongrid.setMargin(true);
		update.setId("update");
		
		//Label map = new Label("a map itt lesz");
		
		mapLayout.setSizeFull();
		mapPanel.setId("map");
		mapPanel.setStyleName("mapPanel");
		mapPanel.setSizeFull();							
		mapLayout.addComponent(mapPanel);	
		this.addComponent(new Label("ChainGuide"));
		this.addComponent(topgrid);
		this.addComponent(maingrid);		
		maingrid.addComponent(actiongrid,1,0);
		maingrid.addComponent(mapLayout, 0, 0);
		maingrid.setColumnExpandRatio(0, 4);
		maingrid.setColumnExpandRatio(1, 1);
		
		//PointLocalizer alert = new PointLocalizer();
		//mapLayout.addComponent(alert);
		
		createLogout(logout);	
		createSave(save);
		createUpdate(update);
		createDelete(delete);
		message.addClickListener(new Button.ClickListener() {			
			public void buttonClick(ClickEvent event) {
				createMessage();
				
			}
		});
		
		AdminMapLoader mp = new AdminMapLoader();
		mapPanel.setContent(mp);
		
	}

	private void createLogout(Button logout2) {
		logout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				myUIClass.getSession().setAttribute("userType", "none");
				getUI().getNavigator().navigateTo("unloggedUser");
				 
		    }
		});
		
	}
	
	private void createSave(Button save){
	  	final Window subWindow = new Window("Save location as: ");
        FormLayout flayout = new FormLayout();
        //flayout.setMargin(true);
        subWindow.setContent(flayout);  
        final Label locType = new Label("Location-type: ");
        flayout.addComponent(locType);
        final ComboBox type = new ComboBox();
        type.addItem("Service-type");
        type.addItem("Place-type");
        flayout.addComponent(type);
        type.setId("locType");
        type.setValue("Service-type");
        
        Button next = new Button("NEXT");
        next.setWidth("90%");
        flayout.addComponent(next);
        flayout.setHeight("10em");
        flayout.setWidth("20em"); 
        subWindow.center();
        subWindow.setResizable(false);

        
        save.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
		    	if(saveAdded==false){
		    		//addWindow(subWindow);
		    		saveAdded=true;
		    		myUIClass.addWindow(subWindow);
		    	}
		    }
		});
        
        subWindow.addCloseListener(new Window.CloseListener() {
			private static final long serialVersionUID = 1L;

			public void windowClose(CloseEvent e) {					
				subWindow.close();
				saveAdded=false;
			}
		});
        
        next.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				subWindow.close();
		    	if(type.getValue().equals("Service-type")){
		    		//ha egy uj intezmenyt szeretnenk felvinni akkor:
		    		
		    		final Window serviceWindow = new Window("Save service-type as: ");
		            FormLayout servicelayout = new FormLayout();
		            //flayout.setMargin(true);
		            serviceWindow.setContent(servicelayout);  
		            final TextField coordX = new TextField("Lat coordinate: ");
		            servicelayout.addComponent(coordX);
		            
		            
		    		coordX.setValue(lat.getValue());
		            
		            coordX.setValue(coordX.getValue());
		            final TextField coordY = new TextField("Lng coordinate: ");
		            servicelayout.addComponent(coordY);
		            coordY.setValue(lng.getValue());
		            
		            final OptionGroup serviceTypes = new OptionGroup("Service-types: ");
		    		final TypeDAO typeDAO = daoFactory.getTypeDAO();
		    		List<Type> types = null;
		    		try{	
		    			types = typeDAO.getAllType();
		    		} catch(RuntimeException ex) {
		    			System.out.println(ex.getMessage());
		    		}
		    		for (Type p:types) {
		    			serviceTypes.addItem(p.getName());
		    		}
		            serviceTypes.setMultiSelect(true);
		            servicelayout.addComponent(serviceTypes);
		            final TextField name = new TextField("Institusion name: ");
		            servicelayout.addComponent(name);
		            final TextField adressField = new TextField("Adress: ");
		      
		            servicelayout.addComponent(adressField);
		            
		            final TextField tel = new TextField("Telephone: ");
		            servicelayout.addComponent(tel);
		            
		            Button openhour = new Button("Set Time-Table");
		            servicelayout.addComponent(openhour);
		            
		            Button saveservcice = new Button("SAVE");
		            saveservcice.setWidth("90%");
		            servicelayout.addComponent(saveservcice);
		            servicelayout.setHeight("30em");
		            servicelayout.setWidth("32em"); 
		            serviceWindow.center();
		            serviceWindow.setResizable(false);
		            
		            
		            openhour.addClickListener(new Button.ClickListener() {
		    			private static final long serialVersionUID = 1L;
		    			public void buttonClick(ClickEvent event) {
		    				final Window openhourWindow = new Window("Save service-type as: ");
				            FormLayout openhourlayout = new FormLayout();
				            //flayout.setMargin(true);
				            openhourWindow.setContent(openhourlayout);
				            openhourlayout.setWidth("35em");
				            //openhourlayout.setHeight("42em");
				            
				            days = new ArrayList<Integer>();
				            closeHours =new ArrayList<String>();
				            openHours = new ArrayList<String>();
				            
				            final ComboBox day = new ComboBox("Day: ");
				            openhourlayout.addComponent(day);
				            day.addItem("Monday");
				            day.addItem("Tuesday");
				            day.addItem("Wednesday");
				            day.addItem("Thursday");
				            day.addItem("Friday");
				            day.addItem("Saturday");
				            day.addItem("Sunday");
				             
				            
				            final TextField open  = new TextField("Opens:");
				            openhourlayout.addComponent(open);
				            open.setInputPrompt("09");
				            final TextField close  = new TextField("Closes:");
				            close.setInputPrompt("12");
				            openhourlayout.addComponent(close);
				            Button add = new Button("add");
				            openhourlayout.addComponent(add);
				            Button remove = new Button("remove");
				            openhourlayout.addComponent(remove);
				            
				            final Table table = new Table("Time-Table");
				            table.setSelectable(true);
				            table.setWidth("90%");
				            table.setPageLength(7);
				            table.addContainerProperty("Day", String.class, null);
				            table.addContainerProperty("Opens",  String.class, null);
				            table.addContainerProperty("Closes",  String.class, null);
				            openhourlayout.addComponent(table);
				            rowIndex = 2;
				            remove.addClickListener(new Button.ClickListener() {								
								public void buttonClick(ClickEvent event) {
									
									String open=  (String) table.getItem(table.getValue()).getItemProperty("Opens").getValue();
									openHours.remove(open);
									String close = (String) table.getItem(table.getValue()).getItemProperty("Closes").getValue();
									closeHours.remove(close);
									String day = (String) table.getItem(table.getValue()).getItemProperty("Day").getValue();
									days.remove((Integer)(daysToInt(day)));
									table.removeItem(table.getValue());
								
								}
							});
				            
				            
				            add.addClickListener(new Button.ClickListener() {
				    			private static final long serialVersionUID = 1L;
				    			public void buttonClick(ClickEvent event) {
				    				if((day.getValue()==null)||(open.getValue()=="")||(close.getValue()=="")){
				    					Notification.show("Empty areas in the form!",
				    			                  "You should fill in all of them.",
				    			                  Notification.Type.ERROR_MESSAGE);
				    				}else{
				    					String o =open.getValue();
				    					String c = close.getValue();
				    					
				    					if((timeFormat(o))&&(timeFormat(c))){
				    						days.add((Integer)daysToInt((String) day.getValue()));
				    						openHours.add(open.getValue());
				    						closeHours.add(close.getValue());
				    						
				    						table.addItem(new Object[]{day.getValue(),open.getValue(),close.getValue()},rowIndex);
					    					rowIndex++;
				    						
				    					}else{
				    						Notification.show("Wrong time-format!",
					    			                  "Use xx format, like 16 or 08 ",
					    			                  Notification.Type.ERROR_MESSAGE);
				    					}
				    									    					
				    					
				    				}
				    				
				    		    }
				    		});
				            
				            
				            
				            Button savetable = new Button("OK");
				            openhourlayout.addComponent(savetable);
				            
				            myUIClass.addWindow(openhourWindow);
				            openhourWindow.center();
				            openhourWindow.setResizable(false);
				            
				            savetable.addClickListener(new Button.ClickListener() {
				    			private static final long serialVersionUID = 1L;
				    			public void buttonClick(ClickEvent event) {
		
				    				
				    				System.out.println(days.toString());
				    				System.out.println(closeHours.toString());
				    				System.out.println(openHours.toString());
				    			
				    		    	openhourWindow.close();
				    		    	
				    		    }
				    		});
				            
				            openhourWindow.addCloseListener(new Window.CloseListener() {
				    			private static final long serialVersionUID = 1L;

				    			public void windowClose(CloseEvent e) {					
				    				openhourWindow.close();
				    			}
				    		});
		    		    }
		    		});
		            
		            
		            serviceWindow.addCloseListener(new Window.CloseListener() {
		    			private static final long serialVersionUID = 1L;

		    			public void windowClose(CloseEvent e) {					
		    				serviceWindow.close();
		    				saveAdded = false;
		    			}
		    		});
		    		
		            myUIClass.addWindow(serviceWindow);
		            saveservcice.addClickListener(new Button.ClickListener() {
		    			private static final long serialVersionUID = 1L;
		    			public void buttonClick(ClickEvent event) {
		    				System.out.println(serviceTypes.getValue().toString());
		    				if((coordX.getValue()=="")||(coordY.getValue()=="")||name.getValue()==""||(adressField.getValue()=="")||(tel.getValue()=="")||(serviceTypes.getValue().toString()=="[]")){
		    					Notification.show("Empty areas in the form!",
		    			                  "You should fill in all of them.",
		    			                  Notification.Type.ERROR_MESSAGE);
		    				}
		    				else{
		    					ServicesDAO servDao = daoFactory.getServicesDAO();
		    					ServicetypeDAO servtypeDao = daoFactory.getServicetypeDAO();
			    				Services serv = new Services(name.getValue(), tel.getValue(), adressField.getValue(), Float.parseFloat(coordX.getValue()), Float.parseFloat(coordY.getValue()));
			    				servDao.insertService(serv);
			    				
			    				String typeString=serviceTypes.getValue().toString();
			    				typeString =  typeString.replaceAll (" ", "");
			    				typeString = typeString.substring(1, typeString.length()-1);
			    				List<String> typeList = Arrays.asList(typeString.split(","));
			    				
			    				for(String t : typeList){
			    					System.out.println(t);
			    					Type tp = typeDAO.getTypeByName(t);
			    					System.out.println(tp.toString());
			    					System.out.println(serv.toString());
			    					Servicetype st = new Servicetype(serv, tp);
			    					System.out.println(st.toString());
			    					servtypeDao.insertServicetype(st);			    								    					
			    				}
			    				
			    				
			    				if(days!=null){
			    					int len = days.size();
				    				OpenhoursDAO  ohdao= daoFactory.getOpenhoursDAO();
				    				for(int i=0;i<len;i++){
				    					Openhours oh = new Openhours(serv, days.get(i), Integer.parseInt(openHours.get(i)), Integer.parseInt(closeHours.get(i)));
				    					ohdao.insertOpehour(oh);
				    				}
			    				}
			    				
			    				actionType = "insertService";
			    				AdminJsConnecter js = new AdminJsConnecter((String)searchType.getValue(),actualSelectedName,actualLat,actualLng,actionType);
					    		jsPanel.setContent(js);
			    				
			    				serviceWindow.close();
			    				saveAdded=false;
		    				}
		    		    	
		    		    }
		    		});
		    	}
		    	else{
		    		if(type.getValue().equals("Place-type")){
		    			
			    		final Window placeWindow = new Window("Save place-type as: ");
			            FormLayout placelayout = new FormLayout();
			            //flayout.setMargin(true);
			            placeWindow.setContent(placelayout);  
			            final TextField coordX = new TextField("Lat coordinate: ");
			            placelayout.addComponent(coordX);
			            coordX.setValue(lat.getValue());
			            final TextField coordY = new TextField("Lng coordinate: ");
			            placelayout.addComponent(coordY);
			            final TextField name = new TextField("Place name: ");
			            coordY.setValue(lng.getValue());
			            placelayout.addComponent(name);
			            final ComboBox placeType = new ComboBox("Place type: ");
			            placeType.addItem("dirtpark");
			            placeType.addItem("skatepark");
			            placeType.addItem("trail");
			            placelayout.addComponent(placeType);
			            
			            			            
			            Button saveplace = new Button("SAVE");
			            saveplace.setWidth("90%");
			            placelayout.addComponent(saveplace);
			            placelayout.setHeight("25em");
			            placelayout.setWidth("30em"); 
			            placeWindow.center();
			            placeWindow.setResizable(false);
			            
			            
			            placeWindow.addCloseListener(new Window.CloseListener() {
			    			private static final long serialVersionUID = 1L;

			    			public void windowClose(CloseEvent e) {					
			    				placeWindow.close();
			    				saveAdded = false;
			    			}
			    		});
			    		
			            myUIClass.addWindow(placeWindow);
			            saveplace.addClickListener(new Button.ClickListener() {
			    			private static final long serialVersionUID = 1L;
			    			public void buttonClick(ClickEvent event) {
			    				PlacesDAO pdao = daoFactory.getPlacesDAO();
			    				Places place = new Places(name.getValue(),placeType.getValue().toString(),Float.parseFloat(coordX.getValue()),Float.parseFloat(coordY.getValue()));
			    				pdao.insertPlace(place);
			    				actionType = "insertPlace";
			    				AdminJsConnecter js = new AdminJsConnecter((String)searchType.getValue(),actualSelectedName,actualLat,actualLng,actionType);
					    		jsPanel.setContent(js);
			    				placeWindow.close();
			    				saveAdded=false;
			    		    	
			    		    }
			    		});
		    		}
		    	}
		    }
		});
        

}
	
	public boolean timeFormat(String a){
		boolean ok = false;
		if(a.length()==1){
			if(a.substring(0, 1).matches("[0-9]")){
				ok=true;
			}
		}
		if(a.length()==2){
			if((a.substring(0, 1).matches("[0-9]"))&&(a.substring(1, 2)).matches("[0-9]")){
					ok = true;
			}
		}
		
		
		return ok;
		}
		
	public int daysToInt(String day){
		int theday = 0;
		if(day.equals("Monday")){
			theday=1;
		}
		if(day.equals("Tuesday")){
			theday=2;
		}
		if(day.equals("Wednesday")){
			theday=3;
		}
		if(day.equals("Thursday")){
			theday=4;
		}
		if(day.equals("Friday")){
			theday=5;
		}
		if(day.equals("Saturday")){
			theday=6;
		}
		if(day.equals("Sunday")){
			theday=7;
		}
		return theday;
	}
	private String returnDay(int day){
		String dayname ="";
		if(day==1){
			dayname = "Monday";
		}
		if(day==2){
			dayname = "Tuesday";
		}
		if(day==3){
			dayname ="Wednesday";
		}
		if(day==4){
			dayname = "Thursday";
		}
		if(day==5){
			dayname = "Friday";
		}
		if(day==6){
			dayname = "Saturday";
		}
		if(day==7){
			dayname = "Sunday";
		}
		System.out.println("fg: "+dayname);
		return dayname;
	}
	
	
	private void createUpdate(Button upd){
		upd.addClickListener(new Button.ClickListener() {			
			public void buttonClick(ClickEvent event) {
				if(serviceStamp>placeStamp){
					serviceUpdate();
				}
				if(placeStamp>serviceStamp){
					placeUpdate();
				}
				
			}
		});
	}
	
	private void placeUpdate(){
		
		final Window placeWindow = new Window("Update place: ");
        FormLayout placelayout = new FormLayout();
        //flayout.setMargin(true);
        placeWindow.setContent(placelayout);  
        final TextField coordX = new TextField("Lat coordinate: ");
        placelayout.addComponent(coordX);
        coordX.setValue(actualSelectedPlace.getCoordX().toString());
        final TextField coordY = new TextField("Lng coordinate: ");
        placelayout.addComponent(coordY);
        final TextField name = new TextField("Place name: ");
        coordY.setValue(actualSelectedPlace.getCoordY().toString());
        placelayout.addComponent(name);
        name.setValue(actualSelectedName);
        final ComboBox placeType = new ComboBox("Place type: ");
        placeType.addItem("dirtpark");
        placeType.addItem("skatepark");
        placeType.addItem("trail");
        placeType.setValue(actualSelectedPlace.getType());
        placelayout.addComponent(placeType);
        
        			            
        Button saveplace = new Button("UPDATE");
        saveplace.setWidth("90%");
        placelayout.addComponent(saveplace);
        placelayout.setHeight("25em");
        placelayout.setWidth("30em"); 
        placeWindow.center();
        placeWindow.setResizable(false);
        
        
        placeWindow.addCloseListener(new Window.CloseListener() {
			private static final long serialVersionUID = 1L;

			public void windowClose(CloseEvent e) {					
				placeWindow.close();
				saveAdded = false;
			}
		});
		
        myUIClass.addWindow(placeWindow);
        saveplace.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				PlacesDAO pdao = daoFactory.getPlacesDAO();
				//Places place = new Places(name.getValue(),placeType.getValue().toString(),Float.parseFloat(coordX.getValue()),Float.parseFloat(coordY.getValue()));
				//pdao.insertPlace(place);
				Places place = pdao.getPlaceById(actualSelectedPlace.getId());
				place.setName(name.getValue());
				place.setCoordX(Float.parseFloat(coordX.getValue()));
				place.setCoordY(Float.parseFloat(coordY.getValue()));
				place.setType((String) placeType.getValue());
				pdao.updatePlace(place);
				placeWindow.close();
				saveAdded=false;
		    	
		    }
		});
	}
	
	
	
	private void serviceUpdate(){
				if(actualSelectedService==null){
					Notification.show("No service selected!",
			                  "You should select a service first.",
			                  Notification.Type.ERROR_MESSAGE);
					
				}else{
					final Window serviceWindow = new Window("Update service as: ");
			        FormLayout servicelayout = new FormLayout();
			        serviceWindow.setContent(servicelayout);  
			        final TextField coordX = new TextField("Lat coordinate: ");
			        servicelayout.addComponent(coordX);        
			        coordX.setValue(actualSelectedService.getCoordX().toString());

			        final TextField coordY = new TextField("Lng coordinate: ");
			        servicelayout.addComponent(coordY);
			        coordY.setValue(actualSelectedService.getCoordY().toString());
			        
			        final OptionGroup serviceTypes = new OptionGroup("Service-types: ");
			        final DAOFactory daoFactory = DAOFactory.getInstance();
					final TypeDAO typeDAO = daoFactory.getTypeDAO();
					final ServicetypeDAO stdao = daoFactory.getServicetypeDAO();
					List<Type> types = null;
					List<Servicetype> servTlist = stdao.getAllServiceTypesByService(actualSelectedService);
					
					try{	
						types = typeDAO.getAllType();
					} catch(RuntimeException ex) {
						System.out.println(ex.getMessage());
					}
					for (Type p:types) {
						serviceTypes.addItem(p.getName());
					}
					serviceTypes.setMultiSelect(true);
					for(Servicetype st:servTlist){
						System.out.println("type:"+st.getType().getName());
						serviceTypes.select(st.getType().getName());
					}
					
			        
			        servicelayout.addComponent(serviceTypes);
			        final TextField name = new TextField("Institusion name: ");
			        name.setValue(actualSelectedService.getName());
			        servicelayout.addComponent(name);
			        final TextField adressField = new TextField("Adress: ");
			        adressField.setValue(actualSelectedService.getAdress());
			        servicelayout.addComponent(adressField);
			        
			        final TextField tel = new TextField("Telephone: ");
			        tel.setValue(actualSelectedService.getTelephone());
			        servicelayout.addComponent(tel);
			        
			        Button openhour = new Button("Set Time-Table");
			        servicelayout.addComponent(openhour);
			        
			        Button updateservcice = new Button("UPDATE");
			        updateservcice.setWidth("90%");
			        servicelayout.addComponent(updateservcice);
			        servicelayout.setHeight("30em");
			        servicelayout.setWidth("32em"); 
			        serviceWindow.center();
			        serviceWindow.setResizable(false);
			        
			        
			        openhour.addClickListener(new Button.ClickListener() {
						private static final long serialVersionUID = 1L;
						public void buttonClick(ClickEvent event) {
							final Window openhourWindow = new Window("Save service-type as: ");
				            FormLayout openhourlayout = new FormLayout();
				            //flayout.setMargin(true);
				            openhourWindow.setContent(openhourlayout);
				            openhourlayout.setWidth("35em");
				            //openhourlayout.setHeight("42em");
				            
				            days = new ArrayList<Integer>();
				            closeHours =new ArrayList<String>();
				            openHours = new ArrayList<String>();
				            
				            final ComboBox day = new ComboBox("Day: ");
				            openhourlayout.addComponent(day);
				            day.addItem("Monday");
				            day.addItem("Tuesday");
				            day.addItem("Wednesday");
				            day.addItem("Thursday");
				            day.addItem("Friday");
				            day.addItem("Saturday");
				            day.addItem("Sunday");
				             
				            
				            final TextField open  = new TextField("Opens:");
				            openhourlayout.addComponent(open);
				            open.setInputPrompt("09");
				            final TextField close  = new TextField("Closes:");
				            close.setInputPrompt("12");
				            openhourlayout.addComponent(close);
				            Button add = new Button("add");
				            openhourlayout.addComponent(add);
				            Button remove = new Button("remove");
				            openhourlayout.addComponent(remove);
				            
				            
				            final Table table = new Table("Time-Table");
				            table.setSelectable(true);
				            table.setWidth("90%");
				            table.setPageLength(7);
				            table.addContainerProperty("Day", String.class, null);
				            table.addContainerProperty("Opens",  String.class, null);
				            table.addContainerProperty("Closes",  String.class, null);
				            openhourlayout.addComponent(table);
				            rowIndex = 2;
				            OpenhoursDAO ohdao = daoFactory.getOpenhoursDAO();
				            List<Openhours> ohlist = ohdao.getAllOpenhoursByService(actualSelectedService);
				            for(Openhours o:ohlist){
				            	days.add(o.getDay());
	    						openHours.add(o.getOpen().toString());
	    						closeHours.add(o.getClose().toString());
				            	table.addItem(new Object[]{returnDay(o.getDay()),o.getOpen().toString(),o.getClose().toString()},rowIndex);
				            	rowIndex++;
				            }
				            
				            remove.addClickListener(new Button.ClickListener() {								
								public void buttonClick(ClickEvent event) {
									
									String open=  (String) table.getItem(table.getValue()).getItemProperty("Opens").getValue();
									System.out.println("open: "+open);
									openHours.remove(open);
									String close = (String) table.getItem(table.getValue()).getItemProperty("Closes").getValue();
									System.out.println("close:"+ close);
									closeHours.remove(close);
									String day = (String) table.getItem(table.getValue()).getItemProperty("Day").getValue();
									System.out.println("day: "+day);
									days.remove((Integer)(daysToInt(day)));
									table.removeItem(table.getValue()); 
									
									
								}
							});
				            add.addClickListener(new Button.ClickListener() {
				    			private static final long serialVersionUID = 1L;
				    			public void buttonClick(ClickEvent event) {
				    				if((day.getValue()==null)||(open.getValue()=="")||(close.getValue()=="")){
				    					Notification.show("Empty areas in the form!",
				    			                  "You should fill in all of them.",
				    			                  Notification.Type.ERROR_MESSAGE);
				    				}else{
				    					String o =open.getValue();
				    					String c = close.getValue();
				    					
				    					if((timeFormat(o))&&(timeFormat(c))){
				    						days.add((Integer)daysToInt((String) day.getValue()));
				    						openHours.add(open.getValue());
				    						closeHours.add(close.getValue());
				    						
				    						table.addItem(new Object[]{day.getValue(),open.getValue(),close.getValue()},rowIndex);
					    					rowIndex++;
				    						
				    					}else{
				    						Notification.show("Wrong time-format!",
					    			                  "Use xx format, like 16 or 08 ",
					    			                  Notification.Type.ERROR_MESSAGE);
				    					}
				    									    					
				    					
				    				}
				    				
				    		    }
				    		});
				            
				            
				            
				            Button savetable = new Button("OK");
				            openhourlayout.addComponent(savetable);
				            
				            myUIClass.addWindow(openhourWindow);
				            openhourWindow.center();
				            openhourWindow.setResizable(false);
				            
				            savetable.addClickListener(new Button.ClickListener() {
				    			private static final long serialVersionUID = 1L;
				    			public void buttonClick(ClickEvent event) {
				    				
				    				System.out.println(days.toString());
				    				System.out.println(closeHours.toString());
				    				System.out.println(openHours.toString());
				    		    	openhourWindow.close();
				    		    	
				    		    }
				    		});
				            
				            openhourWindow.addCloseListener(new Window.CloseListener() {
				    			private static final long serialVersionUID = 1L;

				    			public void windowClose(CloseEvent e) {					
				    				openhourWindow.close();
				    			}
				    		});
					    }
					});
			        
			        
			        serviceWindow.addCloseListener(new Window.CloseListener() {
						private static final long serialVersionUID = 1L;

						public void windowClose(CloseEvent e) {					
							serviceWindow.close();
							updateAdded = false;
						}
					});
					
			        myUIClass.addWindow(serviceWindow);
			        updateservcice.addClickListener(new Button.ClickListener() {
						private static final long serialVersionUID = 1L;
						public void buttonClick(ClickEvent event) {
							System.out.println(serviceTypes.getValue().toString());
							if((coordX.getValue()=="")||(coordY.getValue()=="")||name.getValue()==""||(adressField.getValue()=="")||(tel.getValue()=="")||(serviceTypes.getValue().toString()=="[]")){
								Notification.show("Empty areas in the form!",
						                  "You should fill in all of them.",
						                  Notification.Type.ERROR_MESSAGE);
							}
							else{
								ServicesDAO servDao = daoFactory.getServicesDAO();
								ServicetypeDAO servtypeDao = daoFactory.getServicetypeDAO();
			    				Services serv = servDao.getServiceById(actualSelectedService.getId());
			    				serv.setName(name.getValue());
			    				serv.setTelephone(tel.getValue());
			    				serv.setAdress(adressField.getValue());
			    				serv.setCoordX(Float.parseFloat(coordX.getValue()));
			    				serv.setCoordY(Float.parseFloat(coordY.getValue()));
			    				servDao.updateService(serv);
			    				
			    				List<Servicetype> servTlist = stdao.getAllServiceTypesByService(actualSelectedService);
			    				for(Servicetype st:servTlist){
			    					stdao.deleteServicetype(st);
			    				}
			    				
			    				String typeString=serviceTypes.getValue().toString();
			    				typeString =  typeString.replaceAll (" ", "");
			    				typeString = typeString.substring(1, typeString.length()-1);
			    				List<String> typeList = Arrays.asList(typeString.split(","));
			    				
			    				for(String t : typeList){
			    					System.out.println(t);
			    					Type tp = typeDAO.getTypeByName(t);
			    					System.out.println(tp.toString());
			    					System.out.println(serv.toString());
			    					Servicetype st = new Servicetype(serv, tp);
			    					System.out.println(st.toString());
			    					servtypeDao.insertServicetype(st);			    								    					
			    				}
			    				OpenhoursDAO ohdao = daoFactory.getOpenhoursDAO();
			    				List<Openhours> ohs = ohdao.getAllOpenhoursByService(actualSelectedService);
			    				for(Openhours o:ohs){
			    					ohdao.deleteOpenhour(o);
			    				}
			    				
			    				if(days!=null){
			    					int len = days.size();
				    				for(int i=0;i<len;i++){
				    					Openhours oh = new Openhours(serv, days.get(i), Integer.parseInt(openHours.get(i)), Integer.parseInt(closeHours.get(i)));
				    					ohdao.insertOpehour(oh);
				    				}
			    				}
			    				
			    				
			    				serviceWindow.close();
			    				updateAdded=false;
							}
					    	
					    }
					});
				}
				

		


		
	}
	
	public void createDelete(final Button del){
		del.addClickListener(new Button.ClickListener() {		
			public void buttonClick(ClickEvent event) {
				
				if(placeStamp<serviceStamp){
					actionType = "deleteService";					
					final Window deleteserviceW = new Window("Delete service");
					FormLayout flayout = new FormLayout();
					Label text = new Label("Are you sure about deleting the "+actualSelectedName+" service?");
					Button yes = new Button("YES");
					Button no = new Button("NO");
					flayout.addComponent(text);
					flayout.addComponent(yes);
					flayout.addComponent(no);
					deleteserviceW.setContent(flayout);
					deleteserviceW.center();
					deleteserviceW.setResizable(false);
					if(deleteAdded==false){
						myUIClass.addWindow(deleteserviceW);
						deleteAdded = true;
					}
					no.addClickListener(new Button.ClickListener() {					
						public void buttonClick(ClickEvent event) {
							deleteserviceW.close();
		    				deleteAdded = false;	
						}
					});
					yes.addClickListener(new Button.ClickListener() {					
						public void buttonClick(ClickEvent event) {
							ServicesDAO sdao = daoFactory.getServicesDAO();
							ServicetypeDAO stdao = daoFactory.getServicetypeDAO();
							List<Servicetype> servTlist = stdao.getAllServiceTypesByService(actualSelectedService);
		    				for(Servicetype st:servTlist){
		    					stdao.deleteServicetype(st);
		    				}
		    				OpenhoursDAO ohdao = daoFactory.getOpenhoursDAO();
		    				List<Openhours> ohs = ohdao.getAllOpenhoursByService(actualSelectedService);
		    				for(Openhours o:ohs){
		    					ohdao.deleteOpenhour(o);
		    				}
		    				RatingDAO rdao = daoFactory.getRatingDAO();
		    				List<Rating> rlist = rdao.getAllRatingByService(actualSelectedService);
		    				for(Rating r:rlist){
		    					rdao.deleteRating(r);
		    				}
		    				sdao.deleteService(actualSelectedService);
		    				AdminJsConnecter js = new AdminJsConnecter((String)searchType.getValue(),actualSelectedName,actualLat,actualLng,actionType);
				    		jsPanel.setContent(js);
							deleteserviceW.close();
		    				deleteAdded = false;	
						}
					});
					
					deleteserviceW.addCloseListener(new Window.CloseListener() {
		    			private static final long serialVersionUID = 1L;
		    			public void windowClose(CloseEvent e) {					
		    				deleteserviceW.close();
		    				deleteAdded = false;
		    			}
		    		});
							
					
				}
				if(placeStamp>serviceStamp){
					actionType = "deletePlace";
					final Window deletesplaceW = new Window("Delete place");
					FormLayout flayout = new FormLayout();
					Label text = new Label("Are you sure about deleting the "+actualSelectedName+" place?");
					Button yes = new Button("YES");
					Button no = new Button("NO");
					flayout.addComponent(text);
					flayout.addComponent(yes);
					flayout.addComponent(no);
					deletesplaceW.setContent(flayout);
					deletesplaceW.center();
					deletesplaceW.setResizable(false);
					if(deleteAdded==false){
						myUIClass.addWindow(deletesplaceW);
						deleteAdded = true;
					}
					no.addClickListener(new Button.ClickListener() {					
						public void buttonClick(ClickEvent event) {
							deletesplaceW.close();
		    				deleteAdded = false;	
						}
					});
					yes.addClickListener(new Button.ClickListener() {					
						public void buttonClick(ClickEvent event) {
							PlacesDAO pdao = daoFactory.getPlacesDAO();
		    				pdao.deletePlace(actualSelectedPlace);
		    				AdminJsConnecter js = new AdminJsConnecter((String)searchType.getValue(),actualSelectedName,actualLat,actualLng,actionType);
				    		jsPanel.setContent(js);
							deletesplaceW.close();
		    				deleteAdded = false;	
						}
					});
					
					deletesplaceW.addCloseListener(new Window.CloseListener() {
		    			private static final long serialVersionUID = 1L;
		    			public void windowClose(CloseEvent e) {					
		    				deletesplaceW.close();
		    				deleteAdded = false;
		    			}
		    		});
							
					
				}
				
				
			}
		});
	}
	
	private void createMessage(){
		final Window subWindow = new Window("Messages");
        subWindow.center();
        subWindow.setResizable(false);
        subWindow.setWidth("40em");
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
       MessagesDAO mdao = daoFactory.getMessagesDAO();
       List<Messages> unread = mdao.getAllUnreadMessagesBySender(thisAdmin);
       List<Messages> read = mdao.getAllReadMessagesBySender(thisAdmin);
       
       Table table = new Table();
       form.addComponent(table);
       
       
       
	}	


}
