package com.server.MemberManagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.MemberManagement.model.Board;
import com.server.MemberManagement.model.Member;
import com.server.MemberManagement.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;

@Getter
@Setter
public class BoardSaveDto {

    private String title;
    private String contents;

    @JsonIgnore
    private Member member;

    public Board toEntity(String username) {
        return Board.builder()
                .title(title)
                .contents(contents)
                .writer(username)
                .member(member)
                .build();
    }
}
