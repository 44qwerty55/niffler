package niffler.jupiter.extension;

import niffler.data.dao.PostgresJdbcSpendsDAO;
import niffler.jupiter.annotation.DAO;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DAOResolverSpend implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        List<Field> fields = Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(DAO.class))
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());

        for (Field field : fields) {
            field.set(testInstance, new PostgresJdbcSpendsDAO());
        }
    }
}
