package sample;

import java.io.IOException;
import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
	public Indexer() {
	}

	private IndexWriter indexWriter = null;

	public IndexWriter getIndexWriter(boolean create) throws IOException {
		if (indexWriter == null) {
			Directory indexDir = FSDirectory.open(new File("index-directory"));
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StandardAnalyzer());
			indexWriter = new IndexWriter(indexDir, config);
		}
		return indexWriter;
	}

	public void closeIndexWriter() throws IOException {
		if (indexWriter != null) {
			indexWriter.close();
		}
	}

	public void indexHotel(Hotel hotel) throws IOException {
		System.out.println("Indexing hotel: " + hotel);
		IndexWriter writer = getIndexWriter(false);
		Document doc = new Document();
		doc.add(new StringField("id", hotel.getId(), Field.Store.YES));
		doc.add(new StringField("name", hotel.getName(), Field.Store.YES));
		doc.add(new StringField("city", hotel.getCity(), Field.Store.YES));
		String fullSearchableText = hotel.getName() + " " + hotel.getCity()
				+ " " + hotel.getDescription();
		doc.add(new TextField("content", fullSearchableText, Field.Store.NO));
		writer.addDocument(doc);
	}

	public void rebuildIndexes() throws IOException {
		getIndexWriter(true);
		Hotel[] hotels = HotelDatabase.getHotels();

		for (Hotel hotel : hotels) {
			indexHotel(hotel);
		}

		closeIndexWriter();
	}
}
