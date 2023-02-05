package niffler.utils;

import lombok.SneakyThrows;
import niffler.model.UserDataJson;

import java.sql.*;
import java.util.UUID;

public class DatabaseHelper {


    private static Connection connectionUserData;
    private static String DB_USERDATA_URL = "jdbc:postgresql://localhost:5432/niffler-userdata";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "secret";


    private static void connectUserData() throws SQLException {
        connectionUserData = DriverManager.getConnection(DB_USERDATA_URL, DB_USER, DB_PASSWORD);
    }

    @SneakyThrows
    public UserDataJson selectSelectUserData(String username) {
        connectUserData();
        Statement st = connectionUserData.createStatement();
        ResultSet rs = st.executeQuery("select * from users where username = '" + username + "'");
        UserDataJson userDataJson = new UserDataJson();
        while (rs.next()) {
            userDataJson
                    .setId(UUID.fromString(rs.getString("id")))
                    .setUserName(username)
                    .setFirstname(rs.getString("firstname"))
                    .setSurname(rs.getString("surname"))
                    .setCurrency(rs.getString("currency"));
        }
        rs.close();
        st.close();
        connectionUserData.close();
        return userDataJson;
    }
}
