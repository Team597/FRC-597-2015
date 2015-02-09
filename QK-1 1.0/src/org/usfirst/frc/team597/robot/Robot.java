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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	Command autonomousCommand;
	SendableChooser autoChooser;
	
	long lastPrint = System.currentTimeMillis();

	public Robot() {
		joystickLeft = new Joystick(0);
		joystickRight = new Joystick(1);
		talonLeft = new Talon(0);
		talonRight = new Talon(1);
		omniTalon = new Talon(4);
		omniDirection = new DoubleSolenoid(4, 5);
		solenoidClawLeft = new DoubleSolenoid(0, 1);
		solenoidClawRight = new DoubleSolenoid(2, 7);
		compressorAir = new Compressor();
		
		autonomous = new Autonomous(joystickLeft, joystickLeft, talonLeft, talonRight, omniTalon, omniDirection, solenoidClawLeft, solenoidClawRight);
		elevator = new Elevator();
		robotDriveOmni = new Drive(joystickLeft, joystickRight, talonLeft, talonRight, omniTalon, omniDirection);
		robotClaw = new Claw(joystickLeft, solenoidClawLeft);
	}

	public void robotInit() {
		robotDriveOmni.robotInit();
		elevator.robotInit();
		
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Default program", new Integer(0));
		autoChooser.addDefault("Autonomous number 1", new Integer(1));
		autoChooser.addDefault("Autonomous number 2", new Integer(2));
		autoChooser.addDefault("Autonomous number 3", new Integer(3));
		autoChooser.addDefault("Autonomous number 4", new Integer(4));
		autoChooser.addDefault("Autonomous number 5", new Integer(5));
		autoChooser.addDefault("Autonomous number 6", new Integer(6));
		autoChooser.addDefault("Autonomous number 7", new Integer(7));
		autoChooser.addDefault("Autonomous number 8", new Integer(8));
		
		SmartDashboard.putData("Autonomous mode chooser", autoChooser);
		
	}
	
	public void autonomousInit(){
		// Fintan's special don't-crash try block
		try {
			
			Integer automode = (Integer) autoChooser.getSelected();
			autonomous.setAutonomous(automode.intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		
	}
	
	public void disabledPeriodic(){
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		robotDriveOmni.teleopPeriodic();
		elevator.teleopPeriodic();
		robotClaw.teleopPeriodic();
	
		elevator.elevatorPrint();
		autonomous.autoPrint();
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
