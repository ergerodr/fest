/*
 * Created on Oct 21, 2008
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

import java.awt.Component;

import javax.swing.JLabel;

/**
 * Understands a basic implementation of <code>{@link CellRendererComponentReader}</code>.
 * 
 * @author Alex Ruiz
 */
public class BasicCellRendererComponentReader implements CellRendererComponentReader {

  /**
   * Reads the value in the given cell renderer component, or returns <code>null</code> if the renderer belongs to an
   * unknown component type. Internally, this method will call <code>getText()</code> if the given renderer is an
   * instance of <code>{@link JLabel}</code></li>. <b>Note:</b> this method is <b>not</b> called in the event dispatch
   * thread. This is a helper method, callers of this method are responsible for calling it in the event dispatch
   * thread.
   * @param c the given cell renderer component.
   * @return the value of the given <code>Component</code>, or <code>null</code> if the renderer belongs to an unknown
   * component type.
   */
  public String valueFrom(Component c) {
    if (c instanceof JLabel) return ((JLabel)c).getText();
    return null;
  }

}
