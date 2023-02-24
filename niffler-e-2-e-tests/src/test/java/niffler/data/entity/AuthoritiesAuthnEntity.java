package niffler.data.entity;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import niffler.model.UserAuthn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class AuthoritiesAuthnEntity {
    private UUID id;
    private UUID user_id;
    private String authorities;

    public static List<AuthoritiesAuthnEntity> fromUserAuthn(UserAuthn user) {
        List<AuthoritiesAuthnEntity> authoritiesAuthnEntities = new ArrayList<>();
        user.getAuthorities().forEach(au -> {
            AuthoritiesAuthnEntity authoritiesAuthnEntity = new AuthoritiesAuthnEntity();
            authoritiesAuthnEntities.add(authoritiesAuthnEntity
                    .setId(au.getId())
                    .setUser_id(au.getUser_id())
                    .setAuthorities(au.getAuthorities().AuthoritiesEnum));
        });
        return authoritiesAuthnEntities;
    }
}
