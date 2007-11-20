/*
 * Created on Nov 1, 2007
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
package org.fest.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Understands filtering elements of a collection by their data type.
 *
 * @author Yvonne Wang
 */
public class TypeFilter<T> implements CollectionFilter<T> {

  /**
   * Creates a new <code>{@link TypeFilter}</code>.
   * @param type the target type for this filter.
   * @return the created filter.
   */
  public static <T> TypeFilter<T> byType(Class<T> type) {
    return new TypeFilter<T>(type);
  }

  private final Class<T> type;

  TypeFilter(Class<T> type) {
    this.type = type;
  }

  /**
   * Filters the given collection by the type specified in this filter.
   * @param target the collection to filter.
   * @return a list containing the filtered elements.
   * @throws IllegalArgumentException if the given collection is <code>null</code>.
   */
  @SuppressWarnings("unchecked") 
  public List<T> filter(Collection<?> target) {
    if (target == null) throw new IllegalArgumentException("The collection to filter should not be null");
    List<Object> filtered = new ArrayList<Object>();
    for (Object o : target) {
      if (o == null) continue;
      if (type.isAssignableFrom(o.getClass())) filtered.add(o);
    }
    return (List<T>)filtered;
  }
}
