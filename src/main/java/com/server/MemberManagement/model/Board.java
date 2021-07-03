package com.server.MemberManagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Board {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String contents;

    private String  writer;

    public Board(Board board) {
    }
}
