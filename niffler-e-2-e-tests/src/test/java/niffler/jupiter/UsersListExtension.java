package niffler.jupiter;

import io.qameta.allure.AllureId;
import niffler.model.UserModel;
import org.junit.jupiter.api.extension.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UsersListExtension implements
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UsersListExtension.class);

    private static final Queue<UserModel> USER_MODEL_ADMIN_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<UserModel> USER_MODEL_COMMON_QUEUE = new ConcurrentLinkedQueue<>();

    static {
        USER_MODEL_ADMIN_QUEUE.add(new UserModel("dima", "12345"));
        USER_MODEL_COMMON_QUEUE.add(new UserModel("bill", "12345"));
        USER_MODEL_COMMON_QUEUE.add(new UserModel("test", "test"));
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String id = getTestId(context);
        List<User.UserType> userTypes = new ArrayList<>();

        Arrays.stream(context.getRequiredTestMethod().getParameters())
                .forEach(it -> {
                    if (it.isAnnotationPresent(User.class)) {
                        userTypes.add(it.getAnnotation(User.class).userType());
                    }
                });

        Map<User.UserType, UserModel> users = new HashMap<>();
        userTypes.forEach(it -> {
            UserModel user = null;
            while (user == null) {
                if (it == User.UserType.ADMIN) {
                    user = USER_MODEL_ADMIN_QUEUE.poll();
                } else {
                    user = USER_MODEL_COMMON_QUEUE.poll();
                }
            }
            Objects.requireNonNull(user);
            users.put(it, user);
        });
        context.getStore(NAMESPACE).put(id, users);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        String id = getTestId(context);
        Map<User.UserType, UserModel> users = context.getStore(NAMESPACE).get(id, Map.class);
        for (Map.Entry<User.UserType, UserModel> user : users.entrySet()) {
            if (user.getKey().equals(User.UserType.ADMIN)) {
                USER_MODEL_ADMIN_QUEUE.add(user.getValue());
            } else {
                USER_MODEL_COMMON_QUEUE.add(user.getValue());
            }
        }
    }

    private String getTestId(ExtensionContext context) {
        return Objects.requireNonNull(
                context.getRequiredTestMethod().getAnnotation(AllureId.class)
        ).value();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserModel.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserModel resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        String id = getTestId(extensionContext);
        return (UserModel) extensionContext.getStore(NAMESPACE).get(id, Map.class)
                .get(parameterContext.getParameter().getDeclaredAnnotation(User.class).userType());
    }
}
