package com.example.initialProject.account.controller;


import com.example.initialProject.account.model.domain.Member;
import com.example.initialProject.account.model.dto.response.CreateMemberResponse;
import com.example.initialProject.account.model.dto.response.FindMembersResponse;
import com.example.initialProject.account.service.MemberService;
import com.example.initialProject.util.ObjectConvertUtil;
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
    public CreateMemberResponse saveMember(@RequestBody Member member) {

        Long memberId = memberService.join(member);

        return ObjectConvertUtil.toDto(memberId, CreateMemberResponse.class);
    }


    /**
     * 회원 모두 조회(이름만)
     */
    @GetMapping("/members")
    public List<FindMembersResponse> members() {

        List<Member> findMembers = memberService.findMembers();
        return ObjectConvertUtil.listToListDto(findMembers, FindMembersResponse.class);
    }
}
