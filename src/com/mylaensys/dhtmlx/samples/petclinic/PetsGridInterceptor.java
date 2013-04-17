package com.mylaensys.dhtmlx.samples.petclinic;

import com.mylaensys.dhtmlx.adapter.DefaultGridAdapter;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;

import java.util.Locale;

/**
 * Pets grid interceptor
 *
 * @author Madytyoo
 */

public class PetsGridInterceptor extends GaeJpaGridInterceptor {
    @Override
    public void onRenderCell(DefaultGridAdapter adapter, Locale locale, Object object, String column, StringBuffer buffer) {
        Pet pet = (Pet)object;
        Owner owner = pet.getOwner();
        if( "name".equalsIgnoreCase( column )) {
            String value = pet.getName();
            buffer.append("<cell><![CDATA[").append( "<a onclick='openPet( \"Edit Pet\"," + owner.getId().getId() +"," + pet.getId().getId() + ")'>"  + adapter.formatData( locale , value ) + "</a>").append("]]></cell>");
        } else if( "action".equalsIgnoreCase( column )) {
            buffer.append("<cell><![CDATA[").append( "&#160;<a onclick='openVisit( \"Add New Visit\"," + owner.getId().getId() +"," + pet.getId().getId() + ")'>"  + adapter.formatData( locale , "add" ) + "</a>").append("]]></cell>");
        } else {
            super.onRenderCell(adapter,locale,object,column,buffer);
        }
    }
}
