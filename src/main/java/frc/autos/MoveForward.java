package frc.autos;

import com.analog.adis16470.frc.ADIS16470_IMU;

import frc.robot.*;

public class MoveForward extends Auto{

    public MoveForward(Drive d, Blaster b, Pez p, SystemMap m, ADIS16470_IMU gyro){
        super("MoveForward", d, b, p, m, gyro);
    }

    @Override
    public void execute() {
        switch(this.getStep()){
            case 0: {
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