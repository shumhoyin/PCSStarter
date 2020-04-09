package PCS.SensorHandler;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;

public class SensorHandler extends AppThread {
    protected final MBox pcsCore;
    private SensorStatus sensorStatus;

    //------------------------------------------------------------
    // SensorHandler
    public SensorHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter); //id will be set in PCS Starter
        pcsCore = appKickstarter.getThread("PCSCore").getMBox(); //get the pcs mbox for sending signal
        sensorStatus = SensorStatus.SensorIdle; //default idle state at first
    } // SensorHandler


    //------------------------------------------------------------
    // run
    public void run() {
        Thread.currentThread().setName(id);
        log.info(id + ": starting...");

//        for (boolean quit = false; !quit;) {
//            Msg msg = mbox.receive();
//
//            log.fine(id + ": message received: [" + msg + "].");
//
//            quit = processMsg(msg);
//        }

        // declaring our departure
        appKickstarter.unregThread(this);
        log.info(id + ": terminating...");
    } // run




    //------------------------------------------------------------
    // Sensor Status
    private enum SensorStatus {
        SensorIdle,
        SensorActive,
    }
}
