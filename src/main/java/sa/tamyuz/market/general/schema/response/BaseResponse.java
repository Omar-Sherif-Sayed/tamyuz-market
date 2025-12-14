package sa.tamyuz.market.general.schema.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import sa.tamyuz.market.general.constant.ConstantDatePattern;
import sa.tamyuz.market.general.util.UtilDate;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.ALWAYS)
public class BaseResponse<T> {

    public BaseResponse() {
        response = null;
        status = true;
    }

    public BaseResponse(T response) {
        this.response = response;
        status = true;
    }

    private final T response;

    private final boolean status;

    @JsonFormat(timezone = UtilDate.ASIA_RIYADH_ZONE, pattern = ConstantDatePattern.PATTERN_2)
    private final LocalDateTime currentDate = UtilDate.findRiyadhZoneLocalDateTime();

    private String correlationId;

}
