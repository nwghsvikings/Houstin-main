package org.firstinspires.ftc.teamcode.Autonomous;
// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
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

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.AutoActions;
import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.TeamColor;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

@Config
@Autonomous(name = "DECODE_BLUE_9_BALL_HUMAN_PARK_FAR", group = "Robot")
public class DECODE_BLUE_FAR_AUTO_9_BALL_HUMAN_PLAYER_ZONEPARK extends LinearOpMode {
    public static TeamColor.Colors GOAL = TeamColor.Colors.BLUE;
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

        Pose2d initialPose = new Pose2d(60.4, 14.75*R, Math.toRadians(FieldAngle(90))); //STARTING SPOT
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        FlyWheel flywheel = new FlyWheel(hardwareMap);
        Turret turret = new Turret(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        TeamColor teamColor = new TeamColor();
        teamColor.setColor(GOAL);
        Camera camera = new Camera(hardwareMap,teamColor.getColor());
        turret.setPosition(600*R); //SET INITIAL POSITION ON INITIALIZATION

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






//*/
        TrajectoryActionBuilder TurretStartingPosition = InitializeWorld.endTrajectory().fresh()
                .waitSeconds(0.1) //Won't do turret if this time delay isn't there with auto timer enabled, which is required for the competition
                .stopAndAdd(new AutoActions.TurretAction(turret,600*R, telemetry));

        TrajectoryActionBuilder Shoot3 = TurretStartingPosition.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.Shoot3Balls(intake,1,flywheel,1750,transfer, telemetry)); //SHOOT 3 BALLS

        TrajectoryActionBuilder Intake6 = Shoot3.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.8)) //INTAKE POWER ON
                .strafeToLinearHeading(new Vector2d(34.7,27*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(34.7,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80));

        TrajectoryActionBuilder MoveToShoot6 = Intake6.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.4)) //INTAKE POWER ON
                .strafeToLinearHeading(new Vector2d(60.4,14.75*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80));

        TrajectoryActionBuilder Shoot6 = MoveToShoot6.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.Shoot3Balls(intake,1,flywheel,1750,transfer, telemetry)); //SHOOT 3 BALLS

        TrajectoryActionBuilder Intake9 = Shoot6.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.8)) //INTAKE POWER ON
                .strafeToLinearHeading(new Vector2d(60,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(50), new ProfileAccelConstraint(-50, 50))
                .strafeToLinearHeading(new Vector2d(60,45*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(50), new ProfileAccelConstraint(-50, 50))
                .strafeToLinearHeading(new Vector2d(57,57*R), Math.toRadians(FieldAngle(60)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-50, 50))
                .strafeToLinearHeading(new Vector2d(60,45*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(50), new ProfileAccelConstraint(-50, 50))
                .strafeToLinearHeading(new Vector2d(60,58*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-50, 50));

        TrajectoryActionBuilder MoveToShoot9 = Intake9.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.4)) //INTAKE POWER OFF
                .strafeToLinearHeading(new Vector2d(60.4,14.75*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80));

        TrajectoryActionBuilder Shoot9 = MoveToShoot9.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.Shoot3Balls(intake,1,flywheel,1750,transfer, telemetry)); //SHOOT 3 BALLS

        TrajectoryActionBuilder TurretEndingPosition = Shoot9.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.TurretAction(turret,0, telemetry));

        TrajectoryActionBuilder Park = TurretEndingPosition.endTrajectory().fresh()
                .stopAndAdd(new AutoActions.IntakeAction(intake,.8)) //INTAKE POWER ON
                .strafeToLinearHeading(new Vector2d(35,40*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(100), new ProfileAccelConstraint(-100,100));
        //BUILD ALL ACTIONS - PUT TRAJECTORY HERE WITH AN "A" AT THE END
        Action InitializeWorldA = InitializeWorld.build();
        Action TurretStartingPositionA = TurretStartingPosition.build();
        Action MoveToShoot6A = MoveToShoot6.build();
        Action MoveToShoot9A = MoveToShoot9.build();
        Action Shoot3A = Shoot3.build();
        Action Intake6A = Intake6.build();
        Action Shoot6A = Shoot6.build();
        Action Intake9A = Intake9.build();
        Action Shoot9A = Shoot9.build();
        Action ParkA = Park.build();
        Action TurretEndingPositionA = TurretEndingPosition.build();
        Action TurretCameraAction = new AutoActions.TurretCameraAction(camera,turret, telemetry,500,3*R);


        waitForStart();

        if (isStopRequested()) return;
        // (new AutoActions.IntakeAction(intake,0.8)), //INTAKE POWER ON
        Actions.runBlocking(
                new SequentialAction( //PUT ALL ACTIONS BELOW
                        new ParallelAction(
                            InitializeWorldA,
                            TurretStartingPositionA
                        ),
                        new ParallelAction(
                                TurretCameraAction, //CAMERA CORRECT TURRET
                                Shoot3A
                        ),
                        Intake6A,
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
                        ParkA

                )
        );

        //*********************************************************
        //END OF TEAM 9990 AUTO ACTIONS
        //**********************************************************
    }
}
