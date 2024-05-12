package util;

import org.junit.Test;

import static org.junit.Assert.*;


public class IntSegmentListTest {

    @Test
    public void segmentContainsNewPointTest() {
        IntSegmentList list = new IntSegmentList();

        list.addPoint(5);

        assertTrue(list.containsPoint(5));
        assertFalse(list.containsPoint(3));
        assertFalse(list.containsPoint(8));
        assertEquals(list.getMinPoint(), 5);
    }

    @Test
    public void segmentContainsPointInRange() {
        IntSegmentList list = new IntSegmentList();

        list.addSegment(4, 12);

        assertTrue(list.containsPoint(10));
        assertTrue(list.containsPoint(4));
        assertTrue(list.containsPoint(12));
        assertTrue(list.containsPoint(8));
        assertFalse(list.containsPoint(3));
        assertFalse(list.containsPoint(13));
        assertFalse(list.containsPoint(1));
        assertFalse(list.containsPoint(28));
        assertEquals(list.getMinPoint(), 4);
    }

    @Test
    public void segmentContainsPointsInCombinedList() {
        IntSegmentList list = new IntSegmentList();

        list.addPoint(7);
        list.addSegment(10, 13);

        assertTrue(list.containsPoint(7));
        assertTrue(list.containsPoint(10));
        assertTrue(list.containsPoint(12));
        assertTrue(list.containsPoint(13));
        assertFalse(list.containsPoint(8));
        assertFalse(list.containsPoint(9));
        assertFalse(list.containsPoint(14));
        assertFalse(list.containsPoint(6));
        assertEquals(list.getMinPoint(), 7);
    }

    @Test
    public void segmentContainsPointsInStringConstructor() {
        IntSegmentList list = new IntSegmentList("1-14, 16");

        assertTrue(list.containsPoint(1));
        assertTrue(list.containsPoint(4));
        assertTrue(list.containsPoint(14));
        assertTrue(list.containsPoint(16));
        assertFalse(list.containsPoint(0));
        assertFalse(list.containsPoint(15));
        assertFalse(list.containsPoint(17));
        assertFalse(list.containsPoint(21));
        assertEquals(list.getMinPoint(), 1);
    }

    @Test
    public void segmentGoesToMaxLimitWithUnendedHyphen() {
        IntSegmentList list = new IntSegmentList("1-");

        assertTrue(list.containsPoint(958592));
    }
}