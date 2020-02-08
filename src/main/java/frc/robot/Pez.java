package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Pez{

    CANSparkMax rightIN, leftIN;
    TalonSRX beltL, beltR;
    TalonFX pickup;
    LogitechF310 gunner;

    public Pez(LogitechF310 gunner){

    rightIN = new CANSparkMax(SystemMap.Pez.MAROONR, MotorType.kBrushless);
    leftIN = new CANSparkMax(SystemMap.Pez.MAROONL, MotorType.kBrushless);
    beltL = new TalonSRX(SystemMap.Pez.PEZL);
    beltR = new TalonSRX(SystemMap.Pez.PEZR);

    pickup = new TalonFX(SystemMap.Pez.INTAKE);

    beltL.setInverted(true);
    leftIN.setInverted(true);

    this.gunner =gunner;

    }

    public void teleopUpdate(){
     
        if(gunner.getRawButton(LogitechButton.LB) == true && gunner.getRawButton(LogitechButton.LB) == true){
            pickUp(0.35);
            intake(.5, .5, .65, .65);
        }
        else{
            pickUp(0.0);
            intake(0.0, 0.0, 0.0, 0.0);
        }

    }

    public void pickUp(double num1){
        pickup.set(ControlMode.PercentOutput, num1);
    }

    public void intake(double num1, double num2, double num3, double num4){
        rightIN.set(num1);
        leftIN.set(num2);
        beltL.set(ControlMode.PercentOutput, num3);
        beltR.set(ControlMode.PercentOutput, num4);
    }

}