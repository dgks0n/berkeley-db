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
package org.exoplatform.berkeley.comparator;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @version BerkeleyComparator.java Dec 4, 2013
 *
 */
public class BerkeleyComparator implements Comparator<byte[]> {

  @Override
  public int compare(byte[] o1, byte[] o2) {
    String s1 = null;
    String s2 = null;
    try {
      s1 = new String(o1, "UTF-8");
      s2 = new String(o2, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      System.err.println("Unsupported utf-8 encoding " + e.getMessage());
    }
    return s1.compareTo(s2);
  }
}
