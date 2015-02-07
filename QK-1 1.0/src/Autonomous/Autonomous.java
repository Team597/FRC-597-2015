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
	// Import stuff for drive class
	Joystick joystickLeft;
	Joystick joystickRight;
	Talon talonLeft;
	Talon talonRight;
	Talon omniTalon;
	DoubleSolenoid omniDirection;
	// Import stuff for claw class
	DoubleSolenoid solenoidClawLeft;
	DoubleSolenoid solenoidClawRight;
	long autonomousPrint = System.currentTimeMillis();

	Drive robotDriveOmni;
	Claw robotClaw;
	Elevator autoElevator;

	int autonomous = 0;
	int maxAutonomous = 10;

	public Autonomous(Joystick JL, Joystick JR, Talon TL, Talon TR, Talon OT,
			DoubleSolenoid OD, DoubleSolenoid CL, DoubleSolenoid CR) {
		// local drive names
		joystickLeft = JL;
		joystickRight = JR;
		talonLeft = TL;
		talonRight = TR;
		omniTalon = OT;
		omniDirection = OD;
		// local claw names
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
	
	public void setAutonomous(int auto){
		autonomous = auto;
		if (autonomous > maxAutonomous){
			autonomous = maxAutonomous;
		}
		if (autonomous < 0){
			autonomous = 0;
		}
	}
	
	public void autonomous() {

		autonomousPrint += 1000;
		
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

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(4); // moves forward to get mobility bonus
		}
		if (autonomous == 4) {

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw on the tote

			talonLeft.set(-1);
			talonRight.set(-1);
			Timer.delay(1); // moves back

			autoElevator.autoLift(); // lifts toe

			omniTalon.set(1);
			Timer.delay(2); // moves right

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

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(4);// moves forward to get mobility bonus

		}
		if (autonomous == 5) {

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3); // closes claw on the tote

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
			Timer.delay(.3); //

			autoElevator.autoLift();

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1.5);

			omniTalon.set(-1);
			Timer.delay(1);

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(2.5);
		}
		if (autonomous == 6) {

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3);

			autoElevator.autoLift();

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1);

			omniTalon.set(-1);
			Timer.delay(2);

			solenoidClawLeft.set(Value.kForward);
			solenoidClawRight.set(Value.kForward);
			Timer.delay(.3);

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1);

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3);

			autoElevator.autoDrop();
			autoElevator.autoLift();

			talonLeft.set(-1);
			talonRight.set(-1);
			Timer.delay(1);

			omniTalon.set(-1);
			Timer.delay(2);

			solenoidClawLeft.set(Value.kForward);
			solenoidClawRight.set(Value.kForward);
			Timer.delay(.3);

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(1);

			solenoidClawLeft.set(Value.kReverse);
			solenoidClawRight.set(Value.kReverse);
			Timer.delay(.3);

			autoElevator.autoDrop();
			autoElevator.autoLift();

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(4);

		}
		if (autonomous == 7) {

		}
		if (autonomous == 8) {

		}

		if(System.currentTimeMillis() >= autonomousPrint){
    		System.out.println("autonomous State: "+ autonomous);
		}
	}

}
