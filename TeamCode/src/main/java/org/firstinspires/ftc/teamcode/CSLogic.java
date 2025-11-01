package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Arrays;

@TeleOp
public class CSLogic extends LinearOpMode {
    private RevColorSensorV3 colorSensor1;
    private RevColorSensorV3 colorSensor2;
    private RevColorSensorV3 colorSensor3;
    private final String greenStr = "Green";
    private final String purpleStr = "Purple";
    private final String emptyStr = "Empty";

    private boolean full0 = false;
    private boolean full1 = false;
    private boolean full2 = false;

    private boolean Green0;
    private boolean Green1;
    private boolean Green2;
    private boolean Purple0;
    private boolean Purple1;
    private boolean Purple2;
//----
    private final String[] correctMotif = {purpleStr, purpleStr, greenStr};
    private final String[] wrong1 = {purpleStr, greenStr, greenStr};
    private final String[] wrong2 = {greenStr, greenStr, purpleStr};
    private final String[] wrong3 = {greenStr, purpleStr, greenStr};
    private final String[] wrongOrder1 = {purpleStr, greenStr, purpleStr};
    private final String[] wrongOrder2 = {greenStr, purpleStr, purpleStr};
    private final String[] allGreen = {greenStr, greenStr, greenStr};
    private final String[] allPurple = {purpleStr, purpleStr, purpleStr};

    private String[] detectedMotif = new String[3];
    private double d1;
    private double d2;
    private double d3;

    @Override
    public void runOpMode() {
        colorSensor1 = hardwareMap.get(RevColorSensorV3.class, "colorSensor1");
        colorSensor2 = hardwareMap.get(RevColorSensorV3.class, "colorSensor2");
        colorSensor3 = hardwareMap.get(RevColorSensorV3.class, "colorSensor3");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
//---
        while (opModeIsActive()) {
            detectedMotif[0] = colorDetector(colorSensor1);
            detectedMotif[1] = colorDetector(colorSensor2);
            detectedMotif[2] = colorDetector(colorSensor3);
            d1 = colorSensor1.getDistance(DistanceUnit.MM);
            d2 = colorSensor2.getDistance(DistanceUnit.MM);
            d3 = colorSensor3.getDistance(DistanceUnit.MM);
            telemetry.addData("DistanceCS1", d1);
            telemetry.addData("DistanceCS2", d2);
            telemetry.addData("DistanceCS3", d3);
            telemetry.addData("State", "");
            telemetry.update();

            if (detectedMotif[0].equals(greenStr)) {
                Green0 = true;
            }
            if (detectedMotif[1].equals(greenStr)) {
                Green1 = true;
            }
            if (detectedMotif[2].equals(greenStr)) {
                Green2 = true;
            }
            if (detectedMotif[0].equals(purpleStr)) {
                Purple0 = true;
            }
            if (detectedMotif[1].equals(purpleStr)) {
                Purple1 = true;
            }
            if (detectedMotif[2].equals(purpleStr)) {
                Purple2 = true;
            }


            if (detectedMotif[0].equals(greenStr) || detectedMotif[0].equals(purpleStr)) {
                full0 = true;
                telemetry.addData("Slot1", full0);
            } else {
                full0 = false;
                telemetry.addData("Slot1", full0);

            }

            if (detectedMotif[1].equals(greenStr) || detectedMotif[1].equals(purpleStr)) {
                full1 = true;
                telemetry.addData("Slot2", full1);

            } else {
                full1 = false;
                telemetry.addData("Slot2", full1);
            }

            if (detectedMotif[2].equals(greenStr) || detectedMotif[2].equals(purpleStr)) {
                full2 = true;
                telemetry.addData("Slot3", full2);

            } else {
                full2 = false;
                telemetry.addData("Slot3", full2);
            }

            if (full0 && full1 && full2) {
                telemetry.addData("State", "Full");
                if ((Arrays.equals(detectedMotif, wrong1)) || (Arrays.equals(detectedMotif, wrong2)) || (Arrays.equals(detectedMotif, wrong3)) || (Arrays.equals(detectedMotif, allGreen)) || (Arrays.equals(detectedMotif, allPurple))){
                    telemetry.addData("State", "Full & Wrong Color");
                } else if ((Arrays.equals(detectedMotif, wrongOrder1)) || (Arrays.equals(detectedMotif, wrongOrder2))) {
                    telemetry.addData("State", "Full & Wrong Order");
                } else {
                    telemetry.addData("State", "Correct Color + Order");
                }
            } else if (!full0 && !full1 && !full2) {
                telemetry.addData("State", "Empty");
            } else {
                telemetry.addData("State", "Partially Full");
            }
            telemetry.update();
            }
        }



    public String colorDetector(RevColorSensorV3 cs) {
        int blue = cs.blue();
        int green = cs.green();
        int red = cs.red();
        double dREAD = cs.getDistance(DistanceUnit.MM);

        if (dREAD <= 20) {
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
