package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Config
public class Intake {
    DcMotor intake;
    double power = .6;
    long Outtaketime;
    public static double powerSensitivity = .6;
    public static boolean WasIntaking = false;
    public static boolean Outtaking = false;
    public void setPower(double input){
        power = powerSensitivity * input;
    }
    public Intake(HardwareMap hardwareMap){
        intake = hardwareMap.dcMotor.get("intake");
    }
    public void run(){
        intake.setPower(power);
        if(power > 0.1) {
            WasIntaking = true;
        }
        if(WasIntaking && power < 0.1 && !Outtaking) { //OUTTAKE TEMPORARILY AFTER INTAKING
            intake.setPower(-0.15);
            Outtaking = true;
            Outtaketime = System.currentTimeMillis();
        }
        if(WasIntaking && (System.currentTimeMillis() > (Outtaketime)) && Outtaking) {
            intake.setPower(0);
            WasIntaking = false;
            Outtaking = false;
        }

    }
    public void setPowerA(double powera){
        intake.setPower(powera);

        if(powera > 0.1) {
            WasIntaking = true;
        }
        if(WasIntaking && powera < 0.1 && !Outtaking) { //OUTTAKE TEMPORARILY AFTER INTAKING
            intake.setPower(-0.15);
            Outtaking = true;
            Outtaketime = System.currentTimeMillis();
            while (System.currentTimeMillis() < (Outtaketime+150)) {  }
            intake.setPower(0);
            WasIntaking = false;
            Outtaking = false;
        }
    } //FOR AUTO MODE TO SET INTAKE POWER
}
