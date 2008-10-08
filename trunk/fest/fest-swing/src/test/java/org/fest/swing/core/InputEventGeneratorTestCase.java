/*
 * Created on Apr 3, 2008
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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestWindow;
import org.fest.swing.util.AWT;

import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.query.JTextComponentTextQuery.textOf;
import static org.fest.swing.task.ComponentHasFocusCondition.untilFocused;
import static org.fest.swing.task.ComponentRequestFocusTask.giveFocusTo;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.util.AWT.centerOf;

/**
 * Test case for implementations of <code>{@link InputEventGenerator}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class InputEventGeneratorTestCase {

  private MyWindow window;
  private InputEventGenerator generator;

  protected static final String MOVE_MOUSE_TEST = "Move Mouse Test";

  @BeforeMethod public void setUp() throws Exception {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew(getClass());
    onSetUp();
    generator = generator();
    window.display();
  }

  void onSetUp() throws Exception {}

  abstract InputEventGenerator generator();

  @AfterMethod public void tearDown() {
    window.destroy();
    ScreenLock.instance().release(this);
  }

  @Test(groups = { GUI, MOVE_MOUSE_TEST } )
  public void shouldMoveMouse() {
    MouseMotionRecorder recorder = MouseMotionRecorder.attachTo(window);
    Point center = centerOf(window);
    generator.moveMouse(window, center.x, center.y);
    pause(200);
    assertThat(recorder.point()).isEqualTo(center);
  }

  @Test(groups = GUI, dataProvider = "mouseButtons", dependsOnGroups = MOVE_MOUSE_TEST)
  public void shouldClickMouseButtonOnComponent(MouseButton button) {
    ClickRecorder recorder = ClickRecorder.attachTo(window.textBox);
    Point center = centerOf(window.textBox);
    generator.pressMouse(window.textBox, center, button.mask);
    generator.releaseMouse(button.mask);
    pause(200);
    recorder.clicked(button);
    assertThat(recorder.pointClicked()).isEqualTo(center);
  }

  @Test(groups = GUI, dataProvider = "mouseButtons", dependsOnGroups = MOVE_MOUSE_TEST)
  public void shouldClickMouseButton(MouseButton button) {
    Point center = AWT.centerOf(window);
    generator.moveMouse(window, center.x, center.y);
    ClickRecorder recorder = ClickRecorder.attachTo(window);
    generator.pressMouse(button.mask);
    generator.releaseMouse(button.mask);
    pause(200);
    assertThat(recorder.clicked(button));
  }

  @DataProvider(name = "mouseButtons") public Object[][] mouseButtons() {
    return new Object[][] { { LEFT_BUTTON }, { MIDDLE_BUTTON }, { RIGHT_BUTTON } };
  }

  @Test(dataProvider = "keys")
  public void shouldTypeKey(int keyToPress, String expectedText) {
    giveFocusTo(window.textBox);
    pause(untilFocused(window.textBox));
    generator.pressKey(keyToPress, CHAR_UNDEFINED);
    generator.releaseKey(keyToPress);
    pause(200);
    assertThatTextBoxTextIsEqualTo(expectedText);
  }

  private void assertThatTextBoxTextIsEqualTo(String expectedText) {
    String text = textOf(window.textBox);
    assertThat(text).isEqualTo(expectedText);
  }

  @DataProvider(name = "keys") public Object[][] keys() {
    return new Object[][] { { VK_A , "a" }, { VK_S, "s" }, { VK_D, "d" } };
  }

  private static class MouseMotionRecorder extends MouseMotionAdapter {
    private Point point;

    static MouseMotionRecorder attachTo(Component c) {
      MouseMotionRecorder recorder = new MouseMotionRecorder();
      c.addMouseMotionListener(recorder);
      return recorder;
    }

    @Override public void mouseMoved(MouseEvent e) {
      point = e.getPoint();
    }

    Point point() { return point; }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textBox = new JTextField(20);

    static MyWindow createNew(Class<? extends InputEventGeneratorTestCase> testClass) {
      return new MyWindow(testClass);
    }

    private MyWindow(Class<? extends InputEventGeneratorTestCase> testClass) {
      super(testClass);
      addComponents(textBox);
    }
  }
}
