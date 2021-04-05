package com.capstone2.EV_Sherpa.repository;

import com.capstone2.EV_Sherpa.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findByEmail(String email){
        return em.createQuery("select u from User as u where u.email= :email", User.class)
                .setParameter("email", email)
                .getResultList();
    }

    public User findOneByEmail(String email){
        return em.createQuery("select u from User as u where u.email= :email", User.class)
                .setParameter("email",email)
                .getSingleResult();
    }
}
