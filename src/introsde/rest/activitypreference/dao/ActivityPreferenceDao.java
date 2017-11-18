package introsde.rest.activitypreference.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public enum ActivityPreferenceDao {
    instance;
    private EntityManagerFactory emf;

    /**
     * Constructor ActivityPreferenceDao
     */
    private ActivityPreferenceDao() {
        if (emf!=null) {
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory("assignment");
    }

    /**
     * Create Entity Manager
     * @return EntityManager
     */
    public EntityManager createEntityManager() {
        try {
            return emf.createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;    
    }

    /**
     * Close database connection
     * @param em Entity Manager
     */
    public void closeConnections(EntityManager em) {
        em.close();
    }

    /**
     * Get Transaction from Entity Manager
     * @param em Entity Manager
     * @return EntityTransaction
     */
    public EntityTransaction getTransaction(EntityManager em) {
        return em.getTransaction();
    }

    /**
     * Get Entity Manager Factor
     * @return EntityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }  
}