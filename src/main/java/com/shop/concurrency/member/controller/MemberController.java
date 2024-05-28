package com.shop.concurrency.member.controller;


import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.model.dto.response.MemberRequest;
import com.shop.concurrency.member.model.dto.response.MembersResponse;
import com.shop.concurrency.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("") //@RequestMapping("/${spring.api.version}")
public class MemberController {

    private final MemberService memberService;

    /**
     * 단일 회원가입
     */
    @PostMapping("/member")
    public Long saveMember(@RequestBody MemberRequest memberRequest) {

        Member member = Member.builder()
                .name(memberRequest.getName())
                .build();
        return memberService.join(member);
    }


    /**
     * 회원 모두 조회(이름, 주문)
     */
    @GetMapping("/members")
    public List<MembersResponse> members() {
        List<Member> members = memberService.findMembers();

        return members.stream().map(
                        member ->
                                MembersResponse
                                        .builder()
                                        .name(member.getName())
                                        .build())
                .collect(Collectors.toList());
    }
}
