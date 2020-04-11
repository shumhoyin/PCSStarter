package PCS.ExitGateHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;



//======================================================================
// ExitGateEmulatorController
public class ExitGateEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private ExitGateEmulator exitgateEmulator;
    private MBox exitgateMBox;
    public TextArea exitgateTextArea;
    public Button autoOpenButton;
    public Button autoCloseButton;
    public Button autoPollButton;
    private int lineNo = 0;


    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, ExitGateEmulator exitgateEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
		this.log = log;
		this.exitgateEmulator = exitgateEmulator;
		this.exitgateMBox = appKickstarter.getThread("ExitGateHandler").getMBox();
    } // initialize


    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
	Button btn = (Button) actionEvent.getSource();

	switch (btn.getText()) {
	    case "Gate Open Request":
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateOpenRequest, "GateOpenReq"));
		break;

	    case "Gate Open Reply":
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateOpenReply, "GateOpenReply"));
		break;

	    case "Gate Close Request":
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateCloseRequest, "GateCloseReq"));
		break;

	    case "Gate Close Reply":
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateCloseReply, "GateCloseReply"));
		break;

	    case "Poll Request":
		appendTextArea("Send poll request.");
			exitgateMBox.send(new Msg(id, null, Msg.Type.Poll, ""));
		break;

	    case "Poll ACK":
		appendTextArea("Send poll ack.");
			exitgateMBox.send(new Msg(id, null, Msg.Type.PollAck, ""));
		break;

	    case "Auto Open: On":
		Platform.runLater(() -> autoOpenButton.setText("Auto Open: Off"));
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateEmulatorAutoOpenToggle, "ToggleAutoOpen"));
		break;

	    case "Auto Open: Off":
		Platform.runLater(() -> autoOpenButton.setText("Auto Open: On"));
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateEmulatorAutoOpenToggle, "ToggleAutoOpen"));
		break;

	    case "Auto Close: On":
		Platform.runLater(() -> autoCloseButton.setText("Auto Close: Off"));
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateEmulatorAutoCloseToggle, "ToggleAutoClose"));
		break;

	    case "Auto Close: Off":
		Platform.runLater(() -> autoCloseButton.setText("Auto Close: On"));
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateEmulatorAutoCloseToggle, "ToggleAutoClose"));
		break;

	    case "Auto Poll: On":
		Platform.runLater(() -> autoPollButton.setText("Auto Poll: Off"));
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateEmulatorAutoPollToggle, "ToggleAutoPoll"));
		break;

	    case "Auto Poll: Off":
		Platform.runLater(() -> autoPollButton.setText("Auto Poll: On"));
			exitgateMBox.send(new Msg(id, null, Msg.Type.GateEmulatorAutoPollToggle, "ToggleAutoPoll"));
		break;

	    default:
	        log.warning(id + ": unknown button: [" + btn.getText() + "]");
		break;
	}
    } // buttonPressed


    //------------------------------------------------------------
    // appendTextArea
    public void appendTextArea(String status) {
	Platform.runLater(() -> exitgateTextArea.appendText(String.format("[%04d] %s\n", ++lineNo, status)));
    } // appendTextArea
} // GateEmulatorController
