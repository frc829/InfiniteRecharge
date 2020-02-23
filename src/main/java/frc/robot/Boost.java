package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import frc.util.LogitechAxis;
import frc.util.LogitechButton;
import frc.util.LogitechF310;

public class Boost{

    TalonSRX booster;
    Solenoid raise, otherRaise;
    LogitechF310 gunner;

    long delay = 500, start = 0;
    boolean isTeleop = false;
    boolean isUp = false;
    
        public Boost(LogitechF310 gunner){
            this.booster = new TalonSRX(SystemMap.Boost.BOOST);
            this.raise = new Solenoid(SystemMap.Boost.CLIMB1);
            raise.set(false);
            this.otherRaise = new Solenoid(SystemMap.Boost.CLIMB2);
            otherRaise.set(false);
            this.gunner = gunner;
            this.start = System.currentTimeMillis();
            booster.setNeutralMode(NeutralMode.Brake);
        }

    // long endgame = (60+45)*1000;    
    public void teleopUpdate(){
        // if(!isTeleop){
        //     isTeleop = true;
        //     startOfTeleop = System.currentTimeMillis();
        // }
        lift();
    }

    public void lift(){
        if(gunner.getAxis(LogitechAxis.LY) > 0.1 || gunner.getAxis(LogitechAxis.LY) < -0.1){
            booster.set(ControlMode.PercentOutput, gunner.getAxis(LogitechAxis.LY)*0.4);
        }
        else{
            booster.set(ControlMode.PercentOutput, -.03);
        }
        if(System.currentTimeMillis() - start >= delay){
            if(gunner.getRawButton(LogitechButton.START) && !isUp){
                raise.set(true);
                otherRaise.set(true);
                isUp = !isUp;
                start = System.currentTimeMillis();
            }
            else if(gunner.getRawButton(LogitechButton.START) && isUp){
                raise.set(false);
                otherRaise.set(false);
                isUp = !isUp;
                start = System.currentTimeMillis();
            }
        }
    }
}