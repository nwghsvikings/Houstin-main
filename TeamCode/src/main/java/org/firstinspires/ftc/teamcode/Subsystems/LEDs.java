package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;

public class LEDs {
    DigitalChannel GreenLED1;
    DigitalChannel RedLED1;
    DigitalChannel GreenLED2;
    DigitalChannel RedLED2;
    public LEDs(HardwareMap hardwaremap){
        // Get the color sensor from hardwareMap
        GreenLED1= hardwaremap.get(DigitalChannel.class, "green_left"); //port0
        RedLED1 = hardwaremap.get(DigitalChannel.class, "red_left"); //port1
        GreenLED2= hardwaremap.get(DigitalChannel.class, "green_right"); //port2
        RedLED2 = hardwaremap.get(DigitalChannel.class, "red_right"); //port3
        GreenLED1.setMode(DigitalChannel.Mode.OUTPUT);
        RedLED1.setMode(DigitalChannel.Mode.OUTPUT);
        GreenLED2.setMode(DigitalChannel.Mode.OUTPUT);
        RedLED2.setMode(DigitalChannel.Mode.OUTPUT);
    }

    public void RED() {
        GreenLED1.setState(false);
        RedLED1.setState(true);
        GreenLED2.setState(false);
        RedLED2.setState(true);
    }
    public void GREEN() {
        GreenLED1.setState(true);
        RedLED1.setState(false);
        GreenLED2.setState(true);
        RedLED2.setState(false);
    }
    public void AMBER() {
        GreenLED1.setState(false);
        RedLED1.setState(false);
        GreenLED2.setState(false);
        RedLED2.setState(false);
    }
    public void OFF() {
        GreenLED1.setState(true);
        RedLED1.setState(true);
        GreenLED2.setState(true);
        RedLED2.setState(true);
    }


}
