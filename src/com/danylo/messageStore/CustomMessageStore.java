package com.danylo.messageStore;

import java.io.*;
import java.util.Collection;
import java.util.Date;

public class CustomMessageStore implements MessageStore{

    @Override
    public void persist(Message message) {
        try (FileOutputStream fileStream = new FileOutputStream(new File("c:/temp/message"), true);
             DataOutputStream dataStream = new DataOutputStream(fileStream)) {
            dataStream.writeInt(message.getId());
            dataStream.writeLong(message.getDate().getTime());
            dataStream.writeUTF(message.getContent());
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
            DataInputStream objectStream = new DataInputStream(fileStream)) {
            int id = objectStream.readInt();
            Date date = new Date(objectStream.readLong());
            String content = objectStream.readUTF();
            message = new Message(id, date, content);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void main(String[] args) {
        Message message1 = new Message(1, new Date(), "bla bla bla");
        CustomMessageStore store = new CustomMessageStore();
        store.persist(message1);
        Message message2 = store.retreave();
        System.out.printf("id: %d, date: %s, message: %s", message2.getId(), message2.getDate().toString(), message2.getContent());
    }
}
