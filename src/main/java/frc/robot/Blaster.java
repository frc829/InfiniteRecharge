package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Blaster{

    TalonFX top, bot, tilt;

    double tiltSpeed = .25;
    double topSpeed = 0.6, botSpeed = 0.8;

    ControlMode currentControl = ControlMode.PercentOutput; 


    public Blaster(){
        top = new TalonFX(SystemMap.Blaster.TOPBLASTER);
        bot = new TalonFX(SystemMap.Blaster.BOTBLASTER);
        tilt = new TalonFX(SystemMap.Blaster.TILT);

        bot.setInverted(InvertType.InvertMotorOutput);

    }

    public void teleopUpdate(LogitechF310 gunner){
        if(gunner.getRawButton(LogitechButton.RB) == true && gunner.getRawButton(LogitechButton.LB) == true){
            bot.set(currentControl, botSpeed);
            top.set(currentControl, topSpeed);
        }
        else{
            bot.set(currentControl, 0);
            top.set(currentControl, 0);
        }

        if(gunner.getRawButton(LogitechButton.A) == true){
            tilt.set(currentControl, tiltSpeed);
        }
        else if(gunner.getRawButton(LogitechButton.Y) == true){
            tilt.set(currentControl, tiltSpeed * -1);
        }
        else{
            tilt.set(currentControl, 0);
        }
    }



}