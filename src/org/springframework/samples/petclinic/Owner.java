package org.springframework.samples.petclinic;

import com.google.appengine.api.datastore.Key;
import com.mylaensys.dhtmlx.adapter.format.KeyFormat;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeyFormat
    private Key id;
    @NotEmpty
	private String firstName;
    @NotEmpty
	private String lastName;
    @NotEmpty
	private String address;
    @NotEmpty
	private String city;
    @NotEmpty
	private String telephone;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner",fetch = FetchType.EAGER)
	private List<Pet> pets;

    private java.util.Set<String> words;

    public Owner() {
        this.pets = new ArrayList<Pet>();
        this.words = new HashSet<String>();
    }

    public Owner(String firstName, String lastName, String address, String city, String telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = new ArrayList<Pet>();
        this.words = new HashSet<String>();
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

    public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public List<Pet> getPets() {
        return pets;
	}


	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 *
	 * @param name to test
	 * @return true if pet name is already in use
	 */
	public Pet getPet(String name) {
		for (Pet pet : pets ) {
            if( pet.getName().equalsIgnoreCase( name ) )
                return pet;
        }
        return null;
	}

    public Pet getPet(Long key) {
		for (Pet pet : pets ) {
            if( pet.getId().getId() == key )
                return pet;
        }
        return null;
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 *
	 * @param name to test
	 * @return true if pet name is already in use
	 */
	public Pet getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (Pet pet : pets ) {
			if (!ignoreNew /*|| !pet.isNew()*/) {
				String compName = pet.getName();
				compName = compName.toLowerCase();
				if (compName.equals(name)) {
					return pet;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)

		.append("id", this.getId())

		//.append("new", this.isNew())

		.append("lastName", this.getLastName())

		.append("firstName", this.getFirstName())

		.append("address", this.address)

		.append("city", this.city)

		.append("telephone", this.telephone)

		.toString();
	}

    public boolean  isNew() {
        return (this.id == null);
    }

    public Set<String> getWords() {
        return words;
    }

    public String getPetList() {
        StringBuffer buffer = new StringBuffer();
        for(Pet pet: pets) {
            buffer.append(pet.getName()).append(" ");
        }
        return buffer.toString();
    }
}

