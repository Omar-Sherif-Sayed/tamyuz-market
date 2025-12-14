package sa.tamyuz.market.general.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class UtilDateTest {

    @Test
    void findRiyadhZoneLocalDateTime_ReturnsNonNull() {
        // Act
        LocalDateTime result = UtilDate.findRiyadhZoneLocalDateTime();

        // Assert
        assertNotNull(result);
    }

    @Test
    void findRiyadhZoneLocalDateTime_ReturnsRiyadhTime() {
        // Act
        LocalDateTime riyadhTime = UtilDate.findRiyadhZoneLocalDateTime();
        LocalDateTime systemTime = LocalDateTime.now(ZoneId.of("Asia/Riyadh"));

        // Assert
        // Times should be within 1 second of each other
        assertTrue(Math.abs(riyadhTime.toLocalTime().toSecondOfDay() -
                           systemTime.toLocalTime().toSecondOfDay()) < 2);
    }

    @Test
    void zoneIdConstant_IsRiyadhZone() {
        // Assert
        assertEquals(ZoneId.of("Asia/Riyadh"), UtilDate.ZONE_ID);
    }

    @Test
    void asiaRiyadhZoneConstant_HasCorrectValue() {
        // Assert
        assertEquals("Asia/Riyadh", UtilDate.ASIA_RIYADH_ZONE);
    }

    @Test
    void findRiyadhZoneLocalDateTime_MultipleCalls_ReturnDifferentTimes() throws InterruptedException {
        // Arrange
        LocalDateTime time1 = UtilDate.findRiyadhZoneLocalDateTime();
        Thread.sleep(10); // Wait 10ms

        // Act
        LocalDateTime time2 = UtilDate.findRiyadhZoneLocalDateTime();

        // Assert
        assertTrue(time2.isAfter(time1) || time2.isEqual(time1));
    }
}
