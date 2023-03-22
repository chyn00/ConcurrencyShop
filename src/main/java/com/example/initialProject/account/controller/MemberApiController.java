package com.example.initialProject.account.controller;


import com.example.initialProject.account.domain.Member;
import com.example.initialProject.account.dto.MemberDto;
import com.example.initialProject.account.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${spring.api.version}")
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원가입은 단일로 이루어진다.
     * V1은 Prefix예정
     * 공통 Response는 진행예정
     * 공통 Exception도 진행예정
     * */
    @PostMapping("/member")
    public Object saveMemberV1(@RequestBody Object name) {

        Member member = new Member();
        member.setName(name.toString());
        Long memberId = memberService.join(member);

        return memberId + "is memberId for Join";
    }


    /**
     * 회원 모두 조회
     * 당연히, 회원조회가 Admin이아닌데, 나가면 안되지만 샘플작성중
     */
    @GetMapping("/members")
    public List<MemberDto> membersV1() {

        List<Member> findMembers = memberService.findMembers();
        //엔티티 -> DTO 변환
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return collect;
    }
}
