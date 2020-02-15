package frc.robot.vision;

import org.opencv.core.*;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import org.opencv.imgproc.*;

public class VerticalTarget {
    public static int PIXELS_PER_INCH = 1500; //Total guess.
    public static double VIEW_ANGLE = 0.5; //Quantity in radians. This value was determined experimentally and is open to change

    private double focalDistance;
    private double targetHeight;
    private double targetWidth;
    private PreProc source;
    public VerticalTarget(PreProc source, double focalDistance, double targetHeight, double targetWidth) {
        this.source = source;
        this.focalDistance = focalDistance;
        this.targetHeight = targetHeight;
        this.targetWidth = targetWidth;
    }
    private double getCenterXPos() {
        ArrayList<Rect> rects = source.getRects();
        if (rects.size() != 0) {
            return (((double) rects.get(0).x + rects.get(0).width) / 2) / PIXELS_PER_INCH;
        }
        return -1;
    }
    private double getBottomImageHeight() {
        ArrayList<Rect> rects = source.getRects();
        if (rects.size() != 0) {
            return ((double) rects.get(0).y + rects.get(0).height) / PIXELS_PER_INCH;
        }
        return -1;
    }
    private double getTopImageHeight() {
        ArrayList<Rect> rects = source.getRects();
        if (rects.size() != 0) {
            return ((double) rects.get(0).y) / PIXELS_PER_INCH;
        }
        return -1;
    }
    private double getTanAlpha() {
        return getBottomImageHeight() / focalDistance;
    }
    private double getTanBeta() {
        return getTopImageHeight() / focalDistance;
    }
    public double getYaw() {
        return .25 - Math.atan(getCenterXPos() / focalDistance);
    }
    public double getDistance() {
        SmartDashboard.putNumber("tanalpha", getTanAlpha());
        SmartDashboard.putNumber("tanbeta", getTanBeta());
        return targetHeight / (getTanAlpha() - getTanBeta());
    }
    public double getTheta() {
        double aspectRatio = targetWidth / targetHeight;
        //SmartDashboard.putNumber("aspect ratio", aspectRatio);
        double apparentWidth = (getBottomImageHeight() - getTopImageHeight()) / 40 * aspectRatio;
        //SmartDashboard.putNumber("topimageheight", getTopImageHeight());
        //SmartDashboard.putNumber("bottomimageheight", getBottomImageHeight());
        //SmartDashboard.putNumber("apparentwidth", apparentWidth);
        //SmartDashboard.putNumber("targetwidth", targetWidth);
        return Math.asin(Math.min(apparentWidth / targetWidth, 1));
    }
}