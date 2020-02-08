package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.util.LogitechAxis;
import frc.util.LogitechF310;

public class Boost{

    TalonSRX booster;
    LogitechF310 gunner;

    public Boost(LogitechF310 gunner){
    booster = new TalonSRX(SystemMap.Boost.BOOST);
    this.gunner = gunner;
    }

    public void teleopUpdate(){
        lift();
    }

    public void lift(){
        if(gunner.getAxis(LogitechAxis.LY) > 0.1 || gunner.getAxis(LogitechAxis.LY) < -0.1){
            booster.set(ControlMode.PercentOutput, gunner.getAxis(LogitechAxis.LY)*0.75);
        }
    }

}