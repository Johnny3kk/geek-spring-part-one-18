package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(User user) {
       em.persist(user);
    }

    @Transactional
    public void update(User user) {
        em.merge(user);
    }

    @Transactional
    public void delete(Integer id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(id);
        }
    }

    public User findByLogin(String login) {
       return em.createQuery("from User where login = :login", User.class).setParameter("login", login).getSingleResult();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> getAllUsers() {
      return em.createQuery("from User", User.class).getResultList();
    }

}
