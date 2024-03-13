package com.shop.concurrency.utils.init;

import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialDataUtil {

    private final MemberService memberService;
    private final ItemService itemService;
    @PostConstruct
    public void createData(){
        // 회원 존재하는지 확인
        List<Member> members = memberService.findMembers();
        Member member = Member.builder().name("kim").build();
        boolean result = members.stream().anyMatch(it -> it.getName().equals("kim"));

        // 아이템 생성(/w code, quantity)
        itemService.makeItemWithCodeAndQuantity(82317341L,10);

        if(!result){
            // 회원 가입
            memberService.join(member);
        }

        System.out.println("Member Data가 생성되었습니다.");
        System.out.println("Order Data가 생성되었습니다.");
        System.out.println("Item Data가 생성되었습니다.");
    }
}
