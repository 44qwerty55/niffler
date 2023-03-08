package niffler.generators;
import niffler.model.SpendDto;
import niffler.model.CurrencyValues;
import niffler.model.SpendsCategories;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class SpendGenerator {

    public static SpendDto defaultSpend() {
        SpendDto spend = new SpendDto();
        spend.setUsername("qwerty")
                .setAmount(random(1000, 9999).doubleValue())
                .setCategory(SpendsCategories.Test.spendsCategories)
                .setCurrency(CurrencyValues.RUB)
                .setSpendDate(new Date())
                .setDescription("test".concat(random(10000, 99999).toString()));
        return spend;
    }

    private static Integer random(int min, int max) {
        max -= min;
        return ((int) (Math.random() * ++max) + min);
    }

    public static String getUTCdatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }
}
