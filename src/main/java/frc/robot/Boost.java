package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import frc.util.LogitechAxis;
import frc.util.LogitechF310;

public class Boost{

    TalonSRX booster;
    //Solenoid raise;
    LogitechF310 gunner;

    long startOfTeleop  = 0;
    Boolean isTeleop = false;
    public Boost(LogitechF310 gunner){
    booster = new TalonSRX(SystemMap.Boost.BOOST);
   // raise = new (SystemMap.Boost.PCM);
    //raise.set(false);
    this.gunner = gunner;
    }

    long endgame = (60+45)*1000;
    public void teleopUpdate(){
        if(!isTeleop){
            isTeleop = true;
            startOfTeleop = System.currentTimeMillis();
        }
        //DO NOT LET THE CLIMB WORK UNTIL START OF ENDGAME
        if(System.currentTimeMillis()-startOfTeleop>=endgame){
            lift();
        }
        
    }

    public void lift(){
        if(gunner.getAxis(LogitechAxis.LY) > 0.1 || gunner.getAxis(LogitechAxis.LY) < -0.1){
            booster.set(ControlMode.PercentOutput, gunner.getAxis(LogitechAxis.LY)*0.75);
        }
    }

}