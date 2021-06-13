package com.server.MemberManagement.advice;

import com.server.MemberManagement.advice.exception.*;
import com.server.MemberManagement.response.CommonResult;
import com.server.MemberManagement.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }
    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult defaultException(HttpServletRequest request, Exception e){
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResult userAlreadyExistsException(HttpServletRequest request, UserAlreadyExistsException e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("userAlreadyExists.code")), getMessage("userAlreadyExists.msg"));
    }
}