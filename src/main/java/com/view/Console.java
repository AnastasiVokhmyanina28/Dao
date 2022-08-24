package com.view;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import com.controller.logic.FileOperation;
import com.model.DictionaryType;
import com.controller.logic.Dictionary;
import com.controller.logic.ChoiceOfAction;
import org.springframework.beans.factory.annotation.Autowired;

public class Console {

    public static final String NO_COMMAND = "There is no such command. Try again!";
    public static final String KEY_DELETE = "The key has been successfully deleted!";
    public static final String ENTER_KEY = "Please enter the key";
    public static final String ENTER_VALUE = "Please enter the value";
    public static final String SELECT_DICTIONARY = "Select dictionary";
    public static final String SELECT = "Your choice";
    public static final String SELECT_THE_COMMAND = "Select the action you want to perform with this dictionary ";
    public static final String DICTIONARY_TYPE_ONE = "\t1 English-Russian dictionary";
    public static final String DICTIONARY_TYPE_TWO = "\t2 Digital dictionary";
    public static final String READ = "\t1 Reading a list of pairs";
    public static final String DELETE = "\t2 Deleting an entry";
    public static final String SEARCH = "\t3 Record search";
    public static final String ADD_ENTRY = "\t4 Adding an entry";
    public static final String CHANGE_THE_DICTIONARY = "\t5 Change the dictionary";
    public static final String EXIT = "\t6 Logout";
    public static final String SYSTEM = " Choose a system";
    public static final String MAP_DICTIONARY = "\t1 Work with map";
    public static final String FILE_DICTIONARY = "\t2 Work with file";
    private final int userСhoice = 1;
    private final Scanner scanner;
    private ChoiceOfAction dictionary = null;
    private Map<Integer, ChoiceOfAction> mapDictionaries;
    private boolean isRunningConsole = false;
    @Autowired
    private Map<Integer, DictionaryType> dictionaryTypeMaps;

    public Console() {
        scanner = new Scanner(System.in, "windows-1251");
    }

    public int systemSelectionMenu() {
        System.out.println(SYSTEM);
        System.out.println(MAP_DICTIONARY);
        System.out.println(FILE_DICTIONARY);
        System.out.println(SELECT + DictionaryType.getSymbol());
        return scanner.nextInt();
    }

    private void systemSelection(int choice) {
        Map<Integer, ChoiceOfAction> dictionaries = new HashMap<>();
        for (Map.Entry<Integer, DictionaryType> pair : dictionaryTypeMaps.entrySet()) {
            if (choice == userСhoice) {
                dictionaries.put(pair.getKey(), new Dictionary(pair.getValue()));
            }
            else {
                dictionaries.put(pair.getKey(), new FileOperation(pair.getValue()));
            }
        }
        this.mapDictionaries = dictionaries;
    }

     public void start() {
         while (mapDictionaries == null) {
             systemSelection(systemSelectionMenu());
         }
        while (dictionary == null) {
            int dictionarySelection = this.menuChoiceDictionary();
            if (!mapDictionaries.isEmpty()) {
                chooseDictionary(dictionarySelection);
            }
        }
            while (!isRunningConsole) {
                int choiceOfActions = this.choiceOfAction();
                choice(choiceOfActions, dictionary);
            }
        }

    public int menuChoiceDictionary() {
        System.out.println(SELECT_DICTIONARY);
        System.out.println(DICTIONARY_TYPE_ONE);
        System.out.println(DICTIONARY_TYPE_TWO);
        System.out.println(SELECT + DictionaryType.getSymbol());
        return scanner.nextInt();
    }

    private void chooseDictionary(int chosenAction) {
        this.dictionary = mapDictionaries.get(chosenAction);
    }

    public int choiceOfAction() {
        System.out.println(SELECT_THE_COMMAND + DictionaryType.getSymbol());
        System.out.println(READ);
        System.out.println(DELETE);
        System.out.println(SEARCH);
        System.out.println(ADD_ENTRY);
        System.out.println(CHANGE_THE_DICTIONARY);
        System.out.println(EXIT);
        System.out.println(SELECT + DictionaryType.getSymbol());
        return scanner.nextInt();
    }

    private void choice(int chosenAction, ChoiceOfAction dictionary) {

        switch (chosenAction) {
            case 1:
                System.out.println(dictionary.fileReading());
                break;
            case 2:
                System.out.println(ENTER_KEY);
                String userKey = scanner.next();
                try {
                   System.out.println(dictionary.removeRecord(userKey));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                System.out.println(ENTER_KEY);
                String usKey = scanner.next();
                System.out.println(dictionary.search(usKey));
                break;
            case 4:
                System.out.println(ENTER_KEY);
                String key = scanner.next();
                System.out.println(ENTER_VALUE);
                String value = scanner.next();
                System.out.println(dictionary.addAnEntry(key, value));
                break;
            case 5:
                this.dictionary = null;
                while (this.dictionary == null){
                int nextDictionary = menuChoiceDictionary();
                chooseDictionary(nextDictionary);
                }
                break;
            case 6:
                this.isRunningConsole = true;
                break;
            default:
                System.out.println(NO_COMMAND);
                break;
        }
    }
}