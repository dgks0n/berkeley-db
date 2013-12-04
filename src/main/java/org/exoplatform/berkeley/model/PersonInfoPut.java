package org.exoplatform.berkeley.model;

import java.io.File;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.StoreConfig;

public class PersonInfoPut {

    private Environment envmnt;
    private EntityStore store;
    private PersonAccessor personAccessor;
    
    public void setup() throws DatabaseException {

        /* Open a transactional Berkeley DB engine environment. */
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setTransactional(true);
        envmnt = new Environment(new File("/home/maydt/dbEnv"), envConfig);

        /* Open a transactional entity store. */
        StoreConfig storeConfig = new StoreConfig();
        storeConfig.setAllowCreate(true);
        storeConfig.setTransactional(true);
        store = new EntityStore(envmnt, "PersonStore", storeConfig);
    }
    
    // Close our environment and store.
    public void shutdown() throws DatabaseException {
        store.close();
        envmnt.close();
    }
    
    // Populate the entity store
    private void run() throws DatabaseException {

        setup();

        // Open the data accessor. This is used to store persistent objects.
        personAccessor = new PersonAccessor(store);

        // Instantiate and store some entity classes
        Person person1 = new Person();
        Person person2 = new Person();
        Person person3 = new Person();

        person1.setId("1");
        person1.setName("May");
        person1.setAddress("Ha Dong");
       
        person2.setId("2");
        person2.setName("Bob");
        person2.setAddress("USA");

        person3.setId("3");
        person3.setName("Alice");
        person3.setAddress("California");

        personAccessor.id.put(person1);
        personAccessor.id.put(person2);
        personAccessor.id.put(person3);
        
        System.out.println("Put person info successfully!");
        
        
        //Read person info
        EntityCursor<Person> records = personAccessor.id.entities();
        try {
	        for (Person person : records) {
	        	System.out.println("This is person number " + person.getId() + ": " + person.name + ", " + person.address);
	        }
        } finally {
        	records.close();
        }
        shutdown();
    }
    
    public static void main(String[] args) throws DatabaseException {
        // 
    	PersonInfoPut p = new PersonInfoPut();
        p.run();
    }
}
