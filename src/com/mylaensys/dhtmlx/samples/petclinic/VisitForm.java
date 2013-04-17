package com.mylaensys.dhtmlx.samples.petclinic;


import com.mylaensys.dhtmlx.adapter.Adapter;
import com.mylaensys.dhtmlx.adapter.DefaultFormAdapter;
import com.mylaensys.dhtmlx.adapter.form.*;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.samples.petclinic.Visit;
import org.springframework.validation.BindingResult;

import java.util.Locale;

/**
 * Visit form
 *
 * @author Madytyoo
 */

public class VisitForm  extends DefaultFormAdapter implements Adapter {
    public VisitForm(Owner owner, Pet pet, Visit visit) {
        super(visit,Render);
        init(owner,pet );
    }

    public VisitForm(Owner owner, Pet pet, Visit visit, BindingResult binding) {
        super(visit, binding);
        init(owner,pet);
    }

    private void init(Owner owner, Pet pet) {
        Label fieldBlock = new Label("message", "&#160;",200);
        this.add(new Hidden("id"));
        this.add(new Hidden("pet.id",pet.getId().getId()));
        this.add(new Hidden("pet.owner.id",owner.getId().getId()));

        fieldBlock.add(new Label("petLabel1", "Name:", 150));
        fieldBlock.add(new Label("pet", pet.getName()));
        fieldBlock.add(new Label("spacer1", "&#160;"));
        fieldBlock.add(new Calendar("date", "Date :", 150, null, null, "label-top"));
        fieldBlock.add(new Label("spacer1","&#160;"));
        fieldBlock.add(new Input("description", "Description:", 150, 150, null, "label-top"));
        fieldBlock.add(new NewColumn());
        fieldBlock.add(new Label("petLabel3", "Birth Date:",150));
        fieldBlock.add(new Label("petBirthDate", this.formatData(Locale.getDefault(), pet.getBirthDate())) );

        fieldBlock.add(new NewColumn());
        fieldBlock.add(new Label("petLabel2", "Type:",150));
        fieldBlock.add(new Label("petType", pet.getType()));

        this.add(fieldBlock);

        Label buttonBlock = new Label("dummy1", "&#160;");
        buttonBlock.add( new Button("button_upd", "Save") );
        buttonBlock.add( new NewColumn() );
        buttonBlock.add( new Button("button_cancel", "Cancel") );

        this.add(buttonBlock);

    }
}
