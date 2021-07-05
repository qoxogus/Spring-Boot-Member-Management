package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.BoardSaveDto;
import com.server.MemberManagement.model.Board;
import com.server.MemberManagement.repository.BoardRepository;
import com.server.MemberManagement.repository.MemberRepository;
import com.server.MemberManagement.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
    public Optional<Board> readById(Long id) {
        Optional<Board> findBoardById = boardRepository.findById(id);
        return findBoardById;
    }

    @Override
    public Page<Board> readAllBoard(Pageable pageable) {
        Page<Board> page = boardRepository.findAll(pageable);
        return page;
    }

    @Override
    @Transactional
    public String updateBoard(Long id, BoardSaveDto boardDto, HttpServletRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        if (board.getWriter() != username) throw new IllegalArgumentException("자신의 게시글만 수정할 수 있습니다.");

        board.updateBoard(boardDto.getTitle(), boardDto.getContents());

        return id + "번 게시물 업데이트 완료.";
    }

    @Override
    public String deleteBoard(Long id, HttpServletRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        if (board.getWriter() != username) throw new IllegalArgumentException("자신의 게시글만 삭제할 수 있습니다.");

        boardRepository.deleteById(id);

        return id + "번 게시물 삭제 완료.";
    }
}
