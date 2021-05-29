package com.server.MemberManagement.security;

import com.server.MemberManagement.advice.ExceptionAdvice;
import com.server.MemberManagement.advice.exception.AccessTokenExpiredException;
import com.server.MemberManagement.advice.exception.InvalidTokenException;
import com.server.MemberManagement.advice.exception.UserNotFoundException;
import com.server.MemberManagement.response.CommonResult;
import com.server.MemberManagement.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ExceptionAdvice exceptionAdvice;
    private final ResponseService resService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) {
        try {
            filterChain.doFilter(req, res);
        }catch(InvalidTokenException e){
            setExceptionRes(HttpStatus.BAD_REQUEST, res, exceptionAdvice.invalidToken(req, e));
        }catch(AccessTokenExpiredException e){
            setExceptionRes(HttpStatus.BAD_REQUEST, res, exceptionAdvice.accessTokenExpiredException(req, e));
        }catch (UserNotFoundException e){
            setExceptionRes(HttpStatus.BAD_REQUEST, res, exceptionAdvice.userNotFoundException(req, e));
        } catch (Exception e){
            setExceptionRes(HttpStatus.INTERNAL_SERVER_ERROR, res, exceptionAdvice.defaultException(req, e));
        }


    }

    //
    public void setExceptionRes(HttpStatus status, HttpServletResponse res, CommonResult exceptionResult) {
        res.setStatus(status.value());
        res.setContentType("application/json");

        int exceptionCode = exceptionResult.getCode();
        String exceptionMsg = exceptionResult.getMsg();

        try{
            String exceptionResultToJson = resService.getFailResultConvertString(exceptionCode, exceptionMsg); // CommonResult 에 있는 값을 json 으로 변환
            System.out.println(exceptionResultToJson);
            res.getWriter().write(exceptionResultToJson); // filter 단에서 client 에 json 을 보넨다.
        }catch (Exception e){
            e.printStackTrace();
            throw new UnknownError();
        }
    }

}
