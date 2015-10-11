package cz.somemove.stationdata.vocabs;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

public class TIO {
    public static final String NS = "http://purl.org/tio/ns#";
    
    private static final Model model = ModelFactory.createDefaultModel();
    
    /**
     * From TIO definition: A point or area of interest, e.g. a museum, a train station, 
     * an airport, a bus stop, a store, a box office, etc.
     */
    public static final Resource POI = model.createResource(NS + "POI");
}
