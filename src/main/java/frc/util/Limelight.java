package frc.util;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight{

    private static long lastCamera;

    public static double getLimelight(String arg) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(arg).getDouble(0);
    }

    public static void changeCamera(int camMode, int ledMode){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(ledMode);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(camMode);
    }

    public static double getV(){
        return getLimelight("tv");
    }

    public static double getX(){
        return getLimelight("tx");
    }

    public static double getY(){
        return getLimelight("ty");
    }
}