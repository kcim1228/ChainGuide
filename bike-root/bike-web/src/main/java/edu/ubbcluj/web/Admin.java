package edu.ubbcluj.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import edu.ubbcluj.backend.model.Openhours;
import edu.ubbcluj.backend.model.Places;
import edu.ubbcluj.backend.model.Services;
import edu.ubbcluj.backend.model.Servicetype;
import edu.ubbcluj.backend.model.Type;
import edu.ubbcluj.backend.model.Users;
import edu.ubbcluj.backend.repository.DAOFactory;
import edu.ubbcluj.backend.repository.OpenhoursDAO;
import edu.ubbcluj.backend.repository.PlacesDAO;
import edu.ubbcluj.backend.repository.ServicesDAO;
import edu.ubbcluj.backend.repository.ServicetypeDAO;
import edu.ubbcluj.backend.repository.TypeDAO;
import edu.ubbcluj.backend.repository.UsersDAO;

public class Admin extends VerticalLayout implements View{
	
	boolean saveAdded;
	 boolean registerAdded;
	 UI myUIClass;
	 
	// public static final String NAME = "unloggedUser";
	private GridLayout topgrid = new GridLayout(3, 1);
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
	private TextField adress = new TextField("adress:");
	 private int rowIndex ;
	 private List<Integer> days ;
	 private List<String> closeHours;
	 private List<String> openHours;
	
	

	private Button topsearchButton = new Button("GO");
	private Panel mapPanel = new Panel();
	private VerticalLayout mapLayout = new VerticalLayout();
	
	public Admin(UI ui){
		myUIClass=ui;
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
		adress.setId("adress");
		
		System.out.println(lat.getCaption());
		
		topsearchButton.setId("topsearchButton");	
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
		topgrid.addComponent(topsearchButton,1,0);
		topgrid.addComponent(logingrid, 2,0);
		topgrid.setComponentAlignment(logingrid,  Alignment.TOP_RIGHT);
		topgrid.setComponentAlignment(topsearchButton,  Alignment.TOP_LEFT);		
		logingrid.setComponentAlignment(logout, Alignment.TOP_RIGHT);
		logingrid.setWidth("100%");
		logingrid.setColumnExpandRatio(0, 5);
		logingrid.setColumnExpandRatio(1, 1);
		topgrid.setColumnExpandRatio(0, 5);
		topgrid.setColumnExpandRatio(1, 1);
		topgrid.setColumnExpandRatio(2, 3);
		actiongrid.setWidth("100%");
		actiongrid.setHeight("100%");
		actiongrid.addComponent(save,0,1);
		actiongrid.addComponent(delete,0,3);
		actiongrid.addComponent(update,0,5);
		actiongrid.addComponent(lat,0,6);
		actiongrid.addComponent(lng,0,7);
		actiongrid.addComponent(adress,0,8);
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
		            final DAOFactory daoFactory = DAOFactory.getInstance();
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
		            adressField.setValue(adress.getValue());
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
				            
				            final Table table = new Table("Time-Table");
				            table.setWidth("90%");
				            table.setPageLength(7);
				            table.addContainerProperty("Day		", String.class, null);
				            table.addContainerProperty("Opens",  String.class, null);
				            table.addContainerProperty("Closes",  String.class, null);
				            openhourlayout.addComponent(table);
				            rowIndex = 2;
				            
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
				    						days.add((Integer)days((String) day.getValue()));
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
			    				Services serv = new Services(name.getValue(), tel.getValue(), adress.getValue(), Float.parseFloat(coordX.getValue()), Float.parseFloat(coordY.getValue()));
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
			    				
			    				int len = days.size();
			    				OpenhoursDAO  ohdao= daoFactory.getOpenhoursDAO();
			    				for(int i=0;i<len;i++){
			    					Openhours oh = new Openhours(serv, days.get(i), Integer.parseInt(openHours.get(i)), Integer.parseInt(closeHours.get(i)));
			    					ohdao.insertOpehour(oh);
			    				}
			    				
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
			            final TextField coordY = new TextField("Lng coordinate: ");
			            placelayout.addComponent(coordY);
			            final TextField name = new TextField("Place name: ");
			            placelayout.addComponent(name);
			            ComboBox placeType = new ComboBox();
			            
			            
			           
			            
			            Button saveservcice = new Button("SAVE");
			            saveservcice.setWidth("90%");
			            placelayout.addComponent(saveservcice);
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
			            saveservcice.addClickListener(new Button.ClickListener() {
			    			private static final long serialVersionUID = 1L;
			    			public void buttonClick(ClickEvent event) {
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
		
	public int days(String day){
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

}
