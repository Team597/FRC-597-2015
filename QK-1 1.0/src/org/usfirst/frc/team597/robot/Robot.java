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
	Joystick jsGamepad = new Joystick(3);
	Talon talonLeft = new Talon(0);
	Talon talonRight = new Talon(1);
	Talon talonOmni = new Talon(2);
	Talon talonElev = new Talon(3);
	Compressor comp = new Compressor();
	DoubleSolenoid brake = new DoubleSolenoid(0, 7);
	DoubleSolenoid omniPiston = new DoubleSolenoid(2, 5);
	CameraServer server;

	Gyro gyro = new Gyro(0);
	
	Encoder elevEncoder = new Encoder(7, 8);
	
	OmniDrive omni_Drive = new OmniDrive(talonLeft, talonRight);
	
	PIDController elev = new PIDController(-1 / 100.0, 0, -.01, elevEncoder,
			talonElev);
	PIDController Omni = new PIDController(1 / 90.0, 0, 0, gyro, omni_Drive);
	//PIDController Omni2 = new PIDController(1/100, 0, -.01, gyro,
		// talonRight);
	
	Claw claw = new Claw(jsGamepad, brake, talonElev, elev);
	Drive drive = new Drive(gyro, jsLeft, jsRight, talonLeft, talonRight, talonOmni, omniPiston, Omni);
	
	final Value BRAKE_ON = Value.kReverse;
	final Value BRAKE_OFF = Value.kForward;
	
	DigitalInput topLimitSwitch = new DigitalInput(9);
	DigitalInput botLimitSwitch = new DigitalInput(0);
	boolean lastBotState = false;
	
	int eS = 1;
	long print = System.currentTimeMillis();
	
	// Encoder tests:
	// L: 1597, 1587, 1586
	// R: 2303, 2389, 2280
	// Measured drift: 4degrees / min
	Encoder leftTalonEncoder = new Encoder(5, 6);
	Encoder omniTalonEncoder = new Encoder(1, 2);
	Encoder rightTalonEncoder = new Encoder(3, 4);
	
	int ENCODER_OFFSET = 0;
	int DIFFERENCE_TOP_BOTTOM_ENCODER = 3813;

	int PICKUP_TOTE = -50;
	int ONE_TOTE = 9;
	int TWO_TOTE = 1200;
	int THREE_TOTE = 1900;
	int FOUR_TOTE = 3000;
	int TOP_TOTE = 3800;

	int brakeState = 0;
	int brakeOffSet = 50;
	
	int autonomous = 0;
	int maxAutonomous = 10;
	int autoState = 0;
	int toteFlag = 0;
	Timer autoTimer;

	Command autonomousCommand;
	SendableChooser autoChooser;

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
		double y = 1; // time from one tote to the next
		
		drive.move(0.75, 1);
		
		drive.move(0, 0.01);

		pickup();

		talonOmni.set(-1);
		Timer.delay(y);

		pickup();

		talonOmni.set(-1);
		Timer.delay(y + .1); // extra timer for extra weight

		pickup();

		talonOmni.set(-1);
		Timer.delay(y + .2); // extra timer for extra weight
		
		/* Autonomous with elev PID */
		// Opens claw
		if (autoState == 0) {
			claw.Open();
			
			autoState = 1;
		}
		// Moves elev down to reset encoder
		if (autoState == 1) {
			if (botLimitSwitch.get() != false) {
				talonElev.set(-0.3);
			}
			if (botLimitSwitch.get() == false) {
				talonElev.set(0);
				elevEncoder.reset();
				ENCODER_OFFSET = 0;
				
				autoState = 2;
			}
		}
		// Picks up tote
		if (autoState == 2) {
			pickup();
			
			autoState = 3;
			toteFlag ++;
		}
		// Moves robot right (robot facing us)
		if (autoState == 3) {
			drive.strafe(0.5, 3);
			drive.strafe(0, 0.1);
			
			autoState = 2;
			
			if (toteFlag == 3) {
				autoState = 4;
			}
		}
		// Backup into auto-zone
		if (autoState == 4) {
			drive.move(-0.3, 1);
			drive.move(0, 0.1);
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {

		claw.teleopPeriodic();
		drive.teleopPeriodic();
		drive.PRINT();

		SmartDashboard.putNumber("Elevator Encoder", elevEncoder.get());
		
		// Print out for testing
		if (System.currentTimeMillis() >= print) {
			System.out.println("Elevator: " + elevEncoder.get());
			System.out.println("Bot LimitSwitch hit: "
					+ botLimitSwitch.get());
			System.out.println("Gyro Angle: " + gyro.getAngle());
			System.out.println("Gyro Rate: " + gyro.getRate());
			

			print = System.currentTimeMillis() + 500;
		}

		if (botLimitSwitch.get() != lastBotState) {
			int error = elevEncoder.get() + DIFFERENCE_TOP_BOTTOM_ENCODER;
			
			elevEncoder.reset();
			ENCODER_OFFSET = 0;
			// elev.disable();
			System.out.println("Elevator encoder has been RESET");
			System.out.println("Error top vs. bot: " + error);
		}
		lastBotState = botLimitSwitch.get();
		
		// Pickup position
		if (jsGamepad.getRawButton(2)) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(PICKUP_TOTE + ENCODER_OFFSET);
			// Enable brakes
			brakeState = 0;
		}
		// First tote position
		if (jsGamepad.getRawButton(3)) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(ONE_TOTE + ENCODER_OFFSET);
			// Enable brakes
			brakeState = 1;
		}
		// Second tote position
		if (jsGamepad.getRawButton(4)) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(TWO_TOTE + ENCODER_OFFSET);
			// Enable brakes 
			brakeState = 2;
		}
		// Third tote position
		if (jsGamepad.getRawButton(1)) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(THREE_TOTE + ENCODER_OFFSET);
			// Enable brakes
			brakeState = 3;
		}
		// Fourth tote position
		if (jsGamepad.getRawAxis(2) > 0) {
			brake.set(BRAKE_OFF);
			// Move elevator
			elev.enable();
			elev.setSetpoint(FOUR_TOTE + ENCODER_OFFSET);
			// Enable brakes 
			brakeState = 4;
		}
		
		// Brake check
		if (brakeState == 0) {
			if (elevEncoder.get() < PICKUP_TOTE + brakeOffSet
					&& elevEncoder.get() > PICKUP_TOTE - brakeOffSet) {
				brake.set(BRAKE_ON);
			}
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
		
		/**
		 * This function is called periodically during test mode
		 */
	}

	public void pickup() {
		brake.set(BRAKE_OFF);
		// Move elevator
		elev.enable();
		elev.setSetpoint(TWO_TOTE + ENCODER_OFFSET);
		brakeState = 0;
		
		claw.Open();
		
		brake.set(BRAKE_OFF);
		// Move elevator
		elev.enable();
		elev.setSetpoint(PICKUP_TOTE + ENCODER_OFFSET);
		brakeState = 0;

		claw.Close();
		
		brake.set(BRAKE_OFF);
		// Move elevator
		elev.enable();
		elev.setSetpoint(THREE_TOTE + ENCODER_OFFSET);
		brakeState = 3;
		
	}

	public void turn180(){
		Omni.enable();
		
		double z = gyro.getAngle() + 180;
		
		Omni.setSetpoint(z);
		
	}

	public void testPeriodic() {

	}

}
