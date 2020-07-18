import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class MeetingBuilder {
    private Meeting meeting;


    public void buildInitiator(String initiator){meeting.setInitiator(initiator);}

    public void buildReceiver(String receiver){meeting.setReceiver(receiver);}

    public void buildRequestMeetingDate(LocalDate requestMeetingDate){
        meeting.setRequestMeetingDate(requestMeetingDate);
    }

    public void buildTradeDate(LocalDate tradeDate){meeting.setTradeDate(tradeDate);}

    public void buildReturnDate(LocalDate returnDate){meeting.setReturnDate(returnDate);}

    public Meeting buildMeeting(){return meeting;}
}
