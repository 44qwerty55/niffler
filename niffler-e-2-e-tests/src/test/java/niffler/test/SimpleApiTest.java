package niffler.test;

import niffler.api.NifflerSpendClient;
import niffler.jupiter.Spend;
import niffler.model.SpendJson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SimpleApiTest {

    private NifflerSpendClient nifflerSpendClient = new NifflerSpendClient();

    @ValueSource(strings = {
            "data/spend0.json",
            "data/spend1.json"
    })
    @ParameterizedTest
    void addSpend(@Spend SpendJson spend) throws Exception {
        SpendJson created = nifflerSpendClient.createSpend(spend);
        Assertions.assertNotNull(created.getId());
    }
}
