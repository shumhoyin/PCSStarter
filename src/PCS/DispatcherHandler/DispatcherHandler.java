package PCS.DispatcherHandler;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;

//======================================================================
// DispatcherHandler
public class DispatcherHandler extends AppThread{
    protected final MBox pcsCore;
    private DispatcherStatus dispatcherStatus;


    //------------------------------------------------------------
    // DispatcherHandler
    public DispatcherHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
        pcsCore = appKickstarter.getThread("PCSCore").getMBox();
        dispatcherStatus = DispatcherStatus.Active;
    } // GateHandler


    //------------------------------------------------------------
    // run
    public void run() {
        Thread.currentThread().setName(id);
        log.info(id + ": starting...");

        for (boolean quit = false; !quit;) {
            Msg msg = mbox.receive();

            log.fine(id + ": message received: [" + msg + "].");

            quit = processMsg(msg);
        }

        // declaring our departure
        appKickstarter.unregThread(this);
        log.info(id + ": terminating...");
    }// run


    //------------------------------------------------------------
    // processMsg
    protected boolean processMsg(Msg msg) {
        boolean quit = false;

            switch (msg.getType()) {
            case TicketCollected: handTicketCollected(); break;

            case TicketRequest:  handleTakeTicketRequest();  break;

            case EntranceInfoReply:  handleTicketReply(msg);  break;

            case Poll:		   handlePollReq();	     break;

            case PollAck:	   handlePollAck();	     break;

            case Terminate:	   quit = true;		     break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
        return quit;
    } // processMsg



    //------------------------------------------------------------
    // handlePollAck
    protected final void handTicketCollected() {
        log.info(id + ": ticket collected message received.  Send ticket collected message to PCS Core.");
        pcsCore.send(new Msg(id, mbox, Msg.Type.TicketCollected,  "ticket collected !"));
    } // handTicketCollected


    //------------------------------------------------------------
    // handleTicketReply
    protected void handleTicketReply(Msg msg) {
        // fixme: send reply received to hardware
        log.info(id + ": ticket reply received" + "from " + msg.getSender() );
    } // handleTicketReply




    //------------------------------------------------------------
    // handleTakeTicketRequest
    protected final void handleTakeTicketRequest() {
        switch (dispatcherStatus) {
            case Active:
                log.info(id + ": Ticket request Received");
                log.info(id + ": Sending request to PCS");
                pcsCore.send(new Msg(id, mbox, Msg.Type.TicketRequest,"Driver requests a ticket"));
                break;
        }

    } // handleTakeTicketRequest


    //------------------------------------------------------------
    // handlePollReq
    protected final void handlePollReq() {
        log.info(id + ": poll request received.  Send poll request to hardware.");
        sendPollReq();
    } // handlePollReq

    //------------------------------------------------------------
    // sendPollReq
    protected void sendPollReq() {
        // fixme: send gate poll request to hardware
        log.info(id + ": poll request received");
    } // sendPollReq

    //------------------------------------------------------------
    // handlePollAck
    protected final void handlePollAck() {
        log.info(id + ": poll ack received.  Send poll ack to PCS Core.");
        pcsCore.send(new Msg(id, mbox, Msg.Type.PollAck, id + " is up!"));
    } // handlePollAck


    //------------------------------------------------------------
    // Dispatcher Status (for testing only)
    private enum DispatcherStatus {
        Active,
    }

}
