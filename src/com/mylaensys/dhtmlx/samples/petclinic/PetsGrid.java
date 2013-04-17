package com.mylaensys.dhtmlx.samples.petclinic;


import com.mylaensys.dhtmlx.adapter.Adapter;
import com.mylaensys.dhtmlx.adapter.DefaultGridAdapter;
import com.mylaensys.dhtmlx.adapter.GridInterceptor;
import com.mylaensys.dhtmlx.adapter.grid.Column;

/**
 * Pet Grid
 *
 * @author Madytyoo
 */
public class PetsGrid extends DefaultGridAdapter implements Adapter {
    public PetsGrid(GridInterceptor interceptor) {
        super(interceptor);
        this.add(new Column("name","Name","ro","left", "str", -1));
        this.add(new Column("birthDate","Birth Date","ro","left", "str", -1));
        this.add(new Column("type","Type","ro","left", "str", -1));
        this.add(new Column("action","","ro","center", "str", 40));
    }
}
