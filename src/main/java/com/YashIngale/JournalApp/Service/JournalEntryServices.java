package com.YashIngale.JournalApp.Service;

import com.YashIngale.JournalApp.Entity.JournalEntry;
import com.YashIngale.JournalApp.Entity.UserEntity;
import com.YashIngale.JournalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryServices {
    @Autowired
    public JournalEntryRepository journalEntryRepository;
    @Autowired
    public UserServices userServices;

    @Transactional
    public void saveJournalEntry(JournalEntry journalEntry, String userName){

        try {
            UserEntity userEntity = userServices.findByuserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry journalEntry1= journalEntryRepository.save(journalEntry);
            userEntity.getJournalEntries().add(journalEntry1);
            userServices.SaveUser(userEntity);
            System.out.println("Saved successfully: " + journalEntry.getId());
        } catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error occured means the transaction not completed");
        }
    }
    public void updateJournalEntry(JournalEntry journalEntry) {
        // Only for UPDATE - just saves the entry, no list modification
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }
    //This is for the update we just overloaded the method

    public List<JournalEntry> getAllJournalEntry(){
        try {
            for (JournalEntry journalEntry:journalEntryRepository.findAll()
                 ) {
                System.out.println(journalEntry);
            }
            return journalEntryRepository.findAll();
        }catch (Exception e){
            System.out.println("Error in getting all the Entries "+e.getMessage().toString());
            return null;
        }
    }
    public Optional<JournalEntry> getJournalEntryByID(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteJournalEntry(ObjectId id, String userName) {
        try {
            if (getJournalEntryByID(id).isPresent()) {
                UserEntity userEntity = userServices.findByuserName(userName);
                boolean check = userEntity.getJournalEntries().removeIf(x -> x.getId().equals(id));
                if (check) {
                    userServices.SaveUser(userEntity);
                    journalEntryRepository.deleteById(id);
                    return true;
                } else {
                    return false;
                }

            } else {
                System.out.println("The Entry Not Present");
                return false;
            }


        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }





}
