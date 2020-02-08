package frc.robot;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableInstance;
import frc.util.LogitechAxis;
import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Drive{


    private CANSparkMax flThrust, frThrust, blThrust, brThrust;
    private LogitechF310 pilot;
    private double leftSpeed, rightSpeed;
    private boolean isLeftZone, isRightZone;

    public double v = getLimelight("tv");
    public double x = getLimelight("tx");

    long lastCamera;

    public Drive(LogitechF310 pilot){
        try {

            flThrust = new CANSparkMax(SystemMap.Drive.FLTHRUSTER, MotorType.kBrushless);
            frThrust = new CANSparkMax(SystemMap.Drive.FRTHRUSTER, MotorType.kBrushless);
            blThrust = new CANSparkMax(SystemMap.Drive.BLTHRUSTER, MotorType.kBrushless);
            brThrust = new CANSparkMax(SystemMap.Drive.BRTHRUSTER, MotorType.kBrushless);

            flThrust.setInverted(true);
            blThrust.setInverted(true);

        } catch (Exception e) {
            System.out.println("Error while initializing.");
        }
        this.pilot = pilot;
        lastCamera = System.currentTimeMillis();
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    }

//Moving and Teleop Method
    public void teleopUpdate(){

        flThrust.set(leftSpeed);
        frThrust.set(rightSpeed);
        blThrust.set(leftSpeed);
        brThrust.set(rightSpeed);

        autoAim();
    }


    public void moveForward(){
        flThrust.set(.5);
        frThrust.set(.5);
        blThrust.set(.5);
        brThrust.set(.5);        
    }

    public void moveBack(){
        flThrust.set(-.5);
        frThrust.set(-.5);
        blThrust.set(-.5);
        brThrust.set(-.5);
    }

//Auto Targeting Stuff

    public void autoAim(){
        if(pilot.getRawButton(LogitechButton.X) == true && v == 1){
            if(Math.abs(x) > 2.5){
             flThrust.set(.5);
             frThrust.set(.5);
             blThrust.set(.5);
             brThrust.set(.5);
            }
            else{
             flThrust.set(0);
             frThrust.set(0);
             blThrust.set(0);
             brThrust.set(0);
            }
        }
    }

    public void changeCamera(){
        if(pilot.getRawButton(LogitechButton.BACK) == true && System.currentTimeMillis() - lastCamera >= 1500){
            if((int)NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getNumber(1) == 1){
		        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
            }
            else if((int)NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").getNumber(0) == 1){
                NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
            }
        }
        lastCamera = System.currentTimeMillis();
    }


//Utility Methods - Controllers
    public boolean leftZone(){

        if(pilot.getAxis(LogitechAxis.LY) > 0.1 || pilot.getAxis(LogitechAxis.LY) < -0.10){
            isLeftZone = true;
        }
        else
            isLeftZone = false;

        return isLeftZone;
    }

    public boolean rightZone(){

        if(pilot.getAxis(LogitechAxis.RY) > 0.1 || pilot.getAxis(LogitechAxis.RY) < -0.1){
            isRightZone = true;
        }
        else
            isRightZone = false;


        return isRightZone;
    }

    public double leftSpeed(){

        if(isLeftZone == true){
            leftSpeed = Math.pow(pilot.getAxis(LogitechAxis.LY), 0.75);
        }
        else
            leftSpeed = 0;

        return leftSpeed;
    }

    public double rightSpeed(){

        if(isRightZone == true){
            rightSpeed = Math.pow(pilot.getAxis(LogitechAxis.RY), 0.75);
        }
        else
            rightSpeed = 0;

        return rightSpeed;
    }

    public double getLimelight(String arg) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(arg).getDouble(0);
      }

}