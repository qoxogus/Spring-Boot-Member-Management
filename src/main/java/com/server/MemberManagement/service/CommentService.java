package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.CommentDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {

    void saveComment(Long id, CommentDto commentDto, HttpServletRequest request);
}
