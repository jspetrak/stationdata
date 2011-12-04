package name.jspetrak.stationdata.vocabs;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 *
 * @author Josef Petrak (me@jspetrak.name)
 */
public class TIO {
    public static final String NS = "http://purl.org/tio/ns#";
    
    private static final Model model = ModelFactory.createDefaultModel();
    
    /**
     * From TIO definition: A point or area of interest, e.g. a museum, a train station, 
     * an airport, a bus stop, a store, a box office, etc.
     */
    public static final Resource POI = model.createResource(NS + "POI");
}
