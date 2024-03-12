package com.shop.concurrency.utils.init;

import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialDataUtil {

    private final MemberService memberService;
    @PostConstruct
    public void createData(){
        Member member = Member.builder().name("kim").build();
        memberService.join(member);
        System.out.println("Member Data가 생성되었습니다.");
        System.out.println("Order Data가 생성되었습니다.");
        System.out.println("Item Data가 생성되었습니다.");
    }
}
