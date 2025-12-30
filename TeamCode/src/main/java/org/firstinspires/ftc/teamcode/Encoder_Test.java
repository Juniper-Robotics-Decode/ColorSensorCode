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

        EncoderTest.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        EncoderTest.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        EncoderTest.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        int target = 538;

        telemetry.addData("Status", "Init & Ready");
        telemetry.update();

        waitForStart();

        EncoderTest.setDirection(DcMotor.Direction.REVERSE);

        while (opModeIsActive()) {
            int currentPos = EncoderTest.getCurrentPosition();

            if (Math.abs(currentPos) < target) {
                EncoderTest.setPower(0.25);
                telemetry.addData("Status", "Moving");
            } else {
                EncoderTest.setPower(0);
                telemetry.addData("Status", "Rotation Complete");
            }

            telemetry.addData("Target:", target);
            telemetry.addData("Current Position:", currentPos);
            telemetry.addData("Power", EncoderTest.getPower());
            telemetry.update();
        }
    }
}
