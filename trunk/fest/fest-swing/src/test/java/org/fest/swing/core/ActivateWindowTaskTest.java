/*
 * Created on Feb 23, 2008
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
import java.awt.Dimension;

import javax.swing.JFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.util.Strings;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.factory.JFrames.frame;
import static org.fest.swing.task.FrameShowTask.packAndShow;
import static org.fest.swing.task.WindowDestroyTask.destroy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link ActivateWindowTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ActivateWindowTaskTest {

  private JFrame frameOne;
  private JFrame frameTwo;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    String testName = ActivateWindowTaskTest.class.getSimpleName();
    Dimension size = new Dimension(300, 200);
    frameOne = frame().withTitle(Strings.concat(testName, " - One")).createNew();
    packAndShow(frameOne, size);
    frameTwo = frame().withTitle(Strings.concat(testName, " - Two")).createNew();
    packAndShow(frameTwo, size);
  }

  @AfterMethod public void tearDown() {
    destroy(frameOne);
    destroy(frameTwo);
    ScreenLock.instance().release(this);
  }

  public void shouldActivateWindow() {
    pause(new HasFocusCondition(frameTwo));
    ActivateWindowTask.activateWindow(frameOne);
    // verify that frameOne was given focus (i.e. was activated)
    pause(new HasFocusCondition(frameOne));
  }

  private static class HasFocusCondition extends Condition {
    private final Component c;

    HasFocusCondition(Component c) {
      super("Component has focus");
      this.c = c;
    }

    public boolean test() {
      return c.hasFocus();
    }
  }
}
