package org.springframework.samples.petclinic.web;

import com.mylaensys.dhtmlx.adapter.DefaultFormAdapter;
import com.mylaensys.dhtmlx.samples.petclinic.PetForm;
import com.mylaensys.dhtmlx.samples.petclinic.PetsGrid;
import com.mylaensys.dhtmlx.samples.petclinic.PetsGridInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.samples.petclinic.PetType;
import org.springframework.samples.petclinic.validation.PetValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
/**
 * Vets controller
 *
 * @author Madytyoo
 */
@Controller
public class PetController {
    @Autowired
    private Clinic clinic;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new PetValidator(clinic));
    }

    /**
     * Ajax handler for displaying the list of pets.
     * <p>Note that this handler returns {@link PetsGrid} object</p>
     *
     * @param ownerId id of the owner
     * @return PetsGrid
     */
    @RequestMapping("/owners/{id}/pets")
    public
    @ResponseBody
    PetsGrid getPets(@PathVariable("id") final Long ownerId) {
        Owner owner = clinic.getOwner(ownerId);
        PetsGrid adapter = new PetsGrid(new PetsGridInterceptor());
        adapter.setData(owner.getPets());
        return adapter;
    }

    /**
     * Ajax handler for displaying an empty pet form.
     *
     * @param ownerId id of the owner
     * @return
     */
    @RequestMapping("/owners/{oid}/pet")
    public
    @ResponseBody
    PetForm getPetForm(@PathVariable("oid") final Long ownerId) {
        Owner owner = clinic.getOwner(ownerId);
        List<PetType> types = clinic.getPetTypes();
        PetForm adapter = new PetForm(owner, new Pet(owner), types);
        return adapter;
    }

    /**
     * Ajax handler for displaying a pet form.
     *
     * @param ownerId id of the owner
     * @param petId   id of the pet
     * @return PetForm
     */
    @RequestMapping("/owners/{oid}/pet/{pid}")
    public
    @ResponseBody
    PetForm getPet(@PathVariable("oid") final Long ownerId, @PathVariable("pid") final Long petId) {
        Owner owner = clinic.getOwner(ownerId);
        Pet pet = clinic.getPet(ownerId, petId);
        List<PetType> types = clinic.getPetTypes();
        PetForm adapter = new PetForm(owner, pet, types);
        return adapter;
    }


    /**
     * Ajax handler for insert/update pet
     *
     * @param ownerId id of the owner of the pet
     * @param pet     a Valid pet object
     * @param binding binding results
     * @return a PetForm
     */
    @RequestMapping(value = "/owners/{oid}/pet", method = RequestMethod.POST)
    public
    @ResponseBody
    PetForm storePet(@PathVariable("oid") final Long ownerId, @Valid @ModelAttribute Pet pet, BindingResult binding) {
        Owner owner = clinic.getOwner(ownerId);
        pet.setOwner( owner );
        PetForm adapter = new PetForm(pet, binding);

        if (pet.isNew()) {
            if (adapter.hasValidData()) {
                owner.getPets().add(pet);
                clinic.storeOwner(owner);
                pet = owner.getPet(pet.getName());
            }
        } else {
            Pet toUpdate = owner.getPet(pet.getId().getId());
            int index = owner.getPets().indexOf(toUpdate);
            pet.setId(toUpdate.getId());
            owner.getPets().set(index, pet);
            clinic.storeOwner(owner);
        }
        return adapter;
    }

    /**
     * Ajax handler for delete a pet
     * @param ownerId id of the owner
     * @param petId id of the pet
     * @return a DefaultFormAdapter
     */
     @RequestMapping(value = "/owners/{oid}/pets/{pid}/delete" ,  method = RequestMethod.POST)
    public @ResponseBody
     DefaultFormAdapter deletePet(@PathVariable("oid") final Long ownerId,@PathVariable("pid") final Long petId) {
        Owner owner = clinic.getOwner(ownerId);
        Pet toDelete = owner.getPet(petId);

        clinic.deletePet(ownerId, petId);

        DefaultFormAdapter adapter = new DefaultFormAdapter(toDelete,DefaultFormAdapter.Delete);
        return adapter;
    }

}
