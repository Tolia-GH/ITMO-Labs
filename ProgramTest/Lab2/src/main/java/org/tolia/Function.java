package org.tolia;

import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

public abstract class Function {
    public void writeResultToCSV(double x,double result, String filePath) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(filePath,true));
        //Create record
        String []record ={Double.toString(x),Double.toString(result)};
        //Write the record to file
        writer.writeNext(record);
        //close the writer
        writer.close();
    }
}
