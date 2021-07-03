package com.server.MemberManagement.controller;

import com.server.MemberManagement.dto.WithdrawalDto;
import com.server.MemberManagement.response.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    @GetMapping("/test")
    @ResponseStatus( HttpStatus.OK )
    public String test() {
        return "Hi Admin.";
    }
}
