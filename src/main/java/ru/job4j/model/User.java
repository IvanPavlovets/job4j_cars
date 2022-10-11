package ru.job4j.model;

import lombok.Data;

import javax.persistence.*;

@Entity /* указывает что модель можно сохранить в БД */
@Table(name = "auto_user") /* указывает на таблицу(схему) в бд*/
@Data
public class User {
    @Id /* указывает какое поле в модели первичный ключ */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
}
