package com.capstone2.EV_Sherpa.repository;

import com.capstone2.EV_Sherpa.domain.Preference;
import com.capstone2.EV_Sherpa.domain.User;
import com.capstone2.EV_Sherpa.domain.UserPreference;
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
    public void save(Preference preference){em.persist(preference);}
    public void save(UserPreference userPreference){em.persist(userPreference);}
    public void save(User user, Preference preference, UserPreference userPreference){
        em.persist(user); em.persist(preference); em.persist(userPreference);
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

    public UserPreference findOneByUserId(Long user_id){
        return em.createQuery("select u from UserPreference as u join fetch u.user_id a where a.id = :user_id",UserPreference.class)
                .setParameter("user_id", user_id)
                .getSingleResult();
    }


    public Preference findOneByPreferenceId(Long preference_id){
        return em.createQuery("select u from Preference as u where u.id= :preference_id",Preference.class)
                .setParameter("preference_id", preference_id)
                .getSingleResult();
    }
}
