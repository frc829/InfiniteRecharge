package frc.autos;
import com.analog.adis16470.frc.ADIS16470_IMU;


import frc.robot.*;

public class DoNothing extends Auto{

    public DoNothing(Drive d, Blaster b, Pez p, SystemMap m, ADIS16470_IMU gyro) {
        super("DoNothing", d, b, p, m, gyro);
    }

    @Override
    public void execute() {
        
    }
}