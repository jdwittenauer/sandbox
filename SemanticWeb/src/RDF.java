import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.*;

public class RDF {
	public static void runExamples() {
		System.out.println("RDF Examples");
		System.out.println("");
		
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
		
		System.out.println("");
		
		// now write the model in XML form to a file
		model.write(System.out);
		System.out.println("");
		
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
		model2.write(System.out);
		System.out.println("");
		
		// retrieve the John Smith vcard resource from the model
		Resource vcard = model.getResource(personURI);
		
		// retrieve the value of the FN property
		Resource name = (Resource) vcard.getProperty(VCARD.N).getObject();
		System.out.println(name.toString());
		System.out.println("");
		
		// list vcards
		ResIterator iter2 = model2.listSubjectsWithProperty(VCARD.FN);
		while (iter2.hasNext()) {
		    Resource r = iter2.nextResource();
		    System.out.println(r.getURI());
		}
		
		System.out.println("");
		
        // select all the resources with a VCARD.FN property whose value ends with "Smith"
		StmtIterator iter3 = model.listStatements(
			    new SimpleSelector(null, VCARD.FN, (RDFNode) null) {
			        public boolean selects(Statement s) {
			                return s.getString().endsWith("Smith");
			        }
			    });
        if (iter3.hasNext()) {
            System.out.println("The database contains vcards for:");
            
            while (iter3.hasNext()) {
                System.out.println("  " + iter3.nextStatement().getString());
            }
        } 
        else {
            System.out.println("No Smith's were found in the database");
        } 
		
		System.out.println("");
		
		// create a bag
		Bag smiths = model.createBag();

		// select all the resources with a VCARD.FN property whose value ends with "Smith"
		StmtIterator iter4 = model.listStatements(
		    new SimpleSelector(null, VCARD.FN, (RDFNode) null) {
		        public boolean selects(Statement s) {
		                return s.getString().endsWith("Smith");
		        }
		    });
		
		// add the Smith's to the bag
		while (iter4.hasNext()) {
		    smiths.add(iter4.nextStatement().getSubject());
		}
		
		// print out the members of the bag
		NodeIterator iter5 = smiths.iterator();
		if (iter5.hasNext()) {
		    System.out.println("The bag contains:");
		    
		    while (iter5.hasNext()) {
		        System.out.println("  " +
		            ((Resource) iter5.next())
		                            .getProperty(VCARD.FN)
		                            .getString());
		    }
		} 
		else {
		    System.out.println("The bag is empty");
		}
	}
}
