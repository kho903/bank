package shop.jikim.bank.handler.aop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import shop.jikim.bank.handler.exception.CustomValidationException;

@Component
@Aspect
public class CustomValidationAdvice {

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMapping() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
	public void putMapping() {
	}

	@Around("postMapping() || putMapping()") //
	public Object validationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object[] args = proceedingJoinPoint.getArgs(); // joinPoint의 매개변수
		Arrays.stream(args).forEach(arg -> {
			if (arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;

				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();

					bindingResult.getFieldErrors().forEach(e -> {
						errorMap.put(e.getField(), e.getDefaultMessage());
					});

					throw new CustomValidationException("유효성 검사 실패", errorMap);
				}
			}
		});
		return proceedingJoinPoint.proceed(); // 정상적으로 해당 메서드를 실행해라!
	}
}
