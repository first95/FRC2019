# Software full acceptance test

This test should be fully run, and should fully pass, prior to merging into master.

It's very important to follow the instructions quite literally and exactly.  If there's a mistake in the test, we should fix the test.  This is to make sure we're running the same test every time.

Write a check mark or "P" for every test that passes.  Write an "F" for any test that fails, and write a note describing the failure.

## Teleoperated feature check.

Here we exercise every feature of the robot that can be used during teleoperated mode.  If any of these fail, go back and fix the software, then restart the test from the start.

### Drivebase 
- [ ] Drive forward and backward
- [ ] Manually shift gears

### Elevator
- [ ] Raise and lower elevator through its full range
- [ ] Visit each height preset
- [ ] Hold down a preset button.  Confirm the elevator holds at that position.
- [ ] Tap a preset button, but release it before the elevator gets there.  Confirm the elevator stops moving before it reaches its destination.
- [ ] Run the climber winch

### Wrist and maw
- [ ] Lift wrist into all 4 positions
- [ ] Open and close maw
- [ ] Run chain in and out
- [ ] Automatically acquire a cube

## Startup test.

- [ ] Set up a "practice" in the drivers' station.  This will run an auto move, then transition into teleoperated.  Confirm that the auto move runs, and that no exceptions are thrown on the console when the robot enters teleoperated mode.

## Autonomous mode checks

Here we test our autonomous moves.

## Regression tests

Here we run a test for each major bug we've found.  This is to make sure the bug has remained fixed.

