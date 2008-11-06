/*
 * Created on Jan 27, 2008
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

import java.awt.Container;
import java.awt.Insets;
import java.awt.Point;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.awt.AWT.locationOnScreenOf;
import static org.fest.swing.driver.ComponentMoveTask.moveComponent;
import static org.fest.swing.driver.ContainerResizeInfoQuery.resizeInfoOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.task.ComponentSetSizeTask.setComponentSize;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link Container}</code>. This class is intended for internal use
 * only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ContainerDriver extends ComponentDriver {

  /**
   * Creates a new </code>{@link ContainerDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public ContainerDriver(Robot robot) {
    super(robot);
  }

  /**
   * Resizes the <code>{@link Container}</code> to the given size.
   * @param c the target <code>Container</code>.
   * @param width the width to resize the <code>Container</code> to.
   * @param height the height to resize the <code>Container</code> to.
   * @throws ActionFailedException if the <code>Container</code> is not enabled.
   * @throws ActionFailedException if the <code>Container</code> is not resizable by the user.
   */
  @RunsInEDT
  public void resize(Container c, int width, int height) {
    ContainerResizeInfo resizeInfo = resizeInfoOf(c);
    resizeBy(c, resizeInfo, width - resizeInfo.width, height - resizeInfo.height);
  }

  @RunsInEDT
  private void resizeBy(Container c, ContainerResizeInfo resizeInfo, int x, int y) {
    simulateResizeStarted(c, resizeInfo, x, y);
    setComponentSize(c, resizeInfo.width + x, resizeInfo.height + y);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void simulateResizeStarted(Container c, ContainerResizeInfo resizeInfo, int x, int y) {
    Point p = resizeLocation(resizeInfo);
    robot.moveMouse(c, p.x, p.y);
    robot.moveMouse(c, p.x + x, p.y + y);
    robot.waitForIdle();
  }

  private static Point resizeLocation(final ContainerResizeInfo resizeInfo) {
    return resizeLocation(resizeInfo.width, resizeInfo.height, resizeInfo.right, resizeInfo.bottom);
  }

  /**
   * Returns where the mouse usually grabs to resize a window. The lower right corner of the window is usually a good
   * choice.
   * @param c the target <code>Container</code>.
   * @return where the mouse usually grabs to resize a window.
   */
  @RunsInEDT
  protected Point resizeLocationOf(final Container c) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        return resizeLocation(c);
      }
    });
  }

  @RunsInCurrentThread
  private static Point resizeLocation(Container c) {
    return resizeLocation(c.getWidth(), c.getHeight(), c.getInsets());
  }

  private static Point resizeLocation(int width, int height, Insets insets) {
    return resizeLocation(width, height, insets.right, insets.bottom);
  }

  private static Point resizeLocation(int width, int height, int right, int bottom) {
    return new Point(width - right / 2, height - bottom / 2);
  }

  /**
   * Move the given <code>{@link Container}</code> to the requested location.
   * @param c the target <code>Container</code>.
   * @param x the horizontal coordinate.
   * @param y the vertical coordinate.
   * @throws ActionFailedException if the given container is not showing on the screen.
   */
  public void move(Container c, int x, int y) {
    Point onScreen = locationOnScreenOf(c);
    if (onScreen == null) throw componentNotShowingOnScreen(c);
    moveBy(c, onScreen, x - onScreen.x, y - onScreen.y);
  }

  private ActionFailedException componentNotShowingOnScreen(Container c) {
    throw actionFailure(concat("The component ", format(c), " is not showing on the screen"));
  }

  private void moveBy(Container c, Point locationOnScreen, int horizontally, int vertically) {
    simulateMoveStarted(c, horizontally, vertically);
    Point location = new Point(locationOnScreen.x + horizontally, locationOnScreen.y + vertically);
    moveComponent(c, location);
    simulateMoveComplete(c);
    robot.waitForIdle();
  }

  private void simulateMoveStarted(Container c, int horizontally, int vertically) {
    if (!isUserMovable(c)) return;
    Point p = moveLocationOf(c);
    robot.moveMouse(c, p.x, p.y);
    robot.moveMouse(c, p.x + horizontally, p.y + vertically);
  }

  private void simulateMoveComplete(Container c) {
    if (!isUserMovable(c)) return;
    Point p = moveLocationOf(c);
    robot.moveMouse(c, p.x, p.y);
  }

  /**
   * Returns where the mouse usually grabs to move a container (or window.) Center of the top of the frame is usually a
   * good choice.
   * @param c the given <code>Container</code>.
   * @return where the mouse usually grabs to move a container (or window.)
   */
  protected Point moveLocationOf(Container c) {
    return ContainerMoveLocationQuery.moveLocationOf(c);
  }
}
