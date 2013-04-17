package com.mylaensys.dhtmlx.samples.petclinic;

import com.mylaensys.dhtmlx.adapter.Adapter;
import com.mylaensys.dhtmlx.adapter.DefaultGridAdapter;
import com.mylaensys.dhtmlx.adapter.GridInterceptor;
import com.mylaensys.dhtmlx.adapter.grid.Column;

/**
 * Visit grid
 *
 * @author Madytyoo
 */
public class VisitsGrid extends DefaultGridAdapter implements Adapter {
     public VisitsGrid(GridInterceptor interceptor) {
         super(interceptor);
        this.add(new Column("date","Date","ro","left", "str", -1));
        this.add(new Column("description","Description","ro","left", "str", -1));
    }
}
