package sample;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;

public class Main {
	public Main() {
	}

	public static void main(String[] args) {
		try {
			System.out.println("rebuildIndexes");
			Indexer indexer = new Indexer();
			indexer.rebuildIndexes();
			System.out.println("rebuildIndexes done");

			System.out.println("performSearch");
			SearchEngine se = new SearchEngine();
			TopDocs topDocs = se.performSearch("Notre Dame museum", 100);

			System.out.println("Results found: " + topDocs.totalHits);
			ScoreDoc[] hits = topDocs.scoreDocs;
			
			for (int i = 0; i < hits.length; i++) {
				Document doc = se.getDocument(hits[i].doc);
				System.out.println(doc.get("name") + " " + doc.get("city")
						+ " (" + hits[i].score + ")");

			}
			
			System.out.println("performSearch done");
		} catch (Exception e) {
			System.out.println("Exception caught.\n");
		}
	}

}
