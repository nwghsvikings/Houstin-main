package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class FlyWheel {
   public enum States{
       ON,
       OFF
   }
   States currentState = States.OFF;
    DcMotorEx flywheel;
    DcMotorEx flywheel2;
    public static double maxVelocity = 1900;
    public static double increment = 25;
    public FlyWheel(HardwareMap hardwareMap){
        flywheel = hardwareMap.get(DcMotorEx.class,"shooter2");
        flywheel2 = hardwareMap.get(DcMotorEx.class,"shooter");
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel2.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public void setState(States newState){
        currentState = newState;
    }
    public States getState(){
        return currentState;
    }
    public void add(){ //INCREASE VELOCITY
        if (maxVelocity <= 1850 )
        {
            maxVelocity += increment;
        }
        else{
            maxVelocity = 1900;
        }
    }
    public void sub(){ //DECREASE VELOCITY
        if(maxVelocity >=1350)
        {
            maxVelocity -= increment;
        }
        else {
            maxVelocity = 1300;
        }
    }


    public void setMaxVelocity (double fwvelocity) {
        maxVelocity = fwvelocity;
    }
    //Change velocity setting for when flywheel is toggled on or off

    public void setVelocity(double fwvelocity) { //Used for AUTO mode to set flywheel velocity
        flywheel.setVelocity(fwvelocity); flywheel2.setVelocity(fwvelocity);
    }
    public double getMaxVelocity() {
        return maxVelocity;
    }
    public double getVelocity() {
        return flywheel.getVelocity();
    }
    public double getPower() {
        return flywheel.getPower();
    }
    public void setPower(double power) {
        flywheel.setPower(power); flywheel2.setPower(power);
    }

    public void run() {
        double velocity = 1450;

        switch (currentState) {
            case ON:
                velocity = maxVelocity;
                break;
            case OFF:
                velocity = 0;
                break;
        }

        if(Math.abs(velocity)>maxVelocity)
        {
            velocity=Math.signum(velocity)*maxVelocity;
        }
        flywheel.setVelocity(velocity);
        flywheel2.setVelocity(velocity);
    }
    public void status(Telemetry telemetry){
        telemetry.addData("CurrentVelocity",flywheel.getVelocity());
        telemetry.addData("Power",flywheel.getPower());
        telemetry.addData("Velocity Setting",maxVelocity);
    }

}

