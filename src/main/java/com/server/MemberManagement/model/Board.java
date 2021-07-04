package com.server.MemberManagement.model;

import com.server.MemberManagement.dto.BoardSaveDto;
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
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "BOARD_TITLE")
    private String title;

    @Column(name = "BOARD_CONTENTS")
    private String contents;

    @Column(name = "BOARD_WRITER")
    private String  writer;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void updateBoard(String title, String contents) {
        this.title = title != null ? title : this.title;
        this.contents = contents != null ? contents : this.contents;
    }
}
