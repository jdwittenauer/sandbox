import java.util.Set;
import java.net.UnknownHostException;
import com.mongodb.*;

public class MongoDB {
	public static void main(String[] args) {
		System.out.println("Initalizing MongoDB connection...");
		MongoClient mongoClient = null;
		DB db = null;

		try {
			mongoClient = new MongoClient("localhost");
			db = mongoClient.getDB("mydb");
		} catch (UnknownHostException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}

		if (db != null) {
			System.out.println("Connection established...");

			System.out.println("Getting collections...");
			Set<String> collections = db.getCollectionNames();
			if (collections.isEmpty()) {
				System.out.println("None exists - creating collection...");
				db.createCollection("documents", null);
			}

			System.out.println("Collection names:");
			for (String s : collections) {
				System.out.println(s);
			}

			System.out.println("Using first collection...");
			DBCollection collection = db.getCollection("documents");

			if (collection.getCount() == 0) {
				System.out.println("No documents found - inserting data...");
				for (int i = 0; i < 100; i++) {
					BasicDBObject doc = new BasicDBObject("name", "MongoDB")
							.append("i", i)
							.append("type", "database")
							.append("count", 1)
							.append("info",
									new BasicDBObject("x", 203)
											.append("y", 102));
					collection.insert(doc);
				}
			} else {
				System.out.println("Data found - printing first document...");
				DBObject myDoc = collection.findOne();
				System.out.println(myDoc);
			}

			DBCursor cursor = collection.find();
			BasicDBObject query = new BasicDBObject("i", 70);
			cursor = collection.find(query);

			System.out.println("Creating query for document 70...");
			try {
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}
			} finally {
				cursor.close();
			}

			DBCursor cursor2 = collection.find();
			query = new BasicDBObject("i", new BasicDBObject("$gt", 90));
			cursor2 = collection.find(query);

			System.out.println("Creating query for documents > 90...");
			try {
				while (cursor2.hasNext()) {
					System.out.println(cursor2.next());
				}
			} finally {
				cursor2.close();
			}
		}

		System.out.println("Closing connection...");
		mongoClient.close();
		System.out.println("Connection closed.  Goodbye!");
	}
}
