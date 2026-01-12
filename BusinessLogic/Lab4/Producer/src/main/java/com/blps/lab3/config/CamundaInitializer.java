package com.blps.lab3.config;

import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Repositories.AccountsRepo;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

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
    @Autowired
    private AccountsRepo accountsRepo;

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

        // 3. Sync existing users from DB to Camunda
        syncUsersToCamunda();

        // 4. Ensure USER group has Start Process permissions
        if (authorizationService.createAuthorizationQuery()
                .groupIdIn("USER")
                .resourceType(Resources.PROCESS_DEFINITION)
                .resourceId("*")
                .count() == 0) {

            Authorization processAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
            processAuth.setGroupId("USER");
            processAuth.setResource(Resources.PROCESS_DEFINITION);
            processAuth.setResourceId("*");
            processAuth.addPermission(Permissions.READ);
            processAuth.addPermission(Permissions.CREATE_INSTANCE);
            authorizationService.saveAuthorization(processAuth);
            System.out.println("Granted Process Start permissions to USER group.");
        }

        // 5. Ensure USER group has CREATE permission on Process Instance
        if (authorizationService.createAuthorizationQuery()
                .groupIdIn("USER")
                .resourceType(Resources.PROCESS_INSTANCE)
                .resourceId("*")
                .count() == 0) {

            Authorization instanceAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
            instanceAuth.setGroupId("USER");
            instanceAuth.setResource(Resources.PROCESS_INSTANCE);
            instanceAuth.setResourceId("*");
            instanceAuth.addPermission(Permissions.CREATE);
            instanceAuth.addPermission(Permissions.READ);
            instanceAuth.addPermission(Permissions.UPDATE);
            authorizationService.saveAuthorization(instanceAuth);
            System.out.println("Granted Process Instance CREATE permissions to USER group.");
        }
    }

    private void syncUsersToCamunda() {
        List<AccountsJPA> accounts = accountsRepo.findAll();
        for (AccountsJPA account : accounts) {
            try {
                // Check if user exists in Camunda
                if (identityService.createUserQuery().userId(account.getEmail()).count() == 0) {
                    System.out.println("Syncing user to Camunda: " + account.getEmail());

                    // Create User
                    User camundaUser = identityService.newUser(account.getEmail());
                    camundaUser.setPassword("123456"); // Default password for migrated users
                    camundaUser.setFirstName(account.getUsername());
                    camundaUser.setLastName("Lab4");
                    camundaUser.setEmail(account.getEmail());
                    identityService.saveUser(camundaUser);

                    // Add to Group
                    if (account.getRole().toString().equals("ADMIN")) {
                        identityService.createMembership(account.getEmail(), "camunda-admin");
                    } else if (account.getRole().toString().equals("USER")) {
                        identityService.createMembership(account.getEmail(), "USER");
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to sync user: " + account.getEmail() + " - " + e.getMessage());
            }
        }
    }
}
