package com.Services.Database;

import com.Entities.Account;
import com.Entities.Event;
import com.Entities.Location;
import com.Entities.User;
import com.google.firebase.database.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseService implements IObservable {
    /**
     *
     */
    private static DatabaseService instance;
    private ArrayList<IObserver> observers;
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
        cachedAccounts = new ArrayList<>();
        cachedEvents = new ArrayList<>();
        cachedUsers = new ArrayList<>();
        userReference = FirebaseDatabase.getInstance()
                .getReference("/users");

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("OnDataChange in users");

                cacheUsers((Map<String, User>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
        accountReference = FirebaseDatabase.getInstance()
                .getReference("/accounts");

        accountReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("OnDataChange in accounts");
                cacheAccounts((Map<String, Account>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
        eventReference = FirebaseDatabase.getInstance()
                .getReference("/events");

        eventReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("OnDataChange in events");

                cacheEvents((Map<String, Event>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });


    }

    /**
     * @return
     */
    public static DatabaseService getInstance() {
        if (instance == null)
            instance = new DatabaseService();
        return instance;
    }

    private void cacheUsers(Map<String, User> userMap) {
        cachedUsers.clear();
        System.out.println("Caching users");
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            Map singleUser = (Map) entry.getValue();
            cachedUsers.add(new User(
                    singleUser.get("name").toString(),
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

            JSONObject obj = new JSONObject(singleAccount);

            ArrayList<User> friends = new ArrayList<>();
            User user = new User(
                    obj.getJSONObject("user").getString("name"),
                    obj.getJSONObject("user").getString("email"),
                    obj.getJSONObject("user").getString("uid")
            );
            Account account = new Account(user);
            account.setFireBaseMessagingId(obj.getString("fireBaseMessagingId"));
            if (singleAccount.containsKey("friends")) {
                JSONArray storedFriends = obj.getJSONArray("friends");
                for (int idx = 0; idx < storedFriends.length(); idx++)
                    friends.add(new User(
                            storedFriends.getJSONObject(idx).getString("name"),
                            storedFriends.getJSONObject(idx).getString("email"),
                            storedFriends.getJSONObject(idx).getString("uid")
                    ));
                account.setFriends(friends);
            }
            cachedAccounts.add(account);

        }
        observers.forEach(observer -> observer.notifyAccountDataChanged(cachedAccounts));
    }

    private void cacheEvents(Map<String, Event> eventMap) {
        cachedEvents.clear();
        System.out.println("Caching events");
        for (Map.Entry<String, Event> entry : eventMap.entrySet()) {
            Map singleEvent = (Map) entry.getValue();
            JSONObject obj = null;
            try {
                obj = new JSONObject(singleEvent);

                User eventCreator = new User(
                        obj.getJSONObject("eventCreator").getString("name"),
                        obj.getJSONObject("eventCreator").getString("email"),
                        obj.getJSONObject("eventCreator").getString("uid")
                );
                Map eventLocationMap = (Map) singleEvent.get("location");
                Location eventLocation = new Location(
                        (double) eventLocationMap.get("latitude"),
                        (double) eventLocationMap.get("longitude")
                );
                cachedEvents.add(
                        new Event(eventLocation,
                                singleEvent.get("eventName").toString(),
                                singleEvent.get("eventUID").toString(),
                                singleEvent.get("expirationDateAsString").toString(),
                                eventCreator)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        observers.forEach(observer -> observer.notifyEventDataChanged(cachedEvents));
    }

    /**
     * @param user
     */
    public void insertUser(User user) {
        System.out.println("Adding: " + user + " to database.");
        FirebaseDatabase.getInstance().getReference("users/" + user.getUid()).setValueAsync(user);
    }

    /**
     * @param user
     */
    public void deleteUser(User user) {
        System.out.println("Removing: " + user + " from database.");
        FirebaseDatabase.getInstance().getReference("users/" + user.getUid()).removeValueAsync();
    }

    /**
     * @param account
     */
    public void insertAccount(Account account) {
        System.out.println("Adding: " + account + " to database.");
        FirebaseDatabase.getInstance().getReference("accounts/" + account.getUser().getUid()).setValueAsync(account);
    }

    /**
     * @param account
     */
    public void deleteAccount(Account account) {
        System.out.println("Removing: " + account + " from database.");
        FirebaseDatabase.getInstance().getReference("accounts/" + account.getUser().getUid()).removeValueAsync();
    }

    /**
     * @param event
     */
    public void insertEvent(Event event) {
        System.out.println("Adding: " + event + " to database.");
        FirebaseDatabase.getInstance().getReference("events/" + event.getEventUID()).setValueAsync(event);
    }

    /**
     * @param event
     */
    public void deleteEvent(Event event) {
        System.out.println("Removing: " + event + " from database.");
        FirebaseDatabase.getInstance().getReference("events/" + event.getEventUID()).removeValueAsync();
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
