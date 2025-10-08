package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

//import com.opencsv.CSVWriter;
import com.sun.tools.jdeprscan.CSV;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@TeleOp
public class colorSensorCVS extends LinearOpMode {
    private RevColorSensorV3 colorSensor;

    private static final Logger log = LoggerFactory.getLogger(colorSensorCVS.class);

    private File file;
    private FileWriter fileWriter;
    private CSV csvWriter;
    private IMU imu;
    private final ArrayList<String[]> dataArray = new ArrayList<String[]>();

    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        imu = hardwareMap.get(IMU.class, "IMU");

        waitForStart();

        while (opModeIsActive()){

            int red = colorSensor.red();
            int blue = colorSensor.blue();
            int green = colorSensor.green();
            int alpha = colorSensor.alpha();

            telemetry.addData("Red:", red);
            telemetry.addData("Green:", green);
            telemetry.addData("Blue:", blue);
            telemetry.addData("Alpha:", alpha);
            telemetry.update();

        }
    }

}
