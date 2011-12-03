package name.jspetrak.stationdata.vocabs;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Rail {
    public static final String NS = "http://ontologi.es/rail/vocab#";
    
    private static Model model = ModelFactory.createDefaultModel();
    
    public static final Resource STATION = model.createResource(NS + "Station");
    
    public static final Property MANAGERBY = model.createProperty(NS, "managed_by");
}