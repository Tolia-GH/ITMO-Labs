package com.blps.lab3.config;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CamundaInitializer implements CommandLineRunner {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private FilterService filterService;
    @Autowired
    private TaskService taskService;

    @Override
    public void run(String... args) throws Exception {
        // 1. Initialize USER group
        if (identityService.createGroupQuery().groupId("USER").count() == 0) {
            Group group = identityService.newGroup("USER");
            group.setName("Ordinary Users");
            group.setType("WORKFLOW");
            identityService.saveGroup(group);

            // Create Application Authorizations for USER group (Access Tasklist)
            Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
            auth.setGroupId("USER");
            auth.setResource(Resources.APPLICATION);
            auth.setResourceId("tasklist");
            auth.addPermission(Permissions.ACCESS);
            authorizationService.saveAuthorization(auth);

            // Create default "All Tasks" Filter for USER group
            Filter filter = filterService.newTaskFilter("All Tasks");
            filter.setQuery(taskService.createTaskQuery()); // Query all tasks
            filter.setOwner("admin"); 
            filterService.saveFilter(filter);

            // Grant READ access to this filter for USER group
            Authorization filterAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
            filterAuth.setGroupId("USER");
            filterAuth.setResource(Resources.FILTER);
            filterAuth.setResourceId(filter.getId());
            filterAuth.addPermission(Permissions.READ);
            authorizationService.saveAuthorization(filterAuth);
            
            System.out.println("Camunda 'USER' group and default filter initialized.");
        }

        // 2. Initialize camunda-admin group (if not exists)
        if (identityService.createGroupQuery().groupId("camunda-admin").count() == 0) {
            Group adminGroup = identityService.newGroup("camunda-admin");
            adminGroup.setName("Camunda Admin Group");
            adminGroup.setType("SYSTEM");
            identityService.saveGroup(adminGroup);
            System.out.println("Camunda 'camunda-admin' group initialized.");
        }
    }
}
