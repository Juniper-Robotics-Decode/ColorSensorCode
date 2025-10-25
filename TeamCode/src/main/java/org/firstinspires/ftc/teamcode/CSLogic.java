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
    private String greenStr = "Green";
    private String purpleStr = "Purple";
    private String emptyStr = "Empty";

    private final String[] correctMotif = {purpleStr, purpleStr, greenStr};
    private final String[] wrongMotif1 = {purpleStr, greenStr, greenStr};
    private final String[] wrongMotif2 = {greenStr, greenStr, purpleStr};
    private final String[] wrongMotif3 = {greenStr, purpleStr, greenStr};
    private final String[] allGreenMotif = {greenStr, greenStr, greenStr};
    private final String[] allPurpleMotif = {purpleStr, purpleStr, purpleStr};
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
                telemetry.addData("State", "Correct Colors & Correct Order");
            }
            if (detectedMotif[0].equals(greenStr) || detectedMotif[0].equals(purpleStr)) {
                telemetry.addData("Slot 1:", "Full");
                if (detectedMotif[1].equals(greenStr) || detectedMotif[1].equals(purpleStr)) {
                    telemetry.addData("Slot 2:", "Full");
                    if (detectedMotif[2].equals(greenStr) || detectedMotif[2].equals(purpleStr)) {
                        telemetry.addData("State:", "Full");
                        if ((detectedMotif == wrongMotif1)
                                || (detectedMotif == wrongMotif2)
                                || (detectedMotif == wrongMotif3)
                                || (detectedMotif == allGreenMotif)
                                || (detectedMotif == allPurpleMotif)) {
                            telemetry.addData("State", "Wrong color");
                        } else if (!Arrays.equals(detectedMotif, correctMotif)) {
                            telemetry.addData("State", "Wrong Order");
                        } else {
                            telemetry.addData("State", "Correct Colors & Correct Order");
                        }

                    }
                }
            } else if ((detectedMotif[0].equals(greenStr) || detectedMotif[0].equals(purpleStr))
                    || ((detectedMotif[1].equals(greenStr) || detectedMotif[1].equals(purpleStr))
                    || ((detectedMotif[2].equals(greenStr) || detectedMotif[2].equals(purpleStr))))) {
                telemetry.addData("State:", "Partially Full");

            } else {
                telemetry.addData("State", "Empty");
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
