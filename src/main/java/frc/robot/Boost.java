package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.util.LogitechF310;

public class Boost{

    TalonSRX booster;
    LogitechF310 gunner;

    public Boost(){
    booster = new TalonSRX(SystemMap.Boost.BOOST);
    gunner = new LogitechF310(1);
    }

    public void teleopUpdate(){
         
    }

}