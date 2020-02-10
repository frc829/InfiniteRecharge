package frc.util;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight{

    private boolean isLeftZone, isRightZone;
    private long lastCamera;

    public double v = getLimelight("tv");
    public double x = getLimelight("tx");

    public Limelight(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    }

    public double getLimelight(String arg) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(arg).getDouble(0);
    }

    public void changeCamera(){
        if(System.currentTimeMillis() - lastCamera >= 1500){
            if((int)NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getNumber(1) == 1){
		        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
            }
            else if((int)NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getNumber(0) == 1){
                NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
            }
        }
        lastCamera = System.currentTimeMillis();
    }

    public double getV(){
        return this.v;
    }

    public double getX(){
        return this.x;
    }
    
}