package introsde.rest.activitypreference.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.rest.activitypreference.dao.ActivityPreferenceDao;

@Entity
@Table(name = "\"Activity\"")
@NamedQueries({ @NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a"),
		@NamedQuery(name = "Activity.findActivitiesByIdPersonAndActivityType", query = "SELECT a FROM Activity a "
				+ "JOIN ActivityType at ON a.idActivityType = at.idActivityType "
				+ "WHERE a.idPerson = :param_idPerson AND " 
				+ "at.activity_type = :param_activity_type") })
// @XmlType(propOrder = {"id", "firstname", "lastname", "birthdate"})
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

	// OneToMany relation from Person to Activity
	@OneToOne(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ActivityType activityType;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "\"idPerson\"", referencedColumnName = "\"idPerson\"")
	private Person person;

	public Activity() {

	}

	// Follow getter and setter for every attribute of this class

	public int getId() {
		return idActivity;
	}

	public void setId(int idActivity) {
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

	public static List<Activity> getAll() {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
		ActivityPreferenceDao.instance.closeConnections(em);
		return list;
	}

	public static List<Activity> getActivityByIdPersonAndActivityType(int idPerson, String activity_type) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		List<Activity> list = em.createNamedQuery("Activity.findActivitiesByIdPersonAndActivityType", Activity.class)
				.setParameter("param_idPerson", idPerson)
				.setParameter("param_activity_type", activity_type)
				.getResultList();
		ActivityPreferenceDao.instance.closeConnections(em);
		return list;
	}
}
