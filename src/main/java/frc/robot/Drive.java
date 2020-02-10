package frc.robot;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.util.Limelight;
import frc.util.LogitechAxis;
import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Drive{

    private CANSparkMax flThrust, frThrust, blThrust, brThrust;
    private LogitechF310 pilot;
    private double leftSpeed, rightSpeed;
    private Limelight limelight;

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
        this.limelight = new Limelight();
        this.pilot = pilot;
    }

//Moving and Teleop Method
    public void teleopUpdate(){

        flThrust.set(leftZone());
        frThrust.set(-rightZone());
        blThrust.set(leftZone());
        brThrust.set(rightZone());
        if(pilot.getRawButton(LogitechButton.BACK) == true){
            limelight.changeCamera();
        }

        //autoAim();
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
        if(pilot.getRawButton(LogitechButton.X) == true && limelight.getV() == 1){
            if(Math.abs(limelight.getX()) > 2.5){
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


//Utility Methods - Controllers
    public double leftZone(){

        if(pilot.getAxis(LogitechAxis.LY) > 0.1 || pilot.getAxis(LogitechAxis.LY) < -0.1){
            leftSpeed = pilot.getAxis(LogitechAxis.LY);
        }
        else
            leftSpeed = 0;
 
            return leftSpeed;
    }

    public double rightZone(){

        if(pilot.getAxis(LogitechAxis.RY) > 0.1 || pilot.getAxis(LogitechAxis.RY) < -0.1){
            rightSpeed = pilot.getAxis(LogitechAxis.RY);
        }
        else
            rightSpeed = 0;

            return rightSpeed;
    }

    

}