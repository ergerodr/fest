/*
 * Created on Jul 27, 2008
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
package org.fest.swing.query;

import java.awt.Dialog;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the title of a 
 * <code>{@link Dialog}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class DialogTitleQuery extends GuiQuery<String> {
  private final Dialog dialog;

  /**
   * Returns the title of the given <code>{@link Dialog}</code>. This action is executed in the event dispatch thread.
   * @param dialog the given <code>Dialog</code>.
   * @return the title of the given <code>Dialog</code>.
   */
  public static String titleOf(Dialog dialog) {
    return execute(new DialogTitleQuery(dialog));
  }

  private DialogTitleQuery(Dialog dialog) {
    this.dialog = dialog;
  }

  /**
   * Returns the title in this query's <code>{@link Dialog}</code>. This action is executed in the event dispatch 
   * thread.
   * @return the title in this query's <code>Dialog</code>.
   */
  protected String executeInEDT() {
    return dialog.getTitle();
  }
}