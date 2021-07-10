package com.server.MemberManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String writer;

    private String contents;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    public void updateComment(String contents) {
        this.contents = contents != null ? contents : this.contents;
    }
}
