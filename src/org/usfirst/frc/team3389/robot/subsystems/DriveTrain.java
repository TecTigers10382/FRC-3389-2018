package org.usfirst.frc.team3389.robot.subsystems;

import org.usfirst.frc.team3389.robot.commands.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

@SuppressWarnings("deprecation")
public class DriveTrain extends Subsystem {
	
	
	private DifferentialDrive chassis = new DifferentialDrive(null, null);

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new Drive());
	}
	
	public void TeleopDrive(Joystick leftStick,Joystick rightStick){
		chassis.tankDrive(leftStick,rightStick);
	}
	
	public void Stop(){
		chassis.tankDrive(0,0);
	}
	
}
