
package org.springframework.samples.petclinic.web;

import com.mylaensys.dhtmlx.samples.util.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Annotation-driven <em>MultiActionController</em> that handles all non-form
 * URL's.
 *
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class ClinicController {
	private final Clinic clinic;

    @Autowired
	public ClinicController(Clinic clinic) {
		this.clinic = clinic;
	}

	/**
	 * Custom handler for the welcome view.
	 */
	@RequestMapping("/welcome")
	public String welcomeHandler() {
        Data.createData(clinic);
        return "welcome";
	}
    /**
     * Custom handler for the vets view.
     */
    @RequestMapping("/vets")
	public String vetsHandler() {
        return "vets";
	}
    /**
     * Custom handler for the owners view.
     */
    @RequestMapping(value = "/owners/search", method = RequestMethod.GET)
	public String ownerSearchHandler() {
		return "owners/search";
	}
    /**
     * Custom handler for the new owner.
     */
    @RequestMapping(value = "/owners/new", method = RequestMethod.GET)
	public String newOwnerHandler() {
		return "owners/new";
	}


}
