package com.Services;

import com.Entities.Account;
import com.Entities.Event;
import com.Entities.Location;
import com.Entities.User;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DatabaseService implements IObservable {
    private ArrayList<IObserver> observers;
    /**
     *
     */
    private static DatabaseService instance;
    /**
     *
     */
    private DatabaseReference userReference;
    /**
     *
     */
    private DatabaseReference accountReference;
    /**
     *
     */
    private DatabaseReference eventReference;
    /**
     *
     */
    private ArrayList<User> cachedUsers;
    /**
     *
     */
    private ArrayList<Account> cachedAccounts;
    /**
     *
     */
    private ArrayList<Event> cachedEvents;

    /**
     *
     */
    private DatabaseService() {
        observers = new ArrayList<>();
        userReference = FirebaseDatabase.getInstance()
                .getReference("/users");
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cacheUsers((Map<String, User>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
        accountReference = FirebaseDatabase.getInstance()
                .getReference("/accounts");
        accountReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cacheAccounts((Map<String, Account>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());

            }
        });
        eventReference = FirebaseDatabase.getInstance()
                .getReference("/events");
        eventReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cacheEvents((Map<String, Event>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });

        cachedAccounts = new ArrayList<>();
        cachedEvents = new ArrayList<>();
        cachedUsers = new ArrayList<>();
    }

    private void cacheUsers(Map<String, User> userMap) {
        cachedUsers.clear();
        System.out.println("Caching users");
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            Map singleUser = (Map) entry.getValue();
            cachedUsers.add(
                    new User(singleUser.get("name").toString(),
                            singleUser.get("email").toString(),
                            singleUser.get("uid").toString()));
        }
        observers.forEach(observer -> observer.notifyUserDataChanged(cachedUsers));
    }

    private void cacheAccounts(Map<String, Account> accountMap) {
        cachedAccounts.clear();
        System.out.println("Caching accounts");

        for (Map.Entry<String, Account> entry : accountMap.entrySet()) {
            Map singleAccount = (Map) entry.getValue();

            User[] friendsArray = (User[]) singleAccount.get("friends");
            ArrayList<User> friends = new ArrayList<>();
            Collections.addAll(friends, friendsArray);

            User user = (User) singleAccount.get("user");
            Account account = new Account(user);
            account.setFriends(friends);
            account.setFireBaseMessagingId(singleAccount.get("fireBaseMessagingId").toString());
            cachedAccounts.add(account);
        }
        observers.forEach(observer -> observer.notifyAccountDataChanged(cachedAccounts));
    }

    private void cacheEvents(Map<String, Event> eventMap) {
        cachedEvents.clear();
        System.out.println("Caching events");

        for (Map.Entry<String, Event> entry : eventMap.entrySet()) {
            Map singleEvent = (Map) entry.getValue();

            User eventCreator = (User) singleEvent.get("eventCreator");
            Location eventLocation = (Location) singleEvent.get("location");
            cachedEvents.add(
                    new Event(eventLocation,
                            singleEvent.get("eventName").toString(),
                            singleEvent.get("eventUID").toString(),
                            singleEvent.get("expirationDateAsString").toString(),
                            eventCreator)
            );
        }
        observers.forEach(observer -> observer.notifyEventDataChanged(cachedEvents));
    }

    /**
     * @return
     */
    public static DatabaseService getInstance() {
        if (instance == null)
            instance = new DatabaseService();
        return instance;
    }

    /**
     * @param user
     */
    public void insertUser(User user) {
        System.out.println("Adding: " + user + " to database");
        FirebaseDatabase.getInstance().getReference("users/" + user.getUid()).setValueAsync(user);
    }

    /**
     * @param account
     */
    public void insertAccount(Account account) {
        System.out.println("Adding: " + account + " to database");
        FirebaseDatabase.getInstance().getReference("accounts/" + account.getUser().getUid()).setValueAsync(account);
    }

    /**
     * @param event
     */
    public void insertEvent(Event event) {
        System.out.println("Adding: " + event + " to database");
        FirebaseDatabase.getInstance().getReference("events/" + event.getEventUID()).setValueAsync(event);
    }

    /**
     * @param userUID
     * @return
     */
    public User getUser(String userUID) {
        User user = null;
        for (User cachedUser : cachedUsers) {
            if (cachedUser.getUid().equals(userUID)) {
                user = cachedUser;
                break;
            }
        }

        return user;
    }

    /**
     * @param user
     * @return
     */
    public Account getAccount(User user) {
        Account account = null;
        for (Account cachedAccount : cachedAccounts) {
            if (cachedAccount.getUser().getUid().equals(user.getUid())) {
                account = cachedAccount;
                break;
            }
        }
        return account;
    }

    /**
     * @param eventUID
     * @return
     */
    public Event getEvent(String eventUID) {
        Event event = null;
        for (Event cachedEvent : cachedEvents) {
            if (cachedEvent.getEventUID().equals(eventUID)) {
                event = cachedEvent;
                break;
            }
        }
        return event;
    }

    /**
     * @return
     */
    public List<User> getCachedUsers() {
        return cachedUsers;
    }

    /**
     * @return
     */
    public List<Account> getCachedAccounts() {
        return cachedAccounts;
    }

    /**
     * @return
     */
    public List<Event> getCachedEvents() {
        return cachedEvents;
    }

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove(observer);
    }
}
