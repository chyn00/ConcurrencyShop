package com.shop.concurrency.member.repository;

import com.shop.concurrency.member.model.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member save(Member member);
    @Query("select member from Member member where member.name = :name")
    List<Member> findByName(@Param("name") String name);
    Member findById(Long id);
}
