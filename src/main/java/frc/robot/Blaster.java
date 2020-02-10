package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.util.LogitechAxis;
import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Blaster{

    TalonFX top, bot, tilt;

    double tiltSpeed = .45;
    double topSpeed = -0.7, botSpeed = -0.9;

    ControlMode currentControl = ControlMode.PercentOutput; 

    LogitechF310 gunner;

    public Blaster(LogitechF310 gunner){
        top = new TalonFX(SystemMap.Blaster.TOPBLASTER);
        bot = new TalonFX(SystemMap.Blaster.BOTBLASTER);
        tilt = new TalonFX(SystemMap.Blaster.TILT);
        
        bot.setInverted(InvertType.InvertMotorOutput);
        tilt.getSensorCollection().setIntegratedSensorPosition(0, 0);

        this.gunner = gunner;
    }

    public void teleopUpdate(){
        System.out.println(tilt.getSensorCollection().getIntegratedSensorPosition());
        if(gunner.getAxis(LogitechAxis.RT) >= 0.1){
            bot.set(currentControl, botSpeed);
            top.set(currentControl, topSpeed);
        }
        else{
            bot.set(currentControl, 0);
            top.set(currentControl, 0);
        }

        if(gunner.getRawButton(LogitechButton.Y) == true){
            tilt.set(currentControl, tiltSpeed);
        }
        else if(gunner.getRawButton(LogitechButton.A) == true){
            tilt.set(currentControl, tiltSpeed * -1);
        }
        else{
            tilt.set(currentControl, 0);
        }
    
        System.out.println();

    }

}