package helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gds.swe.challenge.model.FileInfo;
import com.gds.swe.challenge.repository.EmployeeRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class CSVvalidator {
    static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "Login", "Name", "Salary" };
    static String cvsSplitBy = ",";

    public static boolean hasCSVFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

    public static List<FileInfo> csvToString(InputStream is, EmployeeRepo repository) {
        List<FileInfo> fileInfos = new ArrayList<>();
        try
        {
            String line;

            BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            //Skip first line
            line = fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                char firstChar = line.charAt(0);
                //Check for # first char in every line
                if(firstChar != '#') {
                    if(validateRecord(line, repository)) {
                        //Split record into individual data
                        String[] data = line.split(cvsSplitBy);
                        System.out.println(line);
                        System.out.println(data[0]);
                        System.out.println(data[1]);
                        System.out.println(data[2]);
                        System.out.println(Double.parseDouble(data[3]));

                        FileInfo fileinfo = new FileInfo(
                                data[0],
                                data[1],
                                data[2],
                                Double.parseDouble(data[3]));

                        fileInfos.add(fileinfo);
                    }
                }
            }

            return fileInfos;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return fileInfos;
    }

    //Record Validation
    private static boolean validateRecord(String record, EmployeeRepo repository){
        boolean result = false;
        String[] data = record.split(cvsSplitBy);
        //Every field has to have values
        if(data.length == 4){
            //Salary Validation
            //Check Decimal places lesser than 2
            if(BigDecimal.valueOf( Double.parseDouble(data[3])).scale() < 2){
                //Check negative salary
                if(Double.compare(Double.parseDouble(data[3]), 0.0) < 0){
                    FileInfo employee = repository.findBylogin(data[1]);
                    //Check existing Login
                    if(employee == null){
                        result = true;
                    }
                    //Check existing ID if login is true
                    else if (employee.getId().equals(data[0])) {
                        result = true;
                    }
                }
            }
        }

        return result;
    }

}
