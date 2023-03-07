package ru.job4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "model")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id", foreignKey = @ForeignKey(name = "BRAND_ID_FK"))
    private Brand brand;
}
