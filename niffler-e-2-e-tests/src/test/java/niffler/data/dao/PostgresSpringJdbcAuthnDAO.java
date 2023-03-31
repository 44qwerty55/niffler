package niffler.data.dao;

import niffler.data.entity.AuthoritiesAuthnEntity;
import niffler.data.entity.UsersAuthnEntity;
import niffler.data.jdbc.DataSourceContext;
import niffler.data.spring_jdbc.AuthnRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static niffler.data.DataBase.AUTH;

public class PostgresSpringJdbcAuthnDAO implements AuthnDAO {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceContext.INSTANCE.getDatatSource(AUTH));

    @Override
    public void createUserWithAuthorities(UsersAuthnEntity user, List<AuthoritiesAuthnEntity> authoritiesAuthnEntities) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());

        jdbcTemplate.update("INSERT INTO users (username, \"password\", enabled, account_non_expired, account_non_locked," +
                        "credentials_non_expired) VALUES (?, ?, ?, ?, ?, ?);",
                user.getUsername(), password, user.getEnabled(), user.getAccountNonExpired(),
                user.getAccountNonLocked(), user.getCredentialsNonExpired());

        user.setId(getUsersAuthnEntityByUsername(user.getUsername()).getId());

        authoritiesAuthnEntities.forEach(au -> {
            au.setUserId(user.getId());
            jdbcTemplate.update("INSERT INTO authorities (user_id, authority) VALUES (?, ?);",
                    au.getUserId(), au.getAuthorities());
        });
    }

    @Override
    public UsersAuthnEntity getUsersAuthnEntityByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?",
                new AuthnRowMapper(),
                username);
    }
}


