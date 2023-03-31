package niffler.data.dao;

import niffler.data.entity.AuthoritiesAuthnEntity;
import niffler.data.entity.UsersAuthnEntity;

import java.util.List;

public interface AuthnDAO extends DAO {

   void createUserWithAuthorities(UsersAuthnEntity users, List<AuthoritiesAuthnEntity> authoritiesAuthnEntities);

    UsersAuthnEntity getUsersAuthnEntityByUsername(String username);

    }
