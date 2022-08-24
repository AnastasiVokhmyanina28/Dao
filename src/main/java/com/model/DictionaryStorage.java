package com.model;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;

public class DictionaryStorage {

    private final Map<String, String> localMap = new HashMap<>();
    private final String path;


    public DictionaryStorage(String path) {
        this.path = path;
    }

    public Map<String, String> getData() {
      String line = "";
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new ClassPathResource(path).getFile()))) {
            while ((line = bufferedReader.readLine()) !=null) {
                parseLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localMap;
    }

    private void parseLine(String line) {
        String[] lineParts = line.split(DictionaryType.getSymbol());
        localMap.put(lineParts[0], lineParts[1]);
    }

    public void saveData() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new ClassPathResource(path).getFile()))){
            for (Map.Entry<String, String> entry : localMap.entrySet()) {
                bufferedWriter.write(entry.getKey() + DictionaryType.getSymbol() + entry.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}