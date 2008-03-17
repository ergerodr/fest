/*
 * Created on Feb 24, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestList;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JListDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JListDriverTest {

  private Robot robot;
  private TestList dragList;
  private TestList dropList;
  private JListDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JListDriver(robot);
    MyFrame frame = new MyFrame();
    dragList = frame.dragList;
    dropList = frame.dropList;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldReturnListContents() {
    String[] contents = driver.contentsOf(dragList);
    assertThat(contents).isEqualTo(array("one", "two", "three"));
  }

  @Test public void shouldSelectItemAtGivenIndex() {
    driver.selectItem(dragList, 2);
    assertThat(dragList.getSelectedValue()).isEqualTo("three");
  }

  @Test public void shouldSelectItemWithGivenText() {
    driver.selectItem(dragList, "two");
    assertThat(dragList.getSelectedValue()).isEqualTo("two");
  }

  @Test public void shouldSelectItemsWithGivenText() {
    driver.selectItems(dragList, array("two", "three"));
    assertThat(dragList.getSelectedValues()).isEqualTo(array("two", "three"));
  }

  @Test public void shouldSelectItemsWithGivenIndices() {
    driver.selectItems(dragList, new int[] { 1, 2 });
    assertThat(dragList.getSelectedValues()).isEqualTo(array("two", "three"));
  }

  @Test public void shouldSelectItemsInGivenRange() {
    driver.selectItems(dragList, 0, 1);
    assertThat(dragList.getSelectedValues()).isEqualTo(array("one", "two"));
  }

  @Test public void shouldReturnValueAtGivenIndex() {
    String text = driver.text(dragList, 2);
    assertThat(text).isEqualTo("three");
  }

  @Test public void shouldDragAndDropValueUsingGivenNames() {
    driver.drag(dragList, "two");
    driver.drop(dropList, "six");
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "two"));
  }

  @Test public void shouldDrop() {
    driver.drag(dragList, "two");
    driver.drop(dropList);
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).hasSize(4);
  }

  @Test public void shouldDragAndDropValueUsingGivenIndices() {
    driver.drag(dragList, 2);
    driver.drop(dropList, 2);
    assertThat(dragList.elements()).isEqualTo(array("one", "two"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "three"));
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final TestList dragList = new TestList("one", "two", "three");
    final TestList dropList = new TestList("four", "five", "six");

    MyFrame() {
      super(JListDriverTest.class);
      add(decorate(dragList));
      add(decorate(dropList));
      setPreferredSize(new Dimension(300, 100));
    }

    private JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }
}
