package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import frc.util.LogitechAxis;
import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Pez {

    CANSparkMax rightIN, leftIN;
    TalonSRX beltL, beltR, WOF;
    CANSparkMax pickup, deadSpot;
    public DoubleSolenoid taker;
    LogitechF310 gunner, pilot;
    long toggle;
    long delay;
    double lastOne = 0;

    Boolean buttonJustPressed = false;
    int lightState = 0;

    public Pez(LogitechF310 gunner, LogitechF310 pilot) {

        rightIN = new CANSparkMax(SystemMap.Pez.MAROONR, MotorType.kBrushless);
        leftIN = new CANSparkMax(SystemMap.Pez.MAROONL, MotorType.kBrushless);
        beltL = new TalonSRX(SystemMap.Pez.PEZL);
        beltR = new TalonSRX(SystemMap.Pez.PEZR);
        WOF = new TalonSRX(SystemMap.Pez.WOF);

        taker = new DoubleSolenoid(SystemMap.Pez.PORT1, SystemMap.Pez.PORT2);

        taker.set(DoubleSolenoid.Value.kReverse);

        pickup = new CANSparkMax(SystemMap.Pez.INTAKE, MotorType.kBrushless);
        deadSpot = new CANSparkMax(SystemMap.Pez.DEADSPOT, MotorType.kBrushless);

        beltL.setInverted(true);
        rightIN.setInverted(true);
        leftIN.setInverted(true);
        pickup.setInverted(true);

        toggle = System.currentTimeMillis();

        this.gunner = gunner;
        this.pilot = pilot;
       

    }

    public void teleopUpdate() {

        if (gunner.getRawButton(LogitechButton.B) == true) {
            if(!buttonJustPressed){
                System.out.println("Button Just Pressed");
                buttonJustPressed= true;
                lastOne = 0;
                delay = System.currentTimeMillis();
            }
            pickUp(0.9);
            intake(.8, .8, .8);
               
        } else if (gunner.getRawButton(LogitechButton.X) == true) {
            intake(-.5, -.5, -.5);
            pickUp(-0.9);
        } 
        else if(gunner.getAxis(LogitechAxis.RT) > .1){
            if(gunner.getAxis(LogitechAxis.LT) > .1){
                intake(.5,.5, .5);
            }
            
        }
        else if(gunner.getAxis(LogitechAxis.LT) > .1){
            WOF.set(ControlMode.PercentOutput, .5);
        }
        else{
            intake(0, 0, 0);
            pickUp(0);
            WOF.set(ControlMode.PercentOutput, 0);
            buttonJustPressed = false;
        }
        if (gunner.getRawButton(LogitechButton.RB) == true) {
            belt(.5, .5);
        } else if (gunner.getRawButton(LogitechButton.LB) == true) {
            belt(-.4, -4.);
        } else {
            belt(0, 0);
        }
        if (gunner.getRawButton(LogitechButton.BACK) == true) {
            shift();
        }

    }

    public void pickUp(double num1) {
        pickup.set(num1);
    }

    public void intake(double num1, double num2, double num3) {
        rightIN.set(-num1);
        leftIN.set(num2);
        deadSpot.set(-num3);
    }

    public void belt(double num3, double num4) {
        beltL.set(ControlMode.PercentOutput, -1 * num3);
        beltR.set(ControlMode.PercentOutput, -1 * num4);
    }

    public void shift() {
        if (System.currentTimeMillis() - toggle > 500) {
            if (taker.get() == DoubleSolenoid.Value.kForward) {
                taker.set(DoubleSolenoid.Value.kReverse);
            } else {
                taker.set(DoubleSolenoid.Value.kForward);
            }
            toggle = System.currentTimeMillis();
        }

    }

    public void shiftIntake(){
        if (taker.get() == DoubleSolenoid.Value.kForward) {
            taker.set(DoubleSolenoid.Value.kReverse);
        } else {
            taker.set(DoubleSolenoid.Value.kForward);
        }
    }

    public void startBelt(){
        belt(.75, .75);
    }

    public void stopBelt(){
        belt(0, 0);
    }

}