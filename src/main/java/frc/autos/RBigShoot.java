package frc.autos;
import frc.robot.*;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RBigShoot extends Auto{

    long start;

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
                if(this.blaster.shootingForAuto(.7, .5, start) == 1){
                    this.pez.startBelt();
                }
                else if(this.blaster.shootingForAuto(.7, .5, start) == 2){
                    this.nextStep();
                }
                break;
            }
            case 2:{
                //Angle
            }
            case 3:{
                if(this.drive.driveToDistance(.5, 10)){
                    this.nextStep();
                }
                break;
            }
            case 4:{
                //Turn on intake
            }
            case 5:{
                //Move Backwards
            }
            case 6:{
                //Angle
            }
            case 7:{
                //Shoot
            }
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