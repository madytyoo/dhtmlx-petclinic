package org.springframework.samples.petclinic.jpa;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Someone at Google
 */
public final class EMF {
    private static final EntityManagerFactory INSTANCE = Persistence.createEntityManagerFactory("transaction-optional");
    public static EntityManagerFactory get() {
        return INSTANCE;
    }
    private EMF() {
    }
}