
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.EncoderConstants;

public class DriveTrain extends SubsystemBase {
  
  // Drive Motors
  private final WPI_TalonFX m_frontLeftMotor = new WPI_TalonFX(DriveConstants.kFrontLeftMotor); 
  private final WPI_TalonFX m_backLeftMotor= new WPI_TalonFX(DriveConstants.kBackLeftMotor);
  private final WPI_TalonFX m_frontRightMotor = new WPI_TalonFX(DriveConstants.kFrontRightMotor);
  private final WPI_TalonFX m_backRightMotor = new WPI_TalonFX(DriveConstants.kBackRightMotor);
   
  // Robot Drive
  //Robots Drive Type: Differential Drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_frontLeftMotor, m_frontRightMotor);

  // Default threshold value from XboxController
  double joyThreshold = 0.07;

  // Drive Train
  public DriveTrain() {
    m_drive.setSafetyEnabled(false);
    //Set Masters and Followers
    m_backLeftMotor.follow(m_frontLeftMotor);
    m_backRightMotor.follow(m_frontRightMotor);

    m_frontLeftMotor.configFactoryDefault();
    m_backLeftMotor.configFactoryDefault();
    m_frontRightMotor.configFactoryDefault();
    m_backLeftMotor.configFactoryDefault();

     // Drive Train Encoder Setup
   m_frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, EncoderConstants.kPIDLoopIdx, EncoderConstants.kTimeoutMs);
   m_frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, EncoderConstants.kPIDLoopIdx, EncoderConstants.kTimeoutMs);
  }

  // Periodic
  @Override
  public void periodic() {
    // Pushing Drive Encoder Data to the SmartDashboard
   SmartDashboard.putNumber(" FrontLeftSensorPosition", m_frontLeftMotor.getSelectedSensorPosition(Constants.EncoderConstants.kPIDLoopIdx));
   SmartDashboard.putNumber(" BackLeftSensorPosition", m_backLeftMotor.getSelectedSensorPosition(Constants.EncoderConstants.kPIDLoopIdx));
  }
  
  /**
   *Arcade Drive from Drive via XboxController input   
   * 
   */
  public void drive(double xSpeed, double ySpeed, double rot ) {
    if(Math.abs(xSpeed) > joyThreshold || Math.abs(ySpeed) > joyThreshold) {
      m_drive.arcadeDrive(xSpeed*-1.0, ySpeed*1.0);
    }
    else {
      m_drive.arcadeDrive(0.0, 0.0);
    }
   
  }

  public void autoDriveBackwards(){
    m_frontLeftMotor.set(ControlMode.PercentOutput, 1.0);
    m_frontRightMotor.set(ControlMode.PercentOutput, 1.0);
  }

  public void autoDriveForward(){
    m_frontLeftMotor.set(ControlMode.PercentOutput, -1.0);
    m_frontRightMotor.set(ControlMode.PercentOutput, -1.0);
  }

  public void stopMotion() {
    m_frontLeftMotor.set(ControlMode.PercentOutput, 0.0);
    m_frontRightMotor.set(ControlMode.PercentOutput, 0.0);
    
  }

  }

