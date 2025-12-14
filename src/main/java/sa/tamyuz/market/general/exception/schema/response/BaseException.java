package sa.tamyuz.market.general.exception.schema.response;


import lombok.*;
import org.springframework.http.HttpStatus;
import sa.tamyuz.market.general.exception.enums.ErrorCode;

import java.io.Serial;


@Getter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1582167443169968009L;

    @Builder.Default
    private HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
    private final ErrorCode errorCode;
    private Throwable throwable;
    private transient Object detail;
    private String[] args;

    public BaseException() {
        httpStatus = HttpStatus.NOT_ACCEPTABLE;
        errorCode = null;
        detail = null;
    }

    public BaseException(ErrorCode errorCode) {
        httpStatus = HttpStatus.NOT_ACCEPTABLE;
        this.errorCode = errorCode;
        detail = null;
    }

    public BaseException(ErrorCode errorCode, String[] args) {
        httpStatus = HttpStatus.NOT_ACCEPTABLE;
        this.errorCode = errorCode;
        this.args = args;
        detail = null;
    }

    public BaseException(ErrorCode errorCode, Throwable throwable) {
        httpStatus = HttpStatus.NOT_ACCEPTABLE;
        this.errorCode = errorCode;
        this.throwable = throwable;
    }

    public BaseException(ErrorCode errorCode, Object detail) {
        httpStatus = HttpStatus.NOT_ACCEPTABLE;
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public BaseException(ErrorCode errorCode, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.detail = null;
    }

    public BaseException(ErrorCode errorCode, Object detail, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.detail = detail;
    }

}
