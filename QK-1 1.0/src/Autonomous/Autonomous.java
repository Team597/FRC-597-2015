package Autonomous;

import Bane.Claw;
import Bane.Drive;
import Elevator.Elevator;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
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
	Encoder omniEncoder;
	long autoPrint = System.currentTimeMillis();

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
		omniEncoder = new Encoder (4,5);

		robotDriveOmni = new Drive(JR, JR, OT, OT, OT, CR);
		robotClaw = new Claw(JR, CR, CR);
		autoElevator = new Elevator();

	}

	public void robotInit() {
		autoElevator.autoDrop(); // lowers elevator.
		omniDirection.set(Value.kForward); // lowers omni talon
		solenoidClawLeft.set(Value.kForward); // opens claw
		solenoidClawRight.set(Value.kForward); // opens claw
		autoTimer.start(); // starts auto timer.
	}

	public void autoPrint() {
		if (System.currentTimeMillis() >= autoPrint) {
			System.out.println("autonomous State: " + autonomous);

			autoPrint += 1000;
		}

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
				talonRight.set(1); // moves forward

				autoState = 1;
			}
			if (autoState == 1 && autoTimer.get() >= 1) {
				talonLeft.set(0);
				talonRight.set(0); // stops moving
			}
		}
		if (autonomous == 2) {

			if (autoState == 0) { // closes claw
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse);
				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				autoElevator.autoLift(); // lifts elevator

				autoState = 2;
			}
			if (autoState == 2 && autoTimer.get() >= 1.3)

				talonLeft.set(1);
			talonRight.set(1); // moves forward

			autoState = 3;
		}
		if (autoState == 3 && autoTimer.get() >= 2.3) {
			talonLeft.set(0);
			talonRight.set(0); // stops moving
		}
		if (autonomous == 3) {

			if (autoState == 0) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse); // closes claw

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				autoElevator.autoLift(); // lifts toe

				autoState = 2;
			}
			if (autoState == 2 && autoTimer.get() >= 1.3) {
				talonLeft.set(-1);
				talonRight.set(-1); // moves back

				autoState = 3;
			}
			if (autoState == 3 && autoTimer.get() >= 2.3) {
				omniTalon.set(-1); // moves left
				autoState = 4;
			}

			if (autoState == 4 && autoTimer.get() >= 4.3) {
				solenoidClawLeft.set(Value.kForward);
				solenoidClawRight.set(Value.kForward); // opens claw

				autoState = 5;
			}
			if (autoState == 5 && autoTimer.get() >= 4.6) {
				autoElevator.autoDrop(); // lowers elevator

				autoState = 6;
			}
			if (autoState == 6 && autoTimer.get() >= 5.6) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 7;
			}

			if (autoState == 7 && autoTimer.get() >= 6.6) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse); // closes claw

				autoState = 8;
			}
			if (autoState == 8 && autoTimer.get() >= 6.9) {
				autoElevator.autoLift(); // lifts elevator

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
		if (autonomous == 4) {

			if (autoState == 0) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse); // close claw

				autoState = 1;
			}

			if (autoState == 1 && autoTimer.get() >= 0.3) {
				autoElevator.autoLift(); // lifts elevator

				autoState = 2;
			}
			if (autoState == 2 && autoTimer.get() >= 1.3) {
				talonLeft.set(-1);
				talonRight.set(-1); // moves back

				autoState = 3;
			}
			if (autoState == 3 && autoTimer.get() >= 2.3) {
				omniTalon.set(1); // moves Right

				autoState = 4;
			}
			if (autoState == 4 && autoTimer.get() >= 4.3) {
				solenoidClawLeft.set(Value.kForward);
				solenoidClawRight.set(Value.kForward); // opens claw

				autoState = 5;
			}
			if (autoState == 5 && autoTimer.get() >= 4.6) {
				talonLeft.set(1);
				talonRight.set(1); // moves forward

				autoState = 6;
			}
			if (autoState == 6 && autoTimer.get() >= 5.6) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse); // closes claw

				autoState = 7;
			}

			if (autoState == 7 && autoTimer.get() >= 6.6) {
				autoElevator.autoDrop(); // lowers elevator

				autoState = 8;
			}
			if (autoState == 8 && autoTimer.get() >= 6.9) {
				autoElevator.autoLift(); // lifts tote

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
			if (autonomous == 5) {

				if (autoState == 0) {
					solenoidClawLeft.set(Value.kReverse);
					solenoidClawRight.set(Value.kReverse); // closes claw

					autoState = 1;
				}

				if (autoState == 1 && autoTimer.get() >= 0.3) {
					autoElevator.autoLift(); // lifts elevator

					autoState = 2;
				}

				if (autoState == 2 && autoTimer.get() >= 1.3) {
					talonLeft.set(-1);
					talonRight.set(-1); // moves back

					autoState = 3;
				}

				if (autoState == 3 && autoTimer.get() >= 2.3) {
					omniTalon.set(-1); // moves left

					autoState = 4;
				}

				if (autoState == 4 && autoTimer.get() >= 4.3) {
					solenoidClawLeft.set(Value.kForward);
					solenoidClawRight.set(Value.kForward); // opens claw

					autoState = 5;
				}

				if (autoState == 5 && autoTimer.get() >= 4.6) {
					talonLeft.set(1);
					talonRight.set(1); // moves forward

					autoState = 6;
				}

				if (autoState == 6 && autoTimer.get() >= 5.6) {

					solenoidClawLeft.set(Value.kReverse);
					solenoidClawRight.set(Value.kReverse); // closes claw

					autoState = 7;
				}
				if (autoState == 7 && autoTimer.get() >= 5.9) {
					autoElevator.autoLift(); // lifts elevator

					autoState = 8;
				}
				if (autoState == 8 && autoTimer.get() >= 6.9) {
					talonLeft.set(1);
					talonRight.set(1); // moves forward

					autoState = 9;
				}
				if (autoState == 9 && autoTimer.get() >= 7.4) {
					omniTalon.set(-1); // moves left to avoid bump

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
			if (autonomous == 6) {

				if (autoState == 0) {
					solenoidClawLeft.set(Value.kReverse);
					solenoidClawRight.set(Value.kReverse); // close claw

					autoState = 1;
				}

				if (autoState == 1 && autoTimer.get() >= 0.3) {
					autoElevator.autoLift(); // opens elevator

					autoState = 2;
				}

				if (autoState == 2 && autoTimer.get() >= 1.3) {
					talonLeft.set(1);
					talonRight.set(1);

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

				if (autoState == 6 && autoTimer.get() >= 5.6)
					solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse);

				autoState = 7;
			}
			if (autoState == 7 && autoTimer.get() > 5.9) {
				autoElevator.autoDrop();
				autoElevator.autoLift();

				autoState = 8;
			}

			if (autoState == 8 && autoTimer.get() >= 6.9) {
				talonLeft.set(-1);
				talonRight.set(-1);

				autoState = 9;
			}
			if (autoState == 9 && autoTimer.get() >= 7.9) {
				omniTalon.set(-1);

				autoState = 10;
			}

			if (autoState == 10 && autoTimer.get() >= 9.9) {
				solenoidClawLeft.set(Value.kForward);
				solenoidClawRight.set(Value.kForward);

				autoState = 11;
			}
			if (autoState == 11 && autoTimer.get() >= 10.2) {
				talonLeft.set(1);
				talonRight.set(1);

				autoState = 12;
			}
			if (autoState == 12 && autoTimer.get() >= 11.2) {
				solenoidClawLeft.set(Value.kReverse);
				solenoidClawRight.set(Value.kReverse);

				autoState = 13;
			}
			if (autoState == 13 && autoTimer.get() >= 11.5) {
				autoElevator.autoDrop();
				autoElevator.autoLift();

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
		if (autonomous == 7) {

		}
		if (autonomous == 8) {

		}

	}

}
