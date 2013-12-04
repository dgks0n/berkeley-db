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

/**
 * Created by The eXo Platform SAS
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 *          
 * @version BerkeleyException.java Dec 4, 2013
 */
public class BerkeleyException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -1397612352771062759L;

  public BerkeleyException() {
    super();
  }
  
  public BerkeleyException(String message) {
    super(message);
  }
  
  public BerkeleyException(Throwable cause) {
    super(cause);
  }
  
  public BerkeleyException(String message, Throwable cause) {
    super(message, cause);
  }
}
