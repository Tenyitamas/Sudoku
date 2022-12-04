package src;

import org.junit.Test;
import util.TimeUtil;
import org.junit.Assert;

public class TimeUtilTest {
    @Test
    public void toMinuteSecondStringLessThanAnHourTest(){
        Long seconds = 3454L;
        String time = TimeUtil.toMinuteSecondString(seconds);
        Assert.assertEquals("57:34", time);
    }

    @Test
    public void toMinuteSecondStringMoreThanAnHourTest(){
        Long seconds = 3666L;
        String time = TimeUtil.toMinuteSecondString(seconds);
        Assert.assertEquals("01:01:06", time);
    }
}
