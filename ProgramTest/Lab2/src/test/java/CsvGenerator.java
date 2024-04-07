import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.tolia.function.trigfunction.Cos;
import org.tolia.function.trigfunction.Sin;

import java.io.IOException;

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
    @ParameterizedTest
    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~2(0.01)~100(3).csv")
    void generateSin(double x) {
        Sin sin = new Sin();
        try {
            sin.writeResultToCSV(x, sin.getValue(x, targetAcc, terms), fileSin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void generateCos(double x) {
        Cos cos = new Cos();
        try {
            cos.writeResultToCSV(x, cos.getValue(x, targetAcc, terms), fileSin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
