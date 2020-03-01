package frc.robot.commands;

public class DriveInRegularPolygonSequence extends Command {
    private TurnByDegrees[] turn;
    private DriveByTime[] driveByTime;
    public DriveInRegularPolygonSequence(double timeInSeconds, double speed, 
                                    double acceptableError, int numVertices, String name, 
                                    int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        driveByTime = new DriveByTime[4];
        for(int i = 0; i < numVertices; i++) {
            driveByTime[i] = new DriveByTime(timeInSeconds, speed, name + "-drive-" + i, periodInMillis, verbose);
        }
        turn = new TurnByDegrees[4];
        for(int i = 0; i < numVertices; i++) {
            turn[i] = new TurnByDegrees(360 / numVertices, acceptableError, name + "-turn-" + i, periodInMillis, verbose);
        }
    }
    protected void onStart() {
        super.onStart();
        for(int i = 0; i < turn.length; i++) {
            driveByTime[i].start();
            try {
                driveByTime[i].join();
            }
            catch(InterruptedException e) {
                onInterrupted();
            }
            turn[i].start();
            try {
                turn[i].join();
                System.out.print("Turn ");
                System.out.println(i);
            }
            catch(InterruptedException e) {
                onInterrupted();
            }
        }
    }
    protected void whileRunning() {
        //Do nothing, because this Command has no periodic component
    }
    protected boolean isFinished() {
        //As soon as onStart() is finished, the entire Command is finished.
        return true;
    }
}