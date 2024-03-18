package com.shop.concurrency.member.controller;


import com.shop.concurrency.member.model.dto.response.FindMembersResponse;
import com.shop.concurrency.member.service.MemberService;
import com.shop.concurrency.member.model.domain.Member;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("") //@RequestMapping("/${spring.api.version}")
public class MemberController {

    private final MemberService memberService;

    /**
     * 단일 회원가입
     */
    @PostMapping("/member")
    public Long saveMember(@RequestBody Member member) {

        return memberService.join(member);
    }


    /**
     * 회원 모두 조회(이름, 주문)
     */
    @GetMapping("/members")
    public List<FindMembersResponse> members() {
        List<Member> members = memberService.findMembers();

        return members.stream().map(
                member ->
                    FindMembersResponse
                        .builder()
                        .name(member.getName())
                        .orders(member.getOrders())
                        .build())
            .collect(Collectors.toList());
    }
}
