package frc.autos;
import frc.robot.*;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RBigShoot extends Auto{

    long start;
    double autoShoot1 = -.825, autoShoot2;

    public RBigShoot(Drive d, Blaster b, Pez p, SystemMap m, ADIS16470_IMU gyro){
        super("Right Big Shoot", d, b, p, m, gyro);
    }

    @Override
    public void execute() {
        switch(this.getStep()){
            case 0:{
                this.pez.taker.set(DoubleSolenoid.Value.kForward);
                this.nextStep();
                this.start = System.currentTimeMillis();
                break;
            }
            case 1:{
                if(this.blaster.shootingForAuto(.7, .5, start, autoShoot1,5000) == 1){
                    this.pez.startBelt();
                    this.pez.intake(.5, .5, .5);
                    
                }
                else if(this.blaster.shootingForAuto(.7, .5, start, autoShoot1,5000) == 2){
                    this.pez.stopBelt();
                    this.nextStep();
                }
                break;
            }
            case 2:{
                if(!this.drive.driveToAngle(-33)){
                    this.nextStep();
                }
                break;
            }
            case 3:{
                this.pez.pickUp(.5);
                this.nextStep();
                break;
            }
            case 4:{
                this.drive.resetEncoders();
                if(this.drive.driveToDistance(.5, 5)){
                    this.nextStep();
                }
                break;
            }
            // case 5:{
            //     if(!this.drive.driveToAngle(-33)){
            //         this.nextStep();
            //     }
            //     break;
            // }
            // case 6:{
            //     if(this.blaster.shootingForAuto(.7, .5, start, autoShoot2,5000) == 1){
            //         this.pez.startBelt();
            //     }
            //     else if(this.blaster.shootingForAuto(.7, .5, start, autoShoot2,5000) == 2){
            //         this.nextStep();
            //     }
            //     break;
            // }
            default:{
                this.drive.stopAllMotors();
            }
        }
    }
    //Step 0: Lift down intake
    //Step 1: Shoot
    //Step 2: Angle
    //Step 3: Move forward
    //Step 4: Turn on intake
    //Step 5: Move Backwards
    //Step 6: Angle
    //Step 7: Shoot
}