package com.server.MemberManagement.dto;

import com.server.MemberManagement.model.Board;
import com.server.MemberManagement.model.Comment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.swagger2.mappers.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String writer;
    private List<Comment> comments = new ArrayList<>();
    
}
