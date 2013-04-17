package com.mylaensys.dhtmlx.samples.petclinic;

import com.google.appengine.api.datastore.Key;
import com.mylaensys.dhtmlx.adapter.DefaultGridAdapter;
import com.mylaensys.dhtmlx.adapter.WebGridInterceptor;

/**
 * Datastore JPA Grid interceptor
 *
 * @author Madytyoo
 */
public class GaeJpaGridInterceptor extends WebGridInterceptor {
    @Override
    public void onStartRow(DefaultGridAdapter adapter, Object object, StringBuffer buffer) {
        Object entity = adapter.getObjectValue(object,"id");
        if( entity instanceof Key) {
            Key key = (Key)entity;
            buffer.append("<row id='").append( key.getId() ).append("'>");
        }

    }
}
