/*
 * Copyright 2011-2012 Mylaensys LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mylaensys.dhtmlx.samples.petclinic;

import com.mylaensys.dhtmlx.adapter.Adapter;
import com.mylaensys.dhtmlx.adapter.DefaultFormAdapter;
import com.mylaensys.dhtmlx.adapter.form.*;
import org.springframework.samples.petclinic.Owner;
import org.springframework.validation.BindingResult;

/**
 * Owner form
 *
 * @author Madytyoo
 */

public class OwnerForm extends DefaultFormAdapter implements Adapter {
    public OwnerForm(Owner owner) {
        super(owner, Render);
        init(owner);
    }

    public OwnerForm(Owner owner, BindingResult binding) {
        super(owner, binding);
        init(owner);
    }

    private void init(Owner owner) {
        Label fieldBlock = new Label("message", "&#160;",200);
        this.add(new Hidden("id"));
        fieldBlock.add(new Input("firstName", "First Name:", 150, 250, null, "label-top"));
        fieldBlock.add(new Input("address", "Address:", 150, null, null, "label-top"));
        fieldBlock.add(new Input("telephone", "Telephone:", 150, null,null,"label-top"));
        fieldBlock.add(new NewColumn());
        fieldBlock.add(new Input("lastName", "Last Name:", 150, null, null, "label-top"));
        fieldBlock.add(new Input("city", "City:", 150, null, null, "label-top"));

        this.add(fieldBlock);

        Label buttonBlock = new Label("dummy1", "&#160;");
        buttonBlock.add( new Button("button_upd", owner.isNew() ? "Create" : "Update ") );
        buttonBlock.add( new NewColumn() );
        if( !owner.isNew() ) {
            buttonBlock.add( new Button("button_add", "Add New Pet") );
        } else {
            buttonBlock.add( new Button("button_canel", "Cancel") );
        }

        this.add(buttonBlock);
    }
}
