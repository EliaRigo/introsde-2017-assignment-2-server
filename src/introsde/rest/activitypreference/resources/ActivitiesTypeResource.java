package introsde.rest.activitypreference.resources;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import introsde.rest.activitypreference.model.ActivityType;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/activity_types")
public class ActivitiesTypeResource {
	// Allows to insert contextual objects into the class,
		// E.g. ServletContext, Request, Response, UriInfo
		@Context
		UriInfo uriInfo;
		@Context
		Request request;

		// Will work only inside a Java EE application
		@PersistenceUnit(unitName = "assignment")
		EntityManager entityManager;

		// Will work only inside a Java EE application
		@PersistenceContext(unitName = "assignment", type = PersistenceContextType.TRANSACTION)
		private EntityManagerFactory entityManagerFactory;

		/**
		 * Request#6: GET /activity_types 
		 * Return the list of activity_types in XML
		 * @return XML String of ActivitiesTypes
		 */
		@GET
		@Produces({MediaType.APPLICATION_XML})
		public String getAll() {
			System.out.println("Request#6 GET /activity_types ");
	        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
	        		+ "<activityTypes>";
	        for (ActivityType at : ActivityType.getAll()){
	        	result += "<activity_type>" + at.getActivity_type() + "</activity_type>" ;
	        }
	        result += "</activityTypes>";
	        return result;
		}
		
		/**
		 * Request#6: GET /activity_types 
		 * Return the list of activity_types in JSON
		 * @return JSON String of ActivitiesTypes
		 */
	    @GET
	    @Produces({MediaType.APPLICATION_JSON })
	    public String getPersonsJSON() {
	        System.out.println("Getting list of measure definition in JSON...");
	        List<ActivityType> at = ActivityType.getAll();
	        String result = "{\"activityTypes\": [";
	        for (int i = 0; i < at.size()-1; i++){
	        	result += "\"" + at.get(i).getActivity_type() + "\"," ;
	        }
	        result += "\"" +  at.get(at.size()-1).getActivity_type()+ "\"]}" ;
	        return result;
	    }
}
