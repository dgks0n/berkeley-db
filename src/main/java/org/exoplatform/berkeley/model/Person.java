package org.exoplatform.berkeley.model;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class Person {

    @PrimaryKey
    private String id;
    
    String name;
    
    String address;
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public void setAddress(String address) {
    	this.address = address;
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
}
