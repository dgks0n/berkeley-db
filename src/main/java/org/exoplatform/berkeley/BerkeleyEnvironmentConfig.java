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

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Durability;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

/**
 * Created by The eXo Platform SAS
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 *          
 * @version BerkeleyEnvironmentConfig.java Dec 4, 2013
 */
public class BerkeleyEnvironmentConfig implements EnvironmentConfigurator {
  
  private static BerkeleyEnvironmentConfig _berkeleyEnvironmentConfig;
  
  public static BerkeleyEnvironmentConfig getInstance() {
    if (_berkeleyEnvironmentConfig == null)
      _berkeleyEnvironmentConfig = new BerkeleyEnvironmentConfig();
    
    return _berkeleyEnvironmentConfig;
  }

  /* (non-Javadoc)
   * @see org.exoplatform.berkeley.EnvironmentConfigurator#openConnection(java.lang.String, boolean, boolean)
   */
  @Override
  public Database openConnection(Environment environment, String database, boolean allowCreate, boolean allowDuplicates) {
    DatabaseConfig databaseConfig = new DatabaseConfig();
    databaseConfig.setAllowCreate(allowCreate);
    databaseConfig.setSortedDuplicates(allowDuplicates);
    return environment.openDatabase(null, database, databaseConfig);
  }
  
  /* (non-Javadoc)
   * @see org.exoplatform.berkeley.EnvironmentConfigurator#createDatabaseEnvironment(java.lang.String)
   */
  @Override
  public Environment createDatabaseEnvironment(String folder) throws BerkeleyException {
    File home = new File(folder);
    if (!home.exists())
      if (!home.mkdirs())
        throw new BerkeleyException("The " + folder + " doesn't exist");
    
    EnvironmentConfig environmentConfig = new EnvironmentConfig();
    environmentConfig.setDurability(Durability.COMMIT_SYNC);
    environmentConfig.setAllowCreate(true);
    return new Environment(home, environmentConfig);
  }

  /* (non-Javadoc)
   * @see org.exoplatform.berkeley.EnvironmentConfigurator#closeConnection()
   */
  @Override
  public void closeConnection(Environment environment) {
    if (environment != null) {
      environment.close();
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
