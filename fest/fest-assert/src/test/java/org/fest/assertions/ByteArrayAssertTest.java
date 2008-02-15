/*
 * Created on Feb 14, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.testng.Assert.*;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ByteArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ByteArrayAssertTest {

  private static final byte[] NULL_ARRAY = null;
  private static final byte[] EMPTY_ARRAY = new byte[0];

  @Test public void shouldSetDescription() {
    ByteArrayAssert assertion = new ByteArrayAssert(asByte(8), asByte(6));
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    ByteArrayAssert assertion = new ByteArrayAssert(asByte(8), asByte(6));
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyOrNullArrayCondition extends Condition<byte[]> {
    @Override public boolean matches(byte[] array) {
      return array == null || array.length == 0;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new ByteArrayAssert(EMPTY_ARRAY).satisfies(new EmptyOrNullArrayCondition());
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("condition failed with:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).satisfies(new EmptyOrNullArrayCondition());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] condition failed with:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).as("A Test").satisfies(new EmptyOrNullArrayCondition());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("expected:<Empty or null array> but was:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).satisfies(new EmptyOrNullArrayCondition().as("Empty or null array"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] expected:<Empty or null array> but was:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).as("A Test").satisfies(new EmptyOrNullArrayCondition().as("Empty or null array"));
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsIsInArray() {
    new ByteArrayAssert(asByte(8), asByte(6)).contains(asByte(8), asByte(6));
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(NULL_ARRAY).contains(asByte(8), asByte(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(NULL_ARRAY).as("A Test").contains(asByte(8), asByte(6));
      }
    });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expectAssertionError("array:<[]> does not contain element(s):<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(EMPTY_ARRAY).contains(asByte(8), asByte(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(EMPTY_ARRAY).as("A Test").contains(asByte(8), asByte(6));
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArray() {
    new ByteArrayAssert(asByte(8), asByte(6)).excludes(asByte(7));
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ByteArrayAssert(NULL_ARRAY).excludes(asByte(8), asByte(6));
      }
    });
  }

  @Test public void shouldFailShowindDescriptionIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ByteArrayAssert(NULL_ARRAY).as("A Test").excludes(asByte(8), asByte(6));
      }
    });
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("array:<[8, 6]> does not exclude element(s):<[8]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).excludes(asByte(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("[A Test] array:<[8, 6]> does not exclude element(s):<[8]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).as("A Test").excludes(asByte(8));
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new ByteArrayAssert(NULL_ARRAY).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(EMPTY_ARRAY).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(EMPTY_ARRAY).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new ByteArrayAssert(EMPTY_ARRAY).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(NULL_ARRAY).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(NULL_ARRAY).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmpty() {
    new ByteArrayAssert(EMPTY_ARRAY).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(NULL_ARRAY).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(NULL_ARRAY).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty array, but was:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty array, but was:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmpty() {
    new ByteArrayAssert(asByte(8), asByte(6)).isNotEmpty();
  }

  @Test public void shouldFailIfArrayIsEmpty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(EMPTY_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmpty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(EMPTY_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ByteArrayAssert(NULL_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ByteArrayAssert(NULL_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfArraysAreEqual() {
    new ByteArrayAssert(asByte(8), asByte(6)).isEqualTo(array(asByte(8), asByte(6)));
  }

  @Test public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<[7]> but was:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).isEqualTo(array(asByte(7)));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<[7]> but was:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).as("A Test").isEqualTo(array(asByte(7)));
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotEqual() {
    new ByteArrayAssert(asByte(8), asByte(6)).isNotEqualTo(array(asByte(8)));
  }

  @Test public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<[8, 6]> should not be equal to:<[8, 6]>").on(
        new CodeToTest() {
          public void run() {
            new ByteArrayAssert(asByte(8), asByte(6)).isNotEqualTo(array(asByte(8), asByte(6)));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<[8, 6]> should not be equal to:<[8, 6]>").on(
        new CodeToTest() {
          public void run() {
            new ByteArrayAssert(asByte(8), asByte(6)).as("A Test").isNotEqualTo(array(asByte(8), asByte(6)));
          }
        });
  }

  @Test public void shouldPassIfActualContainsOnlyExpectedElements() {
    new ByteArrayAssert(asByte(8)).containsOnly(asByte(8));
  }

  @Test public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("array:<[]> does not contain element(s):<[7]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(EMPTY_ARRAY).containsOnly(array(asByte(7)));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[7]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(EMPTY_ARRAY).as("A Test").containsOnly(array(asByte(7)));
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ByteArrayAssert(NULL_ARRAY).containsOnly(array(asByte(7)));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ByteArrayAssert(NULL_ARRAY).as("A Test").containsOnly(array(asByte(7)));
      }
    });
  }

  @Test public void shouldFailIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("unexpected element(s):<[6]> in array:<[8, 6]>").on(
        new CodeToTest() {
          public void run() {
            new ByteArrayAssert(asByte(8), asByte(6)).containsOnly(array(asByte(8)));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] unexpected element(s):<[6]> in array:<[8, 6]>").on(
        new CodeToTest() {
          public void run() {
            new ByteArrayAssert(asByte(8), asByte(6)).as("A Test").containsOnly(array(asByte(8)));
          }
        });
  }

  @Test public void shouldFailIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("array:<[8, 6]> does not contain element(s):<[7]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).containsOnly(array(asByte(7)));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] array:<[8, 6]> does not contain element(s):<[7]>").on(
        new CodeToTest() {
          public void run() {
            new ByteArrayAssert(asByte(8), asByte(6)).as("A Test").containsOnly(array(asByte(7)));
          }
        });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    byte[] array = array(asByte(6), asByte(8));
    new ByteArrayAssert(array).hasSize(2);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for array:<[8]>").on(new CodeToTest() {
      public void run() {
        byte[] array = array(asByte(8));
        new ByteArrayAssert(array).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for array:<[8]>").on(new CodeToTest() {
      public void run() {
        byte[] array = array(asByte(8));
        new ByteArrayAssert(array).as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldPassIfArraysAreSame() {
    byte[] array = array(asByte(8), asByte(6));
    new ByteArrayAssert(array).isSameAs(array);
  }

  @Test public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<[8, 6]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<[8, 6]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new ByteArrayAssert(asByte(8), asByte(6)).as("A Test").isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        byte[] array = array(asByte(8), asByte(6));
        new ByteArrayAssert(array).isNotSameAs(array);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<[8, 6]>").on(new CodeToTest() {
      public void run() {
        byte[] array = array(asByte(8), asByte(6));
        new ByteArrayAssert(array).as("A Test").isNotSameAs(array);
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotSame() {
    new ByteArrayAssert(asByte(8)).isNotSameAs(EMPTY_ARRAY);
  }

  private void shouldFailIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null array, but it was null").on(codeToTest);
  }

  private void shouldFailShowingDescriptionIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null array, but it was null").on(codeToTest);
  }

  private byte asByte(int i) { return (byte)i; }

  private byte[] array(byte... args) { return args; }
}
