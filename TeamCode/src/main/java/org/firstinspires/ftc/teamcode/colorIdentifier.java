package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp
public class colorIdentifier extends LinearOpMode{
    private RevColorSensorV3 colorSensor1;


    @Override
    public void runOpMode() {
        colorSensor1 = hardwareMap.get(RevColorSensorV3.class, "colorSensor1");
        waitForStart();
        int red1 = colorSensor1.red();
        int blue1 = colorSensor1.blue();
        int green1 = colorSensor1.green();
        int alpha1 = colorSensor1.alpha();

        if (green1>red1 && green1>blue1) {
            telemetry.addData("Slot1:", "Green");
            telemetry.update();
        }
        if (blue1>red1 && blue1>green1) {
            telemetry.addData("Slot1:", "Purple");
            telemetry.update();
        }
        if (alpha1>green1 && alpha1>blue1 && alpha1>red1){
            telemetry.addData("Slot1:", "Empty");
            telemetry.update();

        }

    }

}
