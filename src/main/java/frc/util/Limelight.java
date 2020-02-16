package frc.util;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight{

    private static long lastCamera;

    public static double getLimelight(String arg) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(arg).getDouble(0);
    }

    public static void changeCamera(int camMode, int ledMode){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(camMode);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(ledMode);

        /* Number limelightState = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getNumber(1);

            if( limelightState == (Number)1 ){
		        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
            }
            else {
                NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
            }*/
    }

    public static double getV(){
        return getLimelight("tv");
    }

    public static double getX(){
        return getLimelight("tx");
    }
}