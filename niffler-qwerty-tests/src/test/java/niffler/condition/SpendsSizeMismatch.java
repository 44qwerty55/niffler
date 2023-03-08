package niffler.condition;

import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.CollectionSource;
import niffler.model.SpendDto;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static java.lang.System.lineSeparator;

@ParametersAreNonnullByDefault
public class SpendsSizeMismatch extends UIAssertionError {
    public SpendsSizeMismatch(CollectionSource collection,
                              List<SpendDto> expectedSpends, List<SpendDto> actualSpends,
                              @Nullable String explanation, long timeoutMs) {
        super(
                collection.driver(),
                "Spending size mismatch" +
                        lineSeparator() + "Actual: " + actualSpends + ", List size: " + actualSpends.size() +
                        lineSeparator() + "Expected: " + expectedSpends + ", List size: " + expectedSpends.size() +
                        (explanation == null ? "" : lineSeparator() + "Because: " + explanation) +
                        lineSeparator() + "Collection: " + collection.description(),
                expectedSpends, actualSpends,
                timeoutMs
        );
    }
}
