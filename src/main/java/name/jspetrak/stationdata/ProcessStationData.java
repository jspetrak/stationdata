package name.jspetrak.stationdata;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.io.File;
import java.io.FileOutputStream;
import name.jspetrak.stationdata.vocabs.Goodrelations;
import name.jspetrak.stationdata.vocabs.Rail;
import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableRow;

public class ProcessStationData {
    public static void main( String[] args ) throws Exception {
        Model model = ModelFactory.createDefaultModel();
        
        OdfSpreadsheetDocument osd = OdfSpreadsheetDocument.loadDocument(new File("data/vlastnik.ods"));
        OdfTable table = osd.getTableList().get(0);
        for (OdfTableRow row : table.getRowList()) {
            if (row.getRowIndex() > 0) {
                int code = Integer.valueOf(row.getCellByIndex(0).getStringValue()).intValue();
                String name = row.getCellByIndex(1).getStringValue();
                String abbr = row.getCellByIndex(2).getStringValue();
                
                createOwner(model, code, name, abbr);
            }
        }
        osd.close();
        
        osd = OdfSpreadsheetDocument.loadDocument(new File("data/sr70.ods"));
        table = osd.getTableList().get(0);
        for (OdfTableRow row : table.getRowList()) {
            if (row.getRowIndex() > 0) {
                int code = Integer.valueOf(row.getCellByIndex(0).getStringValue()).intValue();
                String name = row.getCellByIndex(1).getStringValue();
                int owner = Integer.valueOf(row.getCellByIndex(17).getStringValue()).intValue();
                
                createStation(model, code, name, owner);
            }
        }
        
        FileOutputStream fos = new FileOutputStream(new File("data/output.rdf"));
        model.setNsPrefix("rail", Rail.NS);
        model.setNsPrefix("foaf", FOAF.NS);
        model.setNsPrefix("gr", Goodrelations.NS);
        model.setNsPrefix("rdfs", RDFS.getURI());
        model.setNsPrefix("rdf", RDF.getURI());
        model.write(fos, "RDF/XML-ABBREV");
    }
    
    static void createOwner(Model model, int code, String name, String abbr) {
        Resource ownerResource = model.createResource("http://stationdata.example.cz/owner/"+code);
        
        model.add(ownerResource, RDF.type, Goodrelations.BusinessEntity);
        model.add(ownerResource, RDF.type, FOAF.Agent);
        model.add(ownerResource, Goodrelations.legalName, name);
        model.add(ownerResource, RDFS.label, abbr);
    }

    static void createStation(Model model, int code, String name, int owner) {
        Resource stationResource = model.createResource("http://stationdata.example.cz/station/"+code);
        Resource ownerResource = model.createResource("http://stationdata.example.cz/owner/"+owner);
        
        model.add(stationResource, RDF.type, Rail.STATION);
        model.add(stationResource, Rail.MANAGERBY, ownerResource);
        model.add(stationResource, RDFS.label, name);
    }
}
