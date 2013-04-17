package com.mylaensys.dhtmlx.samples.util;

import org.springframework.samples.petclinic.*;
import org.springframework.samples.petclinic.jpa.EMF;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
/**
 * Datastore initializer
 *
 * @author Madytyoo
 */
public class Data {
    private static final Logger log = Logger.getLogger(Data.class.getName());
    public static void createData(Clinic clinic)  {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Specialty surgery = store( new Specialty("surgery") );
            Specialty dentistry = store( new Specialty("dentistry") );
            Specialty radiology = store( new Specialty("radiology") );

            PetType cat = store(  new PetType("cat") );
            PetType dog = store(  new PetType("dog") );
            PetType lizard = store(  new PetType("lizard") );
            PetType snake = store(  new PetType("snake") );
            PetType bird = store(  new PetType("bird") );
            PetType hamster = store(  new PetType("hamster") );

            /* Mr. James Carter */
            Vet james = (Vet) store( new Vet("James","Carter"));
            /* Mrs. Helen Laery */
            Vet helen  = new Vet("Helen","Leary");
            helen.getSpecialties().add( radiology.getName() );
            helen = (Vet) store( helen );
            /* Mrs. Linda Douglas */
            Vet linda = new Vet("Linda","Douglas");
            linda.getSpecialties().add( surgery.getName() );
            linda.getSpecialties().add( dentistry.getName() );
            linda = (Vet) store( linda );
            /* Mr. Rafael Ortega */
            Vet rafael = new Vet("Rafael","Ortega");
            rafael.getSpecialties().add( surgery.getName() );
            rafael = (Vet) store( rafael );
            /* Mr. Henry Stevens */
            Vet henry = new Vet("Henry","Stevens");
            henry.getSpecialties().add( radiology.getName() );
            henry = (Vet) store( henry );
            /* Mrs. Sharon Jenkins */
            Vet sharon = new Vet("Sharon","Jenkins");
            sharon = (Vet) store( sharon );

            /* Owners */
            /* Mr. George Franklin  */
            Owner george = new Owner("George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");
            Pet leo = new Pet("Leo",sdf.parse("2000-09-07"),cat.getName());
            leo.getVisits().add( new Visit(sdf.parse("1996-03-04"),"general") );
            george.getPets().add(leo);
            george = store(clinic,george);


            /* Mrs. Betty Davis */
            Owner betty = new Owner("Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749");
            betty.getPets().add(new Pet("Basil", sdf.parse("2002-08-06"), hamster.getName()));
            betty= store(clinic,betty);

            /* Mr. Eduardo Rodriquez */
            Owner eduardo = new Owner("Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland", "6085558763");
            eduardo.getPets().add(new Pet("Rosy", sdf.parse("2001-04-17"), lizard.getName()));
            eduardo.getPets().add(new Pet("Jewel", sdf.parse("2000-03-07"), lizard.getName()));
            eduardo= store(clinic,eduardo);

            /* Mr. Harold Davis */
            Owner harold = new Owner("Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198");
            harold.getPets().add(new Pet("Iggy", sdf.parse("2000-11-30"), snake.getName()));
            harold= store(clinic,harold);

            /* Mr. Peter McTavish */
            Owner peter = new Owner("Peter", "McTavish", "2387 S. Fair Way", "Madison", "6085552765");
            peter.getPets().add(new Pet("George", sdf.parse("2000-01-20"), bird.getName()));
            peter= store(clinic,peter);

            /* Mrs. Jean Coleman */
            Owner jean = new Owner("Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654");
            jean.getPets().add(new Pet("Samantha", sdf.parse("1995-09-04"), cat.getName()));
            jean= store(clinic,jean);

            /* Mr. Jeff Black */
            Owner jeff = new Owner("Jeff", "Black", "1450 Oak Blvd.", "Monona", "6085555387");
            jeff.getPets().add(new Pet("Lucky", sdf.parse("1999-08-06"), bird.getName()));
            jeff= store(clinic,jeff);

            /* Mrs. Maria Escobito */
            Owner maria = new Owner("Maria", "Escobito", "345 Maple St.", "Madison", "6085557683");
            maria.getPets().add(new Pet("Mulligan", sdf.parse("1997-02-24"), dog.getName()));
            maria= store(clinic,maria);

        } catch (ParseException e) {
        }
    }



    public static Specialty store(Specialty specialty) {
        EntityManager em = EMF.get().createEntityManager();
        Query query = em.createQuery("SELECT s FROM Specialty s where s.name = :name");
        query.setParameter("name", specialty.getName() );
        if(query.getResultList().size() == 0) {
            specialty = (Specialty) storeObject( specialty );
        }
        return specialty;
    }

    public static PetType store(PetType type) {
        EntityManager em = EMF.get().createEntityManager();
        Query query = em.createQuery("SELECT p FROM PetType  p where p.name = :name");
        query.setParameter("name", type.getName() );
        if(query.getResultList().size() == 0) {
            type = (PetType) storeObject( type );
        }
        return type;
    }

    public static Vet store(Vet vet) {
        EntityManager em = EMF.get().createEntityManager();
        Query query = em.createQuery("SELECT v FROM Vet v where v.firstName = :firstName and v.lastName = :lastName ");
        query.setParameter("firstName", vet.getFirstName() );
        query.setParameter("lastName", vet.getLastName() );
        if(query.getResultList().size() == 0) {
            vet = (Vet) storeObject( vet );
        }
        return vet;
    }

    public static Owner store(Clinic clinic, Owner owner) {
        EntityManager em = EMF.get().createEntityManager();
        Query query = em.createQuery("SELECT o FROM Owner o where o.firstName = :firstName and o.lastName = :lastName ");
        query.setParameter("firstName", owner.getFirstName() );
        query.setParameter("lastName", owner.getLastName() );
        if(query.getResultList().size() == 0) {
            owner =  clinic.storeOwner( owner );


        }
        return owner;
    }



    public static Object storeObject(Object object) {
        EntityManager em = EMF.get().createEntityManager();
        try {
                em.getTransaction().begin();
                em.persist( object );
                em.getTransaction().commit();
                //log.warning(e.getMessage());
        } catch (Exception e) {
            log.severe(e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
        return object;
    }


}
