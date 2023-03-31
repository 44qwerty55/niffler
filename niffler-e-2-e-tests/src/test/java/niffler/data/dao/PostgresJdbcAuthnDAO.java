package niffler.data.dao;

import niffler.data.jdbc.DataSourceContext;
import niffler.data.entity.AuthoritiesAuthnEntity;
import niffler.data.entity.UsersAuthnEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import static niffler.data.DataBase.AUTH;

public class PostgresJdbcAuthnDAO implements AuthnDAO {

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private static final Logger LOG = LoggerFactory.getLogger(PostgresJdbcAuthnDAO.class);
    private final DataSource ds = DataSourceContext.INSTANCE.getDatatSource(AUTH);

    @Override
    public void createUserWithAuthorities(UsersAuthnEntity user, List<AuthoritiesAuthnEntity> authoritiesAuthnEntities) {

        String password = passwordEncoder.encode(user.getPassword());

        try (Connection con = ds.getConnection();
             Statement st = con.createStatement()) {
            String sqlUser = String.format("INSERT INTO users (username, \"password\", enabled, account_non_expired, account_non_locked, credentials_non_expired)" +
                            " VALUES ('%s', '%s', '%s','%s','%s','%s' );",
                    user.getUsername(), password, user.getEnabled(), user.getAccountNonExpired(),
                    user.getAccountNonLocked(), user.getCredentialsNonExpired());
            st.addBatch(sqlUser);
            st.executeBatch();

            user.setId(getUsersAuthnEntityByUsername(user.getUsername()).getId());

            authoritiesAuthnEntities.forEach(au -> {
                au.setUserId(user.getId());
                String sqlAuthorities = String.format("INSERT INTO authorities (user_id, authority) VALUES ('%s','%s');",
                        au.getUserId(), au.getAuthorities());
                try {
                    st.addBatch(sqlAuthorities);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        } catch (SQLException e) {
            LOG.error("Error while database operation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public UsersAuthnEntity getUsersAuthnEntityByUsername(String username) {
        UsersAuthnEntity usersAuthnEntity = new UsersAuthnEntity();
        try (Connection con = ds.getConnection();
             Statement st = con.createStatement()) {
            String sql = "SELECT * FROM users WHERE username = '" + username + "';";
            ResultSet resultSet = st.executeQuery(sql);
            if (resultSet.next()) {
                return usersAuthnEntity
                        .setId(UUID.fromString(resultSet.getString("id")))
                        .setAccountNonExpired(resultSet.getBoolean("account_non_expired"))
                        .setAccountNonLocked(resultSet.getBoolean("account_non_locked"))
                        .setCredentialsNonExpired(resultSet.getBoolean("credentials_non_expired"))
                        .setEnabled(resultSet.getBoolean("enabled"));
            } else {
                String msg = "Can`t find user by username: " + username;
                LOG.error(msg);
                throw new RuntimeException(msg);
            }

        } catch (SQLException e) {
            LOG.error("Error while database operation", e);
            throw new RuntimeException(e);
        }

    }
}
