import java.io.InputStream;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class OWL {
	public static void runExamples() {
		System.out.println("OWL Examples");
		System.out.println("");
		
		// use the FileManager to find the input file
		final String fileName = ".\\resources\\eswc-data.rdf";
		InputStream in = FileManager.get().open(fileName);
		if (in == null) {
		    throw new IllegalArgumentException("File: " + fileName + " not found.");
		}
		
		// create the base model
		String NS = "http://www.eswc2006.org/technologies/ontology#";
		OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		base.read(in, "RDF/XML");

		// create the reasoning model using the base
		OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, base);

		// create a dummy paper for this example
		OntClass paper = base.getOntClass(NS + "Paper");
		Individual p1 = base.createIndividual(NS + "paper1", paper);

		// list the asserted types
		for (Iterator<Resource> i = p1.listRDFTypes(false); i.hasNext(); ) {
		    System.out.println(p1.getURI() + " is asserted in class " + i.next());
		}

		// list the inferred types
		p1 = inf.getIndividual( NS + "paper1" );
		for (Iterator<Resource> i = p1.listRDFTypes(false); i.hasNext(); ) {
		    System.out.println(p1.getURI() + " is inferred to be in class " + i.next());
		}
	}
}
