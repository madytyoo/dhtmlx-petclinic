package org.springframework.samples.petclinic;


import com.google.appengine.api.datastore.Key;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Datastore initializer
 *
 * @author Madytyoo
 */

@Entity
public class Index {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
    private String text;

    public Index() {
    }

    public Index(Key id) {
        this.id = id;
    }

    public Index(String text) {
        this.text = text;
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}