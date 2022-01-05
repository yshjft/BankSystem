package com.bankSystem.BankSystem.domain.user;

import com.bankSystem.BankSystem.web.dto.user.join.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserJpaRepository {
    private final EntityManager em;

    public long isExist(String email) {
        return em.createQuery("select count(user) from User user where user.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public User findUserByEmail(String email) {
        return em.createQuery("select user from User user where user.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public User findUserById(Long id) {
        return em.find(User.class, id);
    }

    public User save(UserJoinRequestDto userJoinRequestDto) {
        User newUser = userJoinRequestDto.toEntity();
        em.persist(newUser);
        return newUser;
    }

    // 탈퇴

}
