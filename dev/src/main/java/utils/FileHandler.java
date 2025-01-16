package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHandler {

   public  static final String COMPTES_FILE =System.getProperty("user.dir") + File.separator + "files" + File.separator + "comptes.json";

    public  static final String ORGANIZATION_FILE =System.getProperty("user.dir") + File.separator + "files" + File.separator + "organizations.json";
    public  static final String AUDITS_FILE =System.getProperty("user.dir") + File.separator + "files" + File.separator + "audits.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> loadDataFromJson(String filePath, TypeReference<List<T>> typeReference) throws IOException {
        return objectMapper.readValue(new File(filePath), typeReference);
    }

    public static <T> void saveDataInJson(String filePath, List<T> data) throws IOException {
        System.out.println(data);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
    }
}
