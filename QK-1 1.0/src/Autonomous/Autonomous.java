package Autonomous;

import Bane.Claw;
import Bane.Drive;
import Elevator.Elevator;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Autonomous {
	//Import stuff for drive class
	Joystick joystickLeft;
	Joystick joystickRight;
	Talon talonLeft;
	Talon talonRight;
	Talon omniTalon;
	DoubleSolenoid omniDirection;
	//Import stuff for claw class
	DoubleSolenoid solenoidClawLeft;
	DoubleSolenoid solenoidClawRight;
	
	Drive robotDriveOmni;
	Claw robotClaw;
	Elevator autoElevator;

	int autonomous = 0;
	
	public Autonomous(Joystick JL, Joystick JR, Talon TL, Talon TR, Talon OT, 
			DoubleSolenoid OD, DoubleSolenoid CL, DoubleSolenoid CR) {
		//local drive names 
		joystickLeft = JL;
		joystickRight = JR;
		talonLeft = TL;
		talonRight = TR;
		omniTalon = OT;
		omniDirection = OD;
		//local claw names
		solenoidClawLeft = CL;
		solenoidClawRight = CR;
		
		robotDriveOmni = new Drive(JR, JR, OT, OT, OT, CR);
		robotClaw = new Claw(JR, CR, CR);
		autoElevator = new Elevator();
		
		

	}

	public void robotInit() {
		autoElevator.autoDrop();
		solenoidClawLeft.set(Value.kForward);
		solenoidClawRight.set(Value.kForward);

	}

	public void autonomous() {

		if (autonomous == 0) { // does nothing

		}
		if (autonomous == 1) {

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); // moves forward to get mobility bonus

			talonLeft.set(0);
			talonRight.set(0);
			Timer.delay(.1);

		}
		if (autonomous == 2) {

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw may be used on totes and containers

			autoElevator.autoLift(); // lifts elevator

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); // moves forward to get mobility bonus

			talonLeft.set(0);
			talonRight.set(0);
			Timer.delay(.1);
		}
		if (autonomous == 3) {

			omniDirection.set(Value.kForward);
			Timer.delay(.1); // Drops swerve drive
			
			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw on the tote

			talonLeft.set(-1);
			talonRight.set(-1);
			Timer.delay(1); // moves back

			autoElevator.autoLift(); // lifts toe

			omniTalon.set(-1);
			Timer.delay(2); // moves left

			solenoidClawLeft.set(Value.kForward);
			solenoidClawRight.set(Value.kForward);
			Timer.delay(.3); // opens claw

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); // moves forward into tote

			autoElevator.autoDrop(); // lowers claw

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw on tote

			autoElevator.autoLift(); // lifts tote

			omniDirection.set(Value.kReverse);
			Timer.delay(.1); // lifts swerve Drive
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(4); // moves forward to get mobility bonus
		}
		if (autonomous == 4) {

			omniDirection.set(Value.kForward);
			Timer.delay(.1); // Drops swerve drive
			
			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); //closes claw on the tote

			talonLeft.set(-1);
			talonRight.set(-1);
			Timer.delay(1); //moves back

			autoElevator.autoLift(); // lifts toe

			omniTalon.set(1);
			Timer.delay(2); // moves right

			solenoidClawLeft.set(Value.kForward);
			solenoidClawRight.set(Value.kForward);
			Timer.delay(.3); // opens claw

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); // moves forward into tote

			autoElevator.autoDrop(); //lowers claw

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw on tote

			autoElevator.autoLift(); //lifts tote

			omniDirection.set(Value.kReverse);
			Timer.delay(.1); // lifts swerve drive
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(4);// moves forward to get mobility bonus

		}
		if (autonomous == 5) {

			omniDirection.set(Value.kForward); // Drops swerve drive
			
			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); //closes claw on the tote

			talonLeft.set(-1);
			talonRight.set(-1);
			Timer.delay(1); // moves back

			autoElevator.autoLift(); // lifts tote

			omniTalon.set(-1);
			Timer.delay(2); // moves left

			solenoidClawLeft.set(Value.kForward);
			solenoidClawRight.set(Value.kForward);
			Timer.delay(.3); // opens claw

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); // moves forward into tote

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes Claw
			
			autoElevator.autoLift(); // lifts elevator
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1.5); // moves forward 
			
			omniTalon.set(-1);
			Timer.delay(1); // moves left to avoid bump
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(2.5); // moves forward to get mobility bonus
		}
		if (autonomous == 6) {

			omniDirection.set(Value.kForward);
			Timer.delay(.1); // drops swerve drive
			
			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw

			autoElevator.autoLift(); // moves elevator up

			talonLeft.set(-1);
			talonRight.set(-1);
			Timer.delay(1); // moves back

			omniTalon.set(-1);
			Timer.delay(2); // moves left
 			
			solenoidClawLeft.set(Value.kForward);
			solenoidClawRight.set(Value.kForward);
			Timer.delay(.3); //opens claw

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); //moves forward into tote

			autoElevator.autoDrop(); // drops claw
			
			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw

			talonLeft.set(-1);
			talonRight.set(-1);
			Timer.delay(1); // drive back

			omniTalon.set(-1);
			Timer.delay(2); // drives left

			autoElevator.autoLift(); // lifts elevator
			
			solenoidClawLeft.set(Value.kForward);
			solenoidClawRight.set(Value.kForward);
			Timer.delay(.3); // opens claw
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); // drives towards tote
			
			autoElevator.autoDrop(); // lowers elevator

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw

			omniDirection.set(Value.kReverse);
			Timer.delay(.1); // lifts swerve drive
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); // goes forward
			
			autoElevator.autoLift(); // lifts elevator
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(3); // goes forward for mobility bonus
			
			talonLeft.set(0);
			talonRight.set(0);
			Timer.delay(.1); // stops motors
		}
		if (autonomous == 7) {

			omniDirection.set(Value.kForward);
			Timer.delay(.1); // drops swerve drive
			
			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw

			autoElevator.autoLift(); // lifts elevator

			talonLeft.set(-1);
			talonRight.set(-1);
			Timer.delay(1); // moves back

			omniTalon.set(1);
			Timer.delay(2); // moves right
			
			autoElevator.autoDrop(); // lowers elevator

			solenoidClawLeft.set(Value.kForward);
			solenoidClawRight.set(Value.kForward);
			Timer.delay(.3); // opens claw

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); //moves forward into tote

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw

			talonLeft.set(-1);
			talonRight.set(-1);
			Timer.delay(1); // moves back

			autoElevator.autoLift(); // lifts elevator
			
			omniTalon.set(1);
			Timer.delay(2); // moves right

			autoElevator.autoLift(); // lifts elevator
			
			solenoidClawLeft.set(Value.kForward);
			solenoidClawRight.set(Value.kForward);
			Timer.delay(.3); // opens claw

			autoElevator.autoDrop(); // lowers elevator
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); // moves forward into tote

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw

			omniDirection.set(Value.kReverse);
			Timer.delay(.1); // lifts swerve drive
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1); // moves forward
			
			autoElevator.autoLift(); // lifts tote
			
			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(3); // moves forward for mobility bonus
			
			talonLeft.set(0);
			talonRight.set(0);
			Timer.delay(.1); // stops motors
		}
		if (autonomous == 8) {

			
		}
	}
