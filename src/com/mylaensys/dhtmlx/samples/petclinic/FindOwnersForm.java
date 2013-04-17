package com.mylaensys.dhtmlx.samples.petclinic;

import com.mylaensys.dhtmlx.adapter.Adapter;
import com.mylaensys.dhtmlx.adapter.DefaultFormAdapter;
import com.mylaensys.dhtmlx.adapter.form.Button;
import com.mylaensys.dhtmlx.adapter.form.Input;

/**
 * Find owner form
 *
 * @author Madytyoo
 */
public class FindOwnersForm extends DefaultFormAdapter implements Adapter {
    public FindOwnersForm(Object data) {
        super(data,Render);
        this.add(new Input("text", "Full Text Search:", 170,null,null,"label-top"));
        this.add(new Button("search", "Find Owners"));
    }
}
