package ru.job4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
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
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String login;
    private String password;

    @ToString.Exclude
    @ManyToMany(mappedBy = "participates")
    private Set<Post> participates = new HashSet<>();

    public void addParticipant(Post post) {
        this.participates.add(post);
        post.getParticipates().add(this);
    }

    public void removeParticipant(Post post) {
        this.participates.remove(post);
        post.getParticipates().remove(this);
    }

}
