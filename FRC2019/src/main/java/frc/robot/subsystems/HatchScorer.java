package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.commands.hscorer.ManuallyControlHatchScorer;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls mechanism to score hatches.
 */
public class HatchScorer extends Subsystem {

  // The solenoids for the cylinders that open the scorer and that push the scorer
  // Named to allow for future possibility of adding one more solenoid per motion to have slow/fast options
	private Solenoid openA, pushA;

  public HatchScorer() {
    // False means closed and true means open so default is closed when robot off
    openA = new Solenoid(Constants.HS_OPEN_A);

    // False means retracted, and true means extended
    pushA = new Solenoid(Constants.HS_PUSH_A);

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManuallyControlHatchScorer());
  }

  public void openHS(boolean open) {
    if (open) {
      System.out.println("Opening hatch scorer");
      openA.set(true);
    } else {
      System.out.println("Closing hatch scorer");
      openA.set(false);
    }
  }

  public void toggleOpenHS() {
    if (openA.get()) {
      System.out.println("CLosing hatch scorer");
      openA.set(false);
    } else {
      System.out.println("Opening hatch scorer");
      openA.set(true);
    }
  }

  public void pushHS(boolean push) {
    if (push) {
      System.out.println("Pushing hatch scorer");
      pushA.set(true);
    } else {
      System.out.println("Retracting hatch scorer");
      pushA.set(false);
    }
  }

  public void togglePushHS() {
    if (pushA.get()) {
      System.out.println("Retracting hatch scorer");
      pushA.set(false);
    } else {
      System.out.println("Pushing hatch scorer");
      pushA.set(true);
    }
  }
}
