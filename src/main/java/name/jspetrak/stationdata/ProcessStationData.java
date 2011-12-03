package name.jspetrak.stationdata;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.io.File;
import java.io.FileOutputStream;
import name.jspetrak.stationdata.vocabs.Goodrelations;
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
        
        FileOutputStream fos = new FileOutputStream(new File("data/output.rdf"));       
        model.write(fos);
    }
    
    static void createOwner(Model model, int code, String name, String abbr) {
        Resource ownerResource = model.createResource("http://stationdata.example.cz/owner/"+code);
        
        model.add(ownerResource, RDF.type, Goodrelations.BusinessEntity);
        model.add(ownerResource, Goodrelations.legalName, name);
        model.add(ownerResource, RDFS.label, abbr);
    }
}
