/*
 * Created on Jul 30, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.ExtensionTest.JTextFieldFixtureExtension.textFieldWithName;
import static org.fest.swing.testing.TestGroups.GUI;

import java.awt.Container;

import javax.swing.JTextField;

import org.fest.swing.fixture.ComponentFixture;
import org.fest.swing.fixture.ComponentFixtureExtension;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for issue <a href="http://code.google.com/p/fest/issues/detail?id=109" target="_blank">109</a>: Add support for 
 * extension.
 * 
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ExtensionTest {

  private FrameFixture fixture;

  @BeforeMethod public void setUp() {
    fixture = new FrameFixture(TestWindow.createNew(ExtensionTest.class));
  }

  @AfterMethod public void tearDown() {
    fixture.cleanUp();
  }

  public void shouldCreateFixtureUsingExtension() {
    JTextFieldFixture textField = fixture.with(textFieldWithName("hello"));
    assertThat(textField).isNotNull();
  }
  
  static class JTextFieldFixtureExtension extends ComponentFixtureExtension<JTextField, JTextFieldFixture> {
    static JTextFieldFixtureExtension textFieldWithName(String name) {
      return new JTextFieldFixtureExtension();
    }
    
    public JTextFieldFixture createFixture(Robot robot, Container root) {
      return new JTextFieldFixture(robot, new JTextField());
    }
  }
  
  static class JTextFieldFixture extends ComponentFixture<JTextField> {
    public JTextFieldFixture(Robot robot, JTextField target) {
      super(robot, target);
    }
  }
}
