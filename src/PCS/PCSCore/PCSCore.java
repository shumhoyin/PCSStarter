package PCS.PCSCore;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;


//======================================================================
// PCSCore
public class PCSCore extends AppThread {
    private MBox entrancegateMBox;





//    private MBox collectorMBox;
    private MBox dispatcherMBox;
//    private MBox paymachineMBox;
//    private MBox sensorMBox;
//    private MBox vacancydisplayMBox;



    private final int pollTime;
    private final int PollTimerID=1;
    private final int openCloseGateTime;		// for demo only!!!
    private final int OpenCloseGateTimerID=2;		// for demo only!!!
    private boolean entrancegateIsClosed = true;		// for demo only!!!


    //------------------------------------------------------------
    // PCSCore
    public PCSCore(String id, AppKickstarter appKickstarter) throws Exception {
	super(id, appKickstarter);
	this.pollTime = Integer.parseInt(appKickstarter.getProperty("PCSCore.PollTime"));
	this.openCloseGateTime = Integer.parseInt(appKickstarter.getProperty("PCSCore.OpenCloseGateTime"));		// for demo only!!!
    } // PCSCore


    //------------------------------------------------------------
    // run
    public void run() {
        Thread.currentThread().setName(id);
	Timer.setTimer(id, mbox, pollTime, PollTimerID);
//	Timer.setTimer(id, mbox, openCloseGateTime, OpenCloseGateTimerID);	// for demo only!!!
	log.info(id + ": starting...");

		entrancegateMBox = appKickstarter.getThread("EntranceGateHandler").getMBox();

//	collectorMBox = appKickstarter.getThread("CollectorHandler").getMBox();
	dispatcherMBox = appKickstarter.getThread("DispatcherHandler").getMBox();
//	paymachineMBox = appKickstarter.getThread("PayMachineHandler").getMBox();
//	sensorMBox = appKickstarter.getThread("SensorHandler").getMBox();
//	vacancydisplayMBox = appKickstarter.getThread("VacancyDisplayHandler").getMBox();


	for (boolean quit = false; !quit;) {
			Msg msg = mbox.receive();

			log.fine(id + ": message received: [" + msg + "].");

			switch (msg.getType()) {
			case TimesUp:
				handleTimesUp(msg);
				break;

			case GateOpenReply:
				log.info(id + ": Gate is opened.");
				entrancegateIsClosed = false;
				break;

			case GateCloseReply:
				log.info(id + ": Gate is closed.");
				entrancegateIsClosed = true;
				break;

			case PollAck:
				log.info("PollAck: " + msg.getDetails());
				break;


		   case TicketRequest:
				handleTicketRequest(msg);
				break;

				case TicketCollected:
					handleTicketCollected();
					break;


			case Terminate:
				quit = true;
				break;

			default:
				log.warning(id + ": unknown message type: [" + msg + "]");
//			log.info(id + ": timer cut");
	    }
	}

	// declaring our departure
	appKickstarter.unregThread(this);
	log.info(id + ": terminating...");
    } // run


	//------------------------------------------------------------
	// run
	private void handleTicketCollected() {
		log.info("------------------------------------------------------------");
		log.info(id + " :ticket collected message received !");
		log.info(id + " :sending signal to Gate Handler to open the gate !");

		if (entrancegateIsClosed) {
			log.info(id + ": Open the gate now");
			entrancegateMBox.send(new Msg(id, mbox, Msg.Type.GateOpenRequest, ""));
			entrancegateIsClosed = false;
			Timer.setTimer(id, mbox, openCloseGateTime, OpenCloseGateTimerID);
		}else{
			log.warning(id + ": The Gate is opened now ! ****ignore request***");
		}
//		} else {
//			log.info(id + ": Close the gate now");
//			gateMBox.send(new Msg(id, mbox, Msg.Type.GateCloseRequest, ""));
//		}
	}// handleTicketCollected

	//------------------------------------------------------------
	// run
	private void handleTicketRequest(Msg msg) {
		log.info("------------------------------------------------------------");
		log.info(id + " :ticket request received !");

		dispatcherMBox.send(new Msg(id, mbox, Msg.Type.EntranceInfoReply, "2020/6/30  10:30pm (just hard code for testing)"));

		log.info(id + " : Reply sent back to dispatcherHandler");
	}// handleTicketRequest



    //------------------------------------------------------------
    // run
    private void handleTimesUp(Msg msg) {
	log.info("------------------------------------------------------------");
        switch (Timer.getTimesUpMsgTimerId(msg)) {

	    case PollTimerID:
		log.info("Poll: " + msg.getDetails());
			entrancegateMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));

//		collectorMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
		dispatcherMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
//		paymachineMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
//		sensorMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
//		vacancydisplayMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));

			Timer.setTimer(id, mbox, pollTime, PollTimerID);
	        break;

//	    case OpenCloseGateTimerID:					// for demo only!!!
//	        if (entrancegateIsClosed) {
//		    log.info(id + ": Open the gate now (for demo only!!!)");
//		    gateMBox.send(new Msg(id, mbox, Msg.Type.GateOpenRequest, ""));
//		} else {
//		    log.info(id + ": Close the gate now (for demo only!!!)");
//		    gateMBox.send(new Msg(id, mbox, Msg.Type.GateCloseRequest, ""));
//		}
//		Timer.setTimer(id, mbox, openCloseGateTime, OpenCloseGateTimerID);
//		break;


		/* testing purpose */
	    case OpenCloseGateTimerID:					// for demo only!!!
	        if (entrancegateIsClosed == false) {
		    log.info(id + ": Close the gate now");
				entrancegateMBox.send(new Msg(id, mbox, Msg.Type.GateCloseRequest, ""));
		}
		break;



	    default:
	        log.severe(id + ": why am I receiving a timeout with timer id " + Timer.getTimesUpMsgTimerId(msg));
	        break;
	}
    } // handleTimesUp
} // PCSCore
