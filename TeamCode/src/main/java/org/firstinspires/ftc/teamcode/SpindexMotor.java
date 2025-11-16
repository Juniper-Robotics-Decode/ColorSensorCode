package org.firstinspires.ftc.teamcode;
//eee

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class SpindexMotor extends LinearOpMode {
    private DcMotor spindexMotor;
    private RevColorSensorV3 colorSensor1;
    private RevColorSensorV3 colorSensor2;
    private RevColorSensorV3 colorSensor3;
    private RevTouchSensor TCS1;
    private RevTouchSensor TCS2;
    private RevTouchSensor TCS3;

    private final String greenStr = "Green";
    private final String purpleStr = "Purple";
    private final String emptyStr = "Empty";

    private boolean[] full = new boolean[3];
    private boolean[] green = new boolean[3];
    private boolean[] purple = new boolean[3];
    private String[] detectedMotif = new String[3];
    private double d1;
    private double d2;
    private double d3;

    @Override
    public void runOpMode() {
        colorSensor1 = hardwareMap.get(RevColorSensorV3.class, "colorSensor1");
        colorSensor2 = hardwareMap.get(RevColorSensorV3.class, "colorSensor2");
        colorSensor3 = hardwareMap.get(RevColorSensorV3.class, "colorSensor3");
        TCS1 = hardwareMap.get(RevTouchSensor.class, "TCS1");
        TCS2 = hardwareMap.get(RevTouchSensor.class, "TCS2");
        TCS3 = hardwareMap.get(RevTouchSensor.class, "TCS3");
        spindexMotor = hardwareMap.get(DcMotor.class, "spindexMotor");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (opModeIsActive()) {
            detectedMotif[0] = colorDetector(colorSensor1);
            detectedMotif[1] = colorDetector(colorSensor2);
            detectedMotif[2] = colorDetector(colorSensor3);
            d1 = colorSensor1.getDistance(DistanceUnit.MM);
            d2 = colorSensor2.getDistance(DistanceUnit.MM);
            d3 = colorSensor3.getDistance(DistanceUnit.MM);
            //telemetry.addData("DistanceCS1", d1);
            //telemetry.addData("DistanceCS2", d2);

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
                }
            }

            if (gamepad1.circle) {
                boolean hasGreen = green[0] || green[1] || green[2];
                if (!hasGreen) {
                    gamepad1.rumble(500);
                }
            }
            if (gamepad1.triangle) {
                if (!full[0] && !full[1] && !full[2]) {
                    gamepad1.rumble(500);
                } else {
                    spindexMotor.setPower(0.3);
                }
            }

            if (full[0] && TCS1.isPressed()) {
                spindexMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                spindexMotor.setPower(0);
                telemetry.addData("Pocket 1 is", "Full and under SHOOTER");
            }

            if (full[1] && TCS2.isPressed()) {
                spindexMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                spindexMotor.setPower(0);
                telemetry.addData("Pocket 2 is", "Full and under SHOOTER");
            }

            if (full[2] && TCS3.isPressed()) {
                spindexMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                spindexMotor.setPower(0);
                telemetry.addData("Pocket 3 is", "Full and under SHOOTER");
            }


            telemetry.addData("purple[0]", purple[0]);
            telemetry.addData("purple[1]", purple[1]);
            telemetry.addData("purple[2]", purple[2]);
            telemetry.addData("green[0]", green[0]);
            telemetry.addData("green[1]", green[1]);
            telemetry.addData("green[2]", green[2]);
            telemetry.addData("TCS1", TCS1.isPressed());
            telemetry.addData("TCS2", TCS2.isPressed());
            telemetry.addData("TCS3", TCS3.isPressed());

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

// Square = Shoot purple
// Circle = Shoot green
// Triangle = Shoot any
//TCS = TouchSensor
//TCS 1 = pocket1
//TCS 2 = pocket
//TCS 3 = pocket3
}

