package com.sample.shop.config.filter;

import com.sample.shop.shared.advice.ErrorCode;
import com.sample.shop.shared.advice.exception.TokenValidateException;
import com.sample.shop.shared.advice.exception.LogoutException;
import com.sample.shop.shared.advice.exception.UsernameFromTokenException;
import com.sample.shop.shared.advice.response.ErrorResponse;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }catch (LogoutException e){
            log.error("로그아웃된 회원입니다.");
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,response,e);
        }catch (UsernameFromTokenException e){
            log.error("현재 사용자의 username 을 불러오지 못했습니다.");
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,response,e);
        }catch (TokenValidateException e){
            log.error("토큰의 정보가 유효하지 않습니다.");
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,response,e);
        }catch (RuntimeException e){
            log.error("Runtime exception 이 exception handler filter 에서 발생하였습니다.");
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,response,e);
        }
    }

    public void setErrorResponse(HttpStatus httpStatus, HttpServletResponse response, Throwable ex){
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTER_SERVER_ERROR);
        errorResponse.setDescription(ex.getMessage());

        try{
            String json = errorResponse.convertToJson();
            response.getWriter().write(json);
        }catch (IOException e){
            log.error("Json parsing 에 실패하였습니다 : ", e);
        }

    }
}
