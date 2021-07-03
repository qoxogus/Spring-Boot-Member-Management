package com.server.MemberManagement.controller;

import com.server.MemberManagement.dto.BoardSaveDto;
import com.server.MemberManagement.model.Board;
import com.server.MemberManagement.response.CommonResult;
import com.server.MemberManagement.response.ResponseService;
import com.server.MemberManagement.response.SingleResult;
import com.server.MemberManagement.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @PostMapping("/board")
    public CommonResult saveBoard(@RequestBody BoardSaveDto boardSaveDto, HttpServletRequest request) {
        boardService.saveBoard(boardSaveDto, request);
        return responseService.getSuccessResult();
    }

    @GetMapping("/board")
    public SingleResult<Page<Board>> readAllBoard(@PageableDefault(size = 5) Pageable pageable) {
        Page<Board> boards = boardService.readAllBoard(pageable);
        return responseService.getSingleResult(boards);
    }
}
