package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.CommentDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    String saveComment(Long id, CommentDto commentDto, HttpServletRequest request);

    String updateComment(Long board_id, Long comment_id, CommentDto commentDto);
}
