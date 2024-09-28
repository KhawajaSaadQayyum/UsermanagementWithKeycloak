package com.codeWithSaad.service;

import com.codeWithSaad.model.User;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Value("${app.keycloak.realm}")
   private String realm;
    private final Keycloak keycloak;

    public void createUser(User user) {

        UserRepresentation  userRepresentation= new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getUsername());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));

//        UsersResource usersResource = new getUsersResource();

        UsersResource usersResource =  getUsersResource();
        Response response = usersResource.create(userRepresentation);

        log.info("Status Code "+response.getStatus());
         log.info(realm+"Realm is here or not");
        if(!Objects.equals(201,response.getStatus())){

            throw new RuntimeException("Status code "+response.getStatus());
        }

        log.info("New user has bee created");


        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(user.getUsername(), true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        sendVerificationEmail(userRepresentation1.getId());
    }


    public void sendVerificationEmail(String userId) {

        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();

    }


    public void deleteUser(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);
    }


    public void forgotPassword(String username) {

        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        UserResource userResource = usersResource.get(userRepresentation1.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));


    }


    public UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }


    public List<RoleRepresentation> getUserRoles(String userId) {


        return getUser(userId).roles().realmLevel().listAll();
    }



    public List<GroupRepresentation> getUserGroups(String userId) {


        return getUser(userId).groups();
    }

    private UsersResource getUsersResource(){

        return keycloak.realm(realm).users();
    }

}

