package niffler.jupiter.extension;

import niffler.api.service.SpendClient;
import niffler.data.dao.PostgresJdbcSpendsDAO;
import niffler.data.entity.SpendsEntity;
import niffler.generators.SpendGenerator;
import niffler.model.SpendDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class WithSpendExtension implements BeforeEachCallback, AfterEachCallback {

    public static final Namespace NAMESPACE = Namespace.create(WithSpendExtension.class);
    private static final SpendClient client = new SpendClient();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Object testInstance = extensionContext.getRequiredTestInstance();
        List<Field> fields = Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(f -> f.getType().isAssignableFrom(SpendDto.class))
                .filter(f -> f.isAnnotationPresent(WithSpend.class))
                .toList();

        List<SpendDto> spends = new ArrayList<>();
        for (Field field : fields) {
            WithSpend annotation = field.getAnnotation(WithSpend.class);
            SpendDto spendDto = SpendGenerator.defaultSpend();
            spendDto
                    .setAmount((int) annotation.amount())
                    .setUsername(annotation.username());

            field.setAccessible(true);
            field.set(testInstance, spendDto);
            client.postSpend(spendDto).assertThat().statusCode(HttpStatus.SC_CREATED);
            spends.add(spendDto);
        }

        String testIdentifier = getTestIdentifier(extensionContext);
        extensionContext.getStore(NAMESPACE).put(testIdentifier, spends);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext)  {
        String testIdentifier = getTestIdentifier(extensionContext);
        List<SpendDto> spends = extensionContext.getStore(NAMESPACE).get(testIdentifier, List.class);
        spends.forEach(it -> {
            SpendsEntity spendsEntity = SpendsEntity.fromSpendDto(it);
            new PostgresJdbcSpendsDAO().updatesSpendIdByUsernameAndDescription(spendsEntity);
            it.setId(spendsEntity.getId());
            client.deleteSpend(it);
        });
    }

    private String getTestIdentifier(ExtensionContext extensionContext) {
        return extensionContext.getRequiredTestClass().getName() + ":" +
                extensionContext.getRequiredTestMethod().getName();
    }


}
