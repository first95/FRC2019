package frc.robot.commands.compound;

import frc.robot.FieldSide;
import frc.robot.Robot.StartPosition;
import frc.robot.commands.drivebase.AnyForward;
import frc.robot.commands.drivebase.DriveStraight;
import frc.robot.commands.drivebase.Pivot;
import frc.robot.commands.drivebase.SweepTurn;
import frc.robot.commands.elevator.SetElevatorHeight.ElevatorHoldPoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchThenScale extends CommandGroup
{
	// These strategies assumes we have a cube pre-loaded on the robot.
	
		//INDEPENDENT CONSTANTS:

		// IF LEFT LOGIC:
		
		// IF CENTER LOGIC:
		
		// IF RIGHT LOGIC:

		public SwitchThenScale(FieldSide whichSideOfTheScaleIsOurColor,
				FieldSide whichSideOfTheNearSwitchIsOurColor,
				StartPosition robotStartingPosition)
		{
			// LEFT SIDE MOVE:
			if (robotStartingPosition == StartPosition.LEFT
					&& whichSideOfTheScaleIsOurColor == FieldSide.LEFT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.LEFT)
			{
			
			}
			else if (robotStartingPosition == StartPosition.LEFT
					&& whichSideOfTheScaleIsOurColor == FieldSide.LEFT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.RIGHT)
			{
		
			}
			else if (robotStartingPosition == StartPosition.LEFT
					&& whichSideOfTheScaleIsOurColor == FieldSide.RIGHT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.LEFT)
			{
			
			}
			else if (robotStartingPosition == StartPosition.LEFT
					&& whichSideOfTheScaleIsOurColor == FieldSide.RIGHT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.RIGHT)
			{
			
			}
			
			/*======================================*/
			// CENTER MOVE:
			else if (robotStartingPosition == StartPosition.CENTER
					&& whichSideOfTheScaleIsOurColor == FieldSide.LEFT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.LEFT)
			{
			
			}
			else if (robotStartingPosition == StartPosition.CENTER
					&& whichSideOfTheScaleIsOurColor == FieldSide.LEFT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.RIGHT)
			{
		}
			else if (robotStartingPosition == StartPosition.CENTER
					&& whichSideOfTheScaleIsOurColor == FieldSide.RIGHT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.LEFT)
			{
				
			}
			else if (robotStartingPosition == StartPosition.CENTER
					&& whichSideOfTheScaleIsOurColor == FieldSide.RIGHT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.RIGHT)
			{
				
			}
			/*======================================*/

			// RIGHT SIDE MOVE:
			else if (robotStartingPosition == StartPosition.RIGHT
					&& whichSideOfTheScaleIsOurColor == FieldSide.LEFT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.LEFT)
			{
				
			}
			else if (robotStartingPosition == StartPosition.RIGHT
					&& whichSideOfTheScaleIsOurColor == FieldSide.LEFT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.RIGHT)
			{
				
			}
			else if (robotStartingPosition == StartPosition.RIGHT
					&& whichSideOfTheScaleIsOurColor == FieldSide.RIGHT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.LEFT)
			{
				
			}
			else if (robotStartingPosition == StartPosition.RIGHT
					&& whichSideOfTheScaleIsOurColor == FieldSide.RIGHT
					&& whichSideOfTheNearSwitchIsOurColor == FieldSide.RIGHT)
			{
				
				
			}
			
			/*======================================*/
			// NO GAME DATA:
			else
			{
				addSequential(new AnyForward());
			}
		}
}