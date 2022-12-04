package util;

public class TimeUtil {
    public static String toDisplayString(Long seconds) {
        if(seconds >= 24 * 60 * 60) return toDayHourMinuteSecondString(seconds);
        if(seconds >= 60 * 60) return toHourMinuteSecondString(seconds);
        int minutes = (int) (seconds / 60);
        int secs = (int) (seconds % 60);
        return String.format("%02d:%02d", minutes, secs);
    }

    private static String toDayHourMinuteSecondString(Long seconds) {
        Long secs = seconds;
        int days = (int) (seconds / (24 * 60 * 60));
        secs -= days * 24 * 60 * 60;
        return String.format("%02d:%s", days, toHourMinuteSecondString(secs));

    }

    private static String toHourMinuteSecondString(Long seconds) {
        int hours = (int) (seconds / (60 * 60));
        int minutes = (int) (seconds % 3600) / 60;
        int secs = (int) (seconds % 60);

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }


}
