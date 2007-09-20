/*
 * Created on Sep 19, 2007
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

import java.util.Arrays;

import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Formatting.bracketAround;
import static org.fest.assertions.Formatting.format;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>long</code> arrays. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(long[])}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class LongArrayAssert extends GroupAssert<long[]> {

  LongArrayAssert(long... actual) {
    super(actual);
  }

  /** {@inheritDoc} */
  public LongArrayAssert as(String description) {
    return (LongArrayAssert)description(description);
  }

  /** {@inheritDoc} */
  public LongArrayAssert describedAs(String description) {
    return as(description);
  }
  
  /**
   * Verifies that the actual <code>long</code> array satisfies the given condition. 
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> array does not satisfy the given condition.
   */
  public LongArrayAssert satisfies(Condition<long[]> condition) {
    return (LongArrayAssert)verify(condition);
  }

  /**
   * Verifies that the actual <code>long</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> array is <code>null</code>.
   */
  public LongArrayAssert isNotNull() {
    return (LongArrayAssert)assertNotNull();
  }
  
  /**
   * Verifies that the actual <code>long</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>long</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actual.length > 0) 
      fail(concat(format(description()), "expecting empty array, but was ", bracketAround(actual)));
  }

  /**
   * Verifies that the actual <code>long</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> array is empty.
   */
  public LongArrayAssert isNotEmpty() {
    if (actualGroupSize() == 0) fail(concat(format(description()), "expecting a non-empty array"));
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> array is equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(long[], long[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> array is not equal to the given one.
   */
  public LongArrayAssert isEqualTo(long[] expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(description(), actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> array is not equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(long[], long[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> array is equal to the given one.
   */
  public LongArrayAssert isNotEqualTo(long[] array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(description(), actual, array));
    return this;
  }

  protected int actualGroupSize() {
    return actual.length;
  }

  /**
   * Verifies that the number of elements in the actual <code>long</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>long</code> array.
   * @return this assertion object.
   * @throws AssertionError if the number of elements in the actual <code>long</code> array is not equal to the given 
   * one.
   */
  public LongArrayAssert hasSize(int expected) {
    return (LongArrayAssert)assertEqualSize(expected);
  }
  
  /**
   * Verifies that the actual <code>long</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> array is not the same as the given one.
   */
  public LongArrayAssert isSameAs(long[] expected) {
    return (LongArrayAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>long</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> array is the same as the given one.
   */
  public LongArrayAssert isNotSameAs(long[] expected) {
    return (LongArrayAssert)assertNotSameAs(expected);
  }
}
