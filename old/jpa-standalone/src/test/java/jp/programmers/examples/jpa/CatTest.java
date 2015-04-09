package jp.programmers.examples.jpa;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import junit.framework.TestCase;

public class CatTest extends TestCase {

    public void testPersist() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET WRITE_DELAY FALSE").executeUpdate();
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        Cat cat = new Cat();
        em.persist(cat);
        em.flush();
        em.getTransaction().commit();
        em.close();
        em = emf.createEntityManager();
        System.out.println(em.createQuery("from Cat").getSingleResult());
        Query q = em.createQuery("select m from MMCat AS m, IN (m.friends) f where f.id = :id");
        q.setParameter("id", 1);
        System.out.println(q.getResultList());
        em.close();
    }

}
