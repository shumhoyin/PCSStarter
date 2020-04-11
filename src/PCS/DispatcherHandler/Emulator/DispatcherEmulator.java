package PCS.DispatcherHandler.Emulator;

import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;

import PCS.PCSStarter;
import PCS.DispatcherHandler.DispatcherHandler;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

//======================================================================
// GateEmulator
public class DispatcherEmulator extends DispatcherHandler {

    private Stage myStage;
    private DispatcherEmulatorController dispatcherEmulatorController;
    private final PCSStarter pcsStarter;
    private final String id;
//    private boolean autoPoll;


    //------------------------------------------------------------
    // DispatcherEmulator
    public DispatcherEmulator(String id, PCSStarter pcsStarter) {
        super(id, pcsStarter);
        this.pcsStarter = pcsStarter;
        this.id = id + "Emulator";
    } // DispatcherEmulator


    //------------------------------------------------------------
    // start
    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "DispatcherEmulator.fxml";
        loader.setLocation(DispatcherEmulator.class.getResource(fxmlName));
        root = loader.load();
        dispatcherEmulatorController = (DispatcherEmulatorController) loader.getController();
        dispatcherEmulatorController.initialize(id, pcsStarter, log, this);



        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 497, 725));
        myStage.setTitle("Dispatcher Emulator");
        myStage.setResizable(true);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            pcsStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } //start

    //------------------------------------------------------------
    // processMsg
    protected final boolean processMsg(Msg msg) {
        boolean quit = false;

        switch (msg.getType()) {

            case EntranceInfoReply:
                handleTicketReply(msg);

                break;

            default:
                quit = super.processMsg(msg);
        }
        return quit;
    } // processMsg


    //------------------------------------------------------------
    // handleTicketReply
    @Override
    protected void handleTicketReply(Msg msg) {
        logFine("ticket reply received " + "from " + msg.getSender() );
        dispatcherEmulatorController.appendDispatcherDisplayTextArea(msg);
    } // handleTicketReply





    //------------------------------------------------------------
    // sendPollReq
    @Override
    protected void sendPollReq() {
        logFine("Poll request received. ");
        if (true) {
            logFine("Send poll ack.");
            mbox.send(new Msg(id, mbox, Msg.Type.PollAck, ""));
        }
    } // sendPollReq



    //------------------------------------------------------------
    // logFine
    private final void logFine(String logMsg) {
        dispatcherEmulatorController.appendTextArea("[FINE]: " + logMsg);
        log.fine(id + ": " + logMsg);
    } // logFine


    //------------------------------------------------------------
    // logInfo
    private final void logInfo(String logMsg) {
        dispatcherEmulatorController.appendTextArea("[INFO]: " + logMsg);
        log.info(id + ": " + logMsg);
    } // logInfo


    //------------------------------------------------------------
    // logWarning
    private final void logWarning(String logMsg) {
        dispatcherEmulatorController.appendTextArea("[WARNING]: " + logMsg);
        log.warning(id + ": " + logMsg);
    } // logWarning


    //------------------------------------------------------------
    // logSevere
    private final void logSevere(String logMsg) {
        dispatcherEmulatorController.appendTextArea("[SEVERE]: " + logMsg);
        log.severe(id + ": " + logMsg);
    } // logSevere


}
