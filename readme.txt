1. Первое ДЗ

предусловие:
    - создать категорию:
        insert into categories (description) values ('Test');
    - создать пользователя:
        login: qwerty
тест:
    niffler.test.SpendTest.addSpend
    - описание:
            выполнить создание Spend через апи;
            выполнить получение списка Spends через апи;
            найти созданный Spend.
    - условия:
            niffler.jupiter.BeforeSuiteExtension.beforeAllTests - прописываем первоначальные настройки restassured

1. Второе ДЗ
тест 1:
    niffler.test.UpdateUserTest.updateUserData
    - описание:
            выполнить адейт пользователя qwerty через апи;
            сравнить ответ с бд;
тест 2:
    niffler.test.LoginTest.mainPageShouldBeDisplayedAfterSuccessLogin
    - описание:
                логин нескольких пользователей с использованием UsersListExtension ;


