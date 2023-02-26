package niffler.data.dao;

import niffler.data.entity.SpendsEntity;
import niffler.data.jdbc.DataSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static niffler.data.DataBase.SPEND;

public class PostgresJdbcSpendsDAO implements SpendsDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PostgresJdbcSpendsDAO.class);
    private final DataSource ds = DataSourceContext.INSTANCE.getDatatSource(SPEND);


    @Override
    public void updatesSpendIdByUsernameAndDescription(SpendsEntity spendsEntity) {
        try (Connection con = ds.getConnection();
             Statement st = con.createStatement()) {
            String sql = String.format("SELECT * FROM spends WHERE username = '%s' and description = '%s';",
                    spendsEntity.getUsername(), spendsEntity.getDescription());
            ResultSet resultSet = st.executeQuery(sql);
            if (resultSet.next()) {
                spendsEntity.setId(resultSet.getString("id"));
            } else {
                String msg = "Can`t find user by username: " + spendsEntity.getUsername();
                LOG.error(msg);
                throw new RuntimeException(msg);
            }

        } catch (SQLException e) {
            LOG.error("Error while database operation", e);
            throw new RuntimeException(e);
        }
    }
}
