package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  private CANSparkMax motor;
  private SparkPIDController pidctrl;
  private RelativeEncoder encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  @Override
  public void robotInit() {

    motor = new CANSparkMax(8,MotorType.kBrushless);
    motor.restoreFactoryDefaults();

    pidctrl = motor.getPIDController();
    encoder = motor.getEncoder();

    kP = 0.1;
    kI = 1e-4;
    kD = 1;
    kIz = 0;
    kFF = 0;
    kMaxOutput = 1;
    kMinOutput = -1;

    pidctrl.setP(kP);
    pidctrl.setI(kI);
    pidctrl.setD(kD);
    pidctrl.setIZone(kIz);
    pidctrl.setFF(kFF);
    pidctrl.setOutputRange(kMinOutput, kMaxOutput);

    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kP);
    SmartDashboard.putNumber("D Gain", kP);
    SmartDashboard.putNumber("I Zone", kP);
    SmartDashboard.putNumber("Feed Foward", kFF);
    SmartDashboard.putNumber("Max Out", kMaxOutput);
    SmartDashboard.putNumber("Min Out", kMinOutput);
    SmartDashboard.putNumber("Set Rotation", 0);

  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Foward", 0);
    double max = SmartDashboard.getNumber("Max Out", 0);
    double min = SmartDashboard.getNumber("Min Out", 0);
    double rotation = SmartDashboard.getNumber("Set Rotation", 0);

    if((p != kP)) {pidctrl.setP(p);}
    if((i != kI)) {pidctrl.setI(i);}
    if((d != kD)) {pidctrl.setD(d);}
    if((iz != kIz)) {pidctrl.setIZone(iz);}
    if((ff != kFF)) {pidctrl.setFF(ff);}
    if((max != kMaxOutput) || (min != kMinOutput)){
        pidctrl.setOutputRange(min, max);
        kMaxOutput = max; kMinOutput = min;
      }
    
    pidctrl.setReference(rotation, CANSparkMax.ControlType.kPosition);

    SmartDashboard.putNumber("SetPoint", rotation);
    SmartDashboard.putNumber("ProcessVariable", encoder.getPosition());

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
