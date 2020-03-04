package frc.robot.utils;
public class TransformationUtils {
    public static final double cubicTransform(double input) {
        return Math.pow(input, 3);
    }
    public static final double squareTransform(double input) {
        if(input != 0) {
            return Math.pow(input, 2) * (input / Math.abs(input)); //Apply sign by essentially multiplying by direction vector
        }
        return 0;
    }
    public static final double constrainMinusOneToOne(double input) {
        return Math.min(1, Math.max(-1, input));
    }
}