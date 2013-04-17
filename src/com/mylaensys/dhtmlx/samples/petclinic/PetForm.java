package com.mylaensys.dhtmlx.samples.petclinic;

import com.mylaensys.dhtmlx.adapter.Adapter;
import com.mylaensys.dhtmlx.adapter.DefaultFormAdapter;
import com.mylaensys.dhtmlx.adapter.form.*;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.samples.petclinic.PetType;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Pet form
 *
 * @author Madytyoo
 */

public class PetForm extends DefaultFormAdapter implements Adapter {

    public PetForm(Owner owner, Pet pet, List<PetType> types) {
        super(pet,Render);
        init(owner, types);
    }

    public PetForm(Pet pet, BindingResult binding) {
        super(pet, binding);
    }

    private void init(Owner owner, List<PetType> types) {
        Label fieldBlock = new Label("message", "&#160;",200);
        this.add(new Hidden("id"));
        this.add(new Hidden("owner.id",owner.getId().getId()));

        fieldBlock.add(new Label("ownerLavel", "Owner:"));
        fieldBlock.add(new Label("owner", owner.getFirstName() + " " + owner.getLastName()));
        fieldBlock.add(new Label("spacer1","&#160;"));
        fieldBlock.add(new Input("name", "Name:", 150, 250, null, "label-top"));
        Select select = new Select("type", "Type:", 150, null, null, "label-top");
        for(PetType type : types) {
            select.add(new Option(select,type.getName(),type.getName()));
        }
        fieldBlock.add(select);
        fieldBlock.add(new NewColumn());
        fieldBlock.add(new Label("spacer2","&#160;"));
        fieldBlock.add(new Label("spacer3","&#160;"));
        fieldBlock.add(new Label("spacer4","&#160;"));
        fieldBlock.add(new Calendar("birthDate", "Birth Date :", 150, null, null, "label-top"));

        this.add(fieldBlock);

        Label buttonBlock = new Label("dummy1", "&#160;");
        buttonBlock.add( new Button("button_upd", "Save") );
        buttonBlock.add( new NewColumn() );
        buttonBlock.add( new Button("button_cancel", "Cancel") );

        this.add(buttonBlock);
    }
}
