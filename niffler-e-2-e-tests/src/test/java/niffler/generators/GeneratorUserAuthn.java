package niffler.generators;

import niffler.model.AuthoritiesEnum;
import niffler.model.UserAuthn;

import java.util.List;
import java.util.UUID;

public class GeneratorUserAuthn {

    public UserAuthn GenerateUserWithReadAuthority() {
        UserAuthn userAuthn = new UserAuthn();
        UUID userUUID = UUID.randomUUID();
        generateUser(userAuthn, userUUID);
        userAuthn.setAuthorities(UserAuthn.Authorities.instance(userUUID, List.of(AuthoritiesEnum.READ)));
        return userAuthn;
    }

    public UserAuthn GenerateUserWithReadAndWriteAuthority() {
        UserAuthn userAuthn = new UserAuthn();
        UUID userUUID = UUID.randomUUID();
        generateUser(userAuthn, userUUID);
        userAuthn.setAuthorities(UserAuthn.Authorities.instance(userUUID, List.of(AuthoritiesEnum.READ, AuthoritiesEnum.WRITE)));
        return userAuthn;
    }

    private void generateUser(UserAuthn userAuthn, UUID uuidUser) {
        userAuthn.setUsername("Qwerty".concat(random(10000, 99999).toString()))
                .setId(uuidUser)
                .setPassword("1")
                .setEnabled(true)
                .setAccount_non_expired(true)
                .setAccount_non_locked(true)
                .setEnabled(true)
                .setCredentials_non_expired(true);
    }

    private static Integer random(int min, int max) {
        max -= min;
        return ((int) (Math.random() * ++max) + min);
    }


}
