package com.mylaensys.dhtmlx.samples.petclinic;

import com.mylaensys.dhtmlx.adapter.Adapter;
import com.mylaensys.dhtmlx.adapter.DefaultGridAdapter;
import com.mylaensys.dhtmlx.adapter.grid.Column;

/**
 * Vets grid
 *
 * @author Madytyoo
 */
public class VetsGrid extends DefaultGridAdapter implements Adapter {
    public VetsGrid() {
        this.add(new Column("firstName","First Name","ro","left", "str", -1));
        this.add(new Column("lastName","Last Name","ro","left", "str", -1));
        this.add(new Column("specialtiesList","Specialities","ro","left", "str", -1));
    }
}
