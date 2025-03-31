package net.engineeringdigest.journalApp.services;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


// here we will write the business login and we will use this in controllerV2
@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;   // dependency injection of the @Collection that is this is the bean


    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An occurred while saving the entry!!!!");
//            log.error("Exception "+e);    // log is because of @slf4j
        }
    }


    public void saveEntry(JournalEntry journalEntry) {
        try{
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);
        }
        catch (Exception e){
            log.error("Exception "+e);    // log is because of @slf4j
        }
    }

    public List<JournalEntry> getAll() {

        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {    // optional is nothing but a box saying it can either contain content or it can be empty

        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));   // loop chalega or jab wo id mil jayegi tab wo delete ho jayegi
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }


}
