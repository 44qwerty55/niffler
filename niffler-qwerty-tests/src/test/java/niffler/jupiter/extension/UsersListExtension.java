package niffler.jupiter.extension;

import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.UserLogin;
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
        USER_MODEL_ADMIN_QUEUE.add(new UserModel("dima", "1234"));
        USER_MODEL_COMMON_QUEUE.add(new UserModel("qwerty", "1234"));
        USER_MODEL_COMMON_QUEUE.add(new UserModel("test", "1234"));
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String id = getTestId(context);
        List<UserLogin.UserType> userTypes = new ArrayList<>();

        Arrays.stream(context.getRequiredTestMethod().getParameters())
                .forEach(it -> {
                    if (it.isAnnotationPresent(UserLogin.class)) {
                        userTypes.add(it.getAnnotation(UserLogin.class).userType());
                    }
                });

        Map<UserLogin.UserType, List<UserModel>> users = new HashMap<>();
        List<UserModel> adminUserModel = new ArrayList<>();
        List<UserModel> commonUserModel = new ArrayList<>();
        userTypes.forEach(it -> {
            UserModel user = null;
            while (user == null) {
                switch (it) {
                    case ADMIN -> {
                        user = USER_MODEL_ADMIN_QUEUE.poll();
                        if (user != null) {
                            adminUserModel.add(user);
                        }
                    }
                    case COMMON -> {
                        user = USER_MODEL_COMMON_QUEUE.poll();
                        if (user != null) {
                            commonUserModel.add(user);
                        }
                    }
                }
            }
            if (!adminUserModel.isEmpty()) {
                users.put(UserLogin.UserType.ADMIN, adminUserModel);
            }
            if (!commonUserModel.isEmpty()) {
                users.put(UserLogin.UserType.COMMON, commonUserModel);
            }

        });
        context.getStore(NAMESPACE).put(id, users);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        String id = getTestId(context);
        Map<UserLogin.UserType, List<UserModel>> users = context.getStore(NAMESPACE).get(id, Map.class);
        users.forEach((key, value) -> {
            switch (key) {
                case ADMIN -> value.forEach(us -> {
                    us.setAvailable(true);
                    USER_MODEL_ADMIN_QUEUE.add(us);
                });

                case COMMON -> value.forEach(us -> {
                    us.setAvailable(true);
                    USER_MODEL_COMMON_QUEUE.add(us);
                });

            }
        });
    }

    private String getTestId(ExtensionContext context) {
        return Objects.requireNonNull(
                context.getRequiredTestMethod().getAnnotation(AllureId.class)
        ).value();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserModel.class)
                && parameterContext.getParameter().isAnnotationPresent(UserLogin.class);
    }

    @Override
    public UserModel resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        String id = getTestId(extensionContext);
        UserLogin.UserType role = parameterContext.getParameter().getAnnotation(UserLogin.class).userType();
        Map<UserLogin.UserType, List<UserModel>> users = extensionContext.getStore(NAMESPACE).get(id, Map.class);
        for (UserModel user : users.get(role)) {
            if (!user.isAvailable())
                continue;

            user.setAvailable(false);
            return user;
        }
        throw new RuntimeException("No user found with role " + role);
    }

}