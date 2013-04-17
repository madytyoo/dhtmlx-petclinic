package org.springframework.samples.petclinic.web;

import com.mylaensys.dhtmlx.adapter.DefaultFormAdapter;
import com.mylaensys.dhtmlx.samples.petclinic.GaeJpaGridInterceptor;
import com.mylaensys.dhtmlx.samples.petclinic.VisitForm;
import com.mylaensys.dhtmlx.samples.petclinic.VisitsGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.samples.petclinic.Visit;
import org.springframework.samples.petclinic.validation.VisitValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Vets controller
 *
 * @author Madytyoo
 */
@Controller
public class VisitController {
    @Autowired
    private Clinic clinic;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new VisitValidator(clinic));
    }


    @RequestMapping("/owners/{oid}/pets/{pid}/visits")
    public @ResponseBody
    VisitsGrid getVisits(@PathVariable("oid") final Long ownerId,@PathVariable("pid") final Long petId) {

        VisitsGrid adapter = new VisitsGrid(new GaeJpaGridInterceptor());
        adapter.setData( new ArrayList<Visit>());
        if( petId != 0L ) {
            Pet pet = clinic.getPet(ownerId,petId);
            if( pet != null ) {
                adapter.setData( pet.getVisits() );
            }
        }
        return adapter;
    }
    /**
     * Ajax handler for displaying an empty visit form.
     *
     * @param ownerId id of the owner
     * @return
     */
    @RequestMapping("/owners/{oid}/pets/{pid}/visit")
    public
    @ResponseBody
    VisitForm getVisitForm(@PathVariable("oid") final Long ownerId,@PathVariable("pid") final Long petId) {
        Owner owner = clinic.getOwner(ownerId);
        Pet pet = owner.getPet( petId );
        VisitForm adapter = new VisitForm(owner, pet, new Visit());
        return adapter;
    }

     /**
     * Ajax handler for store visit.
     * @param ownerId id of the owner
     * @param petId id of the pet
     * @return
     */
    @RequestMapping(value =  "/owners/{oid}/pets/{pid}/visit" , method = RequestMethod.POST)
    public
    @ResponseBody
    VisitForm storeVisit(@PathVariable("oid") final Long ownerId,@PathVariable("pid") final Long petId,@Valid @ModelAttribute Visit visit, BindingResult binding) {
        Owner owner = clinic.getOwner(ownerId);
        Pet pet = owner.getPet( petId );
        pet.setOwner( owner );
        visit.setPet( pet );
        VisitForm adapter = new VisitForm(owner, pet, visit , binding);
        if( adapter.hasValidData() ) {
            pet.getVisits().add( visit );
            clinic.storeOwner( owner );
        }
        return adapter;
    }

    /**
     * Ajax handler for delete a visit
     * @param ownerId id of the owner
     * @param petId id of the pet
     * @return a DefaultFormAdapter
     */
     @RequestMapping(value = "/owners/{oid}/pets/{pid}/visits/{vid}/delete" ,  method = RequestMethod.POST)
    public @ResponseBody
     DefaultFormAdapter deleteVisit(@PathVariable("oid") final Long ownerId,@PathVariable("pid") final Long petId,@PathVariable("vid") final Long visitId) {
        Owner owner = clinic.getOwner(ownerId);
        Pet pet = owner.getPet(petId);
        Visit toDelete = pet.getVisit( visitId );

        clinic.deleteVisit(ownerId, petId, visitId);

        DefaultFormAdapter adapter = new DefaultFormAdapter(toDelete,DefaultFormAdapter.Delete);
        return adapter;
    }
}
