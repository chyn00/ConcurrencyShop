package com.shop.concurrency.member.service;

import com.shop.concurrency.common.exception.filter.constant.ExceptionCodeEnum;
import com.shop.concurrency.common.exception.filter.model.common.ExceptionObject;
import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.repository.MemberRepository;
import com.shop.concurrency.order.domain.Orders;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public boolean createOrder(Member member, Orders order) {
        Member memberForUpdate = memberRepository.findById(member.getId());
        memberForUpdate.getOrders().add(order);
        memberRepository.save(memberForUpdate);

        return true;
    }
}
