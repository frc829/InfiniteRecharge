package frc.robot;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.util.*;
//import sun.nio.cs.ext.EUC_CN;

public class Drive {

    private CANSparkMax flThrust, frThrust, blThrust, brThrust;
    private LogitechF310 pilot, gunner;
    private double leftSpeed, rightSpeed;
    boolean xPressed = false;
    ADIS16470_IMU gyro;
    
    //double startGyro;
    //boolean isTurning = false;

    public Drive(LogitechF310 pilot, LogitechF310 gunner, ADIS16470_IMU gyro) {
        try{

            flThrust = new CANSparkMax(SystemMap.Drive.FLTHRUSTER, MotorType.kBrushless);
            frThrust = new CANSparkMax(SystemMap.Drive.FRTHRUSTER, MotorType.kBrushless);
            blThrust = new CANSparkMax(SystemMap.Drive.BLTHRUSTER, MotorType.kBrushless);
            brThrust = new CANSparkMax(SystemMap.Drive.BRTHRUSTER, MotorType.kBrushless);
            this.gyro = gyro;
            gyro.reset();

            flThrust.setInverted(true);
            blThrust.setInverted(true);

            this.resetEncoders();
 
            flThrust.setSmartCurrentLimit(50);
            frThrust.setSmartCurrentLimit(50);
            blThrust.setSmartCurrentLimit(50);
            brThrust.setSmartCurrentLimit(50);

            
            flThrust.setIdleMode(IdleMode.kCoast);
            frThrust.setIdleMode(IdleMode.kCoast);
            blThrust.setIdleMode(IdleMode.kCoast);
            brThrust.setIdleMode(IdleMode.kCoast);

            flThrust.setOpenLoopRampRate(0);
            frThrust.setOpenLoopRampRate(0);
            blThrust.setOpenLoopRampRate(0);
            brThrust.setOpenLoopRampRate(0);

        } 
        catch(Exception e){
            System.out.println("Error while initializing.");
        }
        this.pilot = pilot;
        this.gunner = gunner;
    }

    // Moving and Teleop Method
    public void teleopUpdate() {

        flThrust.set(-rightZone());
        frThrust.set(-leftZone());
        blThrust.set(-rightZone());
        brThrust.set(-leftZone());
        //System.out.println("Gyro Angle:" + gyro.getAngle());
        //System.out.println("EncoderCounts:" + blThrust.getEncoder().getPosition());

        if(pilot.getRawButton(LogitechButton.A) == true){
            Limelight.changeCamera(0, 0);
            autoAim();
        }
        else{
            if(gunner.getPOV() != 180)
                Limelight.changeCamera(1,1);
        }
        
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
        //double outputSpeed = -(3.822)*Math.pow((Math.abs(Limelight.getX())*Math.PI/180), 3)+.1;
        double outputSpeed = .1;
            if(Limelight.getV() == 1){
                if(Limelight.getX() > -8){
                    turnRight(outputSpeed);
                }
                else if(Limelight.getX() < -10){
                    turnLeft(outputSpeed);
                }
                else{
                    stopAllMotors();
                }
            }
    }

    // Utility Methods - Controllers
    public double leftZone() {
        //System.out.println("Left JoyStick: " + LogitechAxis.LY);
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

    // public boolean driveToAngle(double targetAngle){
    //     System.out.println(gyro.getAngle());
    //     double outputSpeed = (3.822)*Math.pow((Math.abs(gyro.getAngle())*Math.PI/180), 3)+.1;
    //     if(targetAngle - gyro.getAngle() >= 5){
    //         turnLeft(outputSpeed);
    //         return false;
    //     }
    //     else if(targetAngle - gyro.getAngle() <= -5){
    //        turnRight(outputSpeed);
    //        return false;
    //     }
    //     else{
    //         stopAllMotors();
    //         return true;
    //     }
    // }
    long lastTurn = 1000000000;
    public boolean driveToAngle(double targetAngle){
        double outputSpeed = (3.822)*Math.pow((Math.abs(gyro.getAngle() - targetAngle)*Math.PI/180), 3)+.1;
        //double outputSpeed = .1 + .2*Math.abs(gyro.getAngle()-startGyro-relativeTarget)/10;
        // if(!isTurning){
        //     startGyro = gyro.getAngle();
        //     isTurning = true;
        // }
        if(gyro.getAngle()-.5>targetAngle){
            turnRight(outputSpeed);
            lastTurn = System.currentTimeMillis();
            return true;
        }
        else if(gyro.getAngle()+.5<targetAngle){
            turnLeft(outputSpeed);
            lastTurn = System.currentTimeMillis();
            return true;
        }
        else{
            stopAllMotors();
            if(System.currentTimeMillis() - lastTurn > 1000){
                //isTurning = true;
                return true;
            }
            return false;
        }

    }

    public boolean driveToDistance(double speed, double distance){
        double encoderCounts = (13.2/(Math.PI * 9))*distance;
        if(brThrust.getEncoder().getPosition() <= encoderCounts-.05){
            //System.out.println(brThrust.getEncoder().getPosition());
            moveForward(speed);
            return false;
        }
        else{
            resetEncoders();
            stopAllMotors();
            return true;
        }

    }

    public void resetEncoders(){
        frThrust.getEncoder().setPosition(0);
        flThrust.getEncoder().setPosition(0);
        brThrust.getEncoder().setPosition(0);
        blThrust.getEncoder().setPosition(0);
    }
}