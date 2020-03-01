package frc.robot;

import com.analog.adis16470.frc.ADIS16470_IMU;

//import edu.wpi.first.networktables.NetworkTableInstance;
import frc.util.LogitechF310;

public abstract class Auto{

    protected Drive drive;
    protected Blaster blaster;
    protected Pez pez;
    protected ADIS16470_IMU gyro;
    protected SystemMap systems;
    protected String name;
    protected int step;

    public LogitechF310 pilot = new LogitechF310(0);
    public LogitechF310 gunner = new LogitechF310(1);

    public Auto(String n, Drive d, Blaster b, Pez p, SystemMap m, ADIS16470_IMU gyro){
        this.drive = d;
        this.blaster = b;
        this.pez = p;
        this.systems = m;
        this.name = n;
        this.step = 0;
        this.gyro = gyro;
        resetGyro();
    }

    public abstract void execute();


    public int getStep(){
        return this.step;
    }

    public void nextStep(){
        this.step += 1;
    }

    public void resetGyro(){
        this.gyro.reset();
    }

    public String getName(){
        return this.name;
    }

}