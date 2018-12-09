package com.Services;

public class DatabaseService {
    /**
     *
     */
    private static DatabaseService instance;

    /**
     *
     */
    private DatabaseService() {
    }


    /**
     * @return
     */
    public static DatabaseService getInstance() {
        if (instance == null)
            instance = new DatabaseService();
        return instance;
    }

}
