package com.redhat.producer;

import java.net.MalformedURLException;
import java.net.URL;

import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.task.TaskService;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;

public class KieProducers {

	
	public TaskService getTaskService(String username, String password) {
		return getRuntimeEngine(username, password).getTaskService();
	}
	
	//  Does the runtime engine "retain" the link to the process engine? If so, we can cache it between calls so we don't 
	//  have to make a new call if the same user makes multiple calls in a row? How to do authorization without doing authentication?
	public RuntimeEngine getRuntimeEngine(String username, String password) {
		URL baseurl = null;
		try {
			baseurl = new URL("http://localhost:8080/business-central");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return RemoteRuntimeEngineFactory.newRestBuilder()
				.addUrl(baseurl)
				.addUserName(username).addPassword(password)
			    .addDeploymentId("example:perm-gen-test:1.0")
			    .build();
	}
}
