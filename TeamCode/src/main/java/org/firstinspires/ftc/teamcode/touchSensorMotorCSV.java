package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.opencsv.CSVWriter;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@TeleOp
public class touchSensorMotorCSV extends LinearOpMode {
    private DcMotorEx spindexMotor;

    private File file1;
    private File file2;
    private FileWriter fileWriter1;
    private CSVWriter csvWriter1;
    private RevTouchSensor TCS1;
    private RevTouchSensor TCS2;
    private RevTouchSensor TCS3;
    private boolean addData = true;

    @Override
    public void runOpMode() throws InterruptedException {
        TCS1 = hardwareMap.get(RevTouchSensor.class, "TCS1");
        TCS2 = hardwareMap.get(RevTouchSensor.class, "TCS2");
        TCS3 = hardwareMap.get(RevTouchSensor.class, "TCS3");
        spindexMotor = hardwareMap.get(DcMotorEx.class, "spindexMotor");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        file1 = new File(String.format("%s/FIRST/TouchSensor1.csv", Environment.getExternalStorageDirectory().getAbsolutePath()));
        file2 = new File(String.format("%s/FIRST/Motor1.csv", Environment.getExternalStorageDirectory().getAbsolutePath()));

        waitForStart();

        while (opModeIsActive() && addData) {
            touchSensorDataCollection(TCS1, file1);
            touchSensorDataCollection(TCS2, file1);
            touchSensorDataCollection(TCS3, file1);
            motorDataCollection(spindexMotor, file2);

            addData = false;
        }

        telemetry.addData("Status", "DATA COLLECTION FINISHED");
        telemetry.update();
    }

    public void touchSensorDataCollection(RevTouchSensor ts, File file) {
        ArrayList<String[]> dataArray = new ArrayList<String[]>();
        dataArray.add(new String[]{"Pressed", "Motor Velocity"});
        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CSVWriter csvWriter = new CSVWriter(fileWriter);
        for (int i = 0; i < 1000; i++) {
            double touchSensorPressed = ts.getValue();

            telemetry.addData("Pressed:", touchSensorPressed);
            telemetry.addData("i:", i);
            telemetry.update();

            String touchSensorPressedStr = touchSensorPressed + "";
            dataArray.add(new String[]{touchSensorPressedStr});
        }

        try {
            csvWriter.writeAll(dataArray);
            csvWriter.close();
        } catch (Exception e) {
            telemetry.addData("-", e.getMessage());
            telemetry.update();
        }
    }

    public void motorDataCollection(DcMotorEx m, File file) {
        ArrayList<String[]> dataArray = new ArrayList<>();
        dataArray.add(new String[]{"Velocity"});

        try (FileWriter fileWriter = new FileWriter(file); CSVWriter csvWriter = new CSVWriter(fileWriter)) {

            for (int i = 0; i < 1000; i++) {
                double velocity = m.getVelocity();
                telemetry.addData("Velocity", velocity);
                telemetry.update();

                String velocityStr = velocity + "";
                dataArray.add(new String[]{velocityStr});
            }

            csvWriter.writeAll(dataArray);

        } catch (Exception e) {
            telemetry.addData("ERROR", e.getMessage());
            telemetry.update();
        }
    }
}


