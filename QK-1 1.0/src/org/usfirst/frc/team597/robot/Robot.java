package org.usfirst.frc.team597.robot;

import java.sql.Time;

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
import edu.wpi.first.wpilibj.CameraServer;

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
	DoubleSolenoid omniPiston = new DoubleSolenoid(2, 5);
	CameraServer server;
	
	Claw claw = new Claw(jsGamepad);
	// DigitalInput test7 = new DigitalInput(7);
	// DigitalInput test8 = new DigitalInput(8);

	// Encoder tests:
	// L: 1597, 1587, 1586
	// R: 2303, 2389, 2280
	Encoder leftTalonEncoder = new Encoder(5, 6);
	Encoder omniTalonEncoder = new Encoder(1, 2);
	Encoder rightTalonEncoder = new Encoder(3, 4);
	// Measured drift: 4degrees / min
	Gyro gyro = new Gyro(0);
	double gyroSetpoint = 0;
	int turnRight = 0;
	int turnLeft = 0;

	OmniDrive omni_Drive = new OmniDrive(talonLeft, talonRight);

	Encoder elevEncoder = new Encoder(7, 8);
	PIDController elev = new PIDController(-1 / 100.0, 0, -.01, elevEncoder,
			talonElev);
	PIDController Omni = new PIDController(1 / 90.0, 0, 0, gyro, omni_Drive);
	// PIDController Omni2 = new PIDController(1/100, 0, -.01, gyro,
	// talonRight);
	DigitalInput botLimitSwitch = new DigitalInput(0);
	DigitalInput topLimitSwitch = new DigitalInput(9);
	int eS = 1;
	long print = System.currentTimeMillis();

	int elevState = 1;

	boolean lastBotState = false;

	boolean lastGyroState = false;

	// Add this to every setpoint to zero it to the robot
	// This will subtract a bunch if the encoder was zeroed to the top
	// And be zero if it was zeroed to the bottom.
	int ENCODER_OFFSET = 0;
	int DIFFERENCE_TOP_BOTTOM_ENCODER = 3813;

	int PICKUP_TOTE = -50;
	int ONE_TOTE = 9;
	int TWO_TOTE = 1200;
	int THREE_TOTE = 1900;
	int FOUR_TOTE = 3000;
	int TOP_TOTE = 3800;


	final Value BRAKE_ON = Value.kReverse;
	final Value BRAKE_OFF = Value.kForward;

	final Value OMNI_ON = Value.kReverse;
	final Value OMNI_OFF = Value.kForward;



	int autonomous = 0;
	int maxAutonomous = 10;
	int autoState = 0;
	Timer autoTimer;

	int brakeState = 0;
	int brakeOffSet = 50;

	int omniState = 0;

	double gyroAngle = 0;

	Command autonomousCommand;
	SendableChooser autoChooser;

	double omniAngle = 0;

	public Robot() {
		server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam0");
	}

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
		autoChooser.addDefault("Autonomous number 100", new Integer(100));
		autoChooser.addDefault("Autonomous number 101", new Integer(101));

		SmartDashboard.putData("Autonomous mode chooser", autoChooser);

	}

	public void autonomousInti() {
		/*
		 * autoTimer = new Timer(); autoState = 0; elev.disable(); gyro.reset();
		 * 
		 * // Fintan's special don't-crash try block try { Integer automode =
		 * (Integer) autoChooser.getSelected(); autonomous =
		 * automode.intValue(); } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * autoTimer.start(); claw.set(CLAW_OPEN);
		 */
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		
		talonLeft.set(.75);
		talonRight.set(.75);
		Timer.delay(1);
		talonLeft.set(0);
		talonRight.set(0);
		Timer.delay(.01);
		/*
		 * 
		 * if (autonomous == 1) { // moves from right to left into zone (not
		 * over // bump) for (int A = 0; A < 2; A++) { if (autoState == 0) {
		 * talonLeft.set(0); // Zeroes values talonRight.set(0);
		 * talonElev.set(0);
		 * 
		 * autoState = 1; } if (autoState == 1 && autoTimer.get() >= .10) {
		 * claw.set(CLAW_CLOSE); // Closes claw
		 * 
		 * autoState = 2; } if (autoState == 2 && autoTimer.get() >= .30) {
		 * talonElev.set(1); // Picks up tote for
		 * 
		 * autoState = 3; } if (autoState == 3 && autoTimer.get() >= .40) {
		 * talonElev.set(0); // Stops elev moving brake.set(BRAKE_ON); //
		 * Enables brakes
		 * 
		 * autoState = 4; } if (autoState == 4 && autoTimer.get() >= .70) {
		 * talonLeft.set(0); talonRight.set(0); talonOmni.set(-0.5); // Strafe
		 * left
		 * 
		 * autoState = 5; } if (autoState == 5 && autoTimer.get() >= 3) {
		 * talonOmni.set(0); brake.set(BRAKE_OFF); // Stops brake
		 * claw.set(CLAW_OPEN); // Opens claw
		 * 
		 * autoState = 6; } if (autoState == 6 && autoTimer.get() >= 5) {
		 * talonElev.set(-1); // Lowers claw
		 * 
		 * autoState = 7; } if (autoState == 7 && autoTimer.get() >= 7) {
		 * talonElev.set(0); // Stops elevator
		 * 
		 * autoState = 0; } }
		 * 
		 * talonLeft.set(1); talonRight.set(1); Timer.delay(3);
		 * talonLeft.set(0); talonRight.set(0); }
		 * 
		 * // moves forward // and stops if (autonomous == 2) { // forward mid
		 * 
		 * if (autoState == 0) { // does nothing talonLeft.set(0);
		 * talonRight.set(0); talonElev.set(0);
		 * 
		 * autoState = 1; }
		 * 
		 * if (autoState == 1 && autoTimer.get() >= .25) { // moves forward
		 * talonLeft.set(1); talonRight.set(1);
		 * 
		 * autoState = 2; }
		 * 
		 * if (autoState == 2 && autoTimer.get() >= 3.75) { // stops
		 * talonLeft.set(0); talonRight.set(0);
		 * 
		 * autoState = 3; }
		 * 
		 * } if (autonomous == 3) {// forward long
		 * 
		 * if (autoState == 0) { // does nothing talonLeft.set(0);
		 * talonRight.set(0); talonElev.set(0);
		 * 
		 * autoState = 1; }
		 * 
		 * if (autoState == 1 && autoTimer.get() >= .25) { // moves forward
		 * talonLeft.set(1); talonRight.set(1);
		 * 
		 * autoState = 2; }
		 * 
		 * if (autoState == 2 && autoTimer.get() >= 5.75) { // stops
		 * talonLeft.set(0); talonRight.set(0);
		 * 
		 * autoState = 3; }
		 * 
		 * } if (autonomous == 4) { // forward short
		 * 
		 * if (autoState == 101) { // does nothing talonLeft.set(0);
		 * talonRight.set(0); talonElev.set(0);
		 * 
		 * autoState = 1; }
		 * 
		 * if (autoState == 1 && autoTimer.get() >= .25) { // moves forward
		 * talonLeft.set(1); talonRight.set(1);
		 * 
		 * autoState = 2; }
		 * 
		 * if (autoState == 2 && autoTimer.get() >= 1.75) { // stops
		 * talonLeft.set(0); talonRight.set(0);
		 * 
		 * autoState = 3; }
		 * 
		 * } // moves forward // and stops // if (autonomous == 5) {
		 * 
		 * if (autoState == 0) { talonLeft.set(1); talonRight.set(1); // moves
		 * forward
		 * 
		 * autoState = 1; }
		 * 
		 * if (autoState == 1 && autoTimer.get() >= 3) { talonLeft.set(0);
		 * talonRight.set(0); // stops moving } }
		 * 
		 * // grabs and lifts tote // moves forward and stops // if (autonomous
		 * == 6) {
		 * 
		 * if (autoState == 0) { // closes claw claw.set(CLAW_CLOSE); autoState
		 * = 1; }
		 * 
		 * if (autoState == 1 && autoTimer.get() >= 0.3) { talonElev.set(1); //
		 * lifts elevator
		 * 
		 * autoState = 2; } if (autoState == 2 && autoTimer.get() >= 1.3) {
		 * talonElev.set(0); brake.set(BRAKE_ON);
		 * 
		 * talonLeft.set(1); talonRight.set(1); // moves forward
		 * 
		 * autoState = 3; } if (autoState == 3 && autoTimer.get() >= 2.3) {
		 * talonLeft.set(0); talonRight.set(0); // stops moving } } if
		 * (autonomous == 7) {
		 * 
		 * if (autoState == 0) { claw.set(CLAW_CLOSE); // opens close
		 * 
		 * autoState = 1; }
		 * 
		 * if (autoState == 1 && autoTimer.get() >= 0.3) {
		 * elev.setSetpoint(TWO_TOTE); // lifts toe
		 * 
		 * autoState = 2; } if (autoState == 2 && autoTimer.get() >= 1.3) {
		 * talonLeft.set(-1); talonRight.set(-1); // moves back
		 * 
		 * autoState = 3; } if (autoState == 3 && autoTimer.get() >= 2.3) {
		 * talonOmni.set(-1); // moves left autoState = 4; }
		 * 
		 * if (autoState == 4 && autoTimer.get() >= 4.3) { claw.set(CLAW_OPEN);
		 * autoState = 5; } if (autoState == 5 && autoTimer.get() >= 4.6) {
		 * elev.setSetpoint(PICKUP_TOTE); // lowers elevator
		 * 
		 * autoState = 6; } if (autoState == 6 && autoTimer.get() >= 5.6) {
		 * talonLeft.set(1); talonRight.set(1); // moves forward
		 * 
		 * autoState = 7; }
		 * 
		 * if (autoState == 7 && autoTimer.get() >= 6.6) {
		 * claw.set(Value.kReverse); // opens claws
		 * 
		 * autoState = 8; } if (autoState == 8 && autoTimer.get() >= 6.9) {
		 * elev.setSetpoint(TWO_TOTE); // lifts elevator
		 * 
		 * autoState = 9; } if (autoState == 9 && autoTimer.get() >= 7.9) {
		 * talonLeft.set(1); talonRight.set(1); // moves forward
		 * 
		 * autoState = 10; }
		 * 
		 * if (autoState == 10 && autoTimer.get() >= 8.9) { talonLeft.set(0);
		 * talonRight.set(0); // stops moving }
		 * 
		 * } if (autonomous == 8) {
		 * 
		 * if (autoState == 0) { // opens claws claw.set(CLAW_CLOSE);
		 * 
		 * autoState = 1; }
		 * 
		 * if (autoState == 1 && autoTimer.get() >= 0.3) {
		 * elev.setSetpoint(TWO_TOTE); // lifts elevator
		 * 
		 * autoState = 2; } if (autoState == 2 && autoTimer.get() >= 1.3) {
		 * talonLeft.set(-1); talonRight.set(-1); // moves back
		 * 
		 * autoState = 3; } if (autoState == 3 && autoTimer.get() >= 2.3) {
		 * talonOmni.set(1); // moves Right
		 * 
		 * autoState = 4; } if (autoState == 4 && autoTimer.get() >= 4.3) {
		 * claw.set(Value.kForward); // closes claw
		 * 
		 * autoState = 5; } if (autoState == 5 && autoTimer.get() >= 4.6) {
		 * talonLeft.set(1); talonRight.set(1); // moves forward
		 * 
		 * autoState = 6; } if (autoState == 6 && autoTimer.get() >= 5.6) {
		 * claw.set(Value.kReverse); // opens claw
		 * 
		 * autoState = 7; }
		 * 
		 * if (autoState == 7 && autoTimer.get() >= 6.6) {
		 * elev.setSetpoint(PICKUP_TOTE); // lowers elevator
		 * 
		 * autoState = 8; } if (autoState == 8 && autoTimer.get() >= 6.9) {
		 * elev.setSetpoint(TWO_TOTE); // lifts tote
		 * 
		 * autoState = 9; } if (autoState == 9 && autoTimer.get() >= 7.9) {
		 * talonLeft.set(1); talonRight.set(1); // moves forward
		 * 
		 * autoState = 10; } if (autoState == 10 && autoTimer.get() >= 9.9) {
		 * talonLeft.set(0); talonRight.set(0); // stops moving
		 * 
		 * } } if (autonomous == 9) {
		 * 
		 * if (autoState == 0) { claw.set(Value.kReverse); // opens claw
		 * 
		 * autoState = 1; }
		 * 
		 * if (autoState == 1 && autoTimer.get() >= 0.3) {
		 * elev.setSetpoint(TWO_TOTE); // lifts elevator
		 * 
		 * autoState = 2; }
		 * 
		 * if (autoState == 2 && autoTimer.get() >= 1.3) { talonLeft.set(-1);
		 * talonRight.set(-1); // moves back
		 * 
		 * autoState = 3; }
		 * 
		 * if (autoState == 3 && autoTimer.get() >= 2.3) { talonOmni.set(-1); //
		 * moves left
		 * 
		 * autoState = 4; }
		 * 
		 * if (autoState == 4 && autoTimer.get() >= 4.3) {
		 * claw.set(Value.kForward); //
		 * 
		 * autoState = 5; }
		 * 
		 * if (autoState == 5 && autoTimer.get() >= 4.6) { talonLeft.set(1);
		 * talonRight.set(1); // moves forward
		 * 
		 * autoState = 6; }
		 * 
		 * if (autoState == 6 && autoTimer.get() >= 5.6) {
		 * claw.set(Value.kReverse);
		 * 
		 * autoState = 7; } if (autoState == 7 && autoTimer.get() >= 5.9) {
		 * elev.setSetpoint(TWO_TOTE); // lifts elevator
		 * 
		 * autoState = 8; } if (autoState == 8 && autoTimer.get() >= 6.9) {
		 * talonLeft.set(1); talonRight.set(1); // moves forward
		 * 
		 * autoState = 9; } if (autoState == 9 && autoTimer.get() >= 7.4) {
		 * talonOmni.set(-1); // moves left to avoid bump
		 * 
		 * autoState = 10; } if (autoState == 10 && autoTimer.get() >= 8.4) {
		 * talonLeft.set(1); talonRight.set(1); // moves forward
		 * 
		 * autoState = 11; } if (autoState == 11 && autoTimer.get() >= 9.4) {
		 * talonLeft.set(0); talonRight.set(0); // stops moving }
		 * 
		 * }
		 */
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {

		SmartDashboard.putNumber("Elevator Encoder", elevEncoder.get());

		if (System.currentTimeMillis() >= print) {

			System.out.println("elevator: " + elevEncoder.get());
			/*
			 * System.out.println("test 7: "+test7.get());
			 * System.out.println("test 8: "+test8.get());
			 */

			System.out.println("Bottm limit switch hit: "
					+ botLimitSwitch.get());
			/*
			 * System.out.println("Top limit switch hit: " +
			 * topLimitSwitch.get()); System.out.println("Left encoder " +
			 * leftTalonEncoder.get()); System.out.println("Right encoder " +
			 * rightTalonEncoder.get()); System.out.println("omni encoder" +
			 */
			System.out.println("Gyro angle " + gyro.getAngle());
			System.out.println("Gyro Rate " + gyro.getRate());

			System.out.println("gamepad pov: " + jsGamepad.getPOV());

			print = System.currentTimeMillis() + 500;
		}

		if (botLimitSwitch.get() != lastBotState) {
			int error = elevEncoder.get() + DIFFERENCE_TOP_BOTTOM_ENCODER;

			elevEncoder.reset();
			ENCODER_OFFSET = 0;
			// elev.disable();
			System.out
					.println("Botswitch has been RESET :) Error top vs bottom: "
							+ error);
		}
		lastBotState = botLimitSwitch.get();

		/*
		 * if (topLimitSwitch.get() != lastTopState) { en1.reset();
		 * elev.disable(); ENCODER_OFFSET = -DIFFERENCE_TOP_BOTTOM_ENCODER;
		 * System.out.println("Topswitch has been RESET :)"); } lastTopState =
		 * topLimitSwitch.get();
		 */

		// Manual control for brake
		/*
		 * if (jsManualClaw.getRawButton(3)) { brake.set(BRAKE_ON); }
		 */
		if (jsManualClaw.getRawButton(2)) {
			elev.disable();
			talonElev.set(jsManualClaw.getY());

		}

		// Enables omni(H) drive at half speed
		SmartDashboard.putNumber("gyro angle", gyro.getAngle());
		if (jsRight.getRawButton(7) == true) {
			omniPiston.set(OMNI_ON);
			if (lastGyroState != jsRight.getRawButton(7)) {
				gyroSetpoint = gyro.getAngle();
			}
			
			Omni.enable();
			Omni.setSetpoint(gyroSetpoint);

			if (jsLeft.getRawButton(7)) {
				omniState = 2;
			} else {
				omniState = 1;
			}
			if (omniState == 1) {
				talonOmni.set(jsRight.getX() / 2);
			}
			if (omniState == 2) {
				talonOmni.set(jsRight.getX());
			}
		}
		lastGyroState = jsRight.getRawButton(7);
		// Standard drive
		if (jsRight.getRawButton(7) == false) {
			Omni.disable();
			talonOmni.set(0);
			omniPiston.set(OMNI_OFF);
			talonLeft.set(jsLeft.getY()*0.75);
			talonRight.set(jsRight.getY()*0.75);
		}
		
		
		// First tote position
		if (jsGamepad.getRawButton(3)) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(ONE_TOTE + ENCODER_OFFSET);

			brakeState = 1;
		}
		// Second tote position
		if (jsGamepad.getRawButton(4)) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(TWO_TOTE + ENCODER_OFFSET);

			brakeState = 2;
		}
		// Third tote position
		if (jsGamepad.getRawButton(1)) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(THREE_TOTE + ENCODER_OFFSET);

			brakeState = 3;
		}
		// Fourth tote position
		if (jsGamepad.getRawAxis(2) > 0) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(FOUR_TOTE + ENCODER_OFFSET);

			brakeState = 4;
		}
		// First pickup position
		if (jsGamepad.getRawButton(2)) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(PICKUP_TOTE + ENCODER_OFFSET);

			brakeState = 0;
		}

		if (brakeState == 1) {
			if (elevEncoder.get() < ONE_TOTE + brakeOffSet
					&& elevEncoder.get() > ONE_TOTE - brakeOffSet) {
				brake.set(BRAKE_ON);
			}
		}
		if (brakeState == 2) {
			if (elevEncoder.get() < TWO_TOTE + brakeOffSet
					&& elevEncoder.get() > TWO_TOTE - brakeOffSet) {
				brake.set(BRAKE_ON);
			}
		}
		if (brakeState == 3) {
			if (elevEncoder.get() < THREE_TOTE + brakeOffSet
					&& elevEncoder.get() > THREE_TOTE - brakeOffSet) {
				brake.set(BRAKE_ON);
			}
		}
		if (brakeState == 4) {
			if (elevEncoder.get() < FOUR_TOTE + brakeOffSet
					&& elevEncoder.get() > FOUR_TOTE - brakeOffSet) {
				brake.set(BRAKE_ON);
			}
		}
		if (brakeState == 0) {
			if (elevEncoder.get() < PICKUP_TOTE + brakeOffSet
					&& elevEncoder.get() > PICKUP_TOTE - brakeOffSet) {
				brake.set(BRAKE_ON);
			}
		}

		/*
		 * 
		 * Sixth pickup position here
		 */

		// Manual enable brakes

		// if the state



		if (jsGamepad.getRawAxis(3) > 0) {
			// Manual control for elevator

			if (jsGamepad.getPOV() == 180) {
				elev.disable();
				brake.set(BRAKE_OFF);
				talonElev.set(0.5);
			} else if (jsGamepad.getPOV() == 0) {
				elev.disable();
				brake.set(BRAKE_OFF);
				talonElev.set(-0.5);

			} else if (jsGamepad.getPOV() == -1) {
				talonElev.set(0);
				brake.set(BRAKE_ON);
			}

		}

		/**
		 * This function is called periodically during test mode
		 */
	}

	public void testPeriodic() {

	}

}
