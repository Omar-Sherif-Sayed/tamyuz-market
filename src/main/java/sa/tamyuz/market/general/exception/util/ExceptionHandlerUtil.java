package sa.tamyuz.market.general.exception.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sa.tamyuz.market.general.enums.Language;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseError;
import sa.tamyuz.market.general.exception.schema.response.BaseException;
import sa.tamyuz.market.general.util.UtilTranslate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerUtil {

    private final UtilTranslate utilTranslate;

    public ResponseEntity<BaseError> commonReturn(BaseException e) {
        return returnBaseErrorResponseEntity(e);
    }

    private ResponseEntity<BaseError> returnBaseErrorResponseEntity(BaseException e) {

        ErrorCode errorCode = e.getErrorCode();
        BaseError errorInfo = new BaseError(errorCode,
                utilTranslate.findTranslatedMessage(errorCode.getMessageCode(), e.getArgs(), Language.AR),
                utilTranslate.findTranslatedMessage(errorCode.getMessageCode(), e.getArgs(), Language.EN),
                e.getDetail());

        HttpStatus status = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.NOT_ACCEPTABLE;
        log.warn("base exception: errorInfo={}, status={}, object={}", errorInfo, status, e.getDetail());
        return new ResponseEntity<>(errorInfo, status);
    }

}
