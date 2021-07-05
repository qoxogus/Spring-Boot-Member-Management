package com.server.MemberManagement.controller;

import com.server.MemberManagement.dto.BoardSaveDto;
import com.server.MemberManagement.dto.CommentDto;
import com.server.MemberManagement.response.CommonResult;
import com.server.MemberManagement.response.ResponseService;
import com.server.MemberManagement.service.BoardService;
import com.server.MemberManagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminBoardController {

    private final BoardService boardService;
    private final ResponseService responseService;
    private final CommentService commentService;

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

    @PutMapping("/board/{board_id}/comment/{comment_id}")
    public CommonResult commentUpdateBoard_Admin(@PathVariable("board_id") Long board_id, @PathVariable("comment_id") Long comment_id, @RequestBody CommentDto commentDto) {
        String data = commentService.updateComment_Admin(board_id, comment_id, commentDto);
        return responseService.getSingleResult(data);
    }

    @DeleteMapping("/board/{board_id}/comment/{comment_id}")
    public CommonResult commentDeleteBoard_Admin(@PathVariable("board_id") Long board_id, @PathVariable("comment_id") Long comment_id) {
        String data = commentService.deleteComment_Admin(board_id, comment_id);
        return responseService.getSingleResult(data);
    }
}
