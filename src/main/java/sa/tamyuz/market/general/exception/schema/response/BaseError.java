package sa.tamyuz.market.general.exception.schema.response;

import lombok.Data;
import org.apache.commons.lang3.time.FastDateFormat;
import sa.tamyuz.market.general.exception.enums.ErrorCode;

@Data
public class BaseError {

    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
    private final boolean status;
    private final String timestamp;
    private final ErrorCode errorCode;
    private final String messageAr;
    private final String messageEn;
    private final Object detail;
    private String correlationId;

    public BaseError(ErrorCode errorCode,
                     String messageAr,
                     String messageEn,
                     Object detail) {
        this.status = false;
        this.timestamp = DATE_FORMAT.format(System.currentTimeMillis());
        this.errorCode = errorCode;
        this.messageAr = messageAr;
        this.messageEn = messageEn;
        this.detail = detail;
    }

}
