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

import java.io.File;

import org.exoplatform.berkeley.comparator.BerkeleyComparator;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Durability;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

/**
 * Created by The eXo Platform SAS
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 *          
 * @version BerkeleyEnvironment.java Dec 4, 2013
 */
public class BerkeleyEnvironment implements IBerkeleyEnvironment {
  
  private static BerkeleyEnvironment _berkeleyEnvironment;
  
  public static BerkeleyEnvironment getInstance() {
    if (_berkeleyEnvironment == null)
      _berkeleyEnvironment = new BerkeleyEnvironment();
    
    return _berkeleyEnvironment;
  }

  /* (non-Javadoc)
   * @see org.exoplatform.berkeley.EnvironmentConfigurator#openConnection(java.lang.String, boolean, boolean)
   */
  @Override
  public Database openConnection(Environment environment, String database, boolean allowCreate, boolean allowDuplicates) {
    DatabaseConfig dbConfig = new DatabaseConfig();
    dbConfig.setAllowCreate(allowCreate);
    dbConfig.setSortedDuplicates(allowDuplicates);
    
    // Set the duplicate comparator class
    dbConfig.setDuplicateComparator(BerkeleyComparator.class);
    dbConfig.setOverrideDuplicateComparator(false);
    return environment.openDatabase(null, database, dbConfig);
  }
  
  /* (non-Javadoc)
   * @see org.exoplatform.berkeley.EnvironmentConfigurator#createDatabaseEnvironment(java.lang.String)
   */
  @Override
  public Environment createDatabaseEnvironment(String folder) throws BerkeleyException {
    File dbDirectory = new File(folder);
    if (!dbDirectory.exists())
      if (!dbDirectory.mkdirs())
        throw new BerkeleyException("The directory " + folder + " does not exist.");
    
    // Instantiate an environment configuration object
    EnvironmentConfig envConfig = new EnvironmentConfig();
    envConfig.setDurability(Durability.COMMIT_SYNC);
    
    // If the environment is opened for write, then we want to be 
    // able to create the environment if it does not exist.
    envConfig.setAllowCreate(true);
    envConfig.setSharedCache(true);
    // Instantiate the Environment. This opens it and also possibly
    // creates it.
    
    // Set it large enough so that it won't page.
    envConfig.setCacheSize(20 * 1024 * 1024);
    
    return new Environment(dbDirectory, envConfig);
  }

  /* (non-Javadoc)
   * @see org.exoplatform.berkeley.EnvironmentConfigurator#closeConnection()
   */
  @Override
  public void closeConnection(Environment environment) {
    if (environment != null) {
      try {
        // Clean the log before closing
        environment.cleanLog();
        environment.close();
      } catch (DatabaseException dbe) {
        System.err.println("Error closing environment" + dbe.toString());
      }
    }
  }

  /* (non-Javadoc)
   * @see org.exoplatform.berkeley.EnvironmentConfigurator#closeDatabase(com.sleepycat.je.Database)
   */
  @Override
  public void closeDatabase(Database database) {
    if (database != null)
      database.close();
  }
}
