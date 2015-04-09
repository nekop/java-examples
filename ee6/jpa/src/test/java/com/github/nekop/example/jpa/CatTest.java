package com.github.nekop.example.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CatTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(Cat.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    UserTransaction ut;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testPersist() throws Exception {
        System.out.println(em);

        ut.begin();
        em.createNativeQuery("SET WRITE_DELAY FALSE").executeUpdate();
        ut.commit();
        
        ut.begin();
        Cat cat = new Cat();
        em.persist(cat);
        em.flush();
        ut.commit();

        System.out.println(em.createQuery("from Cat").getSingleResult());
    }

}
