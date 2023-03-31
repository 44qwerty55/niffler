package niffler.data.spring_jdbc;

import niffler.data.entity.UsersAuthnEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthnRowMapper implements RowMapper<UsersAuthnEntity> {

    @Override
    public UsersAuthnEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        UsersAuthnEntity result = new UsersAuthnEntity();
        result.setId(UUID.fromString(resultSet.getString("id")))
                .setAccountNonExpired(resultSet.getBoolean("account_non_expired"))
                .setAccountNonLocked(resultSet.getBoolean("account_non_locked"))
                .setCredentialsNonExpired(resultSet.getBoolean("credentials_non_expired"))
                .setEnabled(resultSet.getBoolean("enabled"));
        return result;
    }
}
