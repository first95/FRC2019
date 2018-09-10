# FRC2019
The code base for our 2019 robot

When you clone this project for the first time:
* Copy the file "FRC2018/example .classpath"
* Open Eclipse, set workspace to the root of this git working copy

## Branches

- `master`: This is the "polished" branch.  Code should only be merged into this branch after it has passed the software acceptance test.  The idea here is that, at any time, if we need fully-working code on the robot, we can just put the contents of `master` on.
- `develop`: This is the normal common development branch.  Most of the time we should work out of develop, or out of a feature branch.
- `feature/my_cool_feature`: A feature branch.  Make a new feature branch before starting work on a new feature.  Commit all your work here, and push it every time you have a connection.  Merge your feature into `develop` once you finish it.
