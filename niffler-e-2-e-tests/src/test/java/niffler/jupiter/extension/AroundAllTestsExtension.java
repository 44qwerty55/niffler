package niffler.jupiter.extension;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface AroundAllTestsExtension extends BeforeAllCallback {

    default void beforeAllTests(ExtensionContext context) {
        Configuration.browserSize = "1920x1080";
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        desiredCapabilities.setAcceptInsecureCerts(true);
        Configuration.browserCapabilities = desiredCapabilities;
    }

    default void afterAllTests() {
    }

    @Override
    default void beforeAll(ExtensionContext context) throws Exception {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).
                getOrComputeIfAbsent(this.getClass(),
                        k -> {
                            beforeAllTests(context);
                            return (ExtensionContext.Store.CloseableResource) this::afterAllTests;
                        }
                );
    }
}
