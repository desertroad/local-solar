package desertroad.solar;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class LocalSolarTrackerTest {

    @Test
    public void testLocationTolerance() {
        long time = System.currentTimeMillis();
        LocalSolar.Tracker tracker = LocalSolar.tracker(1000);

        // Pier 39
        LocalSolar localSolar0 = tracker.track(37.8086895, -122.4099766, time).getLocalSolar();

        // Coit tower (780m from Pier 39)
        LocalSolar localSolar1 = tracker.track(37.8023813, -122.4059784, time).getLocalSolar();

        assertEquals(localSolar0, localSolar1);


        // Ferry building (2km from Pier 39)
        LocalSolar localSolar2 = tracker.track(37.7954513, -122.3937556, time).getLocalSolar();

        assertNotEquals(localSolar0, localSolar2);

        // Rincon park (544m from Ferry building)
        LocalSolar localSolar3 = tracker.track(37.7916124, -122.3901249, time).getLocalSolar();

        assertEquals(localSolar2, localSolar3);
    }

    @Test
    public void testMeanDayChanges() {
        LocalSolar.Tracker tracker = LocalSolar.tracker();

        // Busan, South Korea
        double latitude = (35 + 11 / 60.0);   // 35°11'N
        double longitude = (129 + 4 / 60.0);  // 129°04'E

        LocalSolar localSolar0 = tracker.track(latitude, longitude, Utils.toEpochMillis("2023-07-09T12:00+09:00")).getLocalSolar();
        LocalSolar localSolar1 = tracker.track(latitude, longitude, Utils.toEpochMillis("2023-07-09T20:00+09:00")).getLocalSolar();

        assertEquals(localSolar0, localSolar1);

        LocalSolar localSolar2 = tracker.track(latitude, longitude, Utils.toEpochMillis("2023-07-10T03:00+09:00")).getLocalSolar();

        assertNotEquals(localSolar0, localSolar2);

        LocalSolar localSolar3 = tracker.track(latitude, longitude, Utils.toEpochMillis("2023-07-10T20:00+09:00")).getLocalSolar();

        assertEquals(localSolar2, localSolar3);
    }
}