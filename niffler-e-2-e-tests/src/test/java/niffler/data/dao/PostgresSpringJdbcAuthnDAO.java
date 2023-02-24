package niffler.data.dao;

import niffler.data.entity.AuthoritiesAuthnEntity;
import niffler.data.entity.UsersAuthnEntity;
import niffler.data.jdbc.DataSourceContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static niffler.data.DataBase.AUTH;

public class PostgresSpringJdbcAuthnDAO implements AuthnDAO {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceContext.INSTANCE.getDatatSource(AUTH));

    @Override
    public void createUser(UsersAuthnEntity user, List<AuthoritiesAuthnEntity> authoritiesAuthnEntities) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());

        jdbcTemplate.update("INSERT INTO users (username, \"password\", enabled, account_non_expired, account_non_locked," +
                        "credentials_non_expired) VALUES (?, ?, ?, ?, ?, ?);",
                user.getUsername(), password, user.getEnabled(), user.getAccount_non_expired(),
                user.getAccount_non_locked(), user.getCredentials_non_expired());

        user.setId(getUUIDUsername(user.getUsername()));

        authoritiesAuthnEntities.forEach(au -> {
            au.setUser_id(user.getId());
            jdbcTemplate.update("INSERT INTO authorities (user_id, authority) VALUES (?, ?);",
                    au.getUser_id(), au.getAuthorities());
        });
    }

    private UUID getUUIDUsername(String username) {
        String id = jdbcTemplate.queryForObject("SELECT id FROM users WHERE username = '".concat(username).concat("'"), String.class);
        assert id != null;
        return UUID.fromString(id);
    }
}


