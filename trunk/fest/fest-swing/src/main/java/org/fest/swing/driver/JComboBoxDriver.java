/*
 * Created on Jan 21, 2008
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
import java.awt.Container;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.core.TypeMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.TimeoutWatch;

import static java.lang.String.valueOf;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.driver.CellRendererComponents.textFrom;
import static org.fest.swing.util.Strings.*;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Arrays.format;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link JComboBox}</code>. Unlike <code>JComboBoxFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JComboBox}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver extends JComponentDriver {

  private static final String EDITABLE_PROPERTY = "editable";
  private static final String SELECTED_INDEX_PROPERTY = "selectedIndex";
  private final JListDriver listDriver;

  /**
   * Creates a new </code>{@link JComboBoxDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JComboBoxDriver(Robot robot) {
    super(robot);
    listDriver = new JListDriver(robot);
  }

  /**
   * Returns an array of <code>String</code>s that represent the <code>{@link JComboBox}</code> list. Note that the
   * current selection might not be included, since it's possible to have a custom (edited) entry there that is not
   * included in the default contents.
   * @param comboBox the target <code>JComboBox</code>.
   * @return an array of <code>String</code>s that represent the <code>JComboBox</code> list.
   */
  public String[] contentsOf(JComboBox comboBox) {
    int itemCount = size(comboBox);
    String[] items = new String[itemCount];
    for (int i = 0; i < itemCount; i++)
      items[i] = itemAt(comboBox, i).toString();
    return items;
  }

  /**
   * Returns the text of the element under the given index.
   * @param comboBox the target <code>JComboBox</code>.
   * @param index the given index.
   * @return the text of the element under the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JComboBox</code>.
   */
  public String text(JComboBox comboBox, int index) {
    validatedIndex(comboBox, index);
    Object item = itemAt(comboBox, index);
    String text = item.toString();
    if (!isDefaultToString(text)) return text;
    return textFrom(cellRendererComponent(comboBox, index, text));
  }

  private Component cellRendererComponent(JComboBox comboBox, int index, Object item) {
    return comboBox.getRenderer().getListCellRendererComponent(dropDownList(), item, index, true, true);
  }

  /**
   * Selects the first item matching the given text in the <code>{@link JComboBox}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @param text the text to match
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   */
  public void selectItem(JComboBox comboBox, String text) {
    if (areEqual(comboBox.getSelectedItem(), text)) return;
    int itemCount = size(comboBox);
    for (int i = 0; i < itemCount; i++) {
      if (areEqual(itemAt(comboBox, i), text)) {
        selectItem(comboBox, i);
        return;
      }
    }
    // While actions are supposed to represent real user actions, it's possible that the current environment does not
    // match sufficiently, so we need to throw an appropriate exception that can be used to diagnose the problem.
    throw new LocationUnavailableException(
        concat("Unable to find item ", quote(text), " among the JComboBox contents (",
            format(contentsOf(comboBox)), ")"));
  }

  private boolean areEqual(Object item, String text) {
    if (item == null && text == null) return true;
    if (item == null) return false;
    return match(text, item.toString());
  }

  private Object itemAt(JComboBox comboBox, int index) {
    return comboBox.getItemAt(index);
  }

  /**
   * Verifies that the <code>String</code> representation of the selected item in the <code>{@link JComboBox}</code> 
   * matches the given text.
   * @param comboBox the target <code>JComboBox</code>.
   * @param text the text to match.
   * @throws AssertionError if the selected item does not match the given text.
   */
  public void requireSelection(JComboBox comboBox, String text) {
    int selectedIndex = comboBox.getSelectedIndex();
    if (selectedIndex == -1) 
      fail(concat("[", selectedIndexProperty(comboBox), "] No selection"));
    assertThat(text(comboBox, selectedIndex)).as(selectedIndexProperty(comboBox)).isEqualTo(text);
  }

  private String selectedIndexProperty(JComboBox comboBox) {
    return propertyName(comboBox, SELECTED_INDEX_PROPERTY);
  }

  /**
   * Selects the item under the given index in the <code>{@link JComboBox}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @param index the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JComboBox</code>.
   */
  public void selectItem(final JComboBox comboBox, int index) {
    final int validatedIndex = validatedIndex(comboBox, index);
    showDropDownList(comboBox);
    try {
      listDriver.selectItem(dropDownList(), index);
    } catch (ComponentLookupException e) {
      robot.invokeAndWait(new Runnable() {
        public void run() {
          comboBox.setSelectedIndex(validatedIndex);
          if (isDropDownVisible(comboBox)) dropDownVisible(comboBox, false);
        }
      });
    }
  }

  private int validatedIndex(JComboBox comboBox, int index) {
    int itemCount = size(comboBox);
    if (index >= 0 && index < itemCount) return index;
    throw new LocationUnavailableException(concat(
        "Item index (", valueOf(index), ") should be between [", valueOf(0), "] and [",  valueOf(itemCount - 1),
        "] (inclusive)"));
  }

  private int size(JComboBox comboBox) { return comboBox.getItemCount(); }

  private void showDropDownList(final JComboBox comboBox) {
    if (isDropDownVisible(comboBox)) return;
    if (!comboBox.isEditable()) {
      click(comboBox);
      return;
    }
    // Location of pop-up button activator is LAF-dependent
    robot.invokeAndWait(new Runnable() {
      public void run() { dropDownVisible(comboBox, true); }
    });
  }

  private boolean isDropDownVisible(JComboBox comboBox) {
    return comboBox.getUI().isPopupVisible(comboBox);
  }

  private void dropDownVisible(JComboBox comboBox, boolean visible) {
    comboBox.getUI().setPopupVisible(comboBox, visible);
  }

  /**
   * Simulates a user entering the specified text in the <code>{@link JComboBox}</code> only if it is editable.
   * @param comboBox the target <code>JComboBox</code>.
   * @param text the text to enter.
   */
  public void enterText(JComboBox comboBox, String text) {
    if (!comboBox.isEditable()) return;
    focus(comboBox);
    robot.enterText(text);
  }

  /**
   * Find the <code>{@link JList}</code> in the pop-up raised by the <code>{@link JComboBox}</code>, if the LAF actually
   * uses one.
   * @return the found <code>JList</code>.
   * @throws ComponentLookupException if the <code>JList</code> in the pop-up could not be found.
   */
  public JList dropDownList() {
    JPopupMenu popup = robot.findActivePopupMenu();
    if (popup == null) {
      TimeoutWatch watch = startWatchWithTimeoutOf(robot.settings().timeoutToFindPopup());
      while ((popup = robot.findActivePopupMenu()) == null) {
        if (watch.isTimeOut()) throw listNotFound();
        pause();
      }
    }
    JList list = findListIn(popup);
    if (list == null) throw listNotFound();
    return list;
  }

  private ComponentLookupException listNotFound() {
    throw new ComponentLookupException("Unable to find the pop-up list for the JComboBox");
  }

  private JList findListIn(Container parent) {
    try {
      return (JList)robot.finder().find(LIST_MATCHER);
    } catch (ComponentLookupException ignored) {
      return null;
    }
  }

  private static final ComponentMatcher LIST_MATCHER = new TypeMatcher(JList.class);

  /**
   * Asserts that the given <code>{@link JComboBox}</code> is editable.
   * @param comboBox the target <code>JComboBox</code>.
   * @throws AssertionError if the <code>JComboBox</code> is not editable.
   */
  public void requireEditable(JComboBox comboBox) {
    assertThat(comboBox.isEditable()).as(editableProperty(comboBox)).isTrue();
  }

  /**
   * Asserts that the given <code>{@link JComboBox}</code> is not editable.
   * @param comboBox the given <code>JComboBox</code>.
   * @throws AssertionError if the <code>JComboBox</code> is editable.
   */
  public void requireNotEditable(JComboBox comboBox) {
    assertThat(comboBox.isEditable()).as(editableProperty(comboBox)).isFalse();
  }

  private static String editableProperty(JComboBox comboBox) {
    return propertyName(comboBox, EDITABLE_PROPERTY);
  }
}