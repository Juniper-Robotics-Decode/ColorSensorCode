package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class CSLogic extends LinearOpMode {
    private RevColorSensorV3 colorSensor1;
    private RevColorSensorV3 colorSensor2;
    private RevColorSensorV3 colorSensor3;
    private String[] =[", , Purple"];
    private String greenStr = "Green";
    private String purpleStr = "Purple";
    private String emptyStr = "Empty";

    private String[] correctMotif = {purpleStr, purpleStr, greenStr};
    private String[] detectedMotif = new String[3];

    @Override
    public void runOpMode() {
        colorSensor1 = hardwareMap.get(RevColorSensorV3.class, "colorSensor1");
        colorSensor2 = hardwareMap.get(RevColorSensorV3.class, "colorSensor2");
        colorSensor3 = hardwareMap.get(RevColorSensorV3.class, "colorSensor3");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (true) {
            detectedMotif[0] = colorDetector(colorSensor1);
            detectedMotif[1] = colorDetector(colorSensor2);
            detectedMotif[2] = colorDetector(colorSensor3);
            if (detectedMotif == correctMotif) {
                telemetry.addData("Slot 1:", "Correct");
            }
            if (detectedMotif != correctMotif) {
                telemetry.addData("Slot 1:", "Incorrect");
            }
            if (detectedMotif != correctMotif) {
                telemetry.addData("Slot 1:", "Correct");
            }



        }
    }

    public String colorDetector(RevColorSensorV3 cs) {
        int blue = cs.blue();
        int green = cs.green();
        int red = cs.red();
        double d1 = cs.getDistance(DistanceUnit.MM);

        if (d1 <= 10) {
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
}
