/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.commands;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3389.robot.utils.Logger;

import edu.wpi.first.wpilibj.command.Command;

/**
 * An example command. You can replace me with your own command.
 */
public class DriveTurn extends Command {
	double kP = 0.5, kI = 0.5, kD = 0; // we wont be using derivative data
	double result_speed = 0, integral = 0, derivative = 0, error = 0, previous_error = 0;
	double target_heading = 0, current = 0, initial = 0, pivot = 0;
	double target_speed = 0;
	double direction = 1.0;
	long timer = 0;

	public DriveTurn(double speed, double heading) {
		// Use requires() here to declare subsystem dependencies
		//requires(Robot.kExampleSubsystem);
		requires(Robot.driveTrain);
		target_speed = speed;
		target_heading = heading;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		timer = System.nanoTime();
		//get initial heading
		initial = DriveTrain.driveGyro.getFilteredYaw();
		//direction calculation variables
		direction = 1.0;
		pivot = initial + 180;
		target_heading = 0;
		if(initial > 180) {
			// flip all of the directional variables when operating in the second half of the circle
			pivot = initial;
			initial = initial - 180;
			direction = -(direction);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// we calculate time between executions to more accurately compute PID derivative 
		double elapsed = (double)(System.nanoTime() - timer) / 1000000000.0;
		timer = System.nanoTime();
		
		current = DriveTrain.driveGyro.getFilteredYaw();
		previous_error = error;
		error = target_heading - current;

		integral += (error * elapsed); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
		derivative = (error - previous_error) / elapsed;
		// the computed offsets are all 'heading values', we divide by the target heading to get a value between 0 .. 1
		result_speed = target_speed * ((kP*error + kI*integral + kD*derivative) / target_heading);

		if((target_heading > initial) && (target_heading <= pivot))
			DriveTrain.driveVelocity((direction * result_speed), -(direction * result_speed));
		else
			DriveTrain.driveVelocity(-(direction * result_speed),(direction * result_speed));

		Robot.robotLogger.log(Logger.INFO, this, "PID turning: target=" + target_heading + ", current=" + current + ", speed=" + result_speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// since we will not perfectly tune the PID
		// we have a safety condition to break from the loop if we are 'close enough'
		// increase the constant if we find we are getting stuck in this loop
		if (Math.abs(current - target_heading) < 0.05)
			return true;
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
