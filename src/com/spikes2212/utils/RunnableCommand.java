package com.spikes2212.utils;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class enables you to run any segment of code in a new thread as a
 * command, allowing you, for example, to easily make a button change the value
 * of a variable in the code.
 *
 * @author Uriah "Jhonny" Rokach
 * @see Thread
 */
public class RunnableCommand extends Command {

    private Runnable runnable;
    private Thread t;

    /**
	 * This constructs a command that runs the {@link Runnable#run()} of the given
	 * runnable in a new thread whenever started.
	 * 
	 * <br>
	 * <br>
	 * This command finishes when the thread finishes, meaning when run stops.
	 *
	 * @param runnable
	 *            the {@link Runnable} to run whenever this command starts
	 */
    public RunnableCommand(Runnable runnable) {
        this(runnable, true);
    }

    /**
	 * This constructs a command that runs the {@link Runnable#run()} of the given
	 * {@link RunnableCommand} in a new thread whenever started. 
	 * 
	 * <br>
	 * <br>
	 * This command finishes when the thread finishes, meaning when run stops. This
	 * command also works in disabled.
	 *
	 * @param runnable
	 *            the {@link Runnable} to run whenever this command starts
	 *            
	 * @see #RunnableCommand(Runnable, boolean)
	 */
    public RunnableCommand(Runnable runnable, boolean runInDisabled) {
        this.runnable = runnable;
        setRunWhenDisabled(runInDisabled);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        t = new Thread(runnable);
        t.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !t.isAlive();
    }

    // Called once after isFinished returns true
    protected void end() {
        t.interrupt();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
