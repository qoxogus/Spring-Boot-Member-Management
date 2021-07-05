package com.server.MemberManagement.repository;

import com.server.MemberManagement.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
