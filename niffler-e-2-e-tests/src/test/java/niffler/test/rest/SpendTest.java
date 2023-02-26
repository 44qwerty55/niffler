package niffler.test.rest;

import niffler.api.service.SpendClient;
import niffler.jupiter.extension.DAOResolverSpend;
import niffler.jupiter.extension.WithSpend;
import niffler.model.SpendDto;
import niffler.test.web.BaseTest;
import niffler.utils.AssertUtil;
import org.apache.http.HttpStatus;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith({DAOResolverSpend.class})
public class SpendTest extends BaseTest {

    private final SpendClient client = new SpendClient();

    @WithSpend(amount = 2500.0, username = "qwerty")
    private SpendDto spendDto;

    @Test
    @DisplayName("spends Корректный поиск созданного spend по пользователю")
    void addSpend() {
        Awaitility.await().untilAsserted(() -> {
            List<SpendDto> actualList = client
                    .getSpend(spendDto)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", SpendDto.class);
            SpendDto actual = actualList.stream().filter(s -> s.getDescription().equals(spendDto.getDescription())).findFirst().orElse(null);
            AssertUtil.assertEquals(actual, spendDto, "spendDate", "id");
        });
    }
}
