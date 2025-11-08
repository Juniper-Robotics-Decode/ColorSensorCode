package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

@TeleOp
public class controllerHaptic extends LinearOpMode {

    Gamepad gamepad = gamepad1;

    @Override
    public void runOpMode() {
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.square) {
                gamepad1.rumble(100);
            }
            if (gamepad1.triangle){
                gamepad1.rumble(500);
            }
            if (gamepad1.circle) {
                gamepad1.rumble(1000);
            }
        }
    }
}

