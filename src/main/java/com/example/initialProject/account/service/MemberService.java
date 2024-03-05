package com.example.initialProject.account.service;

import com.example.initialProject.account.model.domain.Member;
import com.example.initialProject.account.repository.MemberRepository;
import com.example.initialProject.common.exception.filter.constant.ExceptionCodeEnum;
import com.example.initialProject.common.exception.filter.model.common.ExceptionObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    /**
     * 중복 회원 검증
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new ExceptionObject(ExceptionCodeEnum.DuplicatedMemberException);
        }
    }
}
