package org.king2.log.exception;

import com.alibaba.fastjson.JSON;
import org.king2.log.constant.LogAccessTypeEnum;
import org.king2.log.constant.LogTypeEnum;
import org.king2.log.constant.SystemResultCodeEnum;
import org.king2.log.result.SystemResult;
import org.king2.log.schedule.LogServerSchedule;
import org.king2.log.utils.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Set;

/**
 * 描述：全局异常接收器
 * @author 刘梓江
 * @date   2020/6/9 13:55
 */
@ControllerAdvice
public class GlobalExceptionHandle {

	private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandle.class);

	/**
	 * 处理全局异常
	 * @param exception	异常对象
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Object errorHandler(Exception exception) {
		pageExceptionLog(exception);
		return SystemResult.build(SystemResultCodeEnum.ERROR.STATE,SystemResultCodeEnum.ERROR.MESS,null);
	}

	/**
	 * 处理数据校验异常
	 * @param exception	数据校验异常对象
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(BindException.class)
	public Object dataException(BindException exception) {
		pageExceptionLog(exception);
		// 返回数据校验错误信息
		return SystemResult.build(SystemResultCodeEnum.CHECK_ERROR.STATE, SystemResultCodeEnum.CHECK_ERROR.MESS, exception.getFieldError().getDefaultMessage());
	}

	/**
	 * 处理加了@RequestBody数据校验异常
	 * @param exception	数据校验异常对象
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object MethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		pageExceptionLog(exception);
		// 返回数据校验错误信息
		return SystemResult.build(SystemResultCodeEnum.CHECK_ERROR.STATE, SystemResultCodeEnum.CHECK_ERROR.MESS, exception.getBindingResult().getFieldError().getDefaultMessage());
	}

	/**
	 * 处理请求单个参数不满足校验规则的异常信息
	 * @param exception	数据校验异常对象
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	public Object constraintViolationExceptionHandler(ConstraintViolationException exception) {
		pageExceptionLog(exception);
		// 执行校验，获得校验结果
		Set<ConstraintViolation<?>> validResult = exception.getConstraintViolations();
		// 返回错误信息
		return SystemResult.build(SystemResultCodeEnum.CHECK_ERROR.STATE, SystemResultCodeEnum.CHECK_ERROR.MESS, validResult.iterator().next().getMessage());
	}

	/**
	 * 记录异常日志信息
	 * @param e
	 */
	public void pageExceptionLog(Throwable e){
		String exceptionMessage = ApplicationUtil.getExceptionMessage(e);
		// 打印异常信息
		logger.info("异常信息："+exceptionMessage );
		//记录日志信息
		LogServerSchedule.packAccessLog(new Date(),LogAccessTypeEnum.SYSTEM.code, LogTypeEnum.EXCEPTION.code, JSON.toJSONString(exceptionMessage));
	}

}
