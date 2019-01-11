package com.Services;

import com.Entities.Account;
import com.Entities.Event;
import com.Entities.User;

import java.util.ArrayList;

public interface IObserver {
    void notifyEventDataChanged(ArrayList<Event> events);

    void notifyUserDataChanged(ArrayList<User> users);

    void notifyAccountDataChanged(ArrayList<Account> accounts);
}
