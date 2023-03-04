package niffler.test.rest;

import niffler.api.NifflerUseDataClient;
import niffler.jupiter.annotation.UserData;
import niffler.model.UserDataJson;
import niffler.test.web.BaseTest;
import niffler.utils.AssertUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class UpdateUserTest extends BaseTest {

    private final NifflerUseDataClient nifflerUserDataClient = new NifflerUseDataClient();

    @ValueSource(strings = {
            "data/userData0.json",
            "data/userData1.json"
    })
    @ParameterizedTest
    void updateUserData(@UserData UserDataJson userData) throws Exception {
        UserDataJson actual = nifflerUserDataClient.updateUserData(userData);
        AssertUtil.assertEquals(userData, actual, "id");
    }
}
