package org.springframework.samples.petclinic;

import com.google.appengine.api.datastore.Key;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 */
@Entity
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
	private String firstName;
	private String lastName;
	private Set<String> specialties;

    public Vet() {
    }

    public Vet(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialties = new HashSet<String>();
    }


    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<String> getSpecialties() {
        return specialties;
    }

    public String getSpecialtiesList() {
        StringBuffer buffer = new StringBuffer();
        if(specialties.size() == 0) {
            buffer.append("none");
        } else {
            for(String speciality : specialties) {
                buffer.append(speciality).append(" ");
            }
        }
        return buffer.toString();
    }

}
