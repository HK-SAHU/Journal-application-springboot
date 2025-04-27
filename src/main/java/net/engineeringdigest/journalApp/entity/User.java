package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")
@Data       //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.  (lombok)
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)   // to make the username unique and the searching will be fast.  but the indexing will not be done by default
    // we need to do it on our own in application.properties
    @NonNull
    private String userName;
    @NonNull
    private String password;

    @DBRef
    // this means that we are creating the reference in users collections to the entries in journal_entries(not whole content and title) working as foreign key
    private List<JournalEntry> journalEntries= new ArrayList<>();   // parent child relation has been established

    private List<String> roles;   // this will tell that what the user is authorized to do. (user, admin etc)

}
