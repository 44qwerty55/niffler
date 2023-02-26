package niffler.data.dao;

import niffler.data.entity.SpendsEntity;

public interface SpendsDAO extends DAO {

    void updatesSpendIdByUsernameAndDescription(SpendsEntity spend);
}
