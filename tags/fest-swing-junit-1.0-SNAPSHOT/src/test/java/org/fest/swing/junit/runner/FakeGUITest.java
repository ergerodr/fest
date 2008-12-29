/*
 * Created on Nov 17, 2007
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
package org.fest.swing.junit.runner;

import org.fest.swing.annotation.GUITest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Understands a JUnit test to be used to test the TestNG tests in this package.
 *
 * @author Alex Ruiz
 */
@RunWith(GUITestRunner.class)
public class FakeGUITest {

  @GUITest @Test public void successfulGUITest() {}
  
  @GUITest @Test public void failedGUITest() {
    throw new RuntimeException("Failed on purpose");
  }

  @Test public void successfulNonGUITest() {}

  @Test public void failedNonGUITest() {
    throw new RuntimeException("Failed on purpose");
  }
}
