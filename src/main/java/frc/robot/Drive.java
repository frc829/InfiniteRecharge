package frc.robot;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Solenoid;
import frc.util.LogitechAxis;
import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Drive{


    private CANSparkMax flThrust, frThrust, blThrust, brThrust;
    private Solenoid transmissionL, transmissionR;
    private LogitechF310 pilot;
    private double leftSpeed, rightSpeed;
    private boolean isLeftZone, isRightZone;
    private boolean transmissionStatus = false;
    private long lastGearShift;

    public Drive(){
        try {

            flThrust = new CANSparkMax(SystemMap.Drive.FLTHRUSTER, MotorType.kBrushless);
            frThrust = new CANSparkMax(SystemMap.Drive.FRTHRUSTER, MotorType.kBrushless);
            blThrust = new CANSparkMax(SystemMap.Drive.BLTHRUSTER, MotorType.kBrushless);
            brThrust = new CANSparkMax(SystemMap.Drive.BRTHRUSTER, MotorType.kBrushless);
            
            transmissionL = new Solenoid(SystemMap.Drive.PCM, SystemMap.Drive.TRANS_CHANNEL_OFF);
			transmissionL.set(transmissionStatus);
			transmissionR = new Solenoid(SystemMap.Drive.PCM, SystemMap.Drive.TRANS_CHANNEL_ON);
			transmissionL.set(!transmissionStatus);

        } catch (Exception e) {
            System.out.println("Error while initializing.");
        }
        lastGearShift = System.currentTimeMillis();
        pilot = new LogitechF310(0);
    }


    public void teleopUpdate(){

        NetworkTableInstance.getDefault().getTable("Drive").getEntry("Transmission").getBoolean(transmissionStatus);

        flThrust.set(leftSpeed);
        frThrust.set(rightSpeed);
        blThrust.set(leftSpeed);
        brThrust.set(rightSpeed);

        if(pilot.getRawButton(LogitechButton.BACK)){
            shiftGear();
        }


    }

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
  
    public void shiftGear() {
		if (System.currentTimeMillis() - lastGearShift > 1500) {
			transmissionL.set(!transmissionL.get());
			transmissionR.set(!transmissionR.get());
			lastGearShift = System.currentTimeMillis();
		}
	}

}