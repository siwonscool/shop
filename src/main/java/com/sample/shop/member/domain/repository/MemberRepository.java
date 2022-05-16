package com.sample.shop.member.domain.repository;

import com.sample.shop.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Query("update Member m set m.memberStatus = 'ACTIVATE' where m.memberStatus = 'READY'")
    @Modifying
    void updateMemberStatusActivate();

    @Query("update Member m set m.memberStatus = 'WITHDRAWAL' where m.id = :id")
    @Modifying
    void updateMemberStatusWITHDRAWAL(Long id);
}
