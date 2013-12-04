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

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by The eXo Platform SAS
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 *          
 * @version TestBerkeleyStore.java Dec 4, 2013
 */
public class TestBerkeleyStore {

  BerkeleyStore berkeleyStore;
  
  @Before
  public void setup() {
    berkeleyStore = new BerkeleyStore();
  }
  
  @Test
  public void testBerkeleyStore() throws BerkeleyException {
    berkeleyStore.put("88d0fd72378f1d01107738b05f08655e4f2fed1d", "Oracle Berkeley DB Data Store");
    
    String data = berkeleyStore.get("88d0fd72378f1d01107738b05f08655e4f2fed1d");
    assertEquals("Oracle Berkeley DB Data Store", data);
  }
  
  @Test
  public void testBerkeleyStoreWithByte() throws BerkeleyException, UnsupportedEncodingException {
    berkeleyStore.put("94961cf809529c135f53f4425ff8d7ea114a0908", "Berkeley DB".getBytes("UTF-8"));
    
    String data = berkeleyStore.get("94961cf809529c135f53f4425ff8d7ea114a0908");
    assertEquals("Berkeley DB", data);
  }
  
  @Test
  public void testBerkeleyStoreWithoutDuplicate() throws BerkeleyException, UnsupportedEncodingException {
    berkeleyStore.put("94961cf809529c135f53f4425ff8d7ea114a0908", "Berkeley DB Java Edition".getBytes("UTF-8"), false);
    
    String data = berkeleyStore.get("94961cf809529c135f53f4425ff8d7ea114a0908");
    assertEquals("Berkeley DB", data);
  }
}
