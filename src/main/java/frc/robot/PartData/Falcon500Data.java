package frc.robot.partData;

public class Falcon500Data {
    public final static int countsPerRevolution = 2048;
    public final static int freeRPM = 6380;
    public final static int periodInMilliseconds = 100;
    /**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int slotIndex = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
    public static final int pidLoopIndex = 0;
    
    public static int getCountsPerPeriodFromRPM(double revolutionsPerMinute) {
        int millisecondsPerMinute = 60000;
        int periodsPerMinute = millisecondsPerMinute / periodInMilliseconds;
        int countsPerMinute = (int) (countsPerRevolution * revolutionsPerMinute);
        return countsPerMinute / periodsPerMinute;
    }
}