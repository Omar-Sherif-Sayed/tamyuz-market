package sa.tamyuz.market.general.exception;


import jakarta.validation.UnexpectedTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import sa.tamyuz.market.general.enums.Language;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseError;
import sa.tamyuz.market.general.exception.schema.response.BaseException;
import sa.tamyuz.market.general.exception.schema.response.DtoFieldInfo;
import sa.tamyuz.market.general.exception.util.ExceptionHandlerUtil;
import sa.tamyuz.market.general.util.UtilTranslate;

import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final UtilTranslate utilTranslate;
    private final ExceptionHandlerUtil exceptionHandlerUtil;

    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<BaseError> baseExceptionHandler(BaseException baseException) {
        logException("BaseException", baseException);
        Throwable throwable = baseException.getThrowable();
        if (throwable != null) {
            log.error("throwable: ", throwable);
        }
        return exceptionHandlerUtil.commonReturn(baseException);
    }

    @ResponseBody
    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ResponseEntity<BaseError> unexpectedTypeExceptionHandler(UnexpectedTypeException unexpectedTypeException) {
        logException("UnexpectedTypeException", unexpectedTypeException);
        return exceptionHandlerUtil.commonReturn(new BaseException(ErrorCode.EXCEPTION_UNEXPECTED_TYPE));
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BaseError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("MethodArgumentNotValidException error", methodArgumentNotValidException);
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        ErrorCode errorCode = ErrorCode.EXCEPTION_METHOD_ARGUMENT_NOT_VALID;
        List<DtoFieldInfo> fieldsInfo =
                fieldErrors
                        .stream()
                        .map(e ->
                                new DtoFieldInfo(
                                        e.getField(),
                                        e.getRejectedValue(),
                                        utilTranslate.findTranslatedMessage(e.getDefaultMessage(), Language.AR),
                                        utilTranslate.findTranslatedMessage(e.getDefaultMessage(), Language.EN)
                                )
                        )
                        .toList();
        return exceptionHandlerUtil.commonReturn(new BaseException(errorCode, fieldsInfo));
    }

    @ResponseBody
    @ExceptionHandler(value = HandlerMethodValidationException.class)
    public ResponseEntity<BaseError> methodArgumentNotValidExceptionHandler(HandlerMethodValidationException handlerMethodValidationException) {
        log.error("MethodArgumentNotValidException error", handlerMethodValidationException);
        List<ParameterValidationResult> fieldErrors = handlerMethodValidationException.getParameterValidationResults();
        ErrorCode errorCode = ErrorCode.EXCEPTION_METHOD_ARGUMENT_NOT_VALID;
        List<DtoFieldInfo> fieldsInfo =
                fieldErrors
                        .stream()
                        .map(e ->
                                new DtoFieldInfo(
                                        e.getMethodParameter().getParameterName(),
                                        e.getArgument(),
                                        !e.getResolvableErrors().isEmpty()
                                                ? utilTranslate
                                                .findTranslatedMessage(
                                                        e.getResolvableErrors().getFirst().getDefaultMessage(),
                                                        Language.AR)
                                                : "",
                                        !e.getResolvableErrors().isEmpty()
                                                ? utilTranslate
                                                .findTranslatedMessage(
                                                        e.getResolvableErrors().getFirst().getDefaultMessage(),
                                                        Language.EN)
                                                : ""
                                )
                        )
                        .toList();
        return exceptionHandlerUtil.commonReturn(new BaseException(errorCode, fieldsInfo));
    }

    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<BaseError> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException httpMessageNotReadableException) {
        logException("HttpMessageNotReadableException", httpMessageNotReadableException);
        ErrorCode errorCode = ErrorCode.EXCEPTION_HTTP_MESSAGE_NOT_READABLE;
        return exceptionHandlerUtil.commonReturn(new BaseException(errorCode));
    }

    @ResponseBody
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<BaseError> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException dataIntegrityViolationException) {
        logException("DataIntegrityViolationException", dataIntegrityViolationException);
        ErrorCode errorCode = ErrorCode.EXCEPTION_DATA_INTEGRITY_VIOLATION;
        return exceptionHandlerUtil.commonReturn(new BaseException(errorCode));
    }

//    @ResponseBody
//    @ExceptionHandler(value = TokenExpiredException.class)
//    public ResponseEntity<BaseError> tokenExpiredExceptionHandler(TokenExpiredException tokenExpiredException) {
//        logException("TokenExpiredException", tokenExpiredException);
//        ErrorCode errorCode = ErrorCode.EXPIRED_AUTH_TOKEN;
//        return exceptionHandlerUtil.commonReturn(new BaseException(errorCode));
//    }
//
//    @ResponseBody
//    @ExceptionHandler(value = JWTVerificationException.class)
//    public ResponseEntity<BaseError> jwtVerificationExceptionHandler(JWTVerificationException jwtVerificationException) {
//        logException("JWTVerificationException", jwtVerificationException);
//        ErrorCode errorCode = ErrorCode.AUTH_TOKEN_CAN_NOT_BE_TRUSTED;
//        return exceptionHandlerUtil.commonReturn(new BaseException(errorCode));
//    }

    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<BaseError> noHandlerFoundExceptionHandler(NoHandlerFoundException noHandlerFoundException) {
        logException("NoHandlerFoundException", noHandlerFoundException);
        ErrorCode errorCode = ErrorCode.EXCEPTION_NO_HANDLER_FOUND;
        return exceptionHandlerUtil.commonReturn(new BaseException(errorCode, HttpStatus.NOT_FOUND));
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<BaseError> exceptionHandler(Exception exception) {
        logException("Exception", exception);
        return exceptionHandlerUtil.commonReturn(new BaseException(ErrorCode.EXCEPTION_SYSTEM));
    }

    private static void logException(String exceptionName, Exception exception) {
        log.error("GlobalExceptionHandler, name: {}, message: {}, exception: ",
                exceptionName,
                exception.getMessage(),
                exception);
    }

}
