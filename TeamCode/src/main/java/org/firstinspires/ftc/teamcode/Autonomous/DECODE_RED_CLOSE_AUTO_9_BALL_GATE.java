package org.firstinspires.ftc.teamcode.Autonomous;
// RR-specific imports

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.TeamColor;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.AutoActions;

@Config
@Autonomous(name = "DECODE_RED_9_BALL_GATE_PARK_CLOSE", group = "Robot")
public class DECODE_RED_CLOSE_AUTO_9_BALL_GATE extends LinearOpMode {
    public static TeamColor.Colors GOAL = TeamColor.Colors.RED;
    public static int FieldAngle(int angle) {
        int flipped = 0;
        if(angle < 180) { flipped = angle + 180; }
        if(angle >= 180) { flipped = angle - 180;}
        if(GOAL == TeamColor.Colors.RED) {flipped = angle;}
        return flipped;
    }
    public static int R = 1;

    public static int TurretPosition = 0;

    @Override
    public void runOpMode() {
        if(GOAL == TeamColor.Colors.BLUE) {R=-1;}
        if(GOAL == TeamColor.Colors.RED) {R=1;} //DESIGN EVERYTHING IN RED FIRST

        Pose2d initialPose = new Pose2d(-58, 37.9*R, Math.toRadians(FieldAngle(90))); //STARTING SPOT
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        FlyWheel flywheel = new FlyWheel(hardwareMap);
        Turret turret = new Turret(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        TeamColor teamColor = new TeamColor();
        teamColor.setColor(GOAL);
        Camera camera = new Camera(hardwareMap,teamColor.getColor());
        turret.setPosition(400*R); //SET INITIAL POSITION ON INITIALIZATION


        //THIS STAYS HERE ALWAYS, DO NOT REMOVE

        TrajectoryActionBuilder InitializeWorld = drive.actionBuilder(initialPose)
                .stopAndAdd(new AutoActions.ShooterActionDOWN(transfer, telemetry)); //SERVOS IN DEFAULT POSITION - STARTING

        //**********************************************************
        //CODE ACTIONS START HERE - PLACE TEAM 9990 AUTO LOGIC HERE - LOGIC FOR AUTO LEFT
        //
        // YOU MAY ALSO GO BACK AND CHANGE THE INITIAL POSE POSITION A FEW ROWS BACK
        // STEP1: TUNE ROBOT PER ROADRUNNER TUNING FOR MECANUM DRIVE 3 DEAD WHEELS IF HARDWARE WAS CHANGED
        // STEP2: CHANGE INITIAL POSE POSITION (RUN MEEPMEEP, ROBOT STARTS IN CENTER OF FIELD)
        // STEP3: MODIFY TRAJECTORYACTIONBUILDER (TAB) SECTIONS. EACH ONE HAS A UNIQUE NAME
        //        THIS IS A NAME YOU COME UP WITH. EACH TAB IS SET EQUAL TO THE PREVIOUS
        //        TAB. NON-MOVEMENT ACTIONS MUST COME BEFORE MOVEMENTS. IF A NON-MOVEMENT
        //        SUCH AS CLAW OR ARM NEEDS TO HAPPEN, IT NEEDS TO START A NEW TAB IF IT NEEDS
        //        TO OCCUR AFTER AN ACTION. LAST ACTION IN EACH TAB GETS A SEMI-COLON, ALL OTHERS
        //        DO NOT.
        // STEP4: BUILD ALL ACTIONS
        // STEP5: PUT ALL SECTIONS IN RUNBLOCKING (COMMAS AFTER ALL EXCEPT LAST)
        // NOTE: MEEPMEEP IS TO TEST MOVEMENT, NOT OTHER ITEMS SUCH AS CLAW/ARM.
        //**********************************************************
        /*
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-60.4, 37.9*R, Math.toRadians(FieldAngle(90))))
                .strafeToLinearHeading(new Vector2d(11.7,23.5*R), Math.toRadians(FieldAngle(90)))
                .strafeToLinearHeading(new Vector2d(11.7,60.4*R), Math.toRadians(FieldAngle(90)))
                .setTangent(Math.toRadians(FieldAngle(-90)))
                .splineToLinearHeading(new Pose2d(-11.7, 23.5*R, Math.toRadians(FieldAngle(90))), Math.toRadians(180))
                .waitSeconds(3.5)
                .strafeToLinearHeading(new Vector2d(0,40*R), Math.toRadians(FieldAngle(-90)))
*/
        TrajectoryActionBuilder TurretStartingPosition = InitializeWorld.endTrajectory().fresh()
                .waitSeconds(0.1) //Won't do turret if this time delay isn't there with auto timer enabled, which is required for the competition
                .stopAndAdd(new AutoActions.TurretAction(turret,400*R, telemetry));

        TrajectoryActionBuilder MoveToShoot3 = TurretStartingPosition.endTrajectory().fresh()
                .setTangent(Math.toRadians(FieldAngle(270)))
                .stopAndAdd(new AutoActions.FlyWheelAction(flywheel,1450))
                .splineToLinearHeading(new Pose2d(-11.7, 23.5*R, Math.toRadians(FieldAngle(90))), Math.toRadians(0));
                //.strafeToLinearHeading(new Vector2d(-11.7,23.5*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(60), new ProfileAccelConstraint(-60, 60));

        TrajectoryActionBuilder Shoot3 = MoveToShoot3.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.Shoot3Balls(intake,.8,flywheel,1450,transfer, telemetry)); //SHOOT 3 BALLS

        TrajectoryActionBuilder Intake6 = Shoot3.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.8)) //INTAKE POWER ON
                .strafeToLinearHeading(new Vector2d(-11.7,53*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(60), new ProfileAccelConstraint(-60, 60));

        TrajectoryActionBuilder Gate6 = Intake6.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.3)) //INTAKE POWER ON
                .strafeToLinearHeading(new Vector2d(-11.7,45*R), Math.toRadians(FieldAngle(90)))
                .strafeToLinearHeading(new Vector2d(-3,59*R), Math.toRadians(FieldAngle(90)))
                .waitSeconds(2);

        TrajectoryActionBuilder MoveToShoot6 = Gate6.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.3)) //INTAKE POWER OFF
                .stopAndAdd(new AutoActions.FlyWheelAction(flywheel,1450))
                .strafeToLinearHeading(new Vector2d(-11.7,23.5*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(60), new ProfileAccelConstraint(-60, 60));

        TrajectoryActionBuilder Shoot6 = MoveToShoot6.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.Shoot3Balls(intake,.8,flywheel,1450,transfer, telemetry)); //SHOOT 3 BALLS

        TrajectoryActionBuilder Intake9 = Shoot6.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.8)) //INTAKE POWER ON
                .strafeToLinearHeading(new Vector2d(11.7,23.5*R), Math.toRadians(FieldAngle(100)))
                .strafeToLinearHeading(new Vector2d(11.7,60.4*R), Math.toRadians(FieldAngle(100)), new TranslationalVelConstraint(60), new ProfileAccelConstraint(-60, 60));

        TrajectoryActionBuilder MoveToShoot9 = Intake9.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.3)) //INTAKE POWER OFF
                .stopAndAdd(new AutoActions.FlyWheelAction(flywheel,1450))
                .setTangent(Math.toRadians(FieldAngle(270)))
                .splineToLinearHeading(new Pose2d(-11.7, 23.5*R, Math.toRadians(FieldAngle(90))), Math.toRadians(180));

        TrajectoryActionBuilder Shoot9 = MoveToShoot9.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.Shoot3Balls(intake,.8,flywheel,1450,transfer, telemetry)); //SHOOT 3 BALLS

        TrajectoryActionBuilder TurretEndingPosition = Shoot9.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.TurretAction(turret,0, telemetry));

        TrajectoryActionBuilder ParkNearGate = TurretEndingPosition.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.3)) //INTAKE POWER ON
                .strafeToLinearHeading(new Vector2d(0,40*R), Math.toRadians(FieldAngle(-90))); //PARK

        //BUILD ALL ACTIONS - PUT TRAJECTORY HERE WITH AN "A" AT THE END
        Action InitializeWorldA = InitializeWorld.build();
        Action TurretStartingPositionA = TurretStartingPosition.build();
        Action MoveToShoot3A = MoveToShoot3.build();
        Action MoveToShoot6A = MoveToShoot6.build();
        Action MoveToShoot9A = MoveToShoot9.build();
        Action Shoot3A = Shoot3.build();
        Action Intake6A = Intake6.build();
        Action Gate6A = Gate6.build();
        Action Shoot6A = Shoot6.build();
        Action Intake9A = Intake9.build();
        Action Shoot9A = Shoot9.build();
        Action ParkNearGateA = ParkNearGate.build();
        Action TurretEndingPositionA = TurretEndingPosition.build();
        Action TurretCameraAction = new AutoActions.TurretCameraAction(camera,turret, telemetry,600,0);


        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction( //PUT ALL ACTIONS BELOW
                        new ParallelAction(
                            InitializeWorldA,
                            TurretStartingPositionA
                        ),
                        MoveToShoot3A,
                        new ParallelAction(
                                TurretCameraAction, //CAMERA CORRECT TURRET
                                Shoot3A
                        ),
                        Intake6A,
                        Gate6A,
                        MoveToShoot6A,
                        new ParallelAction(
                                TurretCameraAction, //CAMERA CORRECT TURRET
                                Shoot6A
                        ),
                        Intake9A,
                        MoveToShoot9A,
                        new ParallelAction(
                                TurretCameraAction, //CAMERA CORRECT TURRET
                                Shoot9A
                        ),
                        TurretEndingPositionA,
                        ParkNearGateA

                )
        );

        //*********************************************************
        //END OF TEAM 9990 AUTO ACTIONS
        //**********************************************************
    }
}
