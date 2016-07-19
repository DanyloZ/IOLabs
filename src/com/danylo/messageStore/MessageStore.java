package com.danylo.messageStore;

import java.util.Collection;

public interface MessageStore {
    void persist(Message message);
    void persist(Collection<Message> list);
}
