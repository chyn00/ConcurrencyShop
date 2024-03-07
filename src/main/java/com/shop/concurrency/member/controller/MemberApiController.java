package com.shop.concurrency.member.controller;


import com.shop.concurrency.member.service.MemberService;
import com.shop.concurrency.member.model.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${spring.api.version}")
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 단일 회원가입
     */
    @PostMapping("/member")
    public Long saveMember(@RequestBody Member member) {

        return memberService.join(member);
    }


    /**
     * 회원 모두 조회(이름만)
     */
    @GetMapping("/members")
    public List<Member> members() {

        return memberService.findMembers();
    }
}
