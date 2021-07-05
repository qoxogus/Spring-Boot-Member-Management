package com.server.MemberManagement.controller;

import com.server.MemberManagement.dto.BoardSaveDto;
import com.server.MemberManagement.response.CommonResult;
import com.server.MemberManagement.response.ResponseService;
import com.server.MemberManagement.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminBoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @PutMapping("/board/{id}")
    public CommonResult updateBoard_Admin(@PathVariable("id") Long id, @RequestBody BoardSaveDto boardDto) {
        String data = boardService.updateBoard_Admin(id, boardDto);
        return responseService.getSingleResult(data);
    }

    @DeleteMapping("/board/{id}")
    public CommonResult deleteBoard_Admin(@PathVariable("id") Long id) {
        String data = boardService.deleteBoard_Admin(id);
        return responseService.getSingleResult(data);
    }
}
