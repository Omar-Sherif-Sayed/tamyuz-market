package sa.tamyuz.market.general.constant;

import lombok.Getter;

@Getter
public class ConstantDatePattern {

    private ConstantDatePattern() {
    }

    public static final String PATTERN_1 = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String PATTERN_2 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String YEAR_MONTH_DAY = "yyyy/MM/dd";
    public static final String DAY_MONTH_YEAR = "dd/MM/yyyy";
    public static final String YEAR_MONTH = "YYYY-MM";


}
