package com.shop.concurrency.utils.init;

import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitialDataUtil {

    private final MemberService memberService;
    private final ItemService itemService;

    @PostConstruct
    public void createData() {
        Member member = Member.builder().name(Long.toString(Math.round(Math.random()*100000))).build();
        memberService.join(member);
        itemService.makeItem(1000);
        System.out.println("Member Data가 생성되었습니다.");
        System.out.println("Order Data가 생성되었습니다.");
        System.out.println("Item Data가 생성되었습니다.");
    }

}
