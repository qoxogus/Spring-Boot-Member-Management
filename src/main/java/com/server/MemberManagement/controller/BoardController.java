package com.server.MemberManagement.controller;

import com.server.MemberManagement.dto.BoardSaveDto;
import com.server.MemberManagement.dto.CommentDto;
import com.server.MemberManagement.model.Board;
import com.server.MemberManagement.response.CommonResult;
import com.server.MemberManagement.response.ResponseService;
import com.server.MemberManagement.response.SingleResult;
import com.server.MemberManagement.service.BoardService;
import com.server.MemberManagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;
    private final CommentService commentService;

    @PostMapping("/board")
    public CommonResult saveBoard(@RequestBody BoardSaveDto boardSaveDto, HttpServletRequest request) {
        boardService.saveBoard(boardSaveDto, request);
        return responseService.getSuccessResult();
    }

    @GetMapping("/board/{id}")
    public CommonResult readById(@PathVariable("id") Long id) {
        Optional<Board> board = boardService.readById(id);
        return responseService.getSingleResult(board);
    }

    @GetMapping("/board")
    public SingleResult<Page<Board>> readAllBoard(@PageableDefault(size = 5) Pageable pageable) {
        Page<Board> boards = boardService.readAllBoard(pageable);
        return responseService.getSingleResult(boards);
    }

    @PutMapping("/board/{id}")
    public CommonResult updateBoard(@PathVariable("id") Long id, @RequestBody BoardSaveDto boardDto, HttpServletRequest request) {
        String data = boardService.updateBoard(id, boardDto, request);
        return responseService.getSingleResult(data);
    }

    @DeleteMapping("/board/{id}")
    public CommonResult deleteBoard(@PathVariable("id") Long id, HttpServletRequest request) {
        String data = boardService.deleteBoard(id, request);
        return responseService.getSingleResult(data);
    }

    @PostMapping("/board/{id}/comment")
    public CommonResult commentBoard(@PathVariable("id") Long id, @RequestBody CommentDto commentDto, HttpServletRequest request) {
        String data = commentService.saveComment(id, commentDto, request);
        return responseService.getSingleResult(data);
    }

    @PutMapping("/board/{board_id}/comment/{comment_id}")
    public CommonResult commentBoard(@PathVariable("board_id") Long board_id, @PathVariable("comment_id") Long comment_id, @RequestBody CommentDto commentDto) {
        String data = commentService.updateComment(board_id, comment_id, commentDto);
        return responseService.getSingleResult(data);
    }

    @DeleteMapping("/board/{board_id}/comment/{comment_id}")
    public CommonResult commentBoard(@PathVariable("board_id") Long board_id, @PathVariable("comment_id") Long comment_id) {
        String data = commentService.deleteComment(board_id, comment_id);
        return responseService.getSingleResult(data);
    }
}
