package com.bankSystem.BankSystem.domain.user;


import com.bankSystem.BankSystem.api.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public long isExist(String email) {
        return em.createQuery("select count(user) from User user where user.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    // 회원 조회: 회원이 존재할 수 도 있고 존재하지 않을 수 도 있다.

    // 회원 가입
    public User save(UserDto userDto) {
        User newUser = userDto.toEntity();
        em.persist(newUser);
        return newUser;
    }

    // 정보 수정

    // 탈퇴

}
