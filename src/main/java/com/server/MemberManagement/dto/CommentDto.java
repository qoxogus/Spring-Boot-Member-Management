package com.server.MemberManagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.MemberManagement.model.Board;
import com.server.MemberManagement.model.Comment;
import com.server.MemberManagement.model.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private String contents;

    @JsonIgnore
    private Board board;

    public Comment toEntity(String username) {
        return Comment.builder()
                .contents(contents)
                .writer(username)
                .board(board)
                .build();
    }
}
