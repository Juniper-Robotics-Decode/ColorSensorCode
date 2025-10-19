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
    private RevColorSensorV3 colorSensor2;
    private RevColorSensorV3 colorSensor3;


    private ElapsedTime loopTimer;

    private File file;
    private FileWriter fileWriter;
    private CSVWriter csvWriter;
    private final ArrayList<String[]> dataArray = new ArrayList<String[]>();
    private boolean addData = true;
    // ew
    @Override
    public void runOpMode() {
        colorSensor1 = hardwareMap.get(RevColorSensorV3.class, "colorSensor1");
        colorSensor2 = hardwareMap.get(RevColorSensorV3.class, "colorSensor2");
        colorSensor3 = hardwareMap.get(RevColorSensorV3.class, "colorSensor3");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        loopTimer = new ElapsedTime();

        file = new File(String.format("%s/FIRST/Purple2.csv", Environment.getExternalStorageDirectory().getAbsolutePath()));

        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        csvWriter = new CSVWriter(fileWriter);
        dataArray.add(new String[]{"Red1(red1)", "Green1(green1)", "Blue1(blue1)", "Alpha1(alpha1)", "Distance1(distance1)",
                "Red2(red2)", "Green2(green2)", "Blue2(blue2)", "Alpha2(alpha2)", "Distance2(distance2)",
                "Red3(red3)", "Green3(green3)", "Blue3(blue3)", "Alpha3(alpha3)", "Distance3(distance3)"});
        waitForStart();
        if (addData) {
            for (int i = 0; i < 100; i++) {
                loopTimer.reset();
                int red1 = colorSensor1.red();
                int blue1 = colorSensor1.blue();
                int green1 = colorSensor1.green();
                int alpha1 = colorSensor1.alpha();
                int red2 = colorSensor2.red();
                int blue2 = colorSensor2.blue();
                int green2 = colorSensor2.green();
                int alpha2 = colorSensor2.alpha();
                int red3 = colorSensor3.red();
                int blue3 = colorSensor3.blue();
                int green3 = colorSensor3.green();
                int alpha3 = colorSensor3.alpha();
                double distance1 = colorSensor1.getDistance(DistanceUnit.MM);
                double distance2 = colorSensor2.getDistance(DistanceUnit.MM);
                double distance3 = colorSensor3.getDistance(DistanceUnit.MM);

                telemetry.addData("Red1:", red1);
                telemetry.addData("Green1:", green1);
                telemetry.addData("Blue1:", blue1);
                telemetry.addData("Alpha1:", alpha1);
                telemetry.addData("Distance1:", distance1);
                telemetry.addData("Red2:", red2);
                telemetry.addData("Green2:", green2);
                telemetry.addData("Blue2:", blue2);
                telemetry.addData("Alpha2:", alpha2);
                telemetry.addData("Distance2:", distance2);
                telemetry.addData("Red3:", red3);
                telemetry.addData("Green3:", green3);
                telemetry.addData("Blue3:", blue3);
                telemetry.addData("Alpha3:", alpha3);
                telemetry.addData("Distance3:", distance3);
                telemetry.addData("i:", i);
                telemetry.update();

                String Red1 = red1 + "";
                String Green1 = green1 + "";
                String Blue1 = blue1 + "";
                String Alpha1 = alpha1 + "";
                String Distance1 = distance1 + "";
                String Red2 = red2 + "";
                String Green2 = green2 + "";
                String Blue2 = blue2 + "";
                String Alpha2 = alpha2 + "";
                String Distance2 = distance2 + "";
                String Red3 = red3 + "";
                String Green3 = green3 + "";
                String Blue3 = blue3 + "";
                String Alpha3 = alpha3 + "";
                String Distance3 = distance3 + "";

                dataArray.add(new String[]{Red1, Green1, Blue1, Alpha1, Distance1,
                        Red2, Green2, Blue2, Alpha2, Distance2,
                        Red3, Green3, Blue3, Alpha3, Distance3});
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


