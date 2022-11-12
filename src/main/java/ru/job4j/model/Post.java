package ru.job4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private String created = String.valueOf(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    /**
     * список изменения цен.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_post_id")
    private List<PriceHistory> priceHistories;

}
