package niffler.test;

import niffler.api.NifflerSpendClient;
import niffler.api.NifflerUserDataClient;
import niffler.jupiter.Spend;
import niffler.jupiter.UserData;
import niffler.model.SpendJson;
import niffler.model.UserDataJson;
import niffler.utils.DatabaseHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.AssertUtil;

public class SimpleApiTest {

    private NifflerSpendClient nifflerSpendClient = new NifflerSpendClient();
    private NifflerUserDataClient nifflerUserDataClient = new NifflerUserDataClient();
    private DatabaseHelper databaseHelper = new DatabaseHelper();

    @ValueSource(strings = {
            "data/spend0.json",
            "data/spend1.json"
    })
    @ParameterizedTest
    void addSpend(@Spend SpendJson spend) throws Exception {
        SpendJson created = nifflerSpendClient.createSpend(spend);
        Assertions.assertNotNull(created.getId());
    }

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
