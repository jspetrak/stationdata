This project is about Czech railway stations data.

It provides Java-implemented parser from Open Document Format spreadsheets to RDF/XML files. The source files are provided as original files are published in DBF format using unusual DOS/OS2-852 encoding.

See http://jdem.cz/sxdw7 for source data.

To run project just Open as a Maven project in Netbeans and run.
Alternatively use command: mvn clean package exec:java
Output is generate into data/output.rdf.

Please tweet with #stationdata hashtag.

## Known issues

* [ODFTOOLKIT-404](https://issues.apache.org/jira/browse/ODFTOOLKIT-404) ODF Toolkit uses HP Jena internally so we have to depend on both HP and Apache Jena distributions until fixed.