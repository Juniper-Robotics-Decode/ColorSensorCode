package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class SpindexMotor extends LinearOpMode {

    private DcMotor SpindexMotor;
    private TouchSensor tcs1;
    //   private TouchSensor tcs2;
    //   private TouchSensor tcs3;
    private final int i = 1;


    @Override
    public void runOpMode() {
        SpindexMotor = hardwareMap.get(DcMotor.class, "SpindexMotor");
        tcs1 = hardwareMap.get(TouchSensor.class, "TCS1");
        // tcs2 = hardwareMap.get(TouchSensor.class, "TCS2");
        //  tcs3 = hardwareMap.get(TouchSensor.class, "TCS3");

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (opModeIsActive()) {

            if (tcs1.isPressed()) {
                SpindexMotor.setPower(0);
                telemetry.addData("TCS1 Pressed", tcs1.isPressed());
                telemetry.addData("Motor Power", SpindexMotor.getPower());
            } else {
                SpindexMotor.setPower(0.5);
                telemetry.addData("TCS1 Pressed", tcs1.isPressed());
                telemetry.addData("Motor Power", SpindexMotor.getPower());
            }
            telemetry.update();

        }
    }
}
