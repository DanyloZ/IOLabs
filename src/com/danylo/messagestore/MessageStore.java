package com.danylo.messagestore;

import java.io.IOException;
import java.util.Collection;

public interface MessageStore {
    void persist(Message message) throws IOException;
    void persist(Collection<Message> list) throws IOException;
}
