package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import com.opencsv.CSVWriter;
import com.qualcomm.robotcore.util.ElapsedTime;
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
    private CSVWriter csvWriter;
    private final ArrayList<String[]> dataArray = new ArrayList<String[]>();

    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSensor");

        file = new File(String.format("%s/FIRST/colorSensorDat.csv", Environment.getExternalStorageDirectory().getAbsolutePath()));
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeExecption(e);
        }
        csvWriter = new CSVWriter(fileWriter);

        dataArray.add(new String[]{"Red(red)", "Green(green)", "Blue(blue)", "Alpha(alpha)"});
        waitForStart();

        for (int i = 0; i < 5000; i++) {
            int red = colorSensor.red();
            int blue = colorSensor.blue();
            int green = colorSensor.green();
            int alpha = colorSensor.alpha();

            telemetry.addData("Red:", red);
            telemetry.addData("Green:", green);
            telemetry.addData("Blue:", blue);
            telemetry.addData("Alpha:", alpha);
            telemetry.update();

            String Red = red + "";
            String Green = green + "";
            String Blue = blue + "";
            String Alpha = alpha + "";

            if (addData)
                dataArray.add(new String[]{Red, Green, Blue, Alpha});
            }
        }

    }

