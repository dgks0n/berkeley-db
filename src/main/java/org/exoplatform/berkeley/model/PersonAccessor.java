package org.exoplatform.berkeley.model;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

public class PersonAccessor {

	PrimaryIndex<String,Person> id;
	
	// Open the indices
    public PersonAccessor(EntityStore store) throws DatabaseException {

        // Primary key for SimpleEntityClass classes
        id = store.getPrimaryIndex(String.class, Person.class);
    }
}
