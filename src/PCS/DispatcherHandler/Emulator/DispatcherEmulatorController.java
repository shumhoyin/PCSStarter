package PCS.DispatcherHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

//======================================================================
// DispatcherEmulatorController

public class DispatcherEmulatorController {

    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private DispatcherEmulator dispatcherEmulator;
    private MBox dispatcherMBox;
    public TextArea dispatcherTextArea;
    public TextArea dispatcherDisplayTextArea;
    private int lineNo = 0;
    private int lineNo2 = 0;


    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, DispatcherEmulator dispatcherEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.dispatcherEmulator = dispatcherEmulator;
        this.dispatcherMBox = appKickstarter.getThread("DispatcherHandler").getMBox();
    } // initialize


    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();

        switch (btn.getText()) {

            case "Poll Request":
                appendTextArea("Send poll request.");
                dispatcherMBox.send(new Msg(id, null, Msg.Type.Poll, ""));
                break;

            case "Poll ACK":
                appendTextArea("Send poll ack.");
                dispatcherMBox.send(new Msg(id, null, Msg.Type.PollAck, ""));
                break;

            case "Ticket Collected":
                appendTextArea(id + ": Send ticket collected message to Handler.");
                dispatcherDisplayTextArea.clear();
                dispatcherMBox.send(new Msg(id, null, Msg.Type.TicketCollected, "Driver collected the ticket"));
                break;


            case "Ticket Request":
                appendTextArea(id + ": Send a ticket request to DispatcherHandler");
                dispatcherMBox.send(new Msg(id, null, Msg.Type.TicketRequest, "Driver request a ticket"));
                /*check action*/
                break;

            default:
                log.warning(id + ": unknown button: [" + btn.getText() + "]");
                break;
        }
    } // buttonPressed





    //------------------------------------------------------------
    // appendTextArea
    public void appendTextArea(String status) {
        Platform.runLater(() -> dispatcherTextArea.appendText(String.format("[%04d] %s\n", ++lineNo, status)));
    } // appendTextArea

    //------------------------------------------------------------
    // appendDispatcherDisplayTextArea
    public void appendDispatcherDisplayTextArea(Msg msg) {
        Platform.runLater(() -> dispatcherDisplayTextArea.appendText(String.format("[%04d] %s\n", ++lineNo2, msg.getDetails())));
        Platform.runLater(() -> dispatcherDisplayTextArea.appendText(String.format("[%04d] %s\n", ++lineNo2, "Ticket is ready !!")));
    } // appendTextArea

}
