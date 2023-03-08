package niffler.test.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.UserLogin;
import niffler.jupiter.extension.UsersListExtension;
import niffler.model.UserModel;
import niffler.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static niffler.jupiter.annotation.UserLogin.UserType.ADMIN;
import static niffler.jupiter.annotation.UserLogin.UserType.COMMON;


@ExtendWith({UsersListExtension.class})
public class LoginUsersTest extends BaseTest {

    @AllureId("1")
    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin(@UserLogin(userType = ADMIN) UserModel userAdmin,
                                                    @UserLogin(userType = COMMON) UserModel userCommonFirst,
                                                    @UserLogin(userType = COMMON) UserModel userCommonSecond) {
        List<UserModel> users = List.of(userAdmin, userCommonFirst, userCommonSecond);
        users.forEach(data -> {
            System.out.println("#### Test 1 " + data.toString());
            Allure.step("Check login", () -> login(data));
        });
    }

    @AllureId("2")
    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin0(@UserLogin(userType = ADMIN) UserModel user,
                                                     @UserLogin(userType = COMMON) UserModel userCommon) {
        List<UserModel> users = List.of(user, userCommon);
        users.forEach(data -> {
        System.out.println("#### Test 2 " + user.toString());
        Allure.step("Check login", () -> login(user));
        });
    }

    @AllureId("3")
    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin1(@UserLogin(userType = ADMIN) UserModel user) {
        System.out.println("#### Test 3 " + user.toString());
        Allure.step("Check login", () -> login(user));
    }

    @AllureId("4")
    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin4(@UserLogin(userType = ADMIN) UserModel user) {
        System.out.println("#### Test 4 " + user.toString());
        Allure.step("Check login", () -> login(user));
    }

    private void login(UserModel user) {
        Selenide.open("http://127.0.0.1:3000/");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(user.getUsername());
        $("input[name='password']").setValue(user.getPassword());
        $("button[type='submit']").click();
        $(".header__title").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Niffler. The coin keeper."));
        Selenide.closeWebDriver();
    }
}
