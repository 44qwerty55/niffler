package niffler.generators;

import niffler.model.CurrencyValues;
import niffler.model.SpendDto;
import niffler.model.SpendsCategories;
import niffler.model.UserJson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class UserGenerator {

    public static UserJson defaultSpend() {
        UserJson user = new UserJson();
        user.setUserName("qwerty".concat(random(10000, 99999).toString()))
                .setFirstname("qwerty".concat(random(10000, 99999).toString()))
                .setSurname("qwerty".concat(random(10000, 99999).toString()))
                .setCurrency(CurrencyValues.RUB);
        return user;
    }

    private static Integer random(int min, int max) {
        max -= min;
        return ((int) (Math.random() * ++max) + min);
    }

}
