package com.cicd.todomateapi.controller;

import com.cicd.todomateapi.dto.MemberForm;
import com.cicd.todomateapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public String register(@RequestBody MemberForm memberForm) {
        log.info("********** MemberController register memberForm:{}", memberForm);
        String result = memberService.save(memberForm);
        if (result.equals("success")) {
            return memberForm.getName();
        }
        return result;
    }
}
