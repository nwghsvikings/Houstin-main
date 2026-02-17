package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.LEDs;
import org.firstinspires.ftc.teamcode.Subsystems.TeamColor;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;


@TeleOp
public class SubsystemBased extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        waitForStart();
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        FlyWheel flyWheel = new FlyWheel(hardwareMap);
        Turret turret = new Turret(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        TeamColor teamColor = new TeamColor();
        Camera camera = new Camera(hardwareMap,teamColor.getColor());
        ColorSensors colorsensors = new ColorSensors(hardwareMap);
        LEDs leds = new LEDs(hardwareMap);
        double CameraAngleAdjust = 0;
        if (isStopRequested()) return;
        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();
        boolean ai = true;
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double rx = gamepad1.right_stick_x * 1.1; // Counteract imperfect strafing
            boolean RB = gamepad1.right_bumper && !previousGamepad1.right_bumper;
            boolean LB = gamepad1.left_bumper && !previousGamepad1.left_bumper;
            boolean RT = gamepad1.right_trigger > 0.1 && previousGamepad1.right_trigger < 0.1;
            boolean LT = gamepad1.left_trigger > 0.1 && previousGamepad1.left_trigger < 0.1;
            boolean x = !previousGamepad1.xWasPressed() && gamepad1.xWasPressed();
            boolean back = gamepad1.back && !previousGamepad1.back;
            boolean b = gamepad1.b;
            boolean YB = gamepad1.y;
            boolean AB = gamepad1.a;
            boolean RSB = gamepad1.right_stick_button && !previousGamepad1.right_stick_button;
            boolean dpadup = gamepad1.dpad_up && previousGamepad1.dpad_up;
            boolean dpaddown = gamepad1.dpad_down && previousGamepad1.dpad_down;



            double y2 = -gamepad2.left_stick_y; // Remember, Y stick value is reversed
            double rx2 = gamepad2.right_stick_x * 1.1; // Counteract imperfect strafing
            boolean RB2 = gamepad2.right_bumper && !previousGamepad2.right_bumper;
            boolean LB2 = gamepad2.left_bumper && !previousGamepad2.left_bumper;
            boolean RT2 = gamepad2.right_trigger > 0.1 && previousGamepad2.right_trigger < 0.1;
            boolean LT2 = gamepad2.left_trigger > 0.1 && previousGamepad2.left_trigger < 0.1;
            boolean x2 = !previousGamepad2.xWasPressed() && gamepad2.xWasPressed();
            boolean back2 = gamepad2.back && !previousGamepad2.back;
            boolean b2 = gamepad2.b;
            boolean YB2 = gamepad2.y;
            boolean AB2 = gamepad2.a;
            boolean RSB2 = gamepad2.right_stick_button && !previousGamepad2.right_stick_button;
            boolean DL = gamepad1.dpad_left;


            /*
            ----GAMEPAD1----
            DPAD Up            = Adjust Camera Angle +5
            DPAD Down          = Adjust Camera Angle -5
            DPAD Left          = REV Locking
            DPAD Right         = N/A
            START              = N/A
            BACK               = Switch team color
            Left Button        = Flywheel ON/OFF
            Right Button       = Shoot Ball
            Y                  = Increase Flywheel Velocity
            A                  = Decrease Flywheel Velocity
            B                  = Intake Reverse - Outtake
            X                  = Camera Auto-aim ON/OFF
            Right Trigger      = Intake
            Left Trigger       = Slow down driving when held
            Left Stick Button  = N/A
            Right Stick Button = Move to Turret Position 0

            ----GAMEPAD2----
            DPAD Up            = Set Position 0 on Turret
            DPAD Down          = N/A
            DPAD Left          = Rotate Turret Left
            DPAD Right         = Rotate Turret Right
            START              = N/A
            BACK               = Lift/Block - put in stuck mode
            Left Button        =
            Right Button       =
            Y                  = Flywheel Far Goal (1750)
            A                  = Flywheel Close Goal (1450)
            B                  =
            X                  = Camera Auto-aim ON/OFF
            Right Trigger      =
            Left Trigger       =
            Left Stick Button  =
            Right Stick Button =


             */

            previousGamepad1.copy(gamepad1);
            if (back) {
                switch (teamColor.getColor()) {
                    case RED:
                        teamColor.setColor(TeamColor.Colors.BLUE);
                        break;
                    case BLUE:
                        teamColor.setColor(TeamColor.Colors.RED);
                        break;
                }
            }
            drivetrain.run(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 1 - (gamepad1.left_trigger / 2), DL);
            if (LB) {
                switch (flyWheel.getState()) {

                    case ON:
                        flyWheel.setState(FlyWheel.States.OFF);
                        break;
                    case OFF:
                        flyWheel.setState(FlyWheel.States.ON);
                        break;
                }
            }

            //INCREASE FLYWHEEL VELOCITY
            if (YB && !previousGamepad1.aWasPressed()) {
                flyWheel.add();
            }

            //DECREASE FLYWHEEL VELOCITY
            if (AB && !previousGamepad1.aWasPressed()) {
                flyWheel.sub();
            }

            //SHOOT BALL ONLY IF FLYWHEEL ON AND BALL DETECTED IN SHOOTER
            if (RB || (gamepad1.right_trigger > 0.1 && colorsensors.BallDetected() && flyWheel.getState() == FlyWheel.States.ON)) {
                transfer.setState(Transfer.States.SHOOTING);
            }

            if ((transfer.getStates() != Transfer.States.SHOOTING) & !b) { //INTAKE WORKS IF NOT SHOOTING
                intake.setPower(gamepad1.right_trigger);
            } else if (b & gamepad1.right_trigger < 0.1) {
                intake.setPower(-1.0);
            } else {
                intake.setPower(0);
            }

            if (x || x2) { //TOGGLE AUTO-AIM - BOTH CONTROLLERS
                ai = !ai;
            }
            if(ai && !RSB && !turret.BusySettingPosition)
            {
                turret.run(camera.getXError()+CameraAngleAdjust);
            }
            else if (RSB && !turret.BusySettingPosition) {
                ai = false; //turn off auto-aim
                turret.setPosition2(0);
            }
            else if(!ai && gamepad2.dpad_right && !turret.BusySettingPosition) {
                turret.setPower(-0.4);
            }
            else if(!ai && gamepad2.dpad_left && !turret.BusySettingPosition) {
                turret.setPower(0.4);
            }
            else {
                turret.run(0);
            }

            //MANUAL CAMERA ANGLE ADJUSTMENTS - MOST LIKELY NOT NEEDED
            if(dpadup) {
                CameraAngleAdjust += 1;
            }
            if(dpaddown) {
                CameraAngleAdjust -= 1;
            }


            if(!turret.BusySettingPosition && turret.ReactiveAutoAim) {
                ai = true; //reactive auto-aim after turret moves back to center
                turret.ReactiveAutoAim = false;
            }

            //GAMEPAD 2 MORE
            if(!ai) { //DANGEROUS MOVE - MAKE SURE TURRET IS FACING PERFECTLY FORWARD BEFORE ACTIVATING
                if (gamepad2.dpad_up) {
                    turret.stop();
                    turret.reset();
                } //RESET TURRET TO POSITION 0
            }
            if(YB2) { //FAR GOAL VELOCITY - TESTED VALUE - ADJUST CAMERA OFFSET
                flyWheel.setMaxVelocity(1800, DL);
                if(teamColor.getColor() == TeamColor.Colors.BLUE) {
                    CameraAngleAdjust = -4;
                }
                else if (teamColor.getColor() == TeamColor.Colors.RED){
                    CameraAngleAdjust = 3;
                }
            }
            if(AB2) { //CLOSE GOAL VELOCITY - TESTED VALUE
                flyWheel.setMaxVelocity(1550);
                CameraAngleAdjust = 0;
            }
            if(back2 && (transfer.getStates() != Transfer.States.STUCK)) {
                transfer.setState(Transfer.States.STUCK); //RESET STUCK BALL WHEN LIFT SERVO TRAPS BALL BELOW IT
            } //THIS IS ONLY ON GAMEPAD2

            //LEDS
            if((flyWheel.getVelocity() >= (flyWheel.getMaxVelocity()*0.5)) && (flyWheel.getVelocity() > 100)) {
                leds.GREEN();
            }
            else if ((flyWheel.getVelocity() < (flyWheel.getMaxVelocity()*0.5)) && (flyWheel.getVelocity() > 100)) {
                leds.AMBER();
            }
            else {
                leds.RED();
            }

            flyWheel.run();
            camera.update(telemetry);
            camera.setColor(teamColor.getColor());
            turret.status(telemetry);
            intake.run();
            transfer.run(telemetry);
            teamColor.status(telemetry);
            flyWheel.status(telemetry);
            colorsensors.status(telemetry);
            telemetry.addData("Ball Detected:", colorsensors.BallDetected());
            telemetry.addData("Auto-Aim",ai);
            drivetrain.status(telemetry);
            telemetry.addData("Camera Adjust Angle:", CameraAngleAdjust);
            telemetry.update();
        }
    }
}