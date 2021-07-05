package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.BoardResponseDto;
import com.server.MemberManagement.dto.BoardSaveDto;
import com.server.MemberManagement.model.Board;
import com.server.MemberManagement.repository.BoardRepository;
import com.server.MemberManagement.repository.MemberRepository;
import com.server.MemberManagement.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

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
    public BoardResponseDto readById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        ModelMapper modelMapper = new ModelMapper();
        BoardResponseDto map = modelMapper.map(board, BoardResponseDto.class);

        return map;
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

        return id + "번 게시물 수정 완료.";
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

    @Override
    @Transactional
    public String updateBoard_Admin(Long id, BoardSaveDto boardDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        board.updateBoard(boardDto.getTitle(), boardDto.getContents());

        return id + "번 게시물 관리자 권한으로 수정 완료.";
    }

    @Override
    public String deleteBoard_Admin(Long id) {
        boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        boardRepository.deleteById(id);

        return id + "번 게시물 관리자 권한으로 삭제 완료.";
    }
}
