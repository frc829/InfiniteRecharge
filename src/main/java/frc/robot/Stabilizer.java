package frc.robot;

import com.analog.adis16470.frc.ADIS16470_IMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Stabilizer extends ADIS16470_IMU{

    public Stabilizer(){
        super();
    }
    
    public void update(){
        SmartDashboard.putNumber("Gyro: Angle", this.getAngle());
    }
}