package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoActions { //This entire module is for auto operation only. This utilizes other subsystem based logic.

    public static class Shoot3Balls implements Action {
        Intake intake;
        double intakepower;
        FlyWheel flywheel;
        double fwvelocity;
        Transfer transfer;
        Telemetry telemetry;
        long timeSnapshot = System.currentTimeMillis();
        public Shoot3Balls(Intake im, double ipow, FlyWheel fwm1, double fwvel, Transfer transfer, Telemetry telemetry) {
            this.intake = im;
            this.intakepower = ipow;
            this.flywheel = fwm1;
            this.fwvelocity = fwvel;
            this.transfer = transfer;
            this.telemetry = telemetry;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //FLYWHEEL ON
            flywheel.setVelocity(fwvelocity);
            timeSnapshot = System.currentTimeMillis();
            //telemetry.clear();
            while ((Math.abs(flywheel.getVelocity()) < (0.95*fwvelocity)) && (System.currentTimeMillis() < (timeSnapshot+3000))) {
                //Wait until we get to flywheel velocity!
                //telemetry.addData("Shooter Velocity", flywheel.getVelocity());
                //telemetry.addData("Shooter Power", flywheel.getPower());
                //telemetry.addData("Min Target Velocity", 0.95*fwvelocity);
                //telemetry.update();
            }
            intake.setPowerA(0); //STOP INTAKE
            transfer.setState(Transfer.States.SHOOTING); transfer.run(telemetry); //SHOOT BALL 1
            timeSnapshot = System.currentTimeMillis();
            while (System.currentTimeMillis() < (timeSnapshot+200)) {  }
            intake.setPowerA(intakepower/2); //START INTAKE
            timeSnapshot = System.currentTimeMillis();
            while (System.currentTimeMillis() < (timeSnapshot+400)) {  }
            intake.setPowerA(0);
            transfer.setState(Transfer.States.SHOOTING); transfer.run(telemetry); //SHOOT BALL 2
            timeSnapshot = System.currentTimeMillis();
            while (System.currentTimeMillis() < (timeSnapshot+200)) {  }
            intake.setPowerA(intakepower);
            timeSnapshot = System.currentTimeMillis();
            while (System.currentTimeMillis() < (timeSnapshot+400)) {  }

            transfer.setState(Transfer.States.SHOOTING); transfer.run(telemetry); //SHOOT BALL 3
            intake.setPowerA(0);
            timeSnapshot = System.currentTimeMillis();
            while (System.currentTimeMillis() < (timeSnapshot+100)) {  } //FLYWHEEL OFF
            flywheel.setVelocity(0); flywheel.setPower(0);

            return false;
        }
    }

    public static class IntakeAction implements Action {
        Intake intake;
        double power;
        public IntakeAction(Intake m, double pow) {
            this.intake = m;
            this.power = pow;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intake.setPowerA(power);
            return false;
        }
    }


    public static class FlyWheelAction implements Action {
        FlyWheel flywheel;
        double power;
        public FlyWheelAction(FlyWheel m, double pow) {
            this.flywheel = m;
            this.power = pow;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            flywheel.setVelocity(power);
            return false;
        }
    }

    public static class ShooterAction implements Action {
        Transfer transfer;
        Telemetry telemetry;
        public ShooterAction(Transfer transfer, Telemetry telemetry) {
            this.transfer = transfer;
            this.telemetry = telemetry;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            transfer.setState(Transfer.States.SHOOTING); transfer.run(telemetry);
            return false;
        }
    }

    public static class ShooterActionDOWN implements Action {
        Transfer transfer;
        Telemetry telemetry;
        public ShooterActionDOWN(Transfer transfer, Telemetry telemetry) {
            this.transfer = transfer;
            this.telemetry = telemetry;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            transfer.setState(Transfer.States.RESTING); transfer.run(telemetry);
            return false;
        }
    }

    public static class TurretCameraAction implements Action {
        Turret turret;
        Camera camera;
        //  long timeSnapshot = System.currentTimeMillis();
        double kP =-.05;
        double maxPower =.2;
        int maxLeft=667;
        int maxRight=-667;

        double CameraTime=300;

        double CameraAngleAdjust=0;

        Telemetry telemetry;

        public TurretCameraAction(Camera c, Turret turret, Telemetry telemetry,double CameraTime,double CameraAngleAdjust) {
            this.camera = c;
            this.turret = turret;
            this.telemetry = telemetry;
            this.CameraTime = CameraTime;
            this.CameraAngleAdjust = CameraAngleAdjust;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            camera.update(telemetry);
            long timeSnapshot = System.currentTimeMillis();
            while (System.currentTimeMillis() < (timeSnapshot+CameraTime)) {
                turret.run(camera.getXError()+CameraAngleAdjust);
                camera.update(telemetry);
            }
            turret.stop();
            return false;
        }
    }

    public static class TurretAction implements Action {
        Turret turret;
        int maxLeft=667;
        int maxRight=-667;
        int inputposition=0;

        Telemetry telemetry;


        public TurretAction(Turret turret, int position, Telemetry telemetry) {
            this.turret = turret;
            this.inputposition = position;
            this.telemetry = telemetry;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(inputposition < (maxLeft) && inputposition > (maxRight)) {
                turret.setPosition(inputposition);
            }
            return false;
        }
    }
}
