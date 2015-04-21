package main;

import java.util.Date;
import java.util.List;

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


public class Test {

	public static void main(String[] args) {
	
		DAOFactory daoFactory = DAOFactory.getInstance();
		
		PlacesDAO placesDAO = daoFactory.getPlacesDAO();
		TypeDAO typeDAO = daoFactory.getTypeDAO();
		UsersDAO usersDAO = daoFactory.getUsersDAO();
		ServicesDAO servicesDAO = daoFactory.getServicesDAO();
		ServicetypeDAO servicetypeDAO = daoFactory.getServicetypeDAO();
		OpenhoursDAO openhDAO = daoFactory.getOpenhoursDAO();
		MessagesDAO messDAO = daoFactory.getMessagesDAO();
		RatingDAO ratDAO = daoFactory.getRatingDAO();
		
		List<Places> places = null;
		List<Type> types = null;
		List<Users> users = null;
		List<Services> services = null;
		List<Servicetype> servicetypes = null;
		List<Openhours> openh = null;
		List<Messages> mess = null;
		List<Rating> rat = null;
		
		
		Type tipus = new Type(3);
		//Places hely = new Places(1,"skatepark4","extrem",(float)26,(float)48);
		//Users felh = new Users(1,"admin","alma","alma","Csilla","Katay","katay.csilla@gmail.com");
		//Services serv = new Services(1,"bellabike","0745698547","str. mos nicola nr.32",(float)23,(float)45);
		//Services serv2 = new Services("almabike","0745698547","str. unirii  nr.32",(float)23,(float)46.36);
		
		//Services s = new Services(1);
		
		
		
		//System.out.println(servicesDAO.getServiceById(1).toString());
		//System.out.println(typeDAO.getTypeById(2).toString());
		//Servicetype st = new Servicetype(servicesDAO.getServiceById(3),typeDAO.getTypeById(2));
		//Openhours oh = new Openhours(servicesDAO.getServiceById(3),4,0,24);
		//messDAO.insertMessage(m);
		//mess = messDAO.getAllMessagesByReceiver(usersDAO.getUserByName("alma"));
	
		//System.out.println(st.toString());*/
		
		try {
			//placesDAO.deletePlace(hely);
			//places = placesDAO.getPlacesByName("skA");
			//servicesDAO.insertService(serv2);
			services = servicesDAO.getAllServicesByType(tipus);
			//types = typeDAO.getAllType();
			//servicetypeDAO.insertServicetype(st);
			//servicetypes = servicetypeDAO.getAllServicesByType(tipus);
			//openhDAO.insertOpehour(oh);
			//openh = openhDAO.getAllOpenNow();
			//servicesDAO.insertService(serv2);
			//services = servicesDAO.getAllServices();
			
			
		} catch(RuntimeException ex) {
			System.out.println(ex.getMessage());
		}
		
		for (Services p:services) {
			System.out.println(p.toString());
		}

		//System.out.println(typeDAO.getTypeById(2).toString());

	}

}
