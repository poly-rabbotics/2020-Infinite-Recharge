package frc.robot.utils;

public class CustomDifferentialDrive {
    private DriveMotor[] left, right;
    public CustomDifferentialDrive(DriveMotor[] right, DriveMotor[] left) {
        this.left = left;
        this.right = right;
    }
    public void arcadeDrive(double speed, double rotation) {
        //left[0].getMotor().set(1);
        //left[1].getMotor().set(1);
        //left[0].setVoltage(5);
        //left[1].setVoltage(5);
        System.out.println(TransformationUtils.constrainMinusOneToOne(-(speed - rotation))*13.0);
        for (DriveMotor motor: left) {
            motor.setVoltage(TransformationUtils.constrainMinusOneToOne(-(speed - rotation))*13.0);
        }
        for (DriveMotor motor: right) {
            motor.setVoltage(TransformationUtils.constrainMinusOneToOne((speed + rotation))*13.0);
        }
    }
}