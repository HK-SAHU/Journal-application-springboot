package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "journal_entries")
@Data       //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.  (lombok)
@NoArgsConstructor    // this is because the @Data is calling the @RequiredArgsConstructor and we are not passing any Constructor with any argument
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;




}
