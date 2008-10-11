/*
 * Created on Aug 31, 2008
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
package org.fest.swing.launcher;

import java.applet.Applet;
import java.util.Map;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.core.Condition;
import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.Pause.pause;

/**
 * Understands an action, executed in the event dispatch thread, that creates and shows a new
 * <code>{@link AppletViewer}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class NewAppletViewerQuery {

  // TODO test
  static AppletViewer showAppletViewerWith(final Applet applet, final Map<String, String> parameters) {
    final AppletViewer viewer = execute(new GuiQuery<AppletViewer>() {
      protected AppletViewer executeInEDT() {
        AppletViewer newViewer = new AppletViewer(applet, parameters);
        newViewer.pack();
        newViewer.setVisible(true);
        return newViewer;
      }
    });
    pause(new Condition("new AppletViewer is showing") {
      public boolean test() {
        return viewer.isShowing();
      }
    });
    return viewer;
  }

  private NewAppletViewerQuery() {}
}
