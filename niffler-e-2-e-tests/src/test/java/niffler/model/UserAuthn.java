package niffler.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public class UserAuthn {

    private UUID id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean account_non_expired;
    private Boolean account_non_locked;
    private Boolean credentials_non_expired;
    private List<Authorities> authorities;

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Authorities {
        private UUID id;
        private UUID user_id;
        private AuthoritiesEnum authorities;

        public static List<Authorities> instance(UUID user_id, List<AuthoritiesEnum> authorities) {
            List<Authorities> listAuthorities = new ArrayList<>();
            authorities.forEach(au -> {
                Authorities authoritie = new Authorities();
                listAuthorities.add(authoritie
                        .setUser_id(user_id)
                        .setAuthorities(au));
            });
            return listAuthorities;
        }
    }

}
