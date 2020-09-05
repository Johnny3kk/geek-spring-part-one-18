package ru.geekbrains;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        // INSERT
//        EntityManager em = emFactory.createEntityManager();
//        User user1 = new User(null, "alex", "alex");
//        User user2 = new User(null, "ivan", "ivan");
//        User user3 = new User(null, "petr", "petr");
//
//        em.getTransaction().begin();
//        em.persist(user1);
//        em.persist(user2);
//        em.persist(user3);
//        em.getTransaction().commit();
//
//        em.close();

        // SELECT
//        EntityManager em = emFactory.createEntityManager();
//
//        User user = em.find(User.class, 1);
//        System.out.println(user);
//
//        List<User> users = em.createQuery("from User", User.class).getResultList();
//        System.out.println(users);
//
//        user = em.createQuery("from User where login = :login", User.class)
//                .setParameter("login", "petr")
//                .getSingleResult();
//        System.out.println(user);

        // UPDATE
//        EntityManager em = emFactory.createEntityManager();
//
//        User user = em.find(User.class, 1);
//        System.out.println(user);
//
//        em.getTransaction().begin();
//        user.setPassword("new_password");
//        em.getTransaction().commit();
//
//        em.close();

        EntityManager em = emFactory.createEntityManager();

        User user = em.find(User.class, 1);
        Contact contact = new Contact(null, "mobile phone", "123456789", user);

        em.getTransaction().begin();
        em.persist(contact);
        em.getTransaction().commit();
    }
}
