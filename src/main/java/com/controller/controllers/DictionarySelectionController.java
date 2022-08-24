package com.controller.controllers;

import com.config.JdbcConfig;
import com.controller.logic.*;
import com.model.DictionaryType;
import com.model.dto.ListOfDictionaries;
import com.services.dao.impl.DictionaryDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("dictionary")
public class DictionarySelectionController {

    @Autowired
    private Map<Integer, DictionaryType> dictionaryTypeMaps;
    @Autowired
    private JdbcConfig jdbcConfig;
    private ChoiceOfAction dictionary;

    @GetMapping("dictionaries")
    public String chooseDictionary() {
        return "SelectingADictionary";
    }

    @GetMapping("system")
    public String selectingASysteam() {
        return "SelectingASysteam";
    }

    @GetMapping("selection")
    public String dictionarySelection() {
        return "SelectAnAction";
    }


    @PostMapping("selection")
    public String dictionarySelection(@RequestParam(name = "choiceDictionary") Integer choiceDictionary,
                                      @RequestParam(name = "systemChoice") String systemChoice,
                                      Model model) {
        switch (systemChoice) {
            case "map":
                dictionary = new Dictionary(dictionaryTypeMaps.get(choiceDictionary));
                break;
            case "jdbc":
                DictionaryDAOImpl dao = new DictionaryDAOImpl(jdbcConfig);
                dictionary = new Dao(dao);
                List<ListOfDictionaries> a = dao.getListOfDictionary();
                model.addAttribute("array", a);
                return "SelectingADictionaryDAO";
            case "file":
                dictionary = new FileOperation(dictionaryTypeMaps.get(choiceDictionary));
                break;
        }
        return "SelectAnAction";
    }

    @PostMapping("reading")
    public String fileReading(Model model) {
        model.addAttribute("pairs", dictionary.fileReadingList());
        return "Reading";
    }

    @GetMapping("delete")
    public String removeRecord() {
        return "Delete";
    }

    @PostMapping("delete")
    public String removeRecord(@RequestParam(value = "key") String key,
                               Model model) {
        model.addAttribute("remove", dictionary.removeRecord(key));
        return "Delete";
    }

    @GetMapping("add")
    public String addAnEntry() {
        return "AddEntryToDictionary";
    }

    @PostMapping("add")
    public String addAnEntry(@RequestParam(value = "key") String key,
                             @RequestParam(value = "value") String value,
                             Model model) {
        model.addAttribute("add", dictionary.addAnEntry(key, value));
        return "AddEntryToDictionary";
    }

    @GetMapping("search")
    public String search() {
        return "Search";
    }

    @PostMapping("search")
    public String search(@RequestParam(value = "key") String key,
                         Model model) {
        model.addAttribute("search", dictionary.search(key));
        return "Search";
    }
}
