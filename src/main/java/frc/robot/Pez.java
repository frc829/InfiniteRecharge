package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.util.LogitechF310;

public class Pez{

    CANSparkMax rightIN, leftIN, beltL, beltR;
    TalonFX pickup;
    LogitechF310 gunner;

    public Pez(){

    rightIN = new CANSparkMax(SystemMap.Pez.INTAKERIGHT, MotorType.kBrushless);
    leftIN = new CANSparkMax(SystemMap.Pez.INTAKELEFT, MotorType.kBrushless);
    beltL = new CANSparkMax(SystemMap.Pez.BELTLEFT, MotorType.kBrushless);
    beltR = new CANSparkMax(SystemMap.Pez.BELTRIGHT, MotorType.kBrushless);

    pickup = new TalonFX(SystemMap.Pez.PICKUP);

    }

    public void teleopUpdate(){
        
    }


}