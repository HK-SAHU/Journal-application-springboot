package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.JournalEntryService;
import net.engineeringdigest.journalApp.services.UserService;
import org.apache.catalina.security.SecurityUtil;
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
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;   // dependency injection

    @Autowired
    private UserService userService;

//    @GetMapping("{userName}")
//    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {    // localhost:8080/journal  GET
//        User user = userService.findByUserName(userName);   // this will find the journal entries of the particular user
//        List<JournalEntry> all = user.getJournalEntries();
//        if (all != null && !all.isEmpty()) {
//            return new ResponseEntity<>(all, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user = userService.findByUserName(userName);   // this will find the journal entries of the particular user
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping("{userName}")
//    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {  // localhost:8080/journal  POST
//            try {
//                journalEntryService.saveEntry(myEntry, userName);
//                return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
//            } catch (Exception e) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {  // localhost:8080/journal  POST
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName= authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("id/{myId}")    // this is fine until now but need to update during the authentication
//    // now I want the particular journal by id, so I can get it by path variable or request parameter
//    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
//        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
//        if (journalEntry.isPresent()) {
//            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user = userService.findByUserName(userName);  // aur myId jouranlEntry ki list mein nhi hai toh mtlb ki galat id send kar rahe hai toh hume filter karna hai usse
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        /*
        pahale humne user nikala fir uske journal entry ki list list ayi hogi toh unsme dekh liya ki my id hai ki nhi hai
        aur agar hai toh hume wo send kar dijiye warna nhi
         */
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /*
    here we have to make sure that we want to fetch the journal of that particular user, who has logged in. not the entries of the other user
    so we will create the findByUserName in the journalEntryService.
    jo hamare pass username aur id hai na ussi pe call chala denge aur jisse hum login hue hai wo user bhi hum nikal sakte hai

     */


    // similarly we can delete the particular journal by using path variable
//    @DeleteMapping("id/{userName}/{myId}")
//    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId, @PathVariable String userName) {   //? here is wild card pattern
//        /*
//        ? - because it is not necessary to give the Entity class (JournalEntry)
//        we can also return the object of any other class, response entity ke andar wrap kara ke
//         */
//        try {
//            journalEntryService.deleteById(myId, userName);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Invalid ObjectId format", HttpStatus.BAD_REQUEST);
//        }
//    }


    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId) {   //? here is wild card pattern
        /*
        ? - because it is not necessary to give the Entity class (JournalEntry)
        we can also return the object of any other class, response entity ke andar wrap kara ke
         */
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean removed = journalEntryService.deleteById(myId, userName);
            if (removed) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Invalid ObjectId format", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping("id/{userName}/{id}")
//    public ResponseEntity<?> updateJournalEntry(
//            @PathVariable ObjectId id,
//            @RequestBody JournalEntry newEntry,
//            @PathVariable String userName
//    ) {
//        JournalEntry old = journalEntryService.findById(id).orElse(null);
//        if (old != null) {
//            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
//            journalEntryService.saveEntry(old);
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalEntry(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry
    ) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user = userService.findByUserName(userName);  // aur myId jouranlEntry ki list mein nhi hai toh mtlb ki galat id send kar rahe hai toh hume filter karna hai usse
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
            if (journalEntry.isPresent()) {  // logic aur better ho sakta hai
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
