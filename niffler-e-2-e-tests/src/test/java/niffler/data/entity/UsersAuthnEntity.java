package niffler.data.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import niffler.model.UserAuthn;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class UsersAuthnEntity {

private UUID id;
private String username;
private String password;
private Boolean enabled;
private Boolean account_non_expired;
private Boolean account_non_locked;
private Boolean credentials_non_expired;

public static UsersAuthnEntity fromUserAuthn(UserAuthn user) {
    UsersAuthnEntity usersAuthnEntity = new UsersAuthnEntity();
    usersAuthnEntity
            .setId(user.getId())
            .setUsername(user.getUsername())
            .setPassword(user.getPassword())
            .setEnabled(user.getEnabled())
            .setAccount_non_expired(user.getAccount_non_expired())
            .setAccount_non_locked(user.getAccount_non_locked())
            .setCredentials_non_expired(user.getCredentials_non_expired());
    return usersAuthnEntity;
}

}
