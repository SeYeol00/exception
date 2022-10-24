package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j // 레스트 컨트롤러에 리스폰스 바디가 붙은 거, 코드를 분리 시킨 거다.
//@RestControllerAdvice(annotations = RestController.class) //  이 어노테이션이 붙은 것만
@RestControllerAdvice(basePackages = "hello.exception.api") // 대상을 지정하지 않으면 모든 컨트롤러에 적용된다.
public class ExControllerAdvice { // 일반적으로 패키지 정도는 지정한다.

    // 여러 컨트롤러에서 나오는 익셉션을 한데 모아 처리하는 컨트롤러 어드바이스
    // AOP와 같은 방식이다.
    // 핵심 -> 현업에서는 이것만 쓴다.
    // 익셉션 핸들러는 컨트롤러처럼 만들어놨다
    // 매서드 아규먼트 거의 다 넘길 수 있다.
    // 스트링을 리턴하면 뷰 리졸버로 뷰를 그대로 반환도 가능하다.
    // 물론 그냥 컨트롤러일 때 가능하다.
    // 예외 공통 처리는 중요하니 api를 이용할 때 명심하자.

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 에러 코드 기입하기
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

}
