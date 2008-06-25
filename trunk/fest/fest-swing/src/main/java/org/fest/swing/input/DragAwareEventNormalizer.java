/*
 * Created on Jun 25, 2008
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
package org.fest.swing.input;

import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.util.EmptyStackException;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;
import static javax.swing.SwingUtilities.invokeAndWait;

/**
 * Understands an <code>{@link AWTEventListener}</code> which normalizes the event stream:
 * <ul>
 * <li>sends a single <code>WINDOW_CLOSED</code>, instead of one every time dispose is called
 * <li>catches <code>sun.awt.dnd.SunDropTargetEvents</code> during native drags
 * </ul>
 *
 * @author Alex Ruiz
 */
class DragAwareEventNormalizer extends EventNormalizer {

  private static Logger logger = Logger.getLogger(DragAwareEventNormalizer.class.getName());

  private DragAwareEventQueue dragAwareEventQueue;

  @Override void startListening(final Toolkit toolkit, AWTEventListener delegate, long mask) {
    super.startListening(toolkit, delegate, mask);
    try {
      dragAwareEventQueue = createDragAwareEventQueue(toolkit, mask);
      invokeAndWait(new PushEventQueueTask(toolkit, dragAwareEventQueue));
    } catch (Exception e) {
      logger.log(WARNING, "Ignoring error at EventNormalizer startup", e);
    }
  }

  DragAwareEventQueue createDragAwareEventQueue(Toolkit toolkit, long mask) {
    return new DragAwareEventQueue(toolkit, mask, this);
  }

  @Override void stopListening() {
    disposeDragAwareEventQueue();
    super.stopListening();
  }

  private void disposeDragAwareEventQueue() {
    if (dragAwareEventQueue == null) return;
    try {
      dragAwareEventQueue.pop();
    } catch (EmptyStackException e) {}
    dragAwareEventQueue = null;
  }
}
