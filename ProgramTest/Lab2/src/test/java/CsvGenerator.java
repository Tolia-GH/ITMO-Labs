import com.opencsv.CSVWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.tolia.function.logfunction.Ln;
import org.tolia.function.logfunction.frombasefunction.Lg;
import org.tolia.function.logfunction.frombasefunction.Log;
import org.tolia.function.trigfunction.*;
import org.tolia.function.trigfunction.frombasefunction.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvGenerator {

    final double targetAcc = 0.0001;
    final int terms = 1;
    final int taylor_terms = 20;
    final double testAcc = 0.1;
    String fileSin = "src/test/resources/stubTables/sin.csv";
    String fileCos = "src/test/resources/stubTables/cos.csv";
    String fileTan = "src/test/resources/stubTables/tan.csv";
    String fileCot = "src/test/resources/stubTables/cot.csv";
    String fileCsc = "src/test/resources/stubTables/csc.csv";
    String fileSec = "src/test/resources/stubTables/sec.csv";
    String fileLn = "src/test/resources/stubTables/ln.csv";
    String fileLog2 = "src/test/resources/stubTables/log2.csv";
    String fileLog3 = "src/test/resources/stubTables/log3.csv";
    String fileLog5 = "src/test/resources/stubTables/log5.csv";
    String fileLg = "src/test/resources/stubTables/lg.csv";

    @Test
    void clearAllCsv() throws IOException {
        List<String> filePath = new ArrayList<>();
        filePath.add(fileSin);
        filePath.add(fileCos);
        filePath.add(fileTan);
        filePath.add(fileCot);
        filePath.add(fileSec);
        filePath.add(fileCsc);
        filePath.add(fileLn);
        filePath.add(fileLog2);
        filePath.add(fileLog3);
        filePath.add(fileLog5);
        filePath.add(fileLg);

        for (String s : filePath) {
            CSVWriter writer = new CSVWriter(new FileWriter(s, false));
            writer.close();
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
    void generateSin(double x) {
        Sin sin = new Sin();
        try {
            sin.writeResultToCSV(x, sin.getValue(x, targetAcc, terms), fileSin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
    void generateCos(double x) {
        Cos cos = new Cos();
        try {
            cos.writeResultToCSV(x, cos.getValue(x, targetAcc, terms), fileCos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
    void generateTan(double x) {
        Tan tan = new Tan();
        try {
            tan.writeResultToCSV(x, tan.getValue(x, targetAcc, terms), fileTan);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
    void generateCot(double x) {
        Cot cot = new Cot();
        try {
            cot.writeResultToCSV(x, cot.getValue(x, targetAcc, terms), fileCot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
    void generateSec(double x) {
        Sec sec = new Sec();
        try {
            sec.writeResultToCSV(x, sec.getValue(x, targetAcc, terms), fileSec);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
    void generateCsc(double x) {
        Csc csc = new Csc();
        try {
            csc.writeResultToCSV(x, csc.getValue(x, targetAcc, terms), fileCsc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
    void generateLn(double x) {
        Ln ln = new Ln();
        try {
            ln.writeResultToCSV(x, ln.getValue(x, targetAcc, terms), fileLn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
    void generateLog2(double x) {
        Log log2 = new Log();
        try {
            log2.writeResultToCSV(x, log2.getValue(2, x, targetAcc, terms), fileLog2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
    void generateLog3(double x) {
        Log log3 = new Log();
        try {
            log3.writeResultToCSV(x, log3.getValue(3, x, targetAcc, terms), fileLog3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
    void generateLog5(double x) {
        Log log5 = new Log();
        try {
            log5.writeResultToCSV(x, log5.getValue(5, x, targetAcc, terms), fileLog5);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @ParameterizedTest
    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
    void generateLg(double x) {
        Lg lg = new Lg();
        try {
            lg.writeResultToCSV(x, lg.getValue(x, targetAcc, terms), fileLg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
