package introsde.rest.activitypreference.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.rest.activitypreference.dao.ActivityPreferenceDao;

@Entity
@Table(name = "\"ActivityType\"")
@NamedQuery(name = "ActivityType.findAll", query = "SELECT a FROM ActivityType a")
//@XmlType(propOrder = { "id", "firstname", "lastname", "birthdate" })
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
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="\"idActivityType\"",referencedColumnName="\"idActivityType\"")
    private Activity activity;

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
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	public static List<ActivityType> getAll() {
		EntityManager em = ActivityPreferenceDao.instance.createEntityManager();
		List<ActivityType> list = em.createNamedQuery("ActivityType.findAll", ActivityType.class).getResultList();
		ActivityPreferenceDao.instance.closeConnections(em);
		return list;
	}
}
