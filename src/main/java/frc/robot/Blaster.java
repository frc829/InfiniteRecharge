package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.util.LogitechAxis;
import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Blaster{

    TalonFX top, bot, tilt;
    PIDController pid;
    double speed = 0, start;
    double tiltSpeed = .6;
    double topSpeed = 0.7, botSpeed = 0.9;
    DutyCycleEncoder abs;
    final double MAXCURRENT = 3.0;
    double k = -1.0;
    double deltaTime = .2;
    int delay = 500;
    long startTime;

    ControlMode currentControl = ControlMode.PercentOutput; 

    LogitechF310 gunner;

    public Blaster(LogitechF310 gunner){
        top = new TalonFX(SystemMap.Blaster.TOPBLASTER);
        bot = new TalonFX(SystemMap.Blaster.BOTBLASTER);
        tilt = new TalonFX(SystemMap.Blaster.TILT);
        pid = new PIDController((3*k/5), ((6*k)/(5*deltaTime)), (3*k*deltaTime/40), deltaTime);
        tilt.getSensorCollection().setIntegratedSensorPosition(0, 0);
        
        startTime = System.currentTimeMillis();
        bot.setInverted(true);
        this.gunner = gunner;
        this.abs = new DutyCycleEncoder(1);
        
    }

    public void teleopUpdate(){
        System.out.println(abs.get());
        if(gunner.getAxis(LogitechAxis.RT) >= 0.1){
            // ramp();
            bot.set(currentControl, this.botSpeed);
            top.set(currentControl, this.topSpeed);
        }
        else{
            bot.set(currentControl, 0);
            top.set(currentControl, 0);
            this.start = 0;
        }

        if(gunner.getRawButton(LogitechButton.Y) == true){
            tilt.set(currentControl, tiltSpeed);
        }
        else if(gunner.getRawButton(LogitechButton.A) == true){
            tilt.set(currentControl, tiltSpeed * -1);
        }
        else if(gunner.getPOV() == 0){
            setPOS(-.18);
        }
        else if(gunner.getPOV() == 90){
            setPOS(-.88);
        }
        else{
            tilt.set(currentControl, 0);
        }
        
        //this.topSpeed = pid.calculate(top.getStatorCurrent(), MAXCURRENT);
        //this.botSpeed = pid.calculate(bot.getStatorCurrent(), MAXCURRENT);
    }

    public void ramp(){
        if(gunner.getAxis(LogitechAxis.RT) > .1){
            if(speed < 1){
                if(System.currentTimeMillis() - startTime > delay){
                    startTime = System.currentTimeMillis();
                    speed += 0.05;
                }
            }
        }
        bot.set(currentControl, speed * this.botSpeed);
        top.set(currentControl,  speed * this.topSpeed);
    }
    
    public void setPOS(double target){
        // if(abs.get() > 0.40 ){
        //     tilt.set(ControlMode.PercentOutput, -0.5);
        // }
        // else if(abs.get() < .375){
        //     tilt.set(ControlMode.PercentOutput, 0.5);
        // }
        // else
        // tilt.set(ControlMode.PercentOutput, 0);
        double outSpeed = (3.0 * Math.pow(abs.get() - target, 3) / 4) + .7;
        if(Math.abs(target - abs.get()) <= .05){
            tilt.set(ControlMode.PercentOutput, 0);
        }
        else if(abs.get() < target){
            tilt.set(ControlMode.PercentOutput, outSpeed);
        }
        else{
            tilt.set(ControlMode.PercentOutput, -outSpeed);
        }
        
    }
}