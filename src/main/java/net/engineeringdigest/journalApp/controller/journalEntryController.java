//package net.engineeringdigest.journalApp.controller;
//
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/journal")   //add the mapping in the whole class
//public class journalEntryController {
//    private Map<Long, JournalEntry> journalEntries= new HashMap<>();   // as we don't have any database till now so we are using this
//    // id and the journal entry in the map
//
//
//
//    @GetMapping
//    public List<JournalEntry> getAll(){    // localhost:8080/journal  GET
//        return new ArrayList<>(journalEntries.values());   // converting the values of the map into the arraylist
//    }
//
//    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntry myEntry){  // localhost:8080/journal  POST
//        journalEntries.put(myEntry.getId(),myEntry);   //myEntry is the instance of the JournalEntry
//        return true;
//    }
//
//    @GetMapping("id/{myId}")   // now I want the particular journal by id so i can get it by path variable or request parameter
//    public JournalEntry getJournalEntryById(@PathVariable Long myId){
//        return journalEntries.get(myId);
//    }
//    //"id/{myId}" here myId is the path variable and it is not fixed, this is the syntax of the path variable
//
//
//    // similarly we can delete the particular journal by using path variable
//    @DeleteMapping("id/{myId}")
//    public JournalEntry deleteJournalEntry(@PathVariable Long myId){
//        return journalEntries.remove(myId);
//    }
//
//    @PutMapping("id/{id}")
//    public JournalEntry updateJournalEntry(@PathVariable Long id, @RequestBody JournalEntry myEntry){
//        return journalEntries.put(id, myEntry);
//    }
//}
