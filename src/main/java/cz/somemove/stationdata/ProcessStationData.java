package cz.somemove.stationdata;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import cz.somemove.stationdata.vocabs.Goodrelations;
import cz.somemove.stationdata.vocabs.Rail;
import cz.somemove.stationdata.vocabs.TIO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessStationData {
    public static void main( String[] args ) throws Exception {
        Model model = ModelFactory.createDefaultModel();

        try (InputStream is = new FileInputStream("data/vlastnik.xlsx")) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() > 0) {
                    createOwner(
                        model,
                        Integer.parseInt(row.getCell(0).getStringCellValue()),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue()
                    );
                }
            }
        }

        try (InputStream is = new FileInputStream("data/sr70.xlsx")) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() > 0) {
                    int code = Integer.parseInt(row.getCell(0).getStringCellValue());
                    String name = row.getCell(1).getStringCellValue();
                    int type = Integer.parseInt(row.getCell(9).getStringCellValue());
                    int owner = Integer.parseInt(row.getCell(17).getStringCellValue());

                    // Filter out non-passenger railway stations
                    if (type == 1 | type == 3 | type == 7 | type == 61 | type == 62 | type == 63 | type == 65 | type == 82) {
                        createStation(model, code, name, owner);
                    }
                }
            }
        }

        try (OutputStream os = new FileOutputStream("data/output.rdf")) {
            model.setNsPrefix("rail", Rail.NS);
            model.setNsPrefix("foaf", FOAF.NS);
            model.setNsPrefix("gr", Goodrelations.NS);
            model.setNsPrefix("rdfs", RDFS.getURI());
            model.setNsPrefix("rdf", RDF.getURI());
            model.setNsPrefix("tio", TIO.NS);
            //model.write(fos, "RDF/XML-ABBREV");
            model.write(os);
        }
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
        model.add(stationResource, RDF.type, TIO.POI);
        model.add(stationResource, Rail.MANAGEDBY, ownerResource);
        model.add(stationResource, RDFS.label, name);
    }
}
