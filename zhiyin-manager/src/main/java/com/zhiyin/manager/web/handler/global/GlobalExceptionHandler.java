package com.zhiyin.manager.web.handler.global;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;


import com.zhiyin.manager.web.entity.ArcExceptionResponse;
import com.zhiyin.manager.web.entity.IdGenFailException;
import com.zhiyin.manager.web.entity.LoginFailException;
import com.zhiyin.manager.web.entity.RepeatRegisterException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler : 全局异常处理, 通用的异常在此进行处理, 例如: http method不匹配(405),
 * 用户为未认证(401), 用户未获取到授权(403) 所有异常均向上抛, 统一在controller层进行处理
 * 
 * @author http://arccode.net
 * @since 2015-04-16 16:21
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);

	/**
	 * 处理未发现的异常,比如用户名不存在, 产品不存在等等, 抛出http status 404
	 * http://stackoverflow.com/questions/22157687/spring-mvc-rest-handing-bad-url-404-by-returning-json
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ArcExceptionResponse handleNotFoundException(HttpServletRequest req,
                                                        NoHandlerFoundException ex) {
		log.error(req.getRequestURI() + ";" + ex.getMessage(),
				ex);
		return ArcExceptionResponse.create(HttpStatus.NOT_FOUND.value(),
				ex.getMessage());
	}

//	@ExceptionHandler(TypeMismatchException.class)
//	@ResponseStatus(value=HttpStatus.NOT_FOUND)
//	@ResponseBody
//	public ResponseEntity<String> handleTypeMismatchException(HttpServletRequest req, TypeMismatchException ex) {
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/json; charset=utf-8");
//		Locale locale = LocaleContextHolder.getLocale();
//		String errorMessage = messageSource.getMessage("error.patient.bad.request", null, locale);
//
//		errorMessage += ex.getValue();
//		String errorURL = req.getRequestURL().toString();
//
//		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
//		return new ResponseEntity<String>(errorInfo.toJson(), headers, HttpStatus.BAD_REQUEST);
//
//	}

	/**
	 * 处理Http method使用不正确的错误, 例如: 新增产品应该使用POST, 但实际使用了GET
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ArcExceptionResponse handleHttp405Exception(HttpServletRequest req,
			Exception ex) {

		log.error(req.getRequestURI() + ";" + ex.getMessage(),
				ex);
		return ArcExceptionResponse.create(
				HttpStatus.METHOD_NOT_ALLOWED.value(), "请求方法错误");
	}

	/**
	 * 当进行post请求时，请求体为空或者请求mediatype不对
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ArcExceptionResponse handleHttpMessageNotReadableException(
			HttpServletRequest req, Exception ex) {

        log.error(req.getRequestURI() + ";" + ex.getMessage(),
				ex);
		return ArcExceptionResponse.create(
				HttpStatus.METHOD_NOT_ALLOWED.value(), "请求数据错误");
	}



	/**
	 * 如果客户端传过来的json参数包含实体未定义的字段，服务器反序列化会有问题
	 * http://stackoverflow.com/questions/27742642
	 * /unrecognizedpropertyexception-unrecognized-field-resourcetype
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UnrecognizedPropertyException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ArcExceptionResponse handleUnrecognizedPropertyException(
			HttpServletRequest req, Exception ex) {
        log.error(req.getRequestURI() + ";" + ex.getMessage(),
				ex);
		return ArcExceptionResponse.create(HttpStatus.BAD_REQUEST.value(),
				"参数不正确");
	}

	/**
	 * 处理未发现的异常,比如用户名不存在, 产品不存在等等, 抛出http status 404
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RepeatRegisterException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ArcExceptionResponse handleRepeatRegisterException(
			HttpServletRequest req, Exception ex) {
        log.error(req.getRequestURI() + ";" + ex.getMessage(),
				ex);
		return ArcExceptionResponse.create(HttpStatus.NOT_FOUND.value(),
				ex.getMessage());
	}

	@ExceptionHandler(LoginFailException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ArcExceptionResponse handleLoginFailException(
			HttpServletRequest req, Exception ex) {
		log.error(req.getRequestURI() + ";" + ex.getMessage(),
				ex);
		return ArcExceptionResponse.create(HttpStatus.BAD_REQUEST.value(),
				ex.getMessage());
	}

	@ExceptionHandler(IdGenFailException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ArcExceptionResponse handleIdGenFailException(
			HttpServletRequest req, Exception ex) {
		log.error(req.getRequestURI() + ";" + ex.getMessage(),
				ex);
		return ArcExceptionResponse.create(HttpStatus.BAD_REQUEST.value(),
				ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ArcExceptionResponse handleException(HttpServletRequest req,
			Exception ex) {
		log.error(req.getRequestURI() + ";" + ex.getMessage(),
				ex);
		return ArcExceptionResponse.create(HttpStatus.BAD_REQUEST.value(),
				"系统错误");
	}
}
