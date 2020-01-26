package frc.robot.partspecs;

public class Falcon500Math {
    public final static int countsPerRevolution = 2048;
    public final static int freeRPM = 6380;
    public static int getCountsPerPeriodFromRPM(int periodInMilliseconds, double revolutionsPerMinute) {
        int millisecondsPerMinute = 60000;
        int periodsPerMinute = millisecondsPerMinute / periodInMilliseconds;
        int countsPerMinute = (int) (Falcon500Math.countsPerRevolution * revolutionsPerMinute);
        return countsPerMinute / periodsPerMinute;
    }
}