package com.mylaensys.dhtmlx.samples.petclinic;

import com.mylaensys.dhtmlx.adapter.DefaultGridAdapter;
import org.springframework.samples.petclinic.Owner;

import java.util.Locale;

/**
 * Custom owner grid
 *
 * @author Madytyoo
 */
public class OwnersGridInterceptor extends GaeJpaGridInterceptor {
    @Override
    public void onRenderCell(DefaultGridAdapter adapter, Locale locale, Object object, String column, StringBuffer buffer) {
        if( "name".equalsIgnoreCase( column )) {
            Owner owner = (Owner)object;
            String value = owner.getFirstName() + " " + owner.getLastName();
            buffer.append("<cell><![CDATA[").append( "<a onclick='openOwner(" + owner.getId().getId() + ")'>"  + adapter.formatData( locale , value ) + "</a>").append("]]></cell>");
        } else {
            super.onRenderCell(adapter,locale,object,column,buffer);
        }
    }
}
