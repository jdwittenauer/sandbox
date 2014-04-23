import java.io.InputStream;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.*;

public class Program {
	public static void main(String[] args) {
		// some definitions
		String personURI 	= "http://somewhere/JohnSmith";
		String givenName    = "John";
		String familyName   = "Smith";
		String fullName     = givenName + " " + familyName;

		// create an empty Model
		Model model = ModelFactory.createDefaultModel();

		// create the resource and add properties
		Resource johnSmith
		  = model.createResource(personURI)
		         .addProperty(VCARD.FN, fullName)
		         .addProperty(VCARD.N,
		                      model.createResource()
		                           .addProperty(VCARD.Given, givenName)
		                           .addProperty(VCARD.Family, familyName));
		johnSmith.addProperty(VCARD.EMAIL, "johnsmith@emailaddress.com");
		johnSmith.addProperty(VCARD.BDAY, "01/01/1969");
		
		// list the statements in the Model
		StmtIterator iter = model.listStatements();

		// print out the predicate, subject and object of each statement
		System.out.println("Subject\t\t\t\t" + "Predicate\t\t\t\t\t" + "Object");
		
		while (iter.hasNext()) {
		    Statement stmt      = iter.nextStatement();  // get next statement
		    Resource  subject   = stmt.getSubject();     // get the subject
		    Property  predicate = stmt.getPredicate();   // get the predicate
		    RDFNode   object    = stmt.getObject();      // get the object

		    System.out.print(subject.toString() + "\t");
		    System.out.print(predicate.toString() + "\t");
		    
	    	if (predicate.toString().length() < 40) {
	    		System.out.print("\t");
	    	}
		    
		    if (object instanceof Resource) {
		       System.out.print(object.toString());
		    } else {
		        // object is a literal
		        System.out.print("\"" + object.toString() + "\"");
		    }

		    System.out.println("");
		}
		
		// now write the model in XML form to a file
		System.out.println("");
		model.write(System.out);
		
		// this time, read in a model from an external file
		Model model2 = ModelFactory.createDefaultModel();

		// use the FileManager to find the input file
		final String fileName = ".\\resources\\vcard-data.rdf";
		InputStream in = FileManager.get().open(fileName);
		if (in == null) {
		    throw new IllegalArgumentException("File: " + fileName + " not found.");
		}

		// read the RDF/XML file
		model2.read(in, null);

		// write it to standard out
		System.out.println("");
		model2.write(System.out);
	}
}
