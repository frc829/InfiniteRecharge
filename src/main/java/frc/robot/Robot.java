/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.autos.*;
import frc.util.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Auto m_autoSelected;
  private final SendableChooser<Auto> autoChooser = new SendableChooser<Auto>();


  Drive drive;
  Blaster blaster;
  Pez pez;
  Boost boost;
  LogitechF310 pilot, gunner;
  SystemMap sm;
  ADIS16470_IMU gyro;
  Compressor compressor;

  public void addAutos(){
    autoChooser.addOption("Do Nothing", new DoNothing(drive, blaster, pez, sm, gyro));
    autoChooser.addOption("Move Forward", new MoveForward(drive, blaster, pez, sm, gyro));
    autoChooser.addOption("Right Big Shoot", new RBigShoot(drive, blaster, pez,  sm, gyro));
    autoChooser.setDefaultOption("Shoot", new Shoot(drive, blaster, pez, sm, gyro));
    SmartDashboard.putData("Auto choices", autoChooser);
  }

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    
    Limelight.changeCamera(1,1);
    pilot = new LogitechF310(0);
    gunner = new LogitechF310(1);
    gyro = new ADIS16470_IMU();
    
    drive = new Drive(pilot, gunner, gyro);
    blaster = new Blaster(gunner, pilot);
    pez = new Pez(gunner, pilot);
    boost = new Boost(gunner, pilot);
    sm = new SystemMap();
    compressor = new Compressor();
    compressor.stop();

    addAutos();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = autoChooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    SmartDashboard.putData("Auto Chooser", autoChooser);
    autoChooser.getSelected().execute();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    drive.teleopUpdate();
    blaster.teleopUpdate();
    pez.teleopUpdate();
    boost.teleopUpdate();
    compressor.start();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
