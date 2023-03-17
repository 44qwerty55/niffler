package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.*;
import niffler.model.SpendDto;
import niffler.model.UserJson;
import niffler.page.MainPage;
import niffler.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import static niffler.jupiter.extension.CreateUserExtension.Selector.NESTED;

@Execution(ExecutionMode.SAME_THREAD)
public class SpendingTest extends BaseTest {

    @Test
    @AllureId("51")
    @ResourceLock("spend")
    @ApiLogin(username = "qwerty", password = "1234")
    void deleteSpendingTest() {
        Selenide.open(MainPage.URL, MainPage.class)
                .getSpendingTable()
                .deleteSpending()
                .checkTable();
    }

    @Test
    @AllureId("52")
    @ApiLogin(nifflerUser = @GenerateUser(categories = {@GenerateCategory("Test")},
            spends = {@GenerateSpend(category = "Test",  amount = 3, description = "Test Description")}))
    void checkSpendingIsPresent(@User(selector = NESTED) UserJson user) {
        SpendDto expectedSpend = user.getSpendJsons().get(0);
        Selenide.open(MainPage.URL, MainPage.class)
                .getSpendingTable()
                .checkTableContains(expectedSpend);
    }
}
