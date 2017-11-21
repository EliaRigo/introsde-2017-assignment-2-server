package introsde.rest.activitypreference.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.rest.activitypreference.dao.ActivityPreferenceDao;

@Entity
@Table(name = "\"ActivityType\"")
@NamedQueries({ @NamedQuery(name = "ActivityType.findAll", query = "SELECT a FROM ActivityType a"),
		@NamedQuery(name = "ActivityType.findActivityTypeByActivityTypeName", query = "SELECT a FROM ActivityType a WHERE a.activity_type = :param_activity_type") })
@XmlRootElement
public class ActivityType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sqlite_activitiestype")
	// @TableGenerator(name="sqlite_activitiestypes", table="sqlite_sequence",
	// pkColumnName="name", valueColumnName="seq",
	// pkColumnValue="Person")
	@Column(name = "\"idActivityType\"")
	private int idActivityType;

	@Column(name = "\"activity_type\"")
	private String activity_type;

	@OneToMany(mappedBy = "activityType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Activity> activity;

	@XmlTransient
	public int getIdActivityType() {
		return idActivityType;
	}

	public void setIdActivityType(int idActivityType) {
		this.idActivityType = idActivityType;
	}

	public String getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}

	@XmlTransient
	public List<Activity> getActivity() {
		return activity;
	}

	public void setActivity(List<Activity> activity) {
		this.activity = activity;
	}

	/* Follow class methods*/
	
	/**
	 * Get all ActivityType
	 * @return List of ActivityType
	 */
	public static List<ActivityType> getAll() {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		List<ActivityType> list = em.createNamedQuery("ActivityType.findAll", ActivityType.class).getResultList();
		ActivityPreferenceDao.instance.closeConnections(em);
		return list;
	}

	/**
	 * Get ActivityType by ActivityType name
	 * @param activityType activity_type
	 * @return Single ActivityType
	 */
	public static ActivityType getActivityTypeByActivityType(String activityType) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		ActivityType at = em.createNamedQuery("ActivityType.findActivityTypeByActivityTypeName", ActivityType.class)
				.setParameter("param_activity_type", activityType).getSingleResult();
		ActivityPreferenceDao.instance.closeConnections(em);
		return at;

	}
	
	/**
	 * Post ActivityType
	 * @param at ActivityType
	 * @return Insert ActivityType
	 */
	public static ActivityType postActivityType(ActivityType at) {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(at);
		tx.commit();
		ActivityPreferenceDao.instance.closeConnections(em);
		return at;
	}
}
