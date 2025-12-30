package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp
public class colorIdentifier extends LinearOpMode {
    private RevColorSensorV3 colorSensor1;
    private RevColorSensorV3 colorSensor2;
    private RevColorSensorV3 colorSensor3;



    @Override
    public void runOpMode() {
        colorSensor1 = hardwareMap.get(RevColorSensorV3.class, "colorSensor1");
        colorSensor2 = hardwareMap.get(RevColorSensorV3.class, "colorSensor2");
        colorSensor3 = hardwareMap.get(RevColorSensorV3.class, "colorSensor3");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        while (opModeIsActive()) {
            int red1 = colorSensor1.red();
            int blue1 = colorSensor1.blue();
            int green1 = colorSensor1.green();
            double distance1 = colorSensor1.getDistance(DistanceUnit.MM);
            int red2 = colorSensor2.red();
            int blue2 = colorSensor2.blue();
            int green2 = colorSensor2.green();
            double distance2 = colorSensor2.getDistance(DistanceUnit.MM);
            int red3 = colorSensor3.red();
            int blue3 = colorSensor3.blue();
            int green3 = colorSensor3.green();
            double distance3 = colorSensor3.getDistance(DistanceUnit.MM);

            if (distance1 > 85) {
                telemetry.addData("Slot1(cs1): ", "Empty");
            } else if (green1 > red1 && green1 > blue1) {
                telemetry.addData("Slot1(cs1): ", "Green");
            } else if (blue1 > red1 && blue1 > green1) {
                telemetry.addData("Slot1(cs1): ", "Purple");
            }

            if (distance2 > 85) {
                telemetry.addData("Slot2(cs2): ", "Empty");
            } else if (green2 > red2 && green2 > blue2) {
                telemetry.addData("Slot2(cs2): ", "Green");
            } else if (blue2 > red2 && blue2 > green2) {
                telemetry.addData("Slot2(cs2): ", "Purple");
            }

            if (distance3 > 85) {
                telemetry.addData("Slot3(cs3): ", "Empty");
            } else if (green3 > red3 && green3 > blue3) {
                telemetry.addData("Slot3(cs3): ", "Green");
            } else if (blue3 > red3 && blue3 > green3) {
                telemetry.addData("Slot3(cs3): ", "Purple");
            }

            telemetry.addData("distance1", distance1);
            telemetry.addData("distance2", distance2);
            telemetry.addData("distance3", distance3);
            telemetry.update();
        }
    }
}
/*
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Encoder_Test extends LinearOpMode {

    public DcMotor EncoderTest;

    @Override
    public void runOpMode() {
        EncoderTest = hardwareMap.get(DcMotor.class, "EM");

        int target = 538;
        int currentPos = EncoderTest.getCurrentPosition();

        EncoderTest.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        EncoderTest.setTargetPosition(target);

        telemetry.addData("Status", "Init & Ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            EncoderTest.getCurrentPosition();
            EncoderTest.setDirection(DcMotor.Direction.REVERSE);
            EncoderTest.setPower(0.25);
            if(currentPos != target) {
                EncoderTest.setDirection(DcMotor.Direction.REVERSE);
                EncoderTest.setPower(0.25);
                telemetry.addData("Target:", target);
                telemetry.addData("Power", EncoderTest.getPower());
                telemetry.addData("Current Position:", EncoderTest.getCurrentPosition());
                telemetry.update();
            } else {
                EncoderTest.setPower(0);
                EncoderTest.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                telemetry.addData("Status", "Rotation Complete");
                telemetry.update();
            }
        }


    }
}
*/