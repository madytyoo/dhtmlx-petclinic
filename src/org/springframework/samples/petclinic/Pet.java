package org.springframework.samples.petclinic;

import com.google.appengine.api.datastore.Key;
import com.mylaensys.dhtmlx.adapter.format.KeyFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple JavaBean business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeyFormat
    private Key id;
    @NotEmpty
    private String name;
	private Date birthDate;
	private String type;
    @ManyToOne(fetch = FetchType.EAGER)
	private Owner owner;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "pet",fetch = FetchType.EAGER)
	private List<Visit> visits;

    public Pet() {
        this.owner = new Owner();
    }

    public Pet(Owner owner) {
        this.owner = owner;
    }

    public Pet(String name, Date birthDate, String type) {
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
        this.visits = new ArrayList<Visit>();
    }

    public boolean  isNew() {
        return (this.id == null);
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Owner getOwner() {
		return this.owner;
	}
	public List<Visit> getVisits() {
        return visits;
	}

    public Visit getVisit(Long key) {
        for (Visit visit : visits ) {
            if( visit.getId().getId() == key )
                return visit;
        }
        return null;
    }

}
