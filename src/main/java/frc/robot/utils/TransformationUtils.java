package frc.robot.utils;
public class TransformationUtils {
    public static final double cubicTransform(double input) {
        return Math.pow(input, 3);
    }
    public static final double squareTransform(double input) {
        return Math.pow(input, 2) * (input / Math.abs(input)); //Apply sign by essentially multiplying by direction vector
    }
}