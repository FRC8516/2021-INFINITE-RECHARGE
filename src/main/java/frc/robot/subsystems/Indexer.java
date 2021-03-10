// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Indexer extends SubsystemBase {
  private final WPI_VictorSPX motIndexer = new WPI_VictorSPX(Constants.ActuatorConstants.kIndexerVertical);
  // SmartDashboard
  final String indexerSpeed = "Index Speed";
  final String indexerSpeedSlow = "Index Speed Slow";
  final String shootSpeed = "Shoot Speed";
  final String indexTime = "Index Time";
  final double timeDelay = 3.0;
  final double SpeedIndexer = 0.3;
  final double SpeedShoot = 0.3;
  final double SpeedIndexerSlow = 0.2;
  private double setSpeed;

  /** Creates a new Indexer. */
  public Indexer() {
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // -------------------------------------------------------------
  // Moves all balls to the flywheel when shooting
  // -------------------------------------------------------------
  public void ShotBalls() {
    final double backup = SpeedShoot;
    // Get current speed setpoint from the preference tables
    setSpeed = getPreferencesDouble(shootSpeed, backup);
    // Call the motor to move
    this.RunMotors(setSpeed);
  }
    
  // -------------------------------------------------------------
  // Runs the indexing motors until ball at location
  // -------------------------------------------------------------
  private void RunMotors(double speed) {
    motIndexer.set(ControlMode.PercentOutput, speed);
  }

  // -------------------------------------------------------------
  // Stop Motion
  // -------------------------------------------------------------
  public void StopMotion() {
    motIndexer.set(ControlMode.PercentOutput,  0.0);
  }

  // Run
  public void AutoShoot() {
    //Stop indexer motion
    double backup = SpeedShoot;
    setSpeed = getPreferencesDouble(shootSpeed, backup);
    setSpeed = setSpeed * 1;
    motIndexer.set(ControlMode.PercentOutput, setSpeed);
  }

  public void ManualRunUp() {
    double backup = SpeedShoot;
    setSpeed = getPreferencesDouble(indexerSpeed, backup);
    setSpeed = setSpeed * 1;
    motIndexer.set(ControlMode.PercentOutput, setSpeed); 
  }

  public void ManualRunDown() {
    double backup = SpeedIndexer;
    setSpeed = getPreferencesDouble(indexerSpeedSlow, backup);
    setSpeed = setSpeed * -1;
    motIndexer.set(ControlMode.PercentOutput, setSpeed);
  }

  // -------------------------------------------------------------
  // Preferences from network tables---Controls on the Dashboard
  // -------------------------------------------------------------
  private static double getPreferencesDouble(final String key, final double backup) {
    final Preferences preferences = Preferences.getInstance();
    if(!preferences.containsKey(key)) {
      preferences.putDouble(key, backup);
    }
    return preferences.getDouble(key, backup);
  }

}
