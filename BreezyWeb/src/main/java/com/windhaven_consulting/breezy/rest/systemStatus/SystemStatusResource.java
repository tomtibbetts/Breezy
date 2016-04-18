package com.windhaven_consulting.breezy.rest.systemStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.windhaven_consulting.breezy.embeddedcontroller.EmbeddedControllerAdapter;
import com.windhaven_consulting.breezy.embeddedcontroller.SystemStatus;

@Path("/systemStatus")
@RequestScoped
public class SystemStatusResource {

	@Inject
	private EmbeddedControllerAdapter embeddedControllerAdapter;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SystemStatus getSystemStatus() {
    	return embeddedControllerAdapter.getSystemInfo();
    }

}
