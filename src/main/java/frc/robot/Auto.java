package frc.robot;

//import edu.wpi.first.networktables.NetworkTableInstance;
import frc.util.LogitechF310;

public abstract class Auto{

    protected Drive drive;
    protected Blaster blaster;
    protected Pez pez;
    protected Stabilizer stabilizer;
    protected SystemMap systems;
    protected String name;
    protected int step;

    public LogitechF310 pilot = new LogitechF310(0);
    public LogitechF310 gunner = new LogitechF310(1);

    public Auto(String n){
        this.drive = new Drive(pilot);
        this.blaster = new Blaster(gunner);
        this.pez = new Pez(gunner, pilot);
        this.stabilizer = new Stabilizer();
        this.systems = new SystemMap();
        this.name = n;
        this.step = 0;
    }

    public abstract void execute();
    public abstract void updateSmartDashboard();

    public Drive getDrive(){
        return this.drive;
    }

    public int getStep(){
        return this.step;
    }

    public void nextStep(){
        this.step += 1;
    }

    public Blaster getBlaster(){
        return this.blaster;
    }

    public Pez getPez(){
        return this.pez;
    }

    public Stabilizer getStabilizer(){
        return this.stabilizer;
    }

    public SystemMap getSystemMap(){
        return this.systems;
    }

    public String getName(){
        return this.name;
    }

}