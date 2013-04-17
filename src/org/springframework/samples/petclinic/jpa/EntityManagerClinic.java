package org.springframework.samples.petclinic.jpa;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mylaensys.dhtmlx.samples.util.Indexer;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * JPA implementation of the Clinic interface using EntityManager.
 *
 * <p>The mappings are defined in "orm.xml" located in the META-INF directory.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @since 22.4.2006
 */
@Repository
public class EntityManagerClinic implements Clinic {
    private static final Logger log = Logger.getLogger(EntityManagerClinic.class.getName());

	public List<Vet> getVets() {
        EntityManager em = EMF.get().createEntityManager();
		return em.createQuery("SELECT vet FROM Vet vet ORDER BY vet.lastName").getResultList();
	}

	public List<PetType> getPetTypes() {
        EntityManager em = EMF.get().createEntityManager();
		return em.createQuery("SELECT p FROM PetType p ORDER BY p.name").getResultList();
	}

    private String getWordList(String text) {
        StringBuffer keywords = new StringBuffer();
        Set<String> words = Indexer.getTokensForIndexing( text );
        for(String word : words) {
            EntityManager em = EMF.get().createEntityManager();
            try {
                String queryString = String.format("select from " + Index.class.getName() + " s WHERE text like '%s' " , text + "%" );
                Query query = em.createQuery(queryString);
                List<Index> wordList = query.getResultList();
                int count = 1;
                for(Index w : wordList ) {
                    keywords.append("\"").append(w.getText()).append("\"").append(",");
                    if(count > 5 )
                        break;
                    count++;
                }
            } catch (Exception e ) {
                log.severe(e.getMessage());
            } finally {
                em.close();
            }
        }
        if( keywords.length() > 0 ) {
            return  keywords.substring(0,keywords.length()-1);
        }
        return "";
    }
	public List<Owner> findOwners(String text) {
        EntityManager em = EMF.get().createEntityManager();
        String keywords = getWordList(text);

        String queryString = "SELECT owner FROM Owner owner";
        if( keywords.length() > 0 ) {
            queryString = String.format("select from " + Owner.class.getName() + " s WHERE words in (%s) " , keywords );
        }
		Query query = em.createQuery(queryString);
		return query.getResultList();
	}

	public Owner getOwner(Long id) {
        Owner owner = null;
        EntityManager em = EMF.get().createEntityManager();
        try {
            Key key = KeyFactory.createKey(Owner.class.getSimpleName(), id);
		    owner = em.find(Owner.class, id);
        } finally {
            em.close();
        }
        return owner;
	}

	public Pet getPet(Long ownerId,Long petId) {
        EntityManager em = EMF.get().createEntityManager();
        Key owner = KeyFactory.createKey(Owner.class.getSimpleName(),ownerId);
        Key pet = KeyFactory.createKey(owner,Pet.class.getSimpleName(), petId);
		return em.find(Pet.class,pet);
	}

    private Owner storeOwnerEntity(Owner owner) {
        Set<String> words = Indexer.getTokensForIndexing(
                owner.getFirstName() + " " +
                        owner.getLastName() + " " +
                        owner.getCity() + " " +
                        owner.getAddress() + " " +
                        owner.getTelephone() + " " +
                        owner.getPetList()
        );
        EntityManager em = EMF.get().createEntityManager();
        try {
            em.getTransaction().begin();
            if (owner.isNew()) {
                owner.getWords().addAll(words);
                em.persist(owner);

            } else {
                owner.getWords().clear();
                owner.getWords().addAll(words);
                em.merge(owner);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.severe(e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
        return owner;
    }
    private List<Index> storeOwnerKeywords(Owner owner) {
        List<Index> keywords = computeIndex(owner.getWords());
        for (Index index : keywords) {
            EntityManager em = EMF.get().createEntityManager();
            try {
                em.persist( index );
            } catch (Exception e) {
                log.severe(e.getMessage());
            } finally {
                em.close();
            }
        }
        return keywords;
    }

    public Owner storeOwner(Owner owner) {
        Owner stored = storeOwnerEntity(owner);
        List<Index> keywords = storeOwnerKeywords(stored);
        return  stored;
    }

	public void storePet(Pet pet) {
		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
        EntityManager em = EMF.get().createEntityManager();
		Pet merged = em.merge(pet);
		em.flush();
		pet.setId(merged.getId());
	}

	public void storeVisit(Visit visit) {
		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
        EntityManager em = EMF.get().createEntityManager();
		Visit merged = em.merge(visit);
		em.flush();
		visit.setId(merged.getId());
	}

    @Override
    public void deleteOwner(Long ownerId) {
        EntityManager em = EMF.get().createEntityManager();
         try {
             Key key = KeyFactory.createKey(Owner.class.getSimpleName(),ownerId);
             em.getTransaction().begin();
             Owner owner = em.find(Owner.class,key);
             em.remove( owner );
             em.getTransaction().commit();
        } catch (Exception e) {
            log.severe(e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
    public void deletePet(Long ownerId,Long petId) throws DataAccessException {
        EntityManager em = EMF.get().createEntityManager();
         try {
             Key owner = KeyFactory.createKey(Owner.class.getSimpleName(),ownerId);
             Key pet = KeyFactory.createKey(owner,Pet.class.getSimpleName(), petId);
             em.getTransaction().begin();
             Pet p = em.find(Pet.class,pet);
             em.remove( p );
             em.getTransaction().commit();
        } catch (Exception e) {
            log.severe(e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }

	}

    @Override
    public void deleteVisit(Long ownerId, Long petId, Long visitId) {
        EntityManager em = EMF.get().createEntityManager();
         try {
             Key owner = KeyFactory.createKey(Owner.class.getSimpleName(),ownerId);
             Key pet = KeyFactory.createKey(owner,Pet.class.getSimpleName(), petId);
             Key visit = KeyFactory.createKey(pet,Visit.class.getSimpleName(), visitId);

             em.getTransaction().begin();
             Visit v = em.find(Visit.class,visit);
             em.remove( v );
             em.getTransaction().commit();
        } catch (Exception e) {
            log.severe(e.getMessage());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }

	}

    /**
     * Compute the list of words to place in the index
     * @param words a set of words
     * @return a list of Index
     */
    private List<Index> computeIndex(Set<String> words) {
        List<Index> wordList = new ArrayList<Index>();
        EntityManager em = EMF.get().createEntityManager();

        String param = "";
        for (Iterator<String> iterator = words.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            param+= "'" + next +"',";
        }
        param = param.substring(0,param.length()-1);

        try {
            String queryString = String.format("select from " + Index.class.getName() + " WHERE  text in (%s) " , param );
            Query query = em.createQuery(queryString);
            List<Index> existing = query.getResultList();
            for(String word : words) {
                boolean found = false;
                for(Index exist : existing ) {
                    if( word.equalsIgnoreCase( exist.getText() )) {
                        found = true;
                    }
                }
                if(!found) {
                    wordList.add( new Index( word ));
                }
            }


        } finally {
            em.close();
        }


        return wordList;
    }

}
