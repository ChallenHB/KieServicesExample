package com.redhat.rest;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;

import com.redhat.producer.KieProducers;

@Path("/task-service")
public class TaskRestServices {
	
	@Inject
	KieProducers kieProducers;
	String username;
	
	@GET
	@Path("/taskquery")
	public Response getTaskList(@HeaderParam("username") String username, @HeaderParam("password") String password ) {
		List<TaskSummary> tasks = kieProducers.getTaskService(username, password).getTasksAssignedAsPotentialOwner(username, "en-UK");
		return Response.status(200).entity(tasks).build();
	}
	
	@POST
	@Path("/claim/{id}")
	public Response claimTask(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") int id) {
		TaskService taskService = kieProducers.getTaskService(username, password);
		taskService.claim(id, username);
		return Response.ok("Success!").build();
	}
	
	@POST
	@Path("/complete/{id}")
	public Response completeTask(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") int id) {
		kieProducers.getTaskService(username, password).start(id, username);
		kieProducers.getTaskService(username, password).complete(id, username, new HashMap<String, Object>());
		return Response.ok("Success!").build();
	}
}
