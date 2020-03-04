package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.util.Limelight;
import frc.util.LogitechAxis;
import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Blaster{

    TalonFX top, bot, tilt;
    PIDController pid;
    double speed = .3, start;
    double tiltSpeed = .75;
    double topSpeed = 0, botSpeed = 0;
    DutyCycleEncoder abs;
    double startPos, toIntake = -0.5, toTrench = -1.1;
    final double MAXCURRENT = 3.0;
    double k = -1.0;
    double deltaTime = .2;
    int delay = 50;
    long startTime;

    ControlMode currentControl = ControlMode.PercentOutput; 
    TalonFXControlMode shooterControl = TalonFXControlMode.PercentOutput;

    LogitechF310 gunner, pilot;

    int targetVel = 0;
    public Blaster(LogitechF310 gunner, LogitechF310 pilot){
        top = new TalonFX(SystemMap.Blaster.TOPBLASTER);
        bot = new TalonFX(SystemMap.Blaster.BOTBLASTER);
        tilt = new TalonFX(SystemMap.Blaster.TILT);
        pid = new PIDController((3*k/5), ((6*k)/(5*deltaTime)), (3*k*deltaTime/40), deltaTime);
        tilt.getSensorCollection().setIntegratedSensorPosition(0, 0);
        
        startTime = System.currentTimeMillis();
        bot.setInverted(true);
        this.gunner = gunner;
        this.pilot = pilot;
        this.abs = new DutyCycleEncoder(1);
        double st = System.currentTimeMillis();
        while(System.currentTimeMillis() - st < 1000){
            
        }
        this.startPos = abs.get();
        System.out.println(startPos);

    }

    public void teleopUpdate(){
        System.out.println("Current Pos:" + abs.get());
        if(gunner.getAxis(LogitechAxis.RT) >= 0.1){
            //ramp();

            if(top.getSelectedSensorVelocity()<18900){
                topSpeed+=.01;
            }

             if(bot.getSelectedSensorVelocity()<16800){
                botSpeed+=.01;
            }
            
            //System.out.println("Shooter Velocity: " + bot.getSelectedSensorVelocity();

            bot.set(shooterControl, this.botSpeed);
            top.set(shooterControl, this.topSpeed);
        }
        else if(gunner.getPOV() == 270){
            trenchRun();
        }
        else{
            bot.set(shooterControl, 0);
            top.set(shooterControl, 0);
            botSpeed = 0;
            topSpeed = 0;
            this.start = 0;
        }

        if(gunner.getAxis(LogitechAxis.RY) > 0.1){
            System.out.println(gunner.getAxis(LogitechAxis.RY));
            tilt.set(currentControl, Math.pow(gunner.getAxis(LogitechAxis.RY), tiltSpeed));
        }
        else if(gunner.getAxis(LogitechAxis.RY) < -0.1){
            tilt.set(currentControl, -1 * (Math.pow(Math.abs(gunner.getAxis(LogitechAxis.RY)), tiltSpeed)));
        }
        else if(gunner.getPOV() == 0){
            setPOS(startPos + toIntake);
        }
    
        else if(gunner.getPOV() == 90){
            setPOS(startPos + toTrench);
        }
        else if(gunner.getPOV() == 180){
            Limelight.changeCamera(0, 0);
            autoAim();
        }
        else{
            if(!pilot.getRawButton(LogitechButton.A))
                Limelight.changeCamera(1, 1);
            tilt.set(currentControl, -.05);
        }
        
        //this.topSpeed = pid.calculate(top.getStatorCurrent(), MAXCURRENT);
        //this.botSpeed = pid.calculate(bot.getStatorCurrent(), MAXCURRENT);
    }

    // public void ramp(){
    //     if(gunner.getAxis(LogitechAxis.RT) > .1){
    //         if(speed < 1){
    //             speed += .1;
    //         }
    //     }
    //     bot.set(currentControl, speed * this.botSpeed);
    //     top.set(currentControl,  speed * this.topSpeed);
    // }

    public void autoAim(){
        //Height: 98.25
        double x = (90 - 22.5)/Math.tan((Limelight.getY()+20)*Math.PI/180);
        double angle = Math.atan((90 - 17.25)/ (x + 17));
        angle = (2*1.27*angle/Math.PI);
        System.out.println("Reading Limelight Angle:" + Limelight.getY());
        System.out.println(angle);
        System.out.println(startPos - 1.27 + angle);
        setPOS(angle - startPos);
        
    }

    public int shootingForAuto(double topSpeed, double botSpeed, long start, double angle, long waitForBelts){
        if(setPOSAuto(startPos + angle)){
            long shootTime = 3000;
            bot.set(currentControl, botSpeed);
            top.set(currentControl, topSpeed);
            
            if(System.currentTimeMillis() - start >= waitForBelts &&  System.currentTimeMillis() - start < shootTime+waitForBelts){
                System.out.println("Step 1");
                System.out.println(System.currentTimeMillis() - start);
                return 1;
            }
            else if(System.currentTimeMillis() - start >= shootTime+waitForBelts){
                bot.set(currentControl, 0);
                top.set(currentControl, 0);
                System.out.println("Step 2");
                return 2;
            }
            else{
                return -1;
            }
        }
        else{
            bot.set(currentControl, 0);
            top.set(currentControl, 0);
            return 0;
        }
        
    }

    public void trenchRun(){
        setPOS(startPos + toTrench);

        if(top.getSelectedSensorVelocity()<18900){
            topSpeed+=.01;
        }

         if(bot.getSelectedSensorVelocity()<16800){
            botSpeed+=.01;
        }

        bot.set(shooterControl, this.botSpeed);
        top.set(shooterControl, this.topSpeed);
    }
    
    public void setPOS(double target){
        double outSpeed = (3.0 * Math.pow(abs.get() - target, 3) / 4) + .4;
        if(Math.abs(target - abs.get()) <= .1){
            tilt.set(ControlMode.PercentOutput, 0);
        }
        else if(abs.get() < target){
            tilt.set(ControlMode.PercentOutput, outSpeed);
        }
        else{
            tilt.set(ControlMode.PercentOutput, -outSpeed);
        }
    }

    public boolean setPOSAuto(double target){
        System.out.println("Step 0");
        boolean returnVal;
        double outSpeed = (3.0 * Math.pow(abs.get() - target, 3) / 4) + .6;
        if(Math.abs(target - abs.get()) <= .05){
            tilt.set(ControlMode.PercentOutput, 0);
            returnVal = true;
        }
        else if(abs.get() < target){
            tilt.set(ControlMode.PercentOutput, outSpeed);
            returnVal = false;
        }
        else{
            tilt.set(ControlMode.PercentOutput, -outSpeed);
            returnVal = false;
        }

        return returnVal;
    }
        
}