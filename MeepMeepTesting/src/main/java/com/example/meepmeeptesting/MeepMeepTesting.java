package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.jetbrains.annotations.NotNull;

public class MeepMeepTesting {
    public static String GOAL = "BLUE"; //CHANGE TO RED OR BLUE FOR GOAL

    public static int FieldAngle(int angle) {
        int flipped = 0;
        if(angle < 180) { flipped = angle + 180; }
        if(angle >= 180) { flipped = angle - 180;}
        if(GOAL.equals("RED")) {flipped = angle;}
        return flipped;
    }
    public static int R = 1;

    public static void main(String[] args) {
        if(GOAL.equals("BLUE")) {R=-1;}
        if(GOAL.equals("RED")) {R=1;} //DESIGN EVERYTHING IN RED FIRST


        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .build();
//##########################################################################################
//RED FAR BALL AUTO: 9 ball auto
//##########################################################################################
        /*
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(60.4, 14.75*R, Math.toRadians(FieldAngle(90))))
                .waitSeconds(4)
                .strafeToLinearHeading(new Vector2d(34.7,27*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(34.7,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                .strafeToLinearHeading(new Vector2d(60.4,14.75*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                .waitSeconds(4)
                .strafeToLinearHeading(new Vector2d(11.7,23.5*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(11.7,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                .strafeToLinearHeading(new Vector2d(60.4,14.75*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                .waitSeconds(4)
                .strafeToLinearHeading(new Vector2d(60.4,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(100), new ProfileAccelConstraint(-100, 100))
                .strafeToLinearHeading(new Vector2d(60.4,45*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(60.4,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(60.4,14.75*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                .strafeToLinearHeading(new Vector2d(45,30*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
//*/
//##########################################################################################
//RED FAR BALL AUTO: 12 BALL HUMAN PLAYER ZONE
//##########################################################################################
               //*
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(60.4, 14.75*R, Math.toRadians(FieldAngle(90))))
                .waitSeconds(1)
             //   .strafeToLinearHeading(new Vector2d(34.7,27*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
               // .strafeToLinearHeading(new Vector2d(34.7,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                //.strafeToLinearHeading(new Vector2d(60.4,14.75*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(56,59*R), Math.toRadians(FieldAngle(80)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-60, 90))
                .strafeToLinearHeading(new Vector2d(58,59*R), Math.toRadians(FieldAngle(80)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(60.4,55*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .setTangent(Math.toRadians(FieldAngle(80)))
                .splineToLinearHeading(new Pose2d(57, 55*R, Math.toRadians(FieldAngle(90))), Math.toRadians(270))
                .setTangent(Math.toRadians(FieldAngle(0)))
                .splineToLinearHeading(new Pose2d(60, 60*R, Math.toRadians(FieldAngle(90))), Math.toRadians(90))

              //  .strafeToLinearHeading(new Vector2d(60.4,60.4*R), Math.toRadians(FieldAngle(100)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
               // .strafeToLinearHeading(new Vector2d(60.4,50*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
              //  .strafeToLinearHeading(new Vector2d(60.4,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(60.4,25*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(60), new ProfileAccelConstraint(-60, 60))
                .waitSeconds(4)
                .strafeToLinearHeading(new Vector2d(60.4,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(60.4,45*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(60.4,60.4*R), Math.toRadians(FieldAngle(100)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(60.4,50*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(60.4,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(60.4,25*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(60), new ProfileAccelConstraint(-60, 60))
                .waitSeconds(4)
                .strafeToLinearHeading(new Vector2d(50,30*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
//*/
//##########################################################################################
//RED CLOSE AUTO: 3 BALL + GATE + 3 BAlL + GATE + 3 BALL + PARK NEAR GATE
//##########################################################################################

        /*
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-60.4, 37.9*R, Math.toRadians(90+A)))
                .strafeToLinearHeading(new Vector2d(-11.7,23.5*R), Math.toRadians(90+A2))
                .waitSeconds(3.5)
                .strafeToLinearHeading(new Vector2d(-11.7,53*R), Math.toRadians(90+A2))
                .strafeToLinearHeading(new Vector2d(-11.7,45*R), Math.toRadians(180+A2))
                .strafeToLinearHeading(new Vector2d(0,51*R), Math.toRadians(180+A2))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-11.7,23.5*R), Math.toRadians(90+A2))
                .waitSeconds(3.5)
                .strafeToLinearHeading(new Vector2d(11.7,23.5*R), Math.toRadians(90+A2))
                .strafeToLinearHeading(new Vector2d(11.7,60.4*R), Math.toRadians(90+A2))
                .strafeToLinearHeading(new Vector2d(11.7,45*R), Math.toRadians(0+A2))
                .strafeToLinearHeading(new Vector2d(0,51*R), Math.toRadians(0+A2))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-11.7,23.5*R), Math.toRadians(90+A2))
                .waitSeconds(3.5)
                .strafeToLinearHeading(new Vector2d(0,40*R), Math.toRadians(-90+A2))
               // .strafeToLinearHeading(new Vector2d(10,23.5*R), Math.toRadians(90+A2))*/

//##########################################################################################
//RED CLOSE AUTO: 3 BALL + GATE + 6 BAlL + PARK NEAR GATE
//##########################################################################################
        /*
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-60.4, 37.9*R, Math.toRadians(FieldAngle(90))))
                .strafeToLinearHeading(new Vector2d(-11.7,23.5*R), Math.toRadians(FieldAngle(90)))
                .waitSeconds(3.5)
                .strafeToLinearHeading(new Vector2d(-11.7,53*R), Math.toRadians(FieldAngle(90)))
                .strafeToLinearHeading(new Vector2d(-11.7,45*R), Math.toRadians(FieldAngle(180)))
                .strafeToLinearHeading(new Vector2d(0,51*R), Math.toRadians(FieldAngle(180)))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-11.7,23.5*R), Math.toRadians(FieldAngle(90)))
                .waitSeconds(3.5)
                .strafeToLinearHeading(new Vector2d(11.7,23.5*R), Math.toRadians(FieldAngle(90)))
                .strafeToLinearHeading(new Vector2d(11.7,60.4*R), Math.toRadians(FieldAngle(90)))
                .setTangent(Math.toRadians(FieldAngle(-90)))
                .splineToLinearHeading(new Pose2d(-11.7, 23.5*R, Math.toRadians(FieldAngle(90))), Math.toRadians(180))
                .waitSeconds(3.5)
                .strafeToLinearHeading(new Vector2d(0,40*R), Math.toRadians(FieldAngle(-90)))//*/

//##########################################################################################
// RED CLOSE AUTO: 12 BALL + PARK
       //* ##########################################################################################
/*
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-60.4, 37.9*R, Math.toRadians(FieldAngle(90))))
                .strafeToLinearHeading(new Vector2d(-11.7,23.5*R),Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(90), new ProfileAccelConstraint(-80, 80))
                .waitSeconds(3.5) //SHOOT 3 BALLS
                .strafeToLinearHeading(new Vector2d(-11.7,53*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                .lineToY(23.5)
                .waitSeconds(3.5) //SHOOT 3 BALLS
                .strafeToLinearHeading(new Vector2d(11.7,23.5*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(11.7,60.4*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(FieldAngle(270)))
                .splineToLinearHeading(new Pose2d(-11.7, 23.5*R, Math.toRadians(FieldAngle(90))), Math.toRadians(180))
                .waitSeconds(3.5) //SHOOT 3 BALLS
                .strafeToLinearHeading(new Vector2d(34,27*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(34,62*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-80, 80))
                //.strafeToLinearHeading(new Vector2d(-11.7,23.5*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-90, 90))
                .strafeToLinearHeading(new Vector2d(-15,20*R), Math.toRadians(FieldAngle(90)), new TranslationalVelConstraint(70), new ProfileAccelConstraint(-70, 70))
                .waitSeconds(3.5)
                .strafeToLinearHeading(new Vector2d(-5,30*R), Math.toRadians((FieldAngle(90))), new TranslationalVelConstraint(300), new ProfileAccelConstraint(-300, 300))
              //  .strafeToLinearHeading(new Vector2d(0,40*R), Math.toRadians((FieldAngle(-90))), new TranslationalVelConstraint(300), new ProfileAccelConstraint(-300, 300))
//*/


                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(false)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}