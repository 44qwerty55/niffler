package niffler.condition;

import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.CollectionSource;
import niffler.model.SpendDto;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static java.lang.System.lineSeparator;

@ParametersAreNonnullByDefault
public class SpendsMismatch extends UIAssertionError {
    public SpendsMismatch(CollectionSource collection,
                          List<SpendDto> expectedSpends, List<SpendDto> actualSpends,
                          @Nullable String explanation, long timeoutMs) {
        super(
                collection.driver(),
                "Spending mismatch" +
                        lineSeparator() + "Actual: " + actualSpends +
                        lineSeparator() + "Expected: " + expectedSpends +
                        (explanation == null ? "" : lineSeparator() + "Because: " + explanation) +
                        lineSeparator() + "Collection: " + collection.description(),
                expectedSpends, actualSpends,
                timeoutMs);
    }
}
