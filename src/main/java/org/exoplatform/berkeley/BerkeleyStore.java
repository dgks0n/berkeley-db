/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.berkeley;

import java.io.UnsupportedEncodingException;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseNotFoundException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.LockConflictException;
import com.sleepycat.je.LockMode;

/**
 * Created by The eXo Platform SAS
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 *          
 * @version BerkeleyStore.java Dec 4, 2013
 * 
 * Reference https://github.com/JuhaS/SimpleMapStore
 */
public class BerkeleyStore {

  /**
   * Default folder for stored files (inside working directory).
   */
  public static final String ENVIROMENT_FOLDER_PATH = "berkeleyEnv";
  
  /**
   * String used to identify the string database.
   */
  public static final String STRING_PATH = "dataEnv";
  
  public static final String SUPPORTED_ENCODE = "UTF-8";
  
  BerkeleyEnvironmentConfig berkeleyEnvironmentConfig;
  
  Environment environment;
  
  // Database object for storing string values
  Database database;
  
  public BerkeleyStore() {
    this(ENVIROMENT_FOLDER_PATH);
  }
  
  /**
   * Create instance of BerkeleyStore. It first checks for any existing 
   * BerkeleyStore inside the given folder. If one is found it is opened and 
   * instance will use that. If no existing database was found then one is 
   * created and that is taken into use. 
   * 
   * If given folder does not exist it is created if possible.
   * 
   * @param folder Environment folder where files are stored.
   */
  public BerkeleyStore(String folder) {
    try {
      this.berkeleyEnvironmentConfig = BerkeleyEnvironmentConfig.getInstance();
      this.environment = berkeleyEnvironmentConfig.createDatabaseEnvironment(folder);
      this.database = berkeleyEnvironmentConfig.openConnection(this.environment, STRING_PATH, true, false);
    } catch (BerkeleyException e) {
      System.err.println("Couldn't get berkeley enviroment " + e.getMessage());
    } catch (DatabaseNotFoundException e) {
      System.err.println("Database not found " + e.getMessage());
    }
  }
  
  /**
   * Put key value pair into the map. Any existing value with same key is 
   * overwritten. Write is synchronized to the disc before returning 
   * (fulfilling ACID).
   * 
   * Pairs inserted with putObject method are in different namespace than this
   * so same key may exist in object and string namespace.
   * 
   * @param key 
   *              - the string key
   * @param value 
   *              - the string value
   */
  public void put(String key, String value) throws BerkeleyException {
    if (key == null || value == null)
      throw new BerkeleyException("Key and value can not be null for put() function");
    
    try {
      DatabaseEntry keyEntry = new DatabaseEntry(key.getBytes(SUPPORTED_ENCODE));
      DatabaseEntry valueEntry = new DatabaseEntry(value.getBytes(SUPPORTED_ENCODE));
      
      database.put(null, keyEntry, valueEntry);
    } catch (UnsupportedEncodingException e) {
      throw new BerkeleyException("Key and value have unsupported utf-8 encoding" + e.getMessage());
    }
  }
  
  /**
   * Get value for given String key. If no entry is found, null is returned. 
   * 
   * Values inserted with putObject are in different namespace so they are 
   * not reachable with this method.
   * 
   * @param key 
   *              - the string key
   * @return a string
   *              - value matching the key or null if not found.
   */
  public String get(String key) throws BerkeleyException {
    String value = null;
    try {
      DatabaseEntry search = new DatabaseEntry();
      database.get(null, new DatabaseEntry(key.getBytes(SUPPORTED_ENCODE)), search, LockMode.DEFAULT);
      if (search.getData() != null) {
        value = new String(search.getData(), SUPPORTED_ENCODE);
      }
    } catch (LockConflictException e) {
      System.err.println(e.getMessage());
    } catch (DatabaseException e) {
      System.err.println(e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new BerkeleyException("Key can not be null for put() function" + e.getMessage());
    } catch (UnsupportedEncodingException e) {
      throw new BerkeleyException("Key has unsupported utf-8 encoding" + e.getMessage());
    }
    
    return value;
  }
  
  public void close() {
    berkeleyEnvironmentConfig.closeDatabase(database);
    berkeleyEnvironmentConfig.closeConnection(environment);
  }
}
