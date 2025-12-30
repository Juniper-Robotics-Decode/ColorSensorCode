package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class spindexMotorNormalizationEncoder extends LinearOpMode {
    private DcMotor spindexMotor;
    private RevColorSensorV3 colorSensor1;
    private RevColorSensorV3 colorSensor2;
    private RevColorSensorV3 colorSensor3;
    private RevTouchSensor ShootingTCS;
    private RevTouchSensor IntakingTCS;


    private final String greenStr = "Green";
    private final String purpleStr = "Purple";
    private final String emptyStr = "Empty";

    private boolean[] full = new boolean[3];
    private boolean[] green = new boolean[3];
    private boolean[] purple = new boolean[3];
    private String[] detectedMotif = new String[3];//color detection usage
    private double d1;
    private double d2;
    private double d3;
    int x = 0;
    int y = 0;
    int ball;
    int noBall;
    int currentIndex;
    int z;
    int fullRotation =538;
    int pocketRotation = 179;
    int intakingRotation = 179;

    @Override
    public void runOpMode() {
        colorSensor1 = hardwareMap.get(RevColorSensorV3.class, "colorSensor1");
        colorSensor2 = hardwareMap.get(RevColorSensorV3.class, "colorSensor2");
        colorSensor3 = hardwareMap.get(RevColorSensorV3.class, "colorSensor3");
        ShootingTCS = hardwareMap.get(RevTouchSensor.class, "ShootingTCS");
        IntakingTCS = hardwareMap.get(RevTouchSensor.class, "IntakingTCS");
        spindexMotor = hardwareMap.get(DcMotor.class, "spindexMotor");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        while(!ShootingTCS.isPressed()){
            spindexMotor.setPower(-0.16);
        }
        spindexMotor.setPower(0);
//default motor spin
        waitForStart();

        while (opModeIsActive()) {
            detectedMotif[0] = colorDetector(colorSensor1);
            detectedMotif[1] = colorDetector(colorSensor2);
            detectedMotif[2] = colorDetector(colorSensor3);


            d1 = colorSensor1.getDistance(DistanceUnit.MM);
            d2 = colorSensor2.getDistance(DistanceUnit.MM);
            d3 = colorSensor3.getDistance(DistanceUnit.MM);

            for (int i = 0; i < detectedMotif.length; i++) {
                if (detectedMotif[i].equals(greenStr)) {
                    full[i] = true;
                    green[i] = true;
                    purple[i] = false;
                    telemetry.addData("Slot" + i, "Green");
                } else if (detectedMotif[i].equals(purpleStr)) {
                    full[i] = true;
                    purple[i] = true;
                    green[i] = false;
                    telemetry.addData("Slot" + i, "Purple");
                } else {
                    full[i] = false;
                    green[i] = false;
                    purple[i] = false;
                    telemetry.addData("Slot" + i, "Empty");
                }
            }

            if (gamepad1.square) {
                boolean hasPurple = purple[0] || purple[1] || purple[2];
                if (!hasPurple) {
                    gamepad1.rumble(500);
                } else {
                    spindexShootingReset();
                    spindexMotor.setPower(1);
                    spindexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
            }
            if (gamepad1.circle) {
                boolean hasGreen = green[0] || green[1] || green[2];
                if (!hasGreen) {
                    gamepad1.rumble(500);
                } else {
                    spindexShootingReset();
                    spindexMotor.setPower(1);
                    spindexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
            }
            if (gamepad1.triangle) {
                if (!full[0] && !full[1] && !full[2]) {
                    gamepad1.rumble(500);
                } else {
                    spindexShootingReset();
                    spindexMotor.setPower(1);
                    spindexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
            }
            if (gamepad1.x){
                spindexMotor.setPower(0);
                spindexMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                spindexIntakingReset_Movement();
            }
            //This if loop is only a placeholder for now to switch to intake mode, when programms
            // are merged, spindex would switch automatically to intake mode after a ball is shot
            for (int j = 0; j < detectedMotif.length; j++) {
                if ((detectedMotif[j] == greenStr) || (detectedMotif[j] == purpleStr)) {
                    ball = j;
                } else {
                    noBall = j;
                }
            }


            int a = Math.abs(spindexMotor.getCurrentPosition());
            int relativePositionInRotation = a % fullRotation;

            if (relativePositionInRotation < pocketRotation) {
                currentIndex = 1;
            } else if (relativePositionInRotation < pocketRotation * 2) {
                currentIndex = 2;
            } else {
                currentIndex = 3;
            }

            x = currentIndex - ball;
            y = currentIndex - noBall;

            if (x>y) { //Counter-Clockwise/left
                z = (y * pocketRotation);
                spindexMotor.setTargetPosition(z);
            }
            if (y>x) { //Clocwpresise/Right
                z = (x * pocketRotation);
                spindexMotor.setTargetPosition(z);
            }
            if (x==y){ //Counter-Clockwise/left
                z = (y * pocketRotation);
                spindexMotor.setTargetPosition(z);
            }
//X>Y then spindex moves Y # of pockets in the direction of what the sign is of the y, so if -1 then CC, if 1 then C
//X<Y then spindex moves X# of pockets and then the same as above
/*
If (x>y){
    z=(y*pocketRotation) ->pocketRotation==538 which is # of ticks per pocketRotation
   z is how many ticks to move
}
Ex: y=-1 -> move -538 ticks
 */
            telemetry.addData("purple[0]", purple[0]);
            telemetry.addData("purple[1]", purple[1]);
            telemetry.addData("purple[2]", purple[2]);
            telemetry.addData("green[0]", green[0]);
            telemetry.addData("green[1]", green[1]);
            telemetry.addData("green[2]", green[2]);
            telemetry.addData("X", x);
            telemetry.addData("Y", y);
            telemetry.addData("ShootingTCS", ShootingTCS.isPressed());
            telemetry.addData("DistanceCS1", d1);
            telemetry.addData("DistanceCS2", d2);
            telemetry.addData("DistanceCS3", d3);


            telemetry.update();
        }

    }
        public String colorDetector (RevColorSensorV3 cs){
            int blue = cs.blue();
            int green = cs.green();
            int red = cs.red();
            double dREAD = cs.getDistance(DistanceUnit.MM);

            if (dREAD <= 85) {
                if (green > red && green > blue) {
                    return greenStr;

                } else if (blue > red && blue > green) {
                    return purpleStr;
                }
            } else {
                return emptyStr;
            }
            return "";
        }

    public void spindexShootingReset() {
        while (!ShootingTCS.isPressed()) {
            spindexMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            spindexMotor.setPower(1);
        }
        spindexMotor.setPower(0);
        spindexMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spindexMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void spindexIntakingReset_Movement() {
        //bassically this would reset to intaking mode so it would move in intaking thirds
        //then it will move 1/3 then stop for 1 it second to let ball in then move, it will keep
        // going on and on and on
        while (!IntakingTCS.isPressed()) {
            spindexMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            spindexMotor.setPower(1);
        }
        spindexMotor.setPower(0);
        spindexMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spindexMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int l=1;
        while (!gamepad1.circle || !gamepad1.triangle || !gamepad1.square) {
            while (l>0) {
                spindexMotor.setTargetPosition(intakingRotation * l);
                spindexMotor.setPower(1);
                spindexMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sleep(3000);
            }
        }
    }

}


// Square = Shoot purple
// Circle = Shoot green
// Triangle = Shoot any
//TCS = TouchSensor
//TCS 1 = pocket1
//TCS 2 = pocket
//TCS 3 = pocket3

//Pocket 1=0, 2=1, 3=2
