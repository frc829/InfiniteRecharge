package frc.robot;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.util.*;

public class Drive {

    private CANSparkMax flThrust, frThrust, blThrust, brThrust;
    private LogitechF310 pilot;
    private double leftSpeed, rightSpeed;
    private int ledMode, camMode;
    private long lastCam;
    
    

    public Drive(LogitechF310 pilot) {
        try{

            flThrust = new CANSparkMax(SystemMap.Drive.FLTHRUSTER, MotorType.kBrushless);
            frThrust = new CANSparkMax(SystemMap.Drive.FRTHRUSTER, MotorType.kBrushless);
            blThrust = new CANSparkMax(SystemMap.Drive.BLTHRUSTER, MotorType.kBrushless);
            brThrust = new CANSparkMax(SystemMap.Drive.BRTHRUSTER, MotorType.kBrushless);

            flThrust.setInverted(true);
            blThrust.setInverted(true);

            flThrust.setSmartCurrentLimit(40);
            frThrust.setSmartCurrentLimit(40);
            blThrust.setSmartCurrentLimit(40);
            brThrust.setSmartCurrentLimit(40);

        } 
        catch(Exception e){
            System.out.println("Error while initializing.");
        }
        lastCam = System.currentTimeMillis();
        this.pilot = pilot;
    }

    // Moving and Teleop Method
    public void teleopUpdate() {

        flThrust.set(leftZone());
        frThrust.set(rightZone());
        blThrust.set(leftZone());
        brThrust.set(rightZone());
        if(pilot.getRawButton(LogitechButton.RB) == true) {
            // Limelight.changeCamera(0,0);
        } 
        else{
            // Limelight.changeCamera(1,1);
        }

        autoAim();
    }

    public void moveForward(double x) {
        flThrust.set(x);
        frThrust.set(x);
        blThrust.set(x);
        brThrust.set(x);
    }

    public void turnRight(double x) {
        flThrust.set(-x);
        frThrust.set(x);
        blThrust.set(-x);
        brThrust.set(x);
    }

    public void turnLeft(double x) {
        flThrust.set(x);
        frThrust.set(-x);
        blThrust.set(x);
        brThrust.set(-x);
    }

    public void moveBack(double x) {
        flThrust.set(-x);
        frThrust.set(-x);
        blThrust.set(-x);
        brThrust.set(-x);
    }

    public void stopAllMotors() {
        flThrust.set(0);
        frThrust.set(0);
        blThrust.set(0);
        brThrust.set(0);
    }

    // Auto Targeting Stuff

    public void autoAim() {
        lastCam = System.currentTimeMillis();
        // ledMode = 0;
        // camMode = 0;
        if(pilot.getRawButton(LogitechButton.X) == true) {
            // // System.out.println("hey");
            //     Limelight.changeCamera(camMode, ledMode);
            //     if(Limelight.getV() == 1 && Limelight.getX() > 2.5) {
            //         turnLeft(.25);
            //         System.out.println("tracking");
            //     } 
            //     else if(Limelight.getV() == 1 && Limelight.getX() < -2.5) {
            //         turnRight(.25);
            //         System.out.println("tracking");
            //     } 
            //     else if(Limelight.getV() == 0){
            //         turnRight(.25);
            //         System.out.println("pp poopoo");
            //     }
            //     else{
            //         flThrust.set(0);
            //         frThrust.set(0);
            //         blThrust.set(0);
            //         brThrust.set(0);
            //         // System.out.println("nah");
            //     }
            Limelight.changeCamera(0, 0);
            if(Limelight.getV() == 1){
                if(Math.abs(Limelight.getX()) > 10){
                    turnLeft(.1);
                    System.out.println("It's on the left");
                }
                else {
                    stopAllMotors();
                }
            }
            else{
                
                    turnLeft(.25);
                    System.out.println("tracking");
                
            }
        } 
        else{
            // ledMode = 0;
            // camMode = 1;
            Limelight.changeCamera(1, 1);
        }
    }

    // Utility Methods - Controllers
    public double leftZone() {

        if(pilot.getAxis(LogitechAxis.LY) > 0.1 || pilot.getAxis(LogitechAxis.LY) < -0.1) {
            leftSpeed = pilot.getAxis(LogitechAxis.LY);
        } 
        else
            leftSpeed = 0;

        return leftSpeed;
    }

    public double rightZone() {

        if(pilot.getAxis(LogitechAxis.RY) > 0.1 || pilot.getAxis(LogitechAxis.RY) < -0.1) {
            rightSpeed = pilot.getAxis(LogitechAxis.RY);
        } 
        else
            rightSpeed = 0;

        return rightSpeed;
    }

}