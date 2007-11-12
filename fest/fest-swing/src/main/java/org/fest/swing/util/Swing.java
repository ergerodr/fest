/*
 * Created on Oct 12, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.util;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Insets;
import java.lang.reflect.InvocationTargetException;

/**
 * Understands AWT-related utility methods.
 * <p>
 * Adapted from <code>abbot.util.AWT</code> from <a href="http://abbot.sourceforge.net" target="_blank">Abbot</a>.
 * </p>
 */
public final class Swing {

  private static final String APPLET_APPLET_VIEWER_CLASS = "sun.applet.AppletViewer";
  private static final String ROOT_FRAME_CLASSNAME = concat(SwingUtilities.class.getName(), "$");

  /**
   * Returns the insets of the given container, or an empty one if no insets can be found.
   * @param c the given container.
   * @return the insets of the given container, or an empty one if no insets can be found.
   */
  public static Insets insetsFrom(Container c) {
    try {
      Insets insets = c.getInsets();
      if (insets != null) return insets;
    } catch (Exception e) {}
    return new Insets(0, 0, 0, 0);
  }
  
  /**
   * Returns <code>true</code> if the given component is an Applet viewer.
   * @param c the component to check.
   * @return <code>true</code> if the given component is an Applet viewer, <code>false</code> otherwise.
   */
  public static boolean isAppletViewer(Component c) {
    return c != null && APPLET_APPLET_VIEWER_CLASS.equals(c.getClass().getName());
  }

  /**
   * Returns whether the given component is the default Swing hidden frame.
   * @param c the component to check.
   * @return <code>true</code> if the given component is the default hidden frame, <code>false</code> otherwise.
   */
  public static boolean isSharedInvisibleFrame(Component c) {
    if (c == null) return false;
    // Must perform an additional check, since applets may have their own version in their AppContext
    return c instanceof Frame
        && (c == JOptionPane.getRootFrame() || c.getClass().getName().startsWith(ROOT_FRAME_CLASSNAME));
  }

  /**
   * If the current thread is the AWT event thread, this method will simple execute the <code>{@link Runnable}</code>,
   * otherwise the <code>{@link Runnable}</code> will be executed synchronously, blocking until all pending AWT events
   * have been processed and <code>r.run()</code> returns.
   * @param r the <code>Runnable</code> to execute.
   * @exception InterruptedException if we're interrupted while waiting for the event dispatching thread to finish
   *              excecuting <code>r.run()</code>.
   * @exception InvocationTargetException if an exception is thrown while running <code>r</code>.
   * @see SwingUtilities#isEventDispatchThread()
   * @see SwingUtilities#invokeAndWait(Runnable)
   */
  public static void runInEventThreadAndWait(Runnable r) throws InterruptedException, InvocationTargetException {
    if (isEventDispatchThread()) {
      r.run();
      return;
    }
    invokeAndWait(r);
  }

  /**
   * Returns the name of the given component. If the component is <code>null</code>, this method will return
   * <code>null</code>.
   * @param c the given component.
   * @return the name of the given component, or <code>null</code> if the component is <code>null</code>.
   */
  public static String quoteNameOf(Component c) {
    if (c == null) return null;
    return quote(c.getName());
  }

  private Swing() {}
}
