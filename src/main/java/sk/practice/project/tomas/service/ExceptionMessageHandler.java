package sk.practice.project.tomas.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionMessageHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    private Message handleMessageAuth(BadCredentialsException e, HttpServletRequest request) {
        Message message = new Message();
        message.setTimestamp(message.getTimestamp());
        message.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        message.setStatus(HttpStatus.UNAUTHORIZED.value());
        message.setMessage(e.getMessage());
        message.setPath(request.getRequestURI());
        return message;
    }
}
