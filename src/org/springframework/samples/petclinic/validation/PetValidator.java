package org.springframework.samples.petclinic.validation;

import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class PetValidator implements Validator {
    private Clinic clinic;

    public PetValidator(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        if( aClass.getName().equals( Pet.class.getName() ))
            return true;
        return false;
    }

    @Override
    public void validate(Object object, Errors errors) {
        Pet pet = (Pet)object;
        Owner owner = clinic.getOwner(pet.getOwner().getId().getId());

        if ( pet.isNew() && owner.getPet(pet.getName()) != null) {
            errors.rejectValue("name", "duplicate", "already exists");
        }

    }
}
