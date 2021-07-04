package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.BoardSaveDto;
import com.server.MemberManagement.model.Board;
import com.server.MemberManagement.model.Member;
import com.server.MemberManagement.repository.BoardRepository;
import com.server.MemberManagement.repository.MemberRepository;
import com.server.MemberManagement.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void saveBoard(BoardSaveDto boardSaveDto, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        boardSaveDto.setMember(memberRepository.findByUsername(username));
        boardRepository.save(boardSaveDto.toEntity(username));
    }

    @Override
    public Page<Board> readAllBoard(Pageable pageable) {
        Page<Board> page = boardRepository.findAll(pageable);
        return page;
    }

    @Override
    @Transactional
    public String updateBoard(Long id, BoardSaveDto boardDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        board.updateBoard(boardDto.getTitle(), boardDto.getContents());

        return id + "번 게시물 업데이트 완료.";
    }
}