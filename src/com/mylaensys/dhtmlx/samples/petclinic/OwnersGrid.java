package com.mylaensys.dhtmlx.samples.petclinic;

import com.mylaensys.dhtmlx.adapter.Adapter;
import com.mylaensys.dhtmlx.adapter.DefaultGridAdapter;
import com.mylaensys.dhtmlx.adapter.GridInterceptor;
import com.mylaensys.dhtmlx.adapter.grid.Column;

/**
 * Find owner grid
 *
 * @author Madytyoo
 */
public class OwnersGrid extends DefaultGridAdapter implements Adapter {
    public OwnersGrid(GridInterceptor interceptor) {
        super(interceptor);
        this.add(new Column("name","Name","ro","left", "str", 120));
        this.add(new Column("address","Address","ro","left", "str", 150));
        this.add(new Column("city","City","ro","left", "str", -1));
        this.add(new Column("telephone","Telephone","ro","left", "str", -1));
        this.add(new Column("petList","Pets","ro","left", "str", -1));
    }
}
