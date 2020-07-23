import java.io.*;

public abstract class Config{

    public Serializable readInfo(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            InputStream fileInput = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(fileInput);
            ObjectInput input = new ObjectInputStream(buffer);

            Serializable serializable = (Serializable) input.readObject();
            input.close();
            return serializable;
        } else {
            file.createNewFile();
            return readInfo(path);
        }
    }

    public ObjectOutput getObjectOutput(String path) throws IOException {
        OutputStream file = new FileOutputStream(path);
        OutputStream buffer = new BufferedOutputStream(file);
        return new ObjectOutputStream(buffer);

    }

    public abstract void saveInfo(String path) throws IOException;
    public void saveInfo(String path, Serializable manager) throws IOException{
        OutputStream file = new FileOutputStream(path);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(manager);
        output.close();
    }
}
