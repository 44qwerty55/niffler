package niffler.test;

import niffler.api.NifflerUserDataClient;
import niffler.jupiter.UserData;
import niffler.model.UserDataJson;
import niffler.utils.DatabaseHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.AssertUtil;

public class UpdateUserTest extends BaseTest {

    private NifflerUserDataClient nifflerUserDataClient = new NifflerUserDataClient();
    private DatabaseHelper databaseHelper = new DatabaseHelper();

    @ValueSource(strings = {
            "data/userData0.json"
    })
    @ParameterizedTest
    void updateUserData(@UserData UserDataJson userData) throws Exception {
        UserDataJson expected = nifflerUserDataClient.updateUserData(userData);
        UserDataJson actual = databaseHelper.selectSelectUserData(expected.getUserName());
        AssertUtil.assertEquals(actual, expected);
    }
}
