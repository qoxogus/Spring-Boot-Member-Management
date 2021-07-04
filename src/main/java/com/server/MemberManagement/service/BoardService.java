package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.BoardSaveDto;
import com.server.MemberManagement.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {

    void saveBoard(BoardSaveDto boardSaveDto, HttpServletRequest request);

    Page<Board> readAllBoard(Pageable pageable);

    String updateBoard(Long id, BoardSaveDto boardDto);
}
