import org.junit.Before;
import org.junit.Test;

import me.samen.jutil.PerfMeasureHelper;

import static org.junit.Assert.*;

/**
 * Created by santosh on 5/19/16.
 */
public class PerfMeasureHelperTest {
  PerfMeasureHelper<String> perfMeasureHelper;
  String a = "A", b = "B", c = "C";

  @Before
  public void setUp() throws Exception {
    perfMeasureHelper = new PerfMeasureHelper<>();

  }

  @Test
  public void testStartStopPositiveFlow() throws Exception {
    cycle(a, 100);
    assertEquals(100, perfMeasureHelper.getTotalTime(a));
  }

  @Test
  public void testMultipleStartStop() throws Exception {
    cycle(a, 100);
    cycle(a, 100);
    assertEquals(200, perfMeasureHelper.getTotalTime(a));
  }

  @Test
  public void testMultipleStartStopOn2Objects() throws Exception {
    cycle(c,954);
    perfMeasureHelper.start(a);
    waitFor(50);
    perfMeasureHelper.start(b);
    waitFor(100);
    perfMeasureHelper.stop(b);
    waitFor(50);
    perfMeasureHelper.stop(a);
    assertEquals(200, perfMeasureHelper.getTotalTime(a));
    assertEquals(100, perfMeasureHelper.getTotalTime(b));
    assertEquals(954, perfMeasureHelper.getTotalTime(c));
  }

  @Test
  public void testDoubleStartWillReplace() throws Exception {
    perfMeasureHelper.start(a);
    waitFor(100);
    perfMeasureHelper.start(a);
    waitFor(200);
    perfMeasureHelper.stop(a);
    assertEquals(200, perfMeasureHelper.getTotalTime(a));
  }

  @Test
  public void testDoubledStopIsIgnored() throws Exception {
    cycle(a, 100);
    assertFalse(perfMeasureHelper.stop(b));
  }

  @Test
  public void testStopWithoutStarIsIgnored() throws Exception {
    assertFalse(perfMeasureHelper.stop(b));
  }

  private void cycle(String tag, long waitTime) {
    assertTrue(perfMeasureHelper.start(tag));
    waitFor(waitTime);
    assertTrue(perfMeasureHelper.stop(tag));
  }

  private void waitFor(long time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}
