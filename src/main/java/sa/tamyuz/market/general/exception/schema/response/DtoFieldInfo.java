package sa.tamyuz.market.general.exception.schema.response;

import lombok.Data;

@Data
public class DtoFieldInfo {

    private String name;
    private Object rejectedValue;
    private String messageAr;
    private String messageEn;

    public DtoFieldInfo(String name,
                        Object rejectedValue,
                        String messageAr,
                        String messageEn) {
        this.name = name;
        this.rejectedValue = rejectedValue;
        this.messageAr = messageAr;
        this.messageEn = messageEn;
    }

}
