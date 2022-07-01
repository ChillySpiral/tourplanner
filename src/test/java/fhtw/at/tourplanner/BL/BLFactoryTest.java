package fhtw.at.tourplanner.BL;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BLFactoryTest {

    @Test
    void getTourAppManager() {
        assertThat(BLFactory.getTourAppManager()).isEqualTo(BLFactory.getTourAppManager());
    }

    @Test
    void getReportGenerator() {
        assertThat(BLFactory.getReportGenerator()).isEqualTo(BLFactory.getReportGenerator());
    }

    @Test
    void getJsonGenerator() {
        assertThat(BLFactory.getJsonGenerator()).isEqualTo(BLFactory.getJsonGenerator());
    }
}