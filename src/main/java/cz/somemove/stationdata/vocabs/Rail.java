package cz.somemove.stationdata.vocabs;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class Rail {
    public static final String NS = "http://ontologi.es/rail/vocab#";
    
    private static final Model model = ModelFactory.createDefaultModel();
    
    public static final Resource STATION = model.createResource(NS + "Station");
    
    public static final Property MANAGEDBY = model.createProperty(NS, "managed_by");
}