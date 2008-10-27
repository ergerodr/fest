/*
 * Created on Apr 12, 2008
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

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.CustomCellRenderer;
import org.fest.swing.testing.TestListModel;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicJListCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJListCellReaderTest {

  private Robot robot;
  private MyList list;
  private BasicJListCellReader reader;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    list = window.list;
    reader = new BasicJListCellReader();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnModelValueToString() {
    list.setElements(new Jedi("Yoda"));
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    list.setElements(new Object[] { null });
    setRendererComponent(list, new JToolBar());
    robot.waitForIdle();
    Object value = reader.valueAt(list, 0);
    assertThat(value).isNull();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    list.setElements(new Jedi(null));
    JLabel renderer = new JLabel("First");
    setRendererComponent(this.list, renderer);
    robot.waitForIdle();
    Object value = reader.valueAt(this.list, 0);
    assertThat(value).isEqualTo("First");
  }

  private static void setRendererComponent(final JList list, final Component renderer) {
    final CustomCellRenderer cellRenderer = new CustomCellRenderer(renderer);
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setCellRenderer(cellRenderer);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyList list = new MyList("One", "Two");

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(BasicJListCellReaderTest.class);
      addComponents(list);
    }
  }

  private static class MyList extends JList {
    private static final long serialVersionUID = 1L;

    private final TestListModel model;

    MyList(Object... elements) {
      model = new TestListModel(elements);
      setModel(model);
    }

    void setElements(final Object...elements) {
      execute(new GuiTask() {
        protected void executeInEDT() {
          model.setElements(elements);
        }
      });
    }

    @Override public TestListModel getModel() {
      return model;
    }
  }
}