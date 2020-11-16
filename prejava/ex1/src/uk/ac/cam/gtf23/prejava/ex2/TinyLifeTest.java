package uk.ac.cam.gtf23.prejava.ex2;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class TinyLifeTest {

    @org.junit.jupiter.api.Test
    void testGetCell() {
        assertEquals(false, TinyLife.getCell( 0x20A0600000000000L, 1 ,1));
        assertEquals(false, TinyLife.getCell( 0x20A0600000000000L, -1,1));
        assertEquals(false, TinyLife.getCell( 0x20A0600000000000L, 3 ,8));
        assertEquals(false, TinyLife.getCell( 0x20A0600000000000L, 1 ,-12));
        assertEquals(false, TinyLife.getCell( 0x20A0600000000000L, 72,2));
        assertEquals(true, TinyLife.getCell( 0x20A0600000000000L, 5 ,5));
        assertEquals(true, TinyLife.getCell( 0x20A0600000000000L, 5 ,6));
        assertEquals(false, TinyLife.getCell( 0x20A0600000000000L, 6 ,6));
    }
}