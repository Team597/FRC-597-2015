package org.usfirst.frc.team597.robot;

import Autonomous.Autonomous;
import Bane.Claw;
import Bane.Drive;
import Elevator.Elevator;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	Joystick joystickLeft;
	Joystick joystickRight;
	Talon talonLeft;
	Talon talonRight;
	Talon omniTalon;
	DoubleSolenoid omniDirection;
	DoubleSolenoid solenoidClawLeft;
	DoubleSolenoid solenoidClawRight;
	Compressor compressorAir;
	
	Elevator elevator;
	Autonomous autonomous;
	Drive robotDriveOmni;
	Claw robotClaw;
	

	public Robot() {
		joystickLeft = new Joystick(0);
		joystickRight = new Joystick(1);
		talonLeft = new Talon(0);
		talonRight = new Talon(1);
		omniTalon = new Talon(2);
		omniDirection = new DoubleSolenoid(5, 6);
		solenoidClawLeft = new DoubleSolenoid(1, 2);
		solenoidClawRight = new DoubleSolenoid(3, 4);
		compressorAir = new Compressor();
		
		autonomous = new Autonomous(joystickLeft, joystickLeft, talonLeft, talonRight, omniTalon, omniDirection, solenoidClawLeft, solenoidClawRight);
		elevator = new Elevator();
		robotDriveOmni = new Drive(joystickLeft, joystickRight, talonLeft, talonRight, omniTalon, omniDirection);
		robotClaw = new Claw(joystickLeft, solenoidClawLeft, solenoidClawRight);
	}

	public void robotInit() {
		robotDriveOmni.robotInit();
		elevator.robotInit();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		robotDriveOmni.teleopPeriodic();
		elevator.teleopPeriodic();
		robotClaw.teleopPeriodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
