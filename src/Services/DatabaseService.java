package Services;

public class DatabaseService {
    private static DatabaseService instance;

    private DatabaseService() {
        //   queries = new ArrayList<>();
    }

    //private ArrayList<IDatabaseQuery> queries;

    public static DatabaseService getInstance() {
        if (instance == null)
            instance = new DatabaseService();
        return instance;
    }

//  public synchronized void addQueryWithCallback(IDatabaseQuery query) {
//      queries.add(query);
//  }

//  private void performQuery() {
// perform the query and call the callback
//     for (IDatabaseQuery query : queries)
//          query.doQuery();
//}
}
