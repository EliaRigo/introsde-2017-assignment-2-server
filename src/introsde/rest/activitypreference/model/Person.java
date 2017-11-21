package introsde.rest.activitypreference.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import introsde.rest.activitypreference.dao.ActivityPreferenceDao;

/**
 * Persistent class of table "person"
 */
@Entity
@Table(name = "\"Person\"")
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
@XmlType(propOrder = { "idPerson", "firstname", "lastname", "birthdate", "activities" })
@XmlRootElement
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sqlite_person")
	// @TableGenerator(name="sqlite_person", table="sqlite_sequence",
	// pkColumnName="firstname", valueColumnName="seq",
	// pkColumnValue="Person")
	@Column(name = "\"idPerson\"")
	private int idPerson;

	@Column(name = "\"firstname\"")
	private String firstname;

	@Column(name = "\"lastname\"")
	private String lastname;

	@Temporal(TemporalType.DATE)
	@Column(name = "\"birthdate\"")
	private Date birthdate;

	// OneToMany relation from Person to Activity
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Activity> activities;

	public Person() {
	}

	// Follow getter and setter for every attribute of this class

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getBirthdate() {
		if (this.birthdate == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(this.birthdate);
		// return this.birthdate;
	}

	public void setBirthdate(String birthdate) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(birthdate);
		this.birthdate = date;
		// this.birthdate = bd;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@XmlElementWrapper(name = "activitiesPreference")
	@XmlElement(name = "activity")
	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	/* Follow class methods */
	
	/**
	 * Get all person
	 * @return List of Person
	 */
	public static List<Person> getAll() {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
		ActivityPreferenceDao.instance.closeConnections(em);
		return list;
	}

	/**
	 * Get single Person by IdPerson
	 * @param id Integer IdPerson
	 * @return Single Person
	 */
	public static Person getPersonById(int id) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		Person p = em.find(Person.class, id);
		ActivityPreferenceDao.instance.closeConnections(em);
		return p;
	}
	
	/**
	 * Insert new Person
	 * @param p New Person
	 * @return Single Person just inserted
	 */
	public static Person newPerson(Person p) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		ActivityPreferenceDao.instance.closeConnections(em);
		for (Activity a : p.getActivities()) {
			a.setPerson(p);
			a.setIdPerson(p.getIdPerson());
			Activity.updateActivity(a);
		}
		return p;
	}

	/**
	 * Update Person
	 * @param p Updated Person
	 * @return Single updated Person
	 */
	public static Person updatePerson(Person p) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		tx.commit();
		ActivityPreferenceDao.instance.closeConnections(em);
		return p;
	}

	/**
	 * Remove Person
	 * @param p Person to be deleted
	 */
	public static void removePerson(Person p) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		em.remove(p);
		tx.commit();
		ActivityPreferenceDao.instance.closeConnections(em);
	}
}