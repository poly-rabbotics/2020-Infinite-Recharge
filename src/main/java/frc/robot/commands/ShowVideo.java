package frc.robot.commands;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;

import org.opencv.core.*;

public class ShowVideo extends Command {
    private CvSource outputStream;
    private CvSink cameraStream;
    private Mat image;
    private boolean enabled;
    public ShowVideo(CvSink cameraStream, CvSource outputStream, String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        this.outputStream = outputStream;
        this.cameraStream = cameraStream;
        this.enabled = false;
    }
    protected void whileRunning() {
        if(enabled) {
            cameraStream.grabFrame(image);
            //Debugging
            System.out.print("image.rows: ");
            System.out.println(image.rows());
            System.out.print("image.cols: ");
            System.out.println(image.cols());
            
            outputStream.putFrame(image);
        }
    }
    public Mat getImage() {
        return image;
    }
    public void disable() {
        enabled = false;
    }
    public void enable() {
        enabled = true;
    }
    protected boolean isFinished() {
        return false;
    }
}