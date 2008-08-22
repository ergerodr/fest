/*
 * Created on Aug 8, 2008
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
package org.fest.swing.driver;

import javax.swing.JComboBox;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether the drop-down list of a
 * <code>{@link JComboBox}</code> is visible or not.
 * 
 * @author Alex Ruiz
 */
class JComboBoxDropDownVisibleQuery extends GuiQuery<Boolean> {
  
  private final JComboBox comboBox;

  static boolean isDropDownVisible(JComboBox comboBox) {
    return execute(new JComboBoxDropDownVisibleQuery(comboBox));
  }

  JComboBoxDropDownVisibleQuery(JComboBox comboBox) {
    this.comboBox = comboBox;
  }

  protected Boolean executeInEDT() {
    return comboBox.getUI().isPopupVisible(comboBox);
  }
}