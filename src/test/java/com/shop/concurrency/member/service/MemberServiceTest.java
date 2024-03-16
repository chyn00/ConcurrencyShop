package com.shop.concurrency.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    public void createMember() throws Exception {
        //given
        Member member = Member.builder().name("kim").build();

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findById(savedId));
    }
}