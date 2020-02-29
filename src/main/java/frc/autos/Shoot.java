package frc.autos;
import frc.robot.*;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shoot extends Auto{

    long start;

    public Shoot(Drive d, Blaster b, Pez p, SystemMap m, ADIS16470_IMU gyro) {
        super("Shoot", d, b, p, m, gyro);
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
                if(this.drive.driveToDistance(.5, 10)){
                    this.nextStep();
                }
                break;
            }
            default:{
                this.drive.stopAllMotors();
            }

        }
    }
}