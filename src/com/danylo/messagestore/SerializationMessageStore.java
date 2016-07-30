package com.danylo.messagestore;

import java.io.*;
import java.util.Collection;
import java.util.Date;

public class SerializationMessageStore implements MessageStore{

    @Override
    public void persist(Message message) throws IOException{
        try (FileOutputStream fileStream = new FileOutputStream(new File("test/com/danylo/testmessagestore/testmessage"), true);
             ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void persist(Collection<Message> list) throws IOException {
        for(Message message : list) {
            persist(message);
        }
    }

    public Message retreave() throws IOException, ClassNotFoundException {
        File file = new File("test/com/danylo/testmessagestore/testmessage");
        FileInputStream fileStream = new FileInputStream(file);
        ObjectInputStream objectStream = new ObjectInputStream(fileStream);
        Message message = (Message)objectStream.readObject();
        objectStream.close();
        fileStream.close();
        file.delete();
        return message;
    }

    public static void main(String[] args) throws  IOException, ClassNotFoundException {
        Message message1 = new Message(1, new Date(), "bla bla");
        SerializationMessageStore serlialize = new SerializationMessageStore();
        serlialize.persist(message1);
        Message message2 = serlialize.retreave();
        System.out.printf("id: %d, date: %s, message: %s", message2.getId(), message2.getDate().toString(), message2.getContent());
    }
}
