package niffler.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.jsonunit.JsonPatchMatcher;
import lombok.experimental.UtilityClass;
import net.javacrumbs.jsonunit.core.Option;
import org.opentest4j.AssertionFailedError;

@UtilityClass
public class AssertUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void assertEquals(Object expected, Object actual, String... ignorable) {
        String expectedNode = objectMapper.valueToTree(expected).toPrettyString();
        String actualNode = objectMapper.valueToTree(actual).toPrettyString();
        if (!JsonPatchMatcher.jsonEquals(expectedNode)
                .whenIgnoringPaths(ignorable).when(Option.IGNORING_ARRAY_ORDER).matches(actualNode)) {
            throw new AssertionFailedError("errore", expectedNode, actualNode);
        }
    }
}
