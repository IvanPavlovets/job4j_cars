package ru.job4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "auto_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String text;
    private LocalDateTime created = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    /**
     * список изменения цен.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_post_id")
    private List<PriceHistory> priceHistories = new ArrayList<>();

    /**
     * Подписка на обьявление.
     * Пользователи User) подписавшиеся
     * на изменение цены обьявления(Post).
     */
    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> participates = new HashSet<>();

}
