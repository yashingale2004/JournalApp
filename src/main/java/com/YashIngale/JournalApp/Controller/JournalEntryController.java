package com.YashIngale.JournalApp.Controller;

import ch.qos.logback.core.util.StringUtil;
import com.YashIngale.JournalApp.Entity.JournalEntry;
import com.YashIngale.JournalApp.Entity.UserEntity;
import com.YashIngale.JournalApp.Service.JournalEntryServices;
import com.YashIngale.JournalApp.Service.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryServices journalEntryServices;
    @Autowired
    private UserServices userServices;
    @GetMapping()
    public ResponseEntity<?> getJournalEntry() {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            UserEntity userEntity = userServices.findByuserName(userName);
            List<JournalEntry> journalEntries = userEntity.getJournalEntries();
            if (journalEntries != null && !journalEntries.isEmpty()) {
                return new ResponseEntity<>(journalEntries, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity postJournalEntry(@RequestBody JournalEntry userEntry) {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryServices.saveJournalEntry(userEntry,userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getById/{id}")
    public ResponseEntity getJournalEntryByID(@PathVariable ObjectId id) {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            UserEntity userEntity =userServices.findByuserName(userName);
            List<JournalEntry> Entry =userEntity.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
            if(!Entry.isEmpty()){
                Optional<JournalEntry> journalEntry = journalEntryServices.getJournalEntryByID(id);
                if(journalEntry.isPresent()){
                    return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("deleteById/{userId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId userId) {
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            if(journalEntryServices.getJournalEntryByID(userId).isPresent()) {
                journalEntryServices.deleteJournalEntry(userId,userName);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateEntry/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry userEntry) {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            JournalEntry old = journalEntryServices.getJournalEntryByID(id).orElse(null);
            if (old != null) {
                old.setBookname(userEntry.getBookname() != null && !userEntry.getBookname().equals("") ? userEntry.getBookname() : old.getBookname());
                old.setContent(userEntry.getContent() != null && !userEntry.getContent().equals("") ? userEntry.getContent() : old.getContent());
                journalEntryServices.updateJournalEntry(old);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
