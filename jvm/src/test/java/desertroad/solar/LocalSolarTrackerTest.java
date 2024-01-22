package desertroad.solar;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class LocalSolarTrackerTest {

    @Test
    public void testLocationTolerance() {
        long time = System.currentTimeMillis();
        LocalSolar.Tracker tracker = LocalSolar.trackerByTime();

        // Pier 39
        LocalSolar localSolar0 = tracker.track(37.8086895, -122.4099766, time).localSolar;

        // UC Berkeley (17km from Pier 39)
        LocalSolar localSolar1 = tracker.track(37.8701567,-122.2596229, time).localSolar;

        assertEquals(localSolar0, localSolar1);


        // Mt. Diablo (44km from Pier 39)
        LocalSolar localSolar2 = tracker.track(37.8815904,-121.9143138, time).localSolar;

        assertNotEquals(localSolar0, localSolar2);

        // Brentwood city park (20km from Mt. Diablo)
        LocalSolar localSolar3 = tracker.track(37.9337008,-121.6937548, time).localSolar;

        assertEquals(localSolar2, localSolar3);
    }

    @Test
    public void testMeanDayChanges() {
        LocalSolar.Tracker tracker = LocalSolar.trackerByTime();

        // Busan, South Korea
        double latitude = (35 + 11 / 60.0);   // 35°11'N
        double longitude = (129 + 4 / 60.0);  // 129°04'E

        LocalSolar localSolar0 = tracker.track(latitude, longitude, Utils.toEpochMillis("2023-07-09T12:00+09:00")).localSolar;
        LocalSolar localSolar1 = tracker.track(latitude, longitude, Utils.toEpochMillis("2023-07-09T20:00+09:00")).localSolar;

        assertEquals(localSolar0, localSolar1);

        LocalSolar localSolar2 = tracker.track(latitude, longitude, Utils.toEpochMillis("2023-07-10T03:00+09:00")).localSolar;

        assertNotEquals(localSolar0, localSolar2);

        LocalSolar localSolar3 = tracker.track(latitude, longitude, Utils.toEpochMillis("2023-07-10T20:00+09:00")).localSolar;

        assertEquals(localSolar2, localSolar3);
    }
}