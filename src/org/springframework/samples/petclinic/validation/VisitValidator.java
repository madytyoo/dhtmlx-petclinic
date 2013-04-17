package org.springframework.samples.petclinic.validation;

import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.samples.petclinic.Visit;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Visit</code> forms.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class VisitValidator implements Validator {
    private Clinic clinic;

    public VisitValidator(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        if( aClass.getName().equals( Visit.class.getName() ))
            return true;
        return false;

    }

    @Override
    public void validate(Object object, Errors errors) {
        Visit visit = (Visit)object;
        Owner owner = clinic.getOwner( visit.getPet().getOwner().getId().getId() );
        Pet pet = owner.getPet( visit.getPet().getId().getId() );

        for( Visit v : pet.getVisits() ) {
            if( visit.getDate().compareTo( v.getDate()) == 0 ) {
                errors.rejectValue("date", "duplicate", "already exists");
            }
        }

	}

}
