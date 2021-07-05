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
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String writer;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
