package com.sample.shop.config.filter;

import com.sample.shop.shared.advice.ErrorCode;
import com.sample.shop.shared.advice.exception.RestApiException;
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

    }

    public void setErrorResponse(HttpStatus httpStatus, HttpServletResponse response, Throwable ex){
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        //ErrorResponse errorResponse = new ErrorResponse();
    }
}
