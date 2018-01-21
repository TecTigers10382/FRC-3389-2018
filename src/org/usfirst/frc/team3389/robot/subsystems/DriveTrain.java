package org.usfirst.frc.team3389.robot.subsystems;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.Drive;
import org.usfirst.frc.team3389.robot.utils.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	TalonSRX leftFront;
	TalonSRX leftBack;
	TalonSRX rightFront;
	TalonSRX rightBack;	
	
	public DriveTrain() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		
		leftFront = new TalonSRX(RobotMap.DRIVE_LEFTFRONT);
		leftBack = new TalonSRX(RobotMap.DRIVE_LEFTBACK);
		rightFront = new TalonSRX(RobotMap.DRIVE_RIGHTFRONT);
		rightBack = new TalonSRX(RobotMap.DRIVE_RIGHTBACK);	
		
		leftFront.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		leftBack.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		rightFront.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		rightBack.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
		//Outputs Talon debug info
		Debug();
		
	}
	@Override
	protected void initDefaultCommand() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		// TODO Auto-generated method stub
		setDefaultCommand(new Drive());
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}
	
	private void rawDrive(double leftPower, double rightPower){
		Robot.robotLogger.log(Logger.DEBUG, this, "enter:" + leftPower+", "+rightPower);
			
		leftFront.set(ControlMode.PercentOutput, leftPower);
		leftBack.set(ControlMode.PercentOutput, leftPower);
		rightFront.set(ControlMode.PercentOutput, rightPower);
		rightBack.set(ControlMode.PercentOutput, rightPower);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit"+leftFront.getMotorOutputPercent());
	}
	
	public void tankDrive(double left,double right){
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		
		rawDrive(left,right);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}
	
	public void Stop(){
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		
		rawDrive(0,0);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}
	private void Debug() {
		Robot.robotLogger.log(Logger.DEBUG, this, "TALON DEBUG\n==================================");
		Robot.robotLogger.log(Logger.DEBUG, this, "Output Current");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : "+leftFront.getOutputCurrent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: "+rightFront.getOutputCurrent());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : "+leftBack.getOutputCurrent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : "+rightBack.getOutputCurrent());
		
		Robot.robotLogger.log(Logger.DEBUG, this, "Output Voltage");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : "+leftFront.getMotorOutputVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: "+rightFront.getMotorOutputVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : "+leftBack.getMotorOutputVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : "+rightBack.getMotorOutputVoltage());
		
		Robot.robotLogger.log(Logger.DEBUG, this, "Bus Voltage");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : "+leftFront.getBusVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: "+rightFront.getBusVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : "+leftBack.getBusVoltage());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : "+rightBack.getBusVoltage());
		
		Robot.robotLogger.log(Logger.DEBUG, this, "Output Percent");
		Robot.robotLogger.log(Logger.DEBUG, this, "leftFront : "+leftFront.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightFront: "+rightFront.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "leftBack  : "+leftBack.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "rightBack : "+rightBack.getMotorOutputPercent());
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}
	
}