/*
 * Created on Aug 8, 2008
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

import java.io.File;

import javax.swing.JFileChooser;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that sets the current directory in a <code>{@link JFileChooser}</code>. This task should be 
 * executed in the event dispatch thread.
 *
 * @author Yvonne Wang
 */
class JFileChooserSetCurrentDirectoryTask extends GuiTask {

  private final JFileChooser fileChooser;
  private final File dir;

  static JFileChooserSetCurrentDirectoryTask setCurrentDirectoryIn(JFileChooser fileChooser, File dir) {
    return new JFileChooserSetCurrentDirectoryTask(fileChooser, dir);
  }
  
  private JFileChooserSetCurrentDirectoryTask(JFileChooser fileChooser, File dir) {
    this.fileChooser = fileChooser;
    this.dir = dir;
  }

  protected void executeInEDT() {
    fileChooser.setCurrentDirectory(dir);
  }
}