package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.CommentDto;
import com.server.MemberManagement.model.Board;
import com.server.MemberManagement.model.Comment;
import com.server.MemberManagement.repository.BoardRepository;
import com.server.MemberManagement.repository.CommentRepository;
import com.server.MemberManagement.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String saveComment(Long id, CommentDto commentDto, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        commentDto.setBoard(board);
        commentRepository.save(commentDto.toEntity(username));
        return id + "번 게시글에 댓글작성 완료.";
    }

    @Override
    @Transactional
    public String updateComment(Long board_id, Long comment_id, CommentDto commentDto, HttpServletRequest request) {
        boardRepository.findById(board_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);

        if (comment.getWriter() != username) throw new IllegalArgumentException("자신이 쓴 댓글만 수정할 수 있습니다.");

        comment.updateComment(commentDto.getContents());

        return board_id + "번 게시글의 " + comment_id + "번 댓글 수정완료.";
    }

    @Override
    public String deleteComment(Long board_id, Long comment_id, HttpServletRequest request) {
        boardRepository.findById(board_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);

        if (comment.getWriter() != username) throw new IllegalArgumentException("자신이 쓴 댓글만 삭제할 수 있습니다.");

        commentRepository.deleteById(comment_id);
        return board_id + "번 게시글의 " + comment_id + "번 댓글 삭제완료.";
    }

    @Override
    @Transactional
    public String updateComment_Admin(Long board_id, Long comment_id, CommentDto commentDto) {
        boardRepository.findById(board_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        comment.updateComment(commentDto.getContents());

        return board_id + "번 게시글의 " + comment_id + "번 댓글 관리자 권한으로 수정완료.";
    }

    @Override
    public String deleteComment_Admin(Long board_id, Long comment_id) {
        boardRepository.findById(board_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        commentRepository.deleteById(comment_id);
        return board_id + "번 게시글의 " + comment_id + "번 댓글 관리자 권한으로 삭제완료.";
    }

}
