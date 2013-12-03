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
import java.io.FileNotFoundException;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @version BerkeleyEnvironmentConfig.java Dec 3, 2013
 * 
 */
public class BerkeleyEnvironmentConfig {

  // Open the environment. Allow it to be created if it does not already
  // exist.
  Environment myDbEnvironment = null;

  /**
   * Open a database environment by instantiating an Environment object. You
   * must provide to the constructor the name of the on-disk directory where the
   * environment is to reside. This directory location must exist or the open
   * will fail.
   * 
   * By default, the environment is not created for you if it does not exist.
   * Set the creation property to true if you want the environment to be
   * created.
   */
  public void openConnection() {

    //http://docs.oracle.com/cd/E17076_02/html/gsg/JAVA/persistobject.html
    try {
      EnvironmentConfig envConfig = new EnvironmentConfig();
      // If true, the database environment is created when it is opened. If
      // false, environment open fails if the environment does not exist. This
      // property has no meaning if the database environment already exists.
      envConfig.setAllowCreate(true);
      // If true, configures the database environment to support transactions.
      envConfig.setTransactional(true);
      myDbEnvironment = new Environment(new File("/export/dbEnv"), envConfig);
    } catch (DatabaseException dbe) {
      // Exception handling goes here
      System.err.println("Couldn't open database enviroment "
          + dbe.getMessage());
    }
  }

  /**
   * You close your environment by calling the Environment.close() method. This
   * method performs a checkpoint, so it is not necessary to perform a sync or a
   * checkpoint explicitly before calling it.
   * 
   * You should close your environment(s) only after all other database
   * activities have completed. It is recommended that you close any databases
   * currently open in the environment prior to closing the environment. Closing
   * the last environment handle in your application causes all internal data
   * structures to be released. If there are any opened databases or stores,
   * then DB will complain before closing them as well. At this time, any open
   * cursors are also closed, and any on-going transactions are aborted.
   * However, it is recommended that you always close all cursor handles
   * immediately after their use to ensure concurrency and to release resources
   * such as page locks.
   */
  public void closeConnection() {
    try {
      if (myDbEnvironment != null) {
        myDbEnvironment.close();
      }
    } catch (DatabaseException dbe) {
      System.err.println("Couldn't close connection " + dbe.getMessage());
    }
  }
}
