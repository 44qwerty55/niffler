package niffler.test.rest;

import niffler.data.dao.UsersDAO;
import niffler.data.entity.UsersEntity;
import niffler.generators.UserGenerator;
import niffler.jupiter.annotation.DAO;
import niffler.jupiter.extension.DAOResolverUsers;
import niffler.test.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith({DAOResolverUsers.class})
public class FriendsTest extends BaseTest {

    private UsersEntity user, friend1, friend2;
    @DAO()
    private UsersDAO usersDAO;

    @BeforeEach
    void addUserDataBeforeTest() {
        user = UsersEntity.fromUserJson(UserGenerator.defaultSpend());
        friend1 = UsersEntity.fromUserJson(UserGenerator.defaultSpend());
        friend2 = UsersEntity.fromUserJson(UserGenerator.defaultSpend());
        usersDAO.addUsers(List.of(user, friend1, friend2));

    }

    @Test
    void friendsTest() {
        user.addFriends(friend1, friend2);
        usersDAO.updateUser(user);

        UsersEntity entity = usersDAO.getByUsername(user.getUsername());
        Assertions.assertEquals(2, entity.getFriends().size());
    }
}
