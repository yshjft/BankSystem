package com.bankSystem.BankSystem.repository;


import com.bankSystem.BankSystem.domain.User;
import com.bankSystem.BankSystem.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public int isExist(String email) {
        return em.createQuery("select count(user) from User user where user.email = :email", Integer.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    // 회원 조회: 회원이 존재할 수 도 있고 존재하지 않을 수 도 있다.




    // 회원 가입
    public User save(UserSaveRequestDto userSaveRequestDto) {
        User newUser = userSaveRequestDto.toEntity();
        em.persist(newUser);
        return newUser;
    }


    // 정보 수정

    // 탈퇴

}
