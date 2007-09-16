/*
 * Created on Sep 14, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;

/**
 * Understands utility methods related to formatting.
 *
 * @author Yvonne Wang
 */
public final class Formatting {

  private static final String EMPTY_MESSAGE = "";

  public static String formatMessage(String message) {
    if (isEmpty(message)) return EMPTY_MESSAGE;
    return concat("[", message, "] ");
  }

  public static String formatObject(Object o) {
    return concat("<", o, ">");
  }
  
  private Formatting() {}

}
