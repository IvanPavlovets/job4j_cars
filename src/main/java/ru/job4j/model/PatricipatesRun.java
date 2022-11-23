package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class PatricipatesRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

            var user = new User();
            user.setLogin("Ivan1");
            user.setPassword("password1");
            create(user, sf);

            var post = new Post();
            post.setText("car sale1");
            post.setPriceHistories(
                    List.of(new PriceHistory(0, BigInteger.ONE, BigInteger.ONE, LocalDateTime.now()),
                            new PriceHistory(0, BigInteger.ONE, BigInteger.ONE, LocalDateTime.now())
                    )
            );
            post.setUser(user);
            post.addParticipant(user);
            create(post, sf);

            sf.openSession()
                    .createQuery("from Post where id = :fId", Post.class)
                    .setParameter("fId", post.getId())
                    .getSingleResult()
                    .getParticipates()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static void update(Post post, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(post);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Post post = new Post();
        post.setId(id);
        session.delete(post);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Post> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Post").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static Post findById(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Post result = session.get(Post.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }


}
