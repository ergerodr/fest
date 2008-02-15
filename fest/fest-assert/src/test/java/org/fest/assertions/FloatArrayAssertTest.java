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
 * Tests for <code>{@link FloatArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FloatArrayAssertTest {

  private static final float[] NULL_ARRAY = null;
  private static final float[] EMPTY_ARRAY = new float[0];

  @Test public void shouldSetDescription() {
    FloatArrayAssert assertion = new FloatArrayAssert(36.9f);
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    FloatArrayAssert assertion = new FloatArrayAssert(36.9f);
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyOrNullArrayCondition extends Condition<float[]> {
    @Override public boolean matches(float[] array) {
      return array == null || array.length == 0;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new FloatArrayAssert(EMPTY_ARRAY).satisfies(new EmptyOrNullArrayCondition());
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("condition failed with:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).satisfies(new EmptyOrNullArrayCondition());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] condition failed with:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").satisfies(new EmptyOrNullArrayCondition());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("expected:<Empty or null array> but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).satisfies(new EmptyOrNullArrayCondition().as("Empty or null array"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] expected:<Empty or null array> but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").satisfies(new EmptyOrNullArrayCondition().as("Empty or null array"));
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsIsInArray() {
    new FloatArrayAssert(36.9f).contains(36.9f);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).contains(36.9f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").contains(36.9f);
      }
    });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expectAssertionError("array:<[]> does not contain element(s):<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).contains(36.9f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").contains(36.9f);
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArray() {
    new FloatArrayAssert(36.9f).excludes(88.43f);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).excludes(36.9f);
      }
    });
  }

  @Test public void shouldFailShowindDescriptionIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").excludes(36.9f);
      }
    });
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("array:<[36.9]> does not exclude element(s):<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).excludes(36.9f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("[A Test] array:<[36.9]> does not exclude element(s):<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").excludes(36.9f);
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new FloatArrayAssert(NULL_ARRAY).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new FloatArrayAssert(EMPTY_ARRAY).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmpty() {
    new FloatArrayAssert(EMPTY_ARRAY).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty array, but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty array, but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmpty() {
    new FloatArrayAssert(36.9f).isNotEmpty();
  }

  @Test public void shouldFailIfArrayIsEmpty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmpty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfArraysAreEqual() {
    new FloatArrayAssert(36.9f).isEqualTo(array(36.9f));
  }

  @Test public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<[88.43]> but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).isEqualTo(array(88.43f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<[88.43]> but was:<[36.9]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").isEqualTo(array(88.43f));
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotEqual() {
    new FloatArrayAssert(36.9f).isNotEqualTo(array(886.8f));
  }

  @Test public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<[36.9]> should not be equal to:<[36.9]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f).isNotEqualTo(array(36.9f));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<[36.9]> should not be equal to:<[36.9]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f).as("A Test").isNotEqualTo(array(36.9f));
          }
        });
  }

  @Test public void shouldPassIfActualContainsOnlyExpectedElements() {
    new FloatArrayAssert(98.6f).containsOnly(98.6f);
  }

  @Test public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("array:<[]> does not contain element(s):<[88.43]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[88.43]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(EMPTY_ARRAY).as("A Test").containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new FloatArrayAssert(NULL_ARRAY).as("A Test").containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("unexpected element(s):<[86.2]> in array:<[36.9, 86.2]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f, 86.2f).containsOnly(array(36.9f));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] unexpected element(s):<[86.2]> in array:<[36.9, 86.2]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f, 86.2f).as("A Test").containsOnly(array(36.9f));
          }
        });
  }

  @Test public void shouldFailIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("array:<[36.9]> does not contain element(s):<[88.43]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).containsOnly(array(88.43f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] array:<[36.9]> does not contain element(s):<[88.43]>").on(
        new CodeToTest() {
          public void run() {
            new FloatArrayAssert(36.9f).as("A Test").containsOnly(array(88.43f));
          }
        });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    float[] array = array(36.9f, 89.36f);
    new FloatArrayAssert(array).hasSize(2);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for array:<[36.9]>").on(new CodeToTest() {
      public void run() {
        float[] array = array(36.9f);
        new FloatArrayAssert(array).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for array:<[36.9]>").on(new CodeToTest() {
      public void run() {
        float[] array = array(36.9f);
        new FloatArrayAssert(array).as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldPassIfArraysAreSame() {
    float[] array = array(36.9f);
    new FloatArrayAssert(array).isSameAs(array);
  }

  @Test public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<[36.9]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<[36.9]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new FloatArrayAssert(36.9f).as("A Test").isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<[36.9]>").on(new CodeToTest() {
      public void run() {
        float[] array = array(36.9f);
        new FloatArrayAssert(array).isNotSameAs(array);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<[36.9]>").on(new CodeToTest() {
      public void run() {
        float[] array = array(36.9f);
        new FloatArrayAssert(array).as("A Test").isNotSameAs(array);
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotSame() {
    new FloatArrayAssert(36.9f).isNotSameAs(EMPTY_ARRAY);
  }

  private void shouldFailIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null array, but it was null").on(codeToTest);
  }

  private void shouldFailShowingDescriptionIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null array, but it was null").on(codeToTest);
  }

  private float[] array(float... args) { return args; }
}
