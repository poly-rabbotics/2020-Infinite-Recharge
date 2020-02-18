package frc.robot.subsystems;

public class AutoDrive extends Drive {
    private boolean enabled;
    public AutoDrive() {
        super();
    }
    public void enable() {
        enabled = true;
        (new Thread(() -> {
            while(enabled) {
                autoOrient();
                System.out.println("Auto orienting");
                try {
                    Thread.sleep(20);
                }
                catch(InterruptedException e) {
                    System.out.println("TURN_INTERRUPTED.");
                }
            }
        })).start();
    }
    public void disable() {
        enabled = false;
    }
    public void turn(double degrees, double tolerance, Runnable callback) {
        (new Thread(() -> {
            //Don't begin until the robot faces the direction that it should face
            while(!aligned(tolerance) && enabled) {}
            setRotationalSetpoint(degrees);
            System.out.println(getAngle());
            System.out.println(getSetpoint());
            int correctPositionCount = 0;
            System.out.println("***************************************Start_turn_thread*******************");
            while(correctPositionCount < 5 && enabled) {
                System.out.print(getAngle());

                System.out.println(getSetpoint());
                if(aligned(tolerance)) {
                    correctPositionCount++;
                    System.out.print("*************CORRECT_POSITION");
                    System.out.println(correctPositionCount);
                }
                else {
                    correctPositionCount = 0;
                }
                try {
                    Thread.sleep(20);
                }
                catch(InterruptedException e) {
                    System.out.println("TURN_INTERRUPTED.");
                }
            }/*
            callback.run();*/
            
            System.out.println("End turn thread **********************************");
        })).start();
    }
    public void driveForwardByTime() {

    }
}