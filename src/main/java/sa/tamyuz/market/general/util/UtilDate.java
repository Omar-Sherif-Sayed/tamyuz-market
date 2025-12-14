package sa.tamyuz.market.general.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UtilDate {

    private UtilDate() {
    }

    public static final String ASIA_RIYADH_ZONE = "Asia/Riyadh";
    public static final ZoneId ZONE_ID = ZoneId.of(ASIA_RIYADH_ZONE);

    public static LocalDateTime findRiyadhZoneLocalDateTime() {
        return LocalDateTime.now(ZONE_ID);
    }

}
