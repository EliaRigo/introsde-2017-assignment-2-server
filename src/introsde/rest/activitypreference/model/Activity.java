package introsde.rest.activitypreference.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.rest.activitypreference.dao.ActivityPreferenceDao;

@Entity
@Table(name = "\"Activity\"")
//NameQueries used in this class 
@NamedQueries({ @NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a"),
		@NamedQuery(name = "Activity.findActivitiesByIdPersonAndActivityType", query = "SELECT a FROM Activity a "
				+ "JOIN ActivityType at ON a.idActivityType = at.idActivityType "
				+ "WHERE a.idPerson = :param_idPerson AND " + "at.activity_type = :param_activity_type"),
		@NamedQuery(name = "Activity.findActivityByIdPersonAndIdActivityActivityType", query = "SELECT a FROM Activity a "
				+ "JOIN ActivityType at ON a.idActivityType = at.idActivityType "
				+ "WHERE a.idPerson = :param_idPerson AND " + "a.idActivity = :param_idActivity AND "
				+ "at.activity_type = :param_activity_type"), })
@XmlRootElement
public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sqlite_activity")
	// @TableGenerator(name="sqlite_activity", table="sqlite_sequence",
	// pkColumnName="name", valueColumnName="seq",
	// pkColumnValue="Activity")
	@Column(name = "\"idActivity\"")
	private int idActivity;

	@Column(name = "\"idPerson\"")
	private int idPerson;

	@Column(name = "\"idActivityType\"")
	private int idActivityType;

	@Column(name = "\"name\"")
	private String name;

	@Column(name = "\"description\"")
	private String description;

	@Column(name = "\"place\"")
	private String place;

	@Column(name = "\"startdate\"")
	private String startdate;

	@ManyToOne()
	@PrimaryKeyJoinColumn(name = "\"idActivityType\"", referencedColumnName = "\"idActivityType\"")
	private ActivityType activityType;

	@ManyToOne()
	@PrimaryKeyJoinColumn(name = "\"idPerson\"", referencedColumnName = "\"idPerson\"")
	private Person person;

	public Activity() {

	}

	// Follow getter and setter for every attribute of this class

	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public int getIdActivityType() {
		return idActivityType;
	}

	public void setIdActivityType(int idActivityType) {
		this.idActivityType = idActivityType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	// @XmlElement(name = "activityType")
	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	/* Follow class methods */
	
	/**
	 * Get all Activity
	 * @return List of Activity
	 */
	public static List<Activity> getAll() {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
		ActivityPreferenceDao.instance.closeConnections(em);
		return list;
	}

	/**
	 * Get Activity by IdPerson and ActivityType name
	 * @param idPerson Integer IdPerson
	 * @param activity_type String activity_type
	 * @return List of Activity
	 */
	public static List<Activity> getActivityByIdPersonAndActivityType(int idPerson, String activity_type) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		List<Activity> list = em.createNamedQuery("Activity.findActivitiesByIdPersonAndActivityType", Activity.class)
				.setParameter("param_idPerson", idPerson).setParameter("param_activity_type", activity_type)
				.getResultList();
		ActivityPreferenceDao.instance.closeConnections(em);
		return list;
	}

	/**
	 * Get Activity by IdPerson, IdActivity and ActivityType name
	 * @param idPerson Integer IdPerson
	 * @param idActivity Integer IdActivity
	 * @param activity_type String activity_type
	 * @return Single Activity
	 */
	public static Activity getActivityByIdPersonIdActivityAndActivityType(int idPerson, int idActivity,
			String activity_type) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		Activity activity = em
				.createNamedQuery("Activity.findActivityByIdPersonAndIdActivityActivityType", Activity.class)
				.setParameter("param_idPerson", idPerson).setParameter("param_idActivity", idActivity)
				.setParameter("param_activity_type", activity_type).getSingleResult();
		ActivityPreferenceDao.instance.closeConnections(em);
		return activity;
	}
	
	/**
	 * Post new Activity
	 * @param activity Activity new Activity
	 * @param idPerson Integer IdPerson
	 * @param activityType ActivityType activityType
	 * @return Single Activity
	 */
	public static Activity postActivity(Activity activity, int idPerson, ActivityType activityType) {
		activity.setIdPerson(idPerson);
		activity.setPerson(Person.getPersonById(idPerson));
		activity.setIdActivityType(activityType.getIdActivityType());
		activity.setActivityType(activityType);
		
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(activity);
		tx.commit();
		ActivityPreferenceDao.instance.closeConnections(em);
		return activity;
	}
	
	/**
	 * Update Activity
	 * @param activity Activity updated Activity
	 */
	public static void updateActivity(Activity activity) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(activity);
		tx.commit();
		ActivityPreferenceDao.instance.closeConnections(em);
	}
}
