package gateway;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigReader {

    private BufferedReader fileInput;

    public ConfigReader(){

    }

    public void setFileInput(String path) throws FileNotFoundException{
        fileInput = new BufferedReader(new FileReader(path));
    }

    
}
