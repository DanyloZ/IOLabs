package com.danylo.messageStore;

import java.io.*;
import java.util.Collection;
import java.util.Date;

public class SerializationMessageStore implements MessageStore{

    @Override
    public void persist(Message message) {
        try (FileOutputStream fileStream = new FileOutputStream(new File("c:/temp/message"), true);
             ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void persist(Collection<Message> list) {
        list.forEach(this::persist);
    }

    public Message retreave() {
        Message message = null;
        try(FileInputStream fileStream = new FileInputStream(new File("c:/temp/message"));
        ObjectInputStream objectStream = new ObjectInputStream(fileStream)) {
           message = (Message)objectStream.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void main(String[] args) {
        Message message1 = new Message(1, new Date(), "bla bla");
        SerializationMessageStore serlialize = new SerializationMessageStore();
        serlialize.persist(message1);
        Message message2 = serlialize.retreave();
        System.out.printf("id: %d, date: %s, message: %s", message2.getId(), message2.getDate().toString(), message2.getContent());
    }
}
