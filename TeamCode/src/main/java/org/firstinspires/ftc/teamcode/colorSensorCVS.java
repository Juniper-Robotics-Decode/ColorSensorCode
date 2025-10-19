package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.opencsv.CSVWriter;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@TeleOp
public class colorSensorCVS extends LinearOpMode {
    private RevColorSensorV3 colorSensor1;


    private ElapsedTime loopTimer;

    private File file;
    private FileWriter fileWriter;
    private CSVWriter csvWriter;
    private final ArrayList<String[]> dataArray = new ArrayList<String[]>();
    private boolean addData = true;

    @Override
    public void runOpMode() {
        colorSensor1 = hardwareMap.get(RevColorSensorV3.class, "colorSensor1");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        loopTimer = new ElapsedTime();

        file = new File(String.format("%s/FIRST/Empty(1:P, 2:G, 3:P).csv", Environment.getExternalStorageDirectory().getAbsolutePath()));

        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        csvWriter = new CSVWriter(fileWriter);
        dataArray.add(new String[]{"Red1(red1)", "Green1(green1)", "Blue1(blue1)", "Alpha1(alpha1)", "Distance1(distance1)"});
        waitForStart();
        if (addData) {
            for (int i = 0; i < 1000; i++) {
                loopTimer.reset();
                int red1 = colorSensor1.red();
                int blue1 = colorSensor1.blue();
                int green1 = colorSensor1.green();
                int alpha1 = colorSensor1.alpha();
                double distance1 = colorSensor1.getDistance(DistanceUnit.MM);

                telemetry.addData("Red1:", red1);
                telemetry.addData("Green1:", green1);
                telemetry.addData("Blue1:", blue1);
                telemetry.addData("Alpha1:", alpha1);
                telemetry.addData("Distance1:", distance1);
                telemetry.addData("i:", i);
                telemetry.update();

                String Red1 = red1 + "";
                String Green1 = green1 + "";
                String Blue1 = blue1 + "";
                String Alpha1 = alpha1 + "";
                String Distance1 = distance1 + "";

                dataArray.add(new String[]{Red1, Green1, Blue1, Alpha1, Distance1});


            }
        }
        try {
            csvWriter.writeAll(dataArray);
            csvWriter.close();
            addData = false;
        } catch (Exception e) {
            telemetry.addData("-", e.getMessage());
        }
        telemetry.addData("-", "DONE COLLECTING");
        telemetry.update();

    }

}


