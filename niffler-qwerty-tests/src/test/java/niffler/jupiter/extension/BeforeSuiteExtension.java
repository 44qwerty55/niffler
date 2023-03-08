package niffler.jupiter.extension;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.persistence.EntityManagerFactory;
import niffler.data.jpa.EmfContext;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.config.DecoderConfig.decoderConfig;

public class BeforeSuiteExtension implements AroundAllTestsExtension {

    @Override
    public void beforeAllTests(ExtensionContext context) {
        RestAssured.config = RestAssured.config().decoderConfig(decoderConfig().defaultContentCharset(StandardCharsets.UTF_8));
        RestAssured.filters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        Awaitility.setDefaultPollDelay(1, TimeUnit.SECONDS);
        Awaitility.setDefaultPollInterval(5, TimeUnit.SECONDS);
        Awaitility.setDefaultTimeout(60, TimeUnit.SECONDS);
        System.out.println("BEFORE SUITE");
    }

    @Override
    public void afterAllTests() {
        EmfContext.INSTANCE.storedEmf().forEach(EntityManagerFactory::close);
        System.out.println("AFTER SUITE!");
    }
}
