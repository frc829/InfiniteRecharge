package frc.autos;
import com.analog.adis16470.frc.ADIS16470_IMU;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import frc.robot.*;

public class DoNothing extends Auto{

    public DoNothing(Drive d, Blaster b, Pez p, SystemMap m, ADIS16470_IMU gyro) {
        super("DoNothing", d, b, p, m, gyro);
    }

    @Override
    public void execute() {
        switch(this.getStep()){
            case 0:{
                if(this.drive.driveToAngle(90))
                    this.nextStep();
            }
            default:{

            }
    }
}
}