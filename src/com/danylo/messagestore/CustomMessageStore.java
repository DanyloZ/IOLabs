package com.danylo.messagestore;

import java.io.*;
import java.util.Collection;
import java.util.Date;

public class CustomMessageStore implements MessageStore{

    @Override
    public void persist(Message message) throws IOException{
        try (FileOutputStream fileStream = new FileOutputStream(new File("test/com/danylo/testmessagestore/testmessage"), true);
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
    public void persist(Collection<Message> list) throws IOException{
        for(Message message : list) {
            persist(message);
        }
    }

    public Message retreave() throws IOException{
        File file = new File("test/com/danylo/testmessagestore/testmessage");
        FileInputStream fileStream = new FileInputStream(file);
        DataInputStream objectStream = new DataInputStream(fileStream);
        int id = objectStream.readInt();
        Date date = new Date(objectStream.readLong());
        String content = objectStream.readUTF();
        Message message = new Message(id, date, content);
        objectStream.close();
        fileStream.close();
        file.delete();
        return message;
    }

    public static void main(String[] args) throws  IOException{
        Message message1 = new Message(1, new Date(), "bla bla bla");
        CustomMessageStore store = new CustomMessageStore();
        store.persist(message1);
        Message message2 = store.retreave();
        System.out.printf("id: %d, date: %s, message: %s", message2.getId(), message2.getDate().toString(), message2.getContent());
    }
}
