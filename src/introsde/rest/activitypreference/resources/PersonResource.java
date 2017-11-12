package introsde.rest.activitypreference.resources;

import introsde.rest.activitypreference.model.Activity;
import introsde.rest.activitypreference.model.Person;

import java.text.ParseException;
import java.util.List;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/person")
public class PersonResource {

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
	 * Request#1: GET /person 
	 * Return the list of people in JSON and XML
	 * 
	 * @return List<Person> List of Person
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getPersonsBrowser() {
		System.out.println("Request#1: GET /person");
		return Person.getAll();
	}
	
	/**
	 * Request#2: GET /person/{id} 
	 * Return person's and activities information (via XML or JSON)
	 * 
	 * @param id Id integer (from path /person/{id})
	 * @return Person person (in XML or JSON)
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getPersonAndActivityByidPerson(@PathParam("id") int id) {
		System.out.println("Request#2: GET /person/" + String.valueOf(id));
		return Person.getPersonById(id);
	}

	/**
	 * Request#3: PUT /person/{id} 
	 * Updates person's information (via XML or JSON)
	 * 
	 * @param id Id integer (from path /person/{id})
	 * @param person Person (from requests' body)
	 * @return Person Updated person (in XML or JSON)
	 * @throws ParseException
	 */
	@PUT
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Person putPerson(@PathParam("id") int id, Person person) throws ParseException {
		System.out.println("Request#3: PUT /person/" + String.valueOf(id));
		Person existing = Person.getPersonById(id);
		if (existing != null && id == person.getIdPerson()) {
			// We allow updating only if the person exits
			// and request's id and person.getId() match
			if (person.getFirstname() == null) {
				person.setFirstname(existing.getFirstname());
			}
			if (person.getLastname() == null) {
				person.setLastname(existing.getLastname());
			}
			if (person.getBirthdate() == null) {
				person.setBirthdate(existing.getBirthdate());
			}
			Person.updatePerson(person);
		}
		return person;
	}

	/**
	 * Request#5: DELETE /person/{id} 
	 * Delete person with specific {id}
	 * 
	 * @param id Id integer (from path /person/{id})
	 */
	@DELETE
	@Path("{id}")
	public void deletePerson(@PathParam("id") int id) {
		System.out.println("Request#5: DELETE /person/" + String.valueOf(id));
		Person p = Person.getPersonById(id);
		Person.removePerson(p);
	}
}