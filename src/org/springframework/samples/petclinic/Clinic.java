package org.springframework.samples.petclinic;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * The high-level PetClinic business interface.
 *
 * <p>This is basically a data access object.
 * PetClinic doesn't have a dedicated business facade.
 * Updated for dhtmlx spring link support</p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
public interface Clinic {

	/**
	 * Retrieve all <code>Vet</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Vet</code>s
	 */
	List<Vet> getVets() throws DataAccessException;

	/**
	 * Retrieve all <code>PetType</code>s from the data store.
	 * @return a <code>Collection</code> of <code>PetType</code>s
	 */
	List<PetType> getPetTypes() throws DataAccessException;

	/**
	 * Retrieve <code>Owner</code>s from the data store by last name,
	 * returning all owners whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a <code>List</code> of matching <code>Owner</code>s
	 * (or an empty <code>List</code> if none found)
	 */
	List<Owner> findOwners(String lastName) throws DataAccessException;

	/**
	 * Retrieve an <code>Owner</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Owner</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
	Owner getOwner(Long id) throws DataAccessException;

	/**
	 * Retrieve a <code>Pet</code> from the data store by id.
     * @param ownerId
     * @param petId
	 * @return the <code>Pet</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
     */
	Pet getPet(Long ownerId,Long petId) throws DataAccessException;

	/**
	 * Save an <code>Owner</code> to the data store, either inserting or updating it.
	 * @param owner the <code>Owner</code> to save
	 */
	Owner storeOwner(Owner owner) throws DataAccessException;

    /**
     * Deletes a <code>Owner</code> from the data store.
     */
    void deleteOwner(Long ownerId);
    /**
     * Deletes a <code>Pet</code> from the data store.
     */
    void deletePet(Long ownerId,Long petId) throws DataAccessException;
    /**
     * Deletes a <code>Visit</code> from the data store.
     */
    void deleteVisit(Long ownerId, Long petId, Long visitId);

}
