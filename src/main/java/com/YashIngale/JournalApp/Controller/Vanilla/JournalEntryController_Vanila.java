//package com.YashIngale.JournalApp.Controller.Vanilla;
//
//import com.YashIngale.JournalApp.Entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController
//@RequestMapping("_Vanilajournal")
//public class JournalEntryController_Vanila {
//
//    Map<Long,JournalEntry> journals = new HashMap<>();
//    @GetMapping
//    public List<JournalEntry> getJournal(){
//        return new ArrayList<>(journals.values());
//
//    }
//    @PostMapping
//    public boolean putJournal(@RequestBody JournalEntry userEntry){
//        journals.put(userEntry.getIdentity(),userEntry);
//        return true;
//    }
//    @GetMapping("getById/{userId}")
//    public JournalEntry gerJournalEntryByID(@PathVariable Long userId){
//        return journals.get(userId);
//    }
//    @DeleteMapping("deleteById/{userId}")
//    public boolean deleteEntry(@PathVariable Long userId){
//        journals.remove(userId);
//        return true;
//    }
//    @PutMapping("updateEntry/{id}")
//    public JournalEntry updateJournalEntryById(@PathVariable Long id,@RequestBody JournalEntry userEntry){
//        return journals.put(id,userEntry);
//
//    }
//}
