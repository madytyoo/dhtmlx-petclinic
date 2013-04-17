package org.springframework.samples.petclinic.web;


import com.mylaensys.dhtmlx.adapter.DefaultFormAdapter;
import com.mylaensys.dhtmlx.samples.petclinic.FindOwnersForm;
import com.mylaensys.dhtmlx.samples.petclinic.OwnerForm;
import com.mylaensys.dhtmlx.samples.petclinic.OwnersGrid;
import com.mylaensys.dhtmlx.samples.petclinic.OwnersGridInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * Vets controller
 *
 * @author Madytyoo
 */
@Controller
public class OwnerController {
    @Autowired
    private Clinic clinic;

    /**
     * Ajax handler for owner search box.
     * <p>Note that this handler returns {@link com.mylaensys.dhtmlx.samples.petclinic.FindOwnersForm} object</p>
     *
     * @return a FindOwnersForm
     */
    @RequestMapping("/owner/search/form")
    public
    @ResponseBody
    FindOwnersForm getOwnerSearchBox() {
        FindOwnersForm adapter = new FindOwnersForm(new Object());
        return adapter;
    }

    /**
     * Ajax handler for owner list grid.
     * <p>Note that this handler returns {@link com.mylaensys.dhtmlx.samples.petclinic.OwnersGrid} object</p>
     *
     * @return a OwnersGrid
     */
    @RequestMapping("/owner/search/grid")
    public
    @ResponseBody
    OwnersGrid ownersGrid(@RequestParam("text") String text) {
        OwnersGrid adapter = new OwnersGrid(new OwnersGridInterceptor());
        adapter.setData(clinic.findOwners(text));
        return adapter;
    }

    /**
     * Ajax handler for displaying an empty owner form.
     * <p>Note that this handler returns {@link com.mylaensys.dhtmlx.samples.petclinic.OwnerForm} object</p>
     *
     * @return a ownerForm
     */
    @RequestMapping("/owners/form")
    public
    @ResponseBody
    OwnerForm ownerForm() {
        OwnerForm adapter = new OwnerForm(new Owner());
        return adapter;
    }

    /**
     * Ajax handler for displaying a owner form.
     *
     * @param ownerId
     * @return OwnerForm
     */
    @RequestMapping("/owners/{id}")
    public
    @ResponseBody
    OwnerForm getOwner(@PathVariable("id") final Long ownerId) {
        Owner owner = clinic.getOwner(ownerId);
        OwnerForm adapter = new OwnerForm(owner);
        return adapter;
    }

    /**
     * Ajax handler for insert/update owner
     *
     * @param owner
     * @param binding
     * @return DefaultFormAdapter
     */
    @RequestMapping(value = "/owners", method = RequestMethod.POST)
    public
    @ResponseBody
    DefaultFormAdapter storeOwner(@Valid @ModelAttribute Owner owner, BindingResult binding) {
        DefaultFormAdapter adapter = new DefaultFormAdapter(owner, binding);
        if (adapter.hasValidData()) {
            clinic.storeOwner(owner);
        }
        return adapter;
    }

    /**
     * Ajax handler for delete a owner
     *
     * @param ownerId id of the owner
     * @return a DefaultFormAdapter
     */
    @RequestMapping(value = "/owners/{oid}/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    DefaultFormAdapter deleteOwner(@PathVariable("oid") final Long ownerId) {
        Owner toDelete = clinic.getOwner(ownerId);
        DefaultFormAdapter adapter = new DefaultFormAdapter(toDelete, DefaultFormAdapter.Delete);
        clinic.deleteOwner(ownerId);
        return adapter;
    }


}
