package PCS;

import AppKickstarter.timer.Timer;

import PCS.PCSCore.PCSCore;
import PCS.GateHandler.GateHandler;
import PCS.ExitGateHandler.ExitGateHandler;

//import PCS.CollectorHandler.CollectorHandler;
import PCS.DispatcherHandler.DispatcherHandler;
//import PCS.PayMachineHandler.PayMachineHandler;
//import PCS.SensorHandler.SensorHandler;
//import PCS.VacancyDisplayHandler.VacancyDisplayHandler;

import PCS.GateHandler.Emulator.GateEmulator;
import PCS.ExitGateHandler.Emulator.ExitGateEmulator;

//import PCS.CollectorHandler.Emulator.CollectorEmulator;
import PCS.DispatcherHandler.Emulator.DispatcherEmulator;
//import PCS.PayMachineHandler.Emulator.PayMachineHandler;
//import PCS.SensorHandler.Emulator.SensorEmulator;
//import PCS.VacancyDisplayHandler.Emulator.VacancyDisplayEmulator;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

//======================================================================
// PCSEmulatorStarter
public class PCSEmulatorStarter extends PCSStarter {
    //------------------------------------------------------------
    // main
    public static void main(String [] args) {
	new PCSEmulatorStarter().startApp();
    } // main


    //------------------------------------------------------------
    // startHandlers
    @Override
    protected void startHandlers() {
        Emulators.pcsEmulatorStarter = this;
        new Emulators().start();
    } // startHandlers


    //------------------------------------------------------------
    // Emulators
    public static class Emulators extends Application {
        private static PCSEmulatorStarter pcsEmulatorStarter;

	//----------------------------------------
	// start
        public void start() {
            launch();
	} // start

	//----------------------------------------
	// start
        public void start(Stage primaryStage) {
	    Timer timer = null;
	    PCSCore pcsCore = null;
	    GateEmulator entrancegateEmulator = null;
	    ExitGateEmulator exitgateEmulator = null;


//	    CollectorEmulator collectorEmulator = null;
	    DispatcherEmulator dispatcherEmulator = null;
//		PayMachineHandler paymachineEmulator = null;
//		SensorEmulator sensorEmulator = null;
//		VacancyDisplayEmulator vacancydisplayEmulator = null;





	    // create emulators
	    try {
	        timer = new Timer("timer", pcsEmulatorStarter);
	        pcsCore = new PCSCore("PCSCore", pcsEmulatorStarter);
			entrancegateEmulator = new GateEmulator("EntranceGateHandler", pcsEmulatorStarter);
		    exitgateEmulator = new ExitGateEmulator("ExitGateHandler", pcsEmulatorStarter);



//	        collectorEmulator = new CollectorEmulator("CollectorHandler", pcsEmulatorStarter);
        	dispatcherEmulator = new DispatcherEmulator("DispatcherHandler", pcsEmulatorStarter);
//	        paymachineEmulator = new PayMachineHandler("PayMachineHandler", pcsEmulatorStarter);
//	        sensorEmulator = new SensorEmulator("SensorHandler", pcsEmulatorStarter);
//	        vacancydisplayEmulator = new VacancyDisplayEmulator("VacancyDisplayHandler", pcsEmulatorStarter);





		// start emulator GUIs
			entrancegateEmulator.start();
			exitgateEmulator.start();



//		collectorEmulator.start();
		dispatcherEmulator.start();
//		paymachineEmulator.start();
//		sensorEmulator.start();
//		vacancydisplayEmulator.start();


	    } catch (Exception e) {
		System.out.println("Emulators: start failed");
		e.printStackTrace();
		Platform.exit();
	    }
	    pcsEmulatorStarter.setTimer(timer);
	    pcsEmulatorStarter.setPCSCore(pcsCore);
	    pcsEmulatorStarter.setGateHandler(entrancegateEmulator);
	    pcsEmulatorStarter.setExitGateHandler(exitgateEmulator);


//	    pcsEmulatorStarter.setExitGateHandler(exitgateEmulator);


//
//	    pcsEmulatorStarter.setCollectorHandler(collectorEmulator);
	    pcsEmulatorStarter.setDispatcherHandler(dispatcherEmulator);
//	    pcsEmulatorStarter.setPayMachineHandler(paymachineEmulator);
//	    pcsEmulatorStarter.setSensorHandler(sensorEmulator);
//	    pcsEmulatorStarter.setVacancyDisplayHandler(vacancydisplayEmulatorr);


	    // start threads
	    new Thread(timer).start();
	    new Thread(pcsCore).start();
	    new Thread(entrancegateEmulator).start();
	    new Thread(exitgateEmulator).start();



//	    new Thread(collectorEmulator).start();
	    new Thread(dispatcherEmulator).start();
//	    new Thread(paymachineEmulator).start();
//	    new Thread(sensorEmulator).start();
//	    new Thread(vacancydisplayEmulator).start();






	} // start
    } // Emulators


    //------------------------------------------------------------
    //  setters
    private void setTimer(Timer timer) {
        this.timer = timer;
    }
    private void setPCSCore(PCSCore pcsCore) {
        this.pcsCore = pcsCore;
    }
    private void setGateHandler(GateHandler entrancegateHandler) { this.entrancegateHandler = entrancegateHandler; }
	private void setExitGateHandler(ExitGateHandler exitgateHandler) { this.exitgateHandler = exitgateHandler; }


//	private void setExitGateHandler(GateHandler exitgateHandler) { this.exitgateHandler = exitgateHandler; }


//    private void setCollectorHandler(CollectorHandler collectorHandler) { this.collectorHandler = collectorHandler; }
	private void setDispatcherHandler(DispatcherHandler dispatcherHandler) { this.dispatcherHandler = dispatcherHandler; }
//    private void setPayMachineHandler(PayMachineHandler paymachineHandler) { this.paymachineHandler = paymachineHandler; }
//    private void setSensorHandler(SensorHandler sensorHandler) { this.sensorHandler = sensorHandler; }
//    private void setVacancyDisplayHandler(VacancyDisplayHandler vacancydisplayHandler) { this.vacancydisplayHandler = vacancydisplayHandler; }



} // PCSEmulatorStarter
