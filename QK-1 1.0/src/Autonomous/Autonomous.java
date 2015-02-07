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
	Joystick joystickLeft;
	Joystick joystickRight;
	Talon talonLeft;
	Talon talonRight;
	Talon omniTalon;
	DoubleSolenoid omniDirection;
	DoubleSolenoid solenoidClawLeft;
	DoubleSolenoid solenoidClawRight;
	long autonomousPrint = System.currentTimeMillis();

	Drive robotDriveOmni;
	Claw robotClaw;
	Elevator autoElevator;

	int autonomous = 0;
	int maxAutonomous = 10;
	int autoState = 0;
	Timer autoTimer;

	public Autonomous(Joystick JL, Joystick JR, Talon TL, Talon TR, Talon OT,
			DoubleSolenoid OD, DoubleSolenoid CL, DoubleSolenoid CR) {
		joystickLeft = JL;
		joystickRight = JR;
		talonLeft = TL;
		talonRight = TR;
		omniTalon = OT;
		omniDirection = OD;
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

	public void setAutonomous(int auto) {
		autonomous = auto;
		if (autonomous > maxAutonomous) {
			autonomous = maxAutonomous;
		}
		if (autonomous < 0) {
			autonomous = 0;
		}
	}

	public void autonomous() {

		if (autonomous == 0) { // does nothing

		}
		if (autonomous == 1) {

			if (autoState == 0) {
				talonLeft.set(1);
				talonRight.set(1);

				autoState = 1;
			}
			if (autoState == 1 && autoTimer.get() >= 1) {
				talonLeft.set(0);
				talonRight.set(0);
			}
		}
		if (autonomous == 2) {

			if (autoState == 0) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse);
				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				autoElevator.autoLift(); // lifts elevator

				autoState = 2;
			}
			if (autoState == 2 && autoTimer.get() >= 1.3) {

				talonLeft.set(1);
				talonRight.set(1);

				autoState = 3;
			}
			if (autoState == 3 && autoTimer.get() >= 2.3) {
				talonLeft.set(0);
				talonRight.set(0);
			}
		}
		if (autonomous == 3) {

			if (autoState == 0) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse);

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				talonLeft.set(-1);
				talonRight.set(-1);

				autoState = 2;
			}
			if (autoState == 2 && autoTimer.get() >= 1.3) {
				autoElevator.autoLift(); // lifts toe

				autoState = 3;
			}
			if (autoState == 3 && autoTimer.get() >= 2.3) {
				omniTalon.set(-1);
				autoState = 4;
			}

			if (autoState == 4 && autoTimer.get() >= 4.3) {
				solenoidClawLeft.set(Value.kForward);
				solenoidClawRight.set(Value.kForward);
				autoState = 5;
			}
			if (autoState == 5 && autoTimer.get() >= 4.6) {
				talonLeft.set(1);
				talonRight.set(1);

				autoState = 6;
			}
			if (autoState == 6 && autoTimer.get() >= 5.6) {
				autoElevator.autoDrop(); // lowers claw

				autoState = 7;
			}

			if (autoState == 7 && autoTimer.get() >= 6.6) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse);

				autoState = 8;
			}
			if (autoState == 8 && autoTimer.get() >= 6.9) {
				autoElevator.autoLift();

				autoState = 9;
			}
			if (autoState == 9 && autoTimer.get() >= 7.9) {
				talonLeft.set(1);
				talonRight.set(1);

				autoState = 10;
			}

			if (autoState == 10 && autoTimer.get() >= 8.9) {
				talonLeft.set(0);
				talonRight.set(0);
			}

		}
		if (autonomous == 4) {

			if (autoState == 0) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse);

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				talonLeft.set(-1);
				talonRight.set(-1);

				autoState = 2;
			}
			if (autoState == 2 && autoTimer.get() >= 1.3) {
				autoElevator.autoLift();

				autoState = 3;
			}
			if (autoState == 3 && autoTimer.get() >= 2.3) {
				omniTalon.set(1);

				autoState = 4;
			}
			if (autoState == 4 && autoTimer.get() >= 4.3) {
				solenoidClawLeft.set(Value.kForward);
				solenoidClawRight.set(Value.kForward);

				autoState = 5;
			}
			if (autoState == 5 && autoTimer.get() >= 4.6) {
				autoElevator.autoDrop();

				autoState = 6;
			}

			if (autoState == 6 && autoTimer.get() >= 5.6) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse);

				autoState = 7;
			}
			Timer.delay(.3); // closes claw on tote

			autoElevator.autoLift(); // lifts tote

			talonLeft.set(1);
			talonRight.set(1);
			Timer.delay(4);// moves forward to get mobility bonus
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

			if (System.currentTimeMillis() >= autonomousPrint) {
				System.out.println("autonomous State: " + autonomous);
			}
		}

	}
}
