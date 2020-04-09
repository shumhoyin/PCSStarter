package PCS.SensorHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class SensorEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private SensorEmulator sensorEmulator;
    private MBox sensorMBox;
    private MBox pcsMBox; /* get the pcsMBox*/

    public TextArea sensorTextArea;
    public Button GoUp;
    public Button GoDown;
//    public Button autoPollButton;
//    private int lineNo = 0;


    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, SensorEmulator sensorEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.sensorEmulator = sensorEmulator;
        this.sensorMBox = appKickstarter.getThread("SensorHandler").getMBox();
       // this.sensorMBox = appKickstarter.getThread("PCSCore").getMBox();
    } // initialize

    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();

        switch (btn.getText()) {
            case "Gate Open Request":
                sensorMBox.send(new Msg(id, null, Msg.Type.GateOpenRequest, "GateOpenReq"));
                break;

            case "Gate Open Reply":
                sensorMBox.send(new Msg(id, null, Msg.Type.GateOpenReply, "GateOpenReply"));
                break;

            default:
                log.warning(id + ": unknown button: [" + btn.getText() + "]");
                break;
        }
    } // buttonPressed



}
