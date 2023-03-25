package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.arm.ArmExtSubsystem;
import frc.robot.subsystems.wrist.WristSubsystem;


public class CheckHomedCommand extends CommandBase {
    private final ArmExtSubsystem armExtSubsystem;
    private final WristSubsystem wristSubsystem;
    Timer timer = new Timer();
    boolean flag = false;
    public CheckHomedCommand(ArmExtSubsystem armExtSubsystem, WristSubsystem wristSubsystem) {
        this.armExtSubsystem = armExtSubsystem;
        this.wristSubsystem = wristSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.armExtSubsystem, this.wristSubsystem);
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        if(!wristSubsystem.hasWristHomed()) {
            wristSubsystem.goWristToHome();
        }

        if(!armExtSubsystem.hasArmHomed()) {
            armExtSubsystem.goArmToHome();
        }

        if(timer.hasElapsed(.75)) {
            flag = true;
            wristSubsystem.zeroWristAngle();
            armExtSubsystem.resetExtensionEncoder();
        }
    }

    @Override
    public boolean isFinished() {
        return flag || (wristSubsystem.hasWristHomed() && armExtSubsystem.hasArmHomed());
    }

    @Override
    public void end(boolean interrupted) {

    }
}
