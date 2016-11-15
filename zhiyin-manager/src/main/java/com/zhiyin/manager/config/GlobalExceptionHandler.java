//package com.zhiyin.manager.config;
//
//import com.didispace.dto.ErrorInfo;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import javax.servlet.http.HttpServletRequest;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
////    @ExceptionHandler(NoHandlerFoundException.class)
////    @ResponseStatus(HttpStatus.NOT_FOUND)
////    @ResponseBody
////    public ArcExceptionResponse handleNotFoundException(HttpServletRequest req,
////                                                        NoHandlerFoundException  ex) {
////        LoggerUtil.logRequestError(req.getRequestURI() + ";" + ex.getMessage(),
////                ex);
////        return ArcExceptionResponse.create(HttpStatus.NOT_FOUND.value(),
////                ex.getMessage());
////    }
//
//
//}