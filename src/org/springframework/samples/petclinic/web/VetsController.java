package org.springframework.samples.petclinic.web;

/**
 * Vets controller
 *
 * @author Madytyoo
 */

import com.mylaensys.dhtmlx.adapter.DefaultGridAdapter;
import com.mylaensys.dhtmlx.samples.petclinic.GaeJpaGridInterceptor;
import com.mylaensys.dhtmlx.samples.petclinic.VetsGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Vets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Vets controller
 *
 * @author Madytyoo
 */
@Controller
public class VetsController {
    @Autowired
    private Clinic clinic;
    /**
	 * Ajax handler for displaying vets.
	 * <p>Note that this handler returns {@link com.mylaensys.dhtmlx.samples.petclinic.VetsGrid} object</p>
	 * @return a VetsGrid
	 */
    @RequestMapping("/vets/grid")
    public @ResponseBody
    VetsGrid getVets() {
        VetsGrid adapter = new VetsGrid();
        adapter.setData( clinic.getVets() );
        return adapter;
    }

    /**
	 * Ajax handler for displaying vets for Tag Library
	 * <p>Note that this handler returns {@link com.mylaensys.dhtmlx.adapter.DefaultGridAdapter} object</p>
	 * @return a VetsGrid
	 */
    @RequestMapping("/vets/grid/tag")
    public @ResponseBody DefaultGridAdapter getVetsForTags(@RequestParam("columns") String columns) {
        DefaultGridAdapter adapter = new DefaultGridAdapter(columns,Vets.class,new GaeJpaGridInterceptor());
        adapter.setData( clinic.getVets() );
        return adapter;
    }
}
