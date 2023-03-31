package niffler.test.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import niffler.data.dao.AuthnDAO;
import niffler.data.entity.AuthoritiesAuthnEntity;
import niffler.data.entity.UsersAuthnEntity;
import niffler.generators.GeneratorUserAuthn;
import niffler.jupiter.extension.DAOResolverAuthn;
import niffler.jupiter.annotation.DAO;
import niffler.model.UserAuthn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.$;


@ExtendWith({DAOResolverAuthn.class})
public class UserAuthnTest extends BaseTest {

    private UserAuthn user;
    @DAO
    private AuthnDAO authnDAO;


    @BeforeEach
    void addUserDataBeforeTest() {
        user = new GeneratorUserAuthn().GenerateUserWithReadAuthority();
        authnDAO.createUserWithAuthorities(UsersAuthnEntity.fromUserAuthn(user), AuthoritiesAuthnEntity.fromUserAuthn(user));
    }

    @Test
    void checkCurrencyTest() {
        Selenide.open("http://127.0.0.1:3000");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(user.getUsername());
        $("input[name='password']").setValue(user.getPassword());
        $("button[type='submit']").click();
        $(".header__title").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Niffler. The coin keeper."));
    }
}
