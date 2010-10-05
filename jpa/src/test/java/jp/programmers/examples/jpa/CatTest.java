package jp.programmers.examples.jpa;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import junit.framework.TestCase;

public class CatTest extends TestCase {

    public void testPersist() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Cat cat = new Cat();
        em.persist(cat);
        em.flush();
        em.getTransaction().commit();
        em.close();
        em = emf.createEntityManager();
        System.out.println(em.createQuery("from Cat").getSingleResult());
        em.close();
    }

}
