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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by The eXo Platform SAS
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 *          
 * @version TestBerkeleyStore.java Dec 4, 2013
 */
public class TestBerkeleyStore {

  BerkeleyStore berkeleyStore;
  
  @Test
  public void testBerkeleyStore() throws BerkeleyException {
    berkeleyStore = new BerkeleyStore();
    berkeleyStore.put("Berkeley", "520e1b9fa6b2207446fa7717cd69369fe5ba1876");
    
    String data = berkeleyStore.get("Berkeley");
    assertEquals("520e1b9fa6b2207446fa7717cd69369fe5ba1876", data);
  }

}
