package ru.job4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Клас пользователя
 * @Entity - указывает что модель можно сохранить в БД
 * @Table(name = "auto_user") - указывает на таблицу(схему) в бд
 * @Id - указывает какое поле в модели первичный ключ
 */
@Entity
@Table(name = "auto_user")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String login;
    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Post> posts = new HashSet<>();

    public void addPostWithPriseHistory(Post post, PriceHistory history) {
        post.getPriceHistories().add(history);
        this.posts.add(post);
        post.getUsers().add(this);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
        post.getUsers().remove(this);
    }

}
