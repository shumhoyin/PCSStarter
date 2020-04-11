package PCS.PCSCore;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Ticket {
    int TicketId;
    LocalDateTime entranceTime;
    LocalDateTime ExitTime;
    boolean TicketPaid;

    public Ticket(int TicketId){
        this.TicketId = TicketId;
        this.entranceTime = LocalDateTime.now();
        this.ExitTime=null;
        this.TicketPaid = false;

    }

    public int getTicketId(){
        return this.TicketId;
    }

    public LocalDateTime getEntranceTime(){
        return this.entranceTime;
    }

    public LocalDateTime getExitTime(){
        return this.ExitTime;
    }

    public boolean isPaid(){
        return TicketPaid;
    }


    public void setExitTime(){
        this.ExitTime = LocalDateTime.now().plusHours(2);
    }

    public void setPaid(){
        this.TicketPaid = false;
    }

    @Override
    public String toString() {
        return " TicketId= " + TicketId +
                ", EntranceTime= " + entranceTime;
    }
}