package niffler.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import niffler.model.CurrencyValues;
import niffler.model.UserJson;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users", schema = "public", catalog = "niffler-userdata")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyValues currency;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String surname;

    @Column(name = "photo")
    private byte[] photo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<UsersEntity> friends = new ArrayList<>();

    public UsersEntity addFriends(UsersEntity... friends) {
        this.friends.addAll(Arrays.asList(friends));
        return this;
    }

    public static UsersEntity fromUserJson(UserJson user) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername(user.getUserName())
                .setFirstname(user.getFirstname())
                .setSurname(user.getSurname())
                .setCurrency(user.getCurrency());
        return usersEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(currency, that.currency) && Objects.equals(firstname, that.firstname) && Objects.equals(surname, that.surname) && Arrays.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, currency, firstname, surname);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
