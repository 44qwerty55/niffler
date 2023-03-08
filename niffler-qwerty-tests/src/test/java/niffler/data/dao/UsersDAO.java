package niffler.data.dao;

import niffler.data.entity.UsersEntity;

import java.util.List;

public interface UsersDAO extends DAO {
    int addUser(UsersEntity users);

    int addUsers(List<UsersEntity> users);

    void updateUser(UsersEntity user);

    void remove(UsersEntity user);

    UsersEntity getByUsername(String username);
}
