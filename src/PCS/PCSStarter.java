package PCS;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.Msg;
import AppKickstarter.timer.Timer;

import PCS.PCSCore.PCSCore;
import PCS.GateHandler.GateHandler; //this is entrancegate
import PCS.ExitGateHandler.ExitGateHandler; //this is exit gate

//import PCS.CollectorHandler.CollectorHandler;
import PCS.DispatcherHandler.DispatcherHandler;

//import PCS.PayMachineHandler.PayMachineHandler;
//import PCS.SensorHandler.SensorHandler;
//import PCS.VacancyDisplayHandler.VacancyDisplayHandler;

import javafx.application.Platform;


//======================================================================
// PCSStarter
public class PCSStarter extends AppKickstarter {
    protected Timer timer;
    protected PCSCore pcsCore;
    protected GateHandler entrancegateHandler;
	protected ExitGateHandler exitgateHandler;



//    protected CollectorHandler collectorHandler;
    protected DispatcherHandler dispatcherHandler;
//    protected PayMachineHandler paymachineHandler;
//    protected SensorHandler sensorHandler;
//    protected VacancyDisplayHandler vacancydisplayHandler;






    //------------------------------------------------------------
    // main
    public static void main(String [] args) {
        new PCSStarter().startApp();
    } // main


    //------------------------------------------------------------
    // PCSStart
    public PCSStarter() {
	super("PCSStarter", "etc/PCS.cfg");
    } // PCSStart


    //------------------------------------------------------------
    // startApp
    protected void startApp() {
	// start our application
	log.info("");
	log.info("");
	log.info("============================================================");
	log.info(id + ": Application Starting...");

	startHandlers();
    } // startApp


    //------------------------------------------------------------
    // startHandlers
    protected void startHandlers() {
	// create handlers
	try {
	    timer = new Timer("timer", this);
	    pcsCore = new PCSCore("PCSCore", this);
		entrancegateHandler = new GateHandler("EntranceGateHandler", this);
		exitgateHandler = new ExitGateHandler("EntranceGateHandler", this);


//	    collectorHandler = new CollectorHandler("CollectorHandler", this);
	    dispatcherHandler = new DispatcherHandler("DispatcherHandler", this);
//	    paymachineHandler = new PayMachineHandler("PayMachineHandler", this);
//	    sensorHandler = new SensorHandler("SensorHandler", this);
//	    vacancydisplayHandler = new VacancyDisplayHandler("VacancyDisplayHandler", this);



	} catch (Exception e) {
	    System.out.println("AppKickstarter: startApp failed");
	    e.printStackTrace();
	    Platform.exit();
	}

	// start threads
	new Thread(timer).start();
	new Thread(pcsCore).start();
	new Thread(entrancegateHandler).start();
	new Thread(exitgateHandler).start();



//	new Thread(collectorHandler).start();
	new Thread(dispatcherHandler).start();
//	new Thread(paymachineHandler).start();
//	new Thread(sensorHandler).start();
//	new Thread(vacancydisplayHandler).start();






    } // startHandlers


    //------------------------------------------------------------
    // stopApp
    public void stopApp() {
	log.info("");
	log.info("");
	log.info("============================================================");
	log.info(id + ": Application Stopping...");
	pcsCore.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	entrancegateHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	timer.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	exitgateHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));




//	collectorHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	dispatcherHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
//	paymachineHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
//	sensorHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
//	vacancydisplayHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));




    } // stopApp
} // PCS.PCSStarter
