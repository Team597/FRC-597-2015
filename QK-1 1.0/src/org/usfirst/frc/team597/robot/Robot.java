package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
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

	Joystick jsLeft = new Joystick(0);
	Joystick jsRight = new Joystick(1);
	Joystick jsManualClaw = new Joystick(2);
	Joystick jsGamepad = new Joystick(3);
	Talon talonLeft = new Talon(0);
	Talon talonRight = new Talon(1);
	Talon talonOmni = new Talon(2);
	Talon talonElev = new Talon(3);
	Compressor comp = new Compressor();
	DoubleSolenoid brake = new DoubleSolenoid(0, 7);
	DoubleSolenoid claw = new DoubleSolenoid(1, 6);
	DoubleSolenoid OD = new DoubleSolenoid(2, 5);

	// DigitalInput test7 = new DigitalInput(7);
	// DigitalInput test8 = new DigitalInput(8);

	// Encoder tests:
	// L: 1597, 1587, 1586
	// R: 2303, 2389, 2280
	Encoder LT = new Encoder(5, 6);
	Encoder MM = new Encoder(1, 2);
	Encoder RT = new Encoder(3, 4);
	// Measured drift: 4degrees / min
	Gyro gyro = new Gyro(0);
	double gyroSetpoint = 0;
	int turnRight = 0;
	int turnLeft = 0;

	Encoder encoderElev = new Encoder(7, 8);
	PIDController elev = new PIDController(-1 / 100.0, 0, -.01, encoderElev,
			talonElev);
	PIDController Omni = new PIDController(1 / 100, 0, -.01, gyro, talonLeft);
	// PIDController Omni2 = new PIDController(1/100, 0, -.01, gyro,
	// talonRight);
	DigitalInput lBot = new DigitalInput(0);
	DigitalInput lTop = new DigitalInput(9);
	int eS = 1;
	long print = System.currentTimeMillis();

	boolean lastBotState = false;

	// Add this to every setpoint to zero it to the robot
	// This will subtract a bunch if the encoder was zeroed to the top
	// And be zero if it was zeroed to the bottom.
	int ENCODER_OFFSET = 0;
	int DIFFERENCE_TOP_BOTTOM_ENCODER = 3813;

	int PICKUP_TOTE = -50;
	int ONE_TOTE_SCORE = 9;
	int MOVE_CONT = 1200;
	int TWO_TOTE_SCORE = 1900;
	int TOTEFOUR = 3000;
	int TOP = 3800;

	final Value CLAW_CLOSE = Value.kForward;
	final Value CLAW_OPEN = Value.kReverse;

	final Value BRAKE_ON = Value.kReverse;
	final Value BRAKE_OFF = Value.kForward;

	final Value OMNI_ON = Value.kReverse;
	final Value OMNI_OFF = Value.kForward;

	int autonomous = 0;
	int maxAutonomous = 10;
	int autoState = 0;
	Timer autoTimer;

	Command autonomousCommand;
	SendableChooser autoChooser;

	double omniAngle = 0;

	public void robotInit() {
		// elev.setAbsoluteTolerance(50);

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

	public void autonomousInti() {
		autoTimer = new Timer();

		// Fintan's special don't-crash try block
		try {

			Integer automode = (Integer) autoChooser.getSelected();
			autonomous = automode.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}

		autoState = 0;
		elev.disable();
		autoTimer.start();
		claw.set(Value.kReverse);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

		if (autonomous == 0) { // does nothing

		}
		// moves forward
		// and stops
		//
		if (autonomous == 1) {

			if (autoState == 0) { // does nothing
				talonLeft.set(0);
				talonRight.set(0);
				talonElev.set(0);

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= .25) { // moves forward
				talonLeft.set(1);
				talonRight.set(1);

				autoState = 2;
			}

			if (autoState == 2 && autoTimer.get() >= 3.75) { // stops
				talonLeft.set(0);
				talonRight.set(0);

				autoState = 3;
			}

		}
		// moves forward
		// and stops
		//
		if (autonomous == 2) {

			if (autoState == 0) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 3) {
				talonLeft.set(0);
				talonRight.set(0); // stops moving
			}
		}

		// grabs and lifts tote
		// moves forward and stops
		//
		if (autonomous == 3) {

			if (autoState == 0) { // closes claw
				claw.set(CLAW_CLOSE);
				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				talonElev.set(1); // lifts elevator

				autoState = 2;
			}
			if (autoState == 2 && autoTimer.get() >= 1.3) {
				talonElev.set(0);
				brake.set(BRAKE_ON);

				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 3;
			}
			if (autoState == 3 && autoTimer.get() >= 2.3) {
				talonLeft.set(0);
				talonRight.set(0); // stops moving
			}
		}
		if (autonomous == 4) {

			if (autoState == 0) {
				claw.set(CLAW_CLOSE); // opens close

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				elev.setSetpoint(MOVE_CONT); // lifts toe

				autoState = 2;
			}
			if (autoState == 2 && autoTimer.get() >= 1.3) {
				talonLeft.set(-1);
				talonRight.set(-1); // moves back

				autoState = 3;
			}
			if (autoState == 3 && autoTimer.get() >= 2.3) {
				talonOmni.set(-1); // moves left
				autoState = 4;
			}

			if (autoState == 4 && autoTimer.get() >= 4.3) {
				claw.set(CLAW_OPEN);
				autoState = 5;
			}
			if (autoState == 5 && autoTimer.get() >= 4.6) {
				elev.setSetpoint(PICKUP_TOTE); // lowers elevator

				autoState = 6;
			}
			if (autoState == 6 && autoTimer.get() >= 5.6) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 7;
			}

			if (autoState == 7 && autoTimer.get() >= 6.6) {
				claw.set(Value.kReverse); // opens claws

				autoState = 8;
			}
			if (autoState == 8 && autoTimer.get() >= 6.9) {
				elev.setSetpoint(MOVE_CONT); // lifts elevator

				autoState = 9;
			}
			if (autoState == 9 && autoTimer.get() >= 7.9) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 10;
			}

			if (autoState == 10 && autoTimer.get() >= 8.9) {
				talonLeft.set(0);
				talonRight.set(0); // stops moving
			}

		}
		if (autonomous == 5) {

			if (autoState == 0) { // opens claws
				claw.set(CLAW_OPEN);

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				elev.setSetpoint(MOVE_CONT); // lifts elevator

				autoState = 2;
			}
			if (autoState == 2 && autoTimer.get() >= 1.3) {
				talonLeft.set(-1);
				talonRight.set(-1); // moves back

				autoState = 3;
			}
			if (autoState == 3 && autoTimer.get() >= 2.3) {
				talonOmni.set(1); // moves Right

				autoState = 4;
			}
			if (autoState == 4 && autoTimer.get() >= 4.3) {
				claw.set(Value.kForward); // closes claw

				autoState = 5;
			}
			if (autoState == 5 && autoTimer.get() >= 4.6) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 6;
			}
			if (autoState == 6 && autoTimer.get() >= 5.6) {
				claw.set(Value.kReverse); // opens claw

				autoState = 7;
			}

			if (autoState == 7 && autoTimer.get() >= 6.6) {
				elev.setSetpoint(PICKUP_TOTE); // lowers elevator

				autoState = 8;
			}
			if (autoState == 8 && autoTimer.get() >= 6.9) {
				elev.setSetpoint(MOVE_CONT); // lifts tote

				autoState = 9;
			}
			if (autoState == 9 && autoTimer.get() >= 7.9) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 10;
			}
			if (autoState == 10 && autoTimer.get() >= 9.9) {
				talonLeft.set(0);
				talonRight.set(0); // stops moving

			}
		}
		if (autonomous == 6) {

			if (autoState == 0) {
				claw.set(Value.kReverse); // opens claw

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				elev.setSetpoint(MOVE_CONT); // lifts elevator

				autoState = 2;
			}

			if (autoState == 2 && autoTimer.get() >= 1.3) {
				talonLeft.set(-1);
				talonRight.set(-1); // moves back

				autoState = 3;
			}

			if (autoState == 3 && autoTimer.get() >= 2.3) {
				talonOmni.set(-1); // moves left

				autoState = 4;
			}

			if (autoState == 4 && autoTimer.get() >= 4.3) {
				claw.set(Value.kForward); //

				autoState = 5;
			}

			if (autoState == 5 && autoTimer.get() >= 4.6) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 6;
			}

			if (autoState == 6 && autoTimer.get() >= 5.6) {
				claw.set(Value.kReverse);

				autoState = 7;
			}
			if (autoState == 7 && autoTimer.get() >= 5.9) {
				elev.setSetpoint(MOVE_CONT); // lifts elevator

				autoState = 8;
			}
			if (autoState == 8 && autoTimer.get() >= 6.9) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 9;
			}
			if (autoState == 9 && autoTimer.get() >= 7.4) {
				talonOmni.set(-1); // moves left to avoid bump

				autoState = 10;
			}
			if (autoState == 10 && autoTimer.get() >= 8.4) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 11;
			}
			if (autoState == 11 && autoTimer.get() >= 9.4) {
				talonLeft.set(0);
				talonRight.set(0); // stops moving
			}
		}
		if (autonomous == 7) {

			if (autoState == 0) {
				claw.set(Value.kReverse);

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				elev.setSetpoint(MOVE_CONT); // opens elevator

				autoState = 2;
			}

			if (autoState == 2 && autoTimer.get() >= 1.3) {
				talonLeft.set(1);
				talonRight.set(1);

				autoState = 3;
			}

			if (autoState == 3 && autoTimer.get() >= 2.3) {
				talonOmni.set(-1);

				autoState = 4;
			}

			if (autoState == 4 && autoTimer.get() >= 4.3) {
				claw.set(Value.kForward);
				autoState = 5;
			}

			if (autoState == 5 && autoTimer.get() >= 4.6) {

				talonLeft.set(1);
				talonRight.set(1);

				autoState = 6;
			}

			if (autoState == 6 && autoTimer.get() >= 5.6) {
				claw.set(Value.kReverse);

				autoState = 7;
			}
			if (autoState == 7 && autoTimer.get() > 5.9) {
				elev.setSetpoint(MOVE_CONT);

				autoState = 8;
			}

			if (autoState == 8 && autoTimer.get() >= 6.9) {
				talonLeft.set(-1);
				talonRight.set(-1);

				autoState = 9;
			}
			if (autoState == 9 && autoTimer.get() >= 7.9) {
				talonOmni.set(-1);

				autoState = 10;
			}

			if (autoState == 10 && autoTimer.get() >= 9.9) {
				claw.set(Value.kForward);
				autoState = 11;
			}
			if (autoState == 11 && autoTimer.get() >= 10.2) {
				talonLeft.set(1);
				talonRight.set(1);

				autoState = 12;
			}
			if (autoState == 12 && autoTimer.get() >= 11.2) {
				claw.set(Value.kReverse);

				autoState = 13;
			}
			if (autoState == 13 && autoTimer.get() >= 11.5) {
				elev.setSetpoint(MOVE_CONT);

				autoState = 14;
			}
			if (autoState == 14 && autoTimer.get() >= 12.5) {
				talonLeft.set(1);
				talonRight.set(1);

				autoState = 15;
			}
			if (autoState == 15 && autoTimer.get() >= 14) {
				talonLeft.set(0);
				talonRight.set(0);
			}
		}
	}

	/*
	 * if (autoState == 0) { t1.set(0.5); t2.set(-0.5); // moves forward
	 * 
	 * autoState = 1; } if (autoState == 1 && autoTimer.get() >= 0.5) {
	 * t1.set(0); t2.set(-0); // stops moving } if (autonomous == 2) {
	 * 
	 * claw.set(Value.kForward); Timer.delay(.3); // closes claw may be used on
	 * totes and containers
	 * 
	 * // lifts elevator
	 * 
	 * t1.set(1); t2.set(-1); Timer.delay(1); // moves forward to get mobility
	 * bonus
	 * 
	 * t1.set(0); t2.set(0); Timer.delay(.1); } if (autonomous == 3) {
	 * 
	 * // MOTORS ARE REVERSED PAST THIS POINT // v
	 * 
	 * claw.set(Value.kReverse); Timer.delay(.3); // closes claw on the tote
	 * 
	 * t1.set(-1); t2.set(1); Timer.delay(1); // moves back
	 * 
	 * elev.enable(); elev.setSetpoint(TOTETHREE + ENCODER_OFFSET);
	 * 
	 * oT.set(-1); Timer.delay(2); // moves left
	 * 
	 * claw.set(Value.kForward); Timer.delay(.3); // opens claw
	 * 
	 * t1.set(-1); t2.set(1); Timer.delay(1); // moves forward into tote
	 * 
	 * elev.enable(); elev.setSetpoint(TOTETWO + ENCODER_OFFSET);
	 * 
	 * claw.set(Value.kReverse); Timer.delay(.3); // closes claw on tote
	 * 
	 * elev.enable(); elev.setSetpoint(TOTETHREE + ENCODER_OFFSET);
	 * 
	 * t1.set(-1); t2.set(1); Timer.delay(4); // moves forward to get mobility
	 * bonus }
	 */

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {

		SmartDashboard.putNumber("Elevator Encoder", encoderElev.get());

		if (System.currentTimeMillis() >= print) {

			System.out.println("elevator: " + encoderElev.get());
			/*
			 * System.out.println("test 7: "+test7.get());
			 * System.out.println("test 8: "+test8.get());
			 */
			System.out.println("Bottm limit switch hit: " + lBot.get());
			System.out.println("Top limit switch hit: " + lTop.get());
			System.out.println("Left encoder " + LT.get());
			System.out.println("Right encoder " + RT.get());
			System.out.println("omni encoder" + MM.get());
			System.out.println("Gyro angle " + gyro.getAngle());
			System.out.println("Gyro Rate " + gyro.getRate());

			print = System.currentTimeMillis() + 500;
		}

		if (lBot.get() != lastBotState) {
			int error = encoderElev.get() + DIFFERENCE_TOP_BOTTOM_ENCODER;

			encoderElev.reset();
			ENCODER_OFFSET = 0;
			// elev.disable();
			System.out
					.println("Botswitch has been RESET :) Error top vs bottom: "
							+ error);
		}
		lastBotState = lBot.get();

		/*
		 * if (lTop.get() != lastTopState) { en1.reset(); elev.disable();
		 * ENCODER_OFFSET = -DIFFERENCE_TOP_BOTTOM_ENCODER;
		 * System.out.println("Topswitch has been RESET :)"); } lastTopState =
		 * lTop.get();
		 */

		// Opens claw
		if (jsGamepad.getRawButton(8) || jsManualClaw.getRawButton(1)) {
			claw.set(Value.kForward);
		}
		// Closes claw
		if (jsGamepad.getRawButton(6) || jsManualClaw.getRawButton(4)) {
			claw.set(Value.kReverse);
		}
		// Manual control for elevator
		if (jsManualClaw.getRawButton(2)) {
			elev.disable();
			talonElev.set(jsManualClaw.getY());
			brake.set(Value.kForward);
		}
		// Enables omni(H) drive at half speed
		if (jsRight.getRawButton(7)) {
			OD.set(Value.kReverse);
			talonOmni.set(jsRight.getX() / 2);
			talonLeft.set(0);
			talonRight.set(0);

			// Gyro code
			/*
			 * gyroSetpoint = gyro.getAngle(); if (gyroSetpoint >
			 * gyro.getAngle()){ talonRight.set(1); if (gyroSetpoint ==
			 * gyro.getAngle()){ talonRight.set(0); } } else if (gyroSetpoint <
			 * gyro.getAngle()){ talonLeft.set(1); if (gyroSetpoint ==
			 * gyro.getAngle()){ talonLeft.set(0); } } else { talonRight.set(0);
			 * talonLeft.set(0); }
			 */
			// Enables full speed omni(H) drive
			if (jsLeft.getRawButton(7)) {
				omniAngle = gyro.getAngle();
				OD.set(Value.kReverse);
				omniAngle = gyro.getAngle();
				talonOmni.set(jsRight.getX());
				talonLeft.set(0);
				talonRight.set(0);

				Omni.setSetpoint(omniAngle);

				// Gyro code
				/*
				 * gyroSetpoint = gyro.getAngle(); if (gyroSetpoint >
				 * gyro.getAngle()){ talonRight.set(1); if (gyroSetpoint ==
				 * gyro.getAngle()){ talonRight.set(0); } } else if
				 * (gyroSetpoint < gyro.getAngle()){ talonLeft.set(1); if
				 * (gyroSetpoint == gyro.getAngle()){ talonLeft.set(0); } } else
				 * { talonRight.set(0); talonLeft.set(0); }
				 */
			}
		} else {
			// Standard tank drive
			OD.set(Value.kForward);
			talonOmni.set(0);
			talonLeft.set(jsLeft.getY());
			talonRight.set(jsRight.getY());
		}

		// Second score pickup position
		if (jsManualClaw.getRawButton(6) || jsGamepad.getRawButton(1)) {
			// Disable brakes
			brake.set(Value.kForward);
			// Move elevator
			elev.enable();
			elev.setSetpoint(ONE_TOTE_SCORE + ENCODER_OFFSET);
			// Enable brakes
			brake.set(Value.kReverse);
		}
		// Third pickup position
		if (jsManualClaw.getRawButton(7) || jsGamepad.getRawButton(2)) {
			// Disable brakes
			brake.set(Value.kForward);
			// Move elevator
			elev.enable();
			elev.setSetpoint(MOVE_CONT + ENCODER_OFFSET);
			// Enable brakes
			brake.set(Value.kReverse);
		}
		// Fourth pickup position
		if (jsManualClaw.getRawButton(10) || jsGamepad.getRawButton(3)) {
			// Disable brakes
			brake.set(Value.kForward);
			// Move elevator
			elev.enable();
			elev.setSetpoint(TWO_TOTE_SCORE + ENCODER_OFFSET);
			// Enable brakes
			brake.set(Value.kReverse);
		}
		// Fifth pickup position
		if (jsManualClaw.getRawButton(11) || jsGamepad.getRawButton(4)) {
			// Disable brakes
			brake.set(Value.kForward);
			// Move elevator
			elev.enable();
			elev.setSetpoint(TOTEFOUR + ENCODER_OFFSET);
			// Enable brakes
			brake.set(Value.kReverse);
		}
		// First pickup position
		if (jsManualClaw.getRawButton(5) || jsGamepad.getRawAxis(2) > 0) {
			// Disable brakes
			brake.set(Value.kForward);
			// Move elevator
			elev.enable();
			elev.setSetpoint(PICKUP_TOTE + ENCODER_OFFSET);
			// Enable brakes
			brake.set(Value.kReverse);
		}

		/*
		 * 
		 * Sixth pickup position here
		 */

		// Manual enable brakes
		if (jsManualClaw.getRawButton(3)) {
			brake.set(Value.kReverse);
			elev.disable();
			talonElev.set(0);
		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
