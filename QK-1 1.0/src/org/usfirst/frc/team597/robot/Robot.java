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

	int autonomous = 0;
	int maxAutonomous = 10;
	int autoState = 0;
	Timer autoTimer;
	
	
	
	
	public void robotInit() {
		// elev.setAbsoluteTolerance(50);

	}

	public void autonomousInti() {
		autoTimer = new Timer();
		autoState = 0;
		elev.disable();
		autoTimer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

		if (autoState == 0) {
			talonLeft.set(0);
			talonRight.set(0);
			talonElev.set(0);

			autoState = 1;
		}
		if (autoState == 1 && autoTimer.get() >= .25) {
			talonLeft.set(-1);
			talonRight.set(1);

			autoState = 2;
		}
		if (autoState == 2 && autoTimer.get() >= 3.75) {
			talonLeft.set(0);
			talonRight.set(0);

			autoState = 3;
		}
		/*
		 * if (autoState == 0) { t1.set(0.5); t2.set(-0.5); // moves forward
		 * 
		 * autoState = 1; } if (autoState == 1 && autoTimer.get() >= 0.5) {
		 * t1.set(0); t2.set(-0); // stops moving } if (autonomous == 2) {
		 * 
		 * claw.set(Value.kForward); Timer.delay(.3); // closes claw may be used
		 * on totes and containers
		 * 
		 * // lifts elevator
		 * 
		 * t1.set(1); t2.set(-1); Timer.delay(1); // moves forward to get
		 * mobility bonus
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
		 * t1.set(-1); t2.set(1); Timer.delay(4); // moves forward to get
		 * mobility bonus }
		 */
	}

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
			System.out.println("Gyro angle "+ gyro.getAngle());
			System.out.println("Gyro Rate "+ gyro.getRate());

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
			talonOmni.set(jsRight.getX() / 1.25);
			talonLeft.set(0);
			talonRight.set(0);
			
			// Gyro code
			gyroSetpoint = gyro.getAngle();
			if (gyroSetpoint > gyro.getAngle()){
				talonRight.set(1);
				if (gyroSetpoint == gyro.getAngle()){
					talonRight.set(0);
				}
			}
			else if (gyroSetpoint < gyro.getAngle()){
				talonLeft.set(1);
				if (gyroSetpoint == gyro.getAngle()){
					talonLeft.set(0);
				}
			}
			else {
				talonRight.set(0);
				talonLeft.set(0);
			}
			// Enables full speed omni(H) drive
			if (jsLeft.getRawButton(7)) {
				OD.set(Value.kReverse);
				talonOmni.set(jsRight.getX());
				talonLeft.set(0);
				talonRight.set(0);
				
				// Gyro code
				gyroSetpoint = gyro.getAngle();
				if (gyroSetpoint > gyro.getAngle()){
					talonRight.set(1);
					if (gyroSetpoint == gyro.getAngle()){
						talonRight.set(0);
					}
				}
				else if (gyroSetpoint < gyro.getAngle()){
					talonLeft.set(1);
					if (gyroSetpoint == gyro.getAngle()){
						talonLeft.set(0);
					}
				}
				else {
					talonRight.set(0);
					talonLeft.set(0);
				}
				
			}
		} 
		else {
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
		if (jsManualClaw.getRawButton(12) || jsGamepad.getRawAxis(2) > 0) {
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
