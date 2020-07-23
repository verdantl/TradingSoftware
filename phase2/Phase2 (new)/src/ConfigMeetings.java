import java.io.IOException;
import java.io.ObjectOutput;

public class ConfigMeetings extends Config{
    MeetingManager meetingManager;

    public MeetingManager readInfo(String path) throws IOException, ClassNotFoundException {
        meetingManager = (MeetingManager) super.readInfo(path);
        return meetingManager;
    }

    @Override
    public void saveInfo(String path) throws IOException {
        ObjectOutput output = getObjectOutput(path);
        output.writeObject(meetingManager);
        output.close();
    }
}
