package com.windhaven_consulting.breezy.rest.ping;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.windhaven_consulting.breezy.component.library.ComponentTemplate;
import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.manager.MountedComponent;


@Path("/ping")
@RequestScoped
public class Ping {
	@Inject
	private ComponentTemplateLibraryManager componentLibraryManager;
	
	@Inject
	private MountedBoardManager mountedBoardManager;
	
	@GET
	@Path("findBoardComponents")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<MountedComponent> getAllComponents() {
		return mountedBoardManager.getAllMountedComponents();
	}
	
    @GET
    @Path("findLibraryComponents")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ComponentTemplate> listAllMembers() throws InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	return componentLibraryManager.getComponentTemplates();
    }
}
