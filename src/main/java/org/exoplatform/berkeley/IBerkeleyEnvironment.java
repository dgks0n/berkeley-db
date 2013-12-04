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

import com.sleepycat.je.Database;
import com.sleepycat.je.Environment;

/**
 * Created by The eXo Platform SAS
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 *          
 * @version IBerkeleyEnvironment.java Dec 4, 2013
 */
public interface IBerkeleyEnvironment {

  /**
   * Open a database environment by instantiating an Environment object. You
   * must provide to the constructor the name of the on-disk directory where the
   * environment is to reside. This directory location must exist or the open
   * will fail.
   * 
   * By default, the environment is not created for you if it does not exist.
   * Set the creation property to true if you want the environment to be
   * created.
   * 
   * @param enviroment
   *              - database environment used to interact woth databases. Allow it to be created if it does not already exist.
   * @param database
   *              - the database's name
   * @param allowCreate
   *              - If true, the database environment is created when it is opened. 
   *                If false, environment open fails if the environment does not exist. 
   *                This property has no meaning if the database environment already exists.
   * @param allowDuplicates
   *              - Allow duplicate entities or not
   * @return
   *              - 
   */
  public Database openConnection(Environment environment, String database, boolean allowCreate, boolean allowDuplicates);
  
  /**
   * Inits the databased environment used for all databases.
   * 
   * @param folder
   *              - Folder's path for stored files (inside working directory).
   * @return
   * @throws BerkeleyException 
   */
  public Environment createDatabaseEnvironment(String folder) throws BerkeleyException;
  
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
   * 
   * @param enviroment
   *              - database environment used to interact woth databases. Allow it to be created if it does not already exist.
   */
  public void closeConnection(Environment environment);
  
  /**
   * Close all databases and the environment.
   * 
   * @param database
   *              - 
   */
  public void closeDatabase(Database database);
}
