package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeleOp.SubsystemBased;

@Config
public class Turret {
    DcMotor turret;
    public static double kP = -.02;
    public static double maxPower = .3;
    public static double minPower = .1;
    public static int maxLeft = 667;
    public static int maxRight = -667;
    public static double resetPower = -0.005;
    public static int resetTolerance = 20;
    public boolean doneResetting = true;
    public boolean ReactiveAutoAim = false;
    public boolean BusySettingPosition = false;

    public Turret(HardwareMap hardwaremap) {
        turret = hardwaremap.dcMotor.get("gears");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void reset() {  //DANGEROUS - Running this can damage wiring if run without turret facing straight ahead
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPosition(int position) { //FOR AUTO ONLY
        if (position < maxLeft || position > maxRight) {
        BusySettingPosition = true;
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turret.setTargetPosition(position);
        turret.setPower(0.7);
        while (turret.isBusy() && turret.getCurrentPosition() < maxLeft && turret.getCurrentPosition() > maxRight) {
        }
        turret.setPower(0);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BusySettingPosition = false;
        }
    }
    public void setPosition2(int position) { //FOR TELEOP ONLY
        if (position < maxLeft || position > maxRight) {
        BusySettingPosition = true;
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turret.setTargetPosition(position);
        turret.setPower(0.7);
        }
    }
    public int getCurrentPosition() {
        return turret.getCurrentPosition();
    }

    public void stop() {
        turret.setPower(0);
    }

    public void setPower(double power) {
        turret.setPower(power);
    }

    public void run(double xError) {
        if(!BusySettingPosition) {
            turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        double power = 0;
        if (doneResetting && !BusySettingPosition) {
            power = xError * kP;
        } else if (!BusySettingPosition){
            power = turret.getCurrentPosition() * resetPower;
        }
        if ((Math.abs(power) > maxPower) && !BusySettingPosition) {
            power = Math.signum(power) * maxPower;
        }
        if ((xError > 0 && turret.getCurrentPosition() < maxRight) && !BusySettingPosition) {
            power = 0;
        }
        if ((xError < 0 && turret.getCurrentPosition() > maxLeft) && !BusySettingPosition) {
            power = 0;
        }
        if (Math.abs(turret.getCurrentPosition()) < resetTolerance){
            doneResetting = true;
        }
        if((power >= minPower | power <= -minPower) && !BusySettingPosition && doneResetting) {
            turret.setPower(power);
        }
        else if (!BusySettingPosition && doneResetting){
            turret.setPower(0);
        }
        if(!turret.isBusy() && BusySettingPosition) { //After setting position and turret stops, run these commands
             turret.setPower(0);
             turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
             BusySettingPosition = false;
             ReactiveAutoAim = true;
        }
    }
    public void status(Telemetry telemetry) {
        telemetry.addData("Turret Position:", turret.getCurrentPosition());
        telemetry.addData("Turret Power:",turret.getPower());
    }
}
