/*
 * Created on Sep 25, 2008
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
package org.fest.swing.task;

import javax.swing.JComboBox;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;

/**
 * Understands a task that selects an element at the given index in a <code>{@link JComboBox}</code>. This task is
 * executed in the event dispatch thread.
 * @see JComboBox#setSelectedIndex(int)
 *
 * @author Yvonne Wang
 */
public final class JComboBoxSetSelectedIndexTask {

  public static void setSelectedIndex(final JComboBox comboBox, final int index) {
    GuiActionRunner.execute(new GuiTask() {
      protected void executeInEDT() {
        comboBox.setSelectedIndex(index);
      }
    });
  }

  private JComboBoxSetSelectedIndexTask() {}
}