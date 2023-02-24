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

    private static final Logger LOG = LoggerFactory.getLogger(PostgresJdbcAuthnDAO.class);
    private final DataSource ds = DataSourceContext.INSTANCE.getDatatSource(AUTH);

    @Override
    public void createUser(UsersAuthnEntity user, List<AuthoritiesAuthnEntity> authoritiesAuthnEntities) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());

        try (Connection con = ds.getConnection();
            Statement st = con.createStatement()) {
            String sqlUser = String.format("INSERT INTO users (username, \"password\", enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES ('%s', '%s', '%s','%s','%s','%s' );",
                    user.getUsername(), password, user.getEnabled(), user.getAccount_non_expired(),
                    user.getAccount_non_locked(), user.getCredentials_non_expired());
            st.addBatch(sqlUser);
            st.executeBatch();

            user.setId(getUUIDUsername(user.getUsername()));

            authoritiesAuthnEntities.forEach(au -> {
                au.setUser_id(user.getId());
                String sqlAuthorities = String.format("INSERT INTO authorities (user_id, authority) VALUES ('%s','%s');",
                        au.getUser_id(), au.getAuthorities());
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


    private UUID getUUIDUsername(String username) {
        try (Connection con = ds.getConnection();
             Statement st = con.createStatement()) {
            String sql = "SELECT * FROM users WHERE username = '" + username + "';";
            ResultSet resultSet = st.executeQuery(sql);
            if (resultSet.next()) {
                return UUID.fromString(resultSet.getString("id"));
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