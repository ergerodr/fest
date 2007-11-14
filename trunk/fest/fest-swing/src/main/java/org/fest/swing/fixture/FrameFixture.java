/*
 * Created on Feb 8, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import abbot.tester.FrameTester;
import abbot.util.Bugs;
import org.fest.swing.Condition;
import org.fest.swing.MouseButton;
import org.fest.swing.RobotFixture;

import java.awt.*;
import static java.awt.Frame.*;

/**
 * Understands simulation of user events on a <code>{@link Frame}</code> and verification of the state of such
 * <code>{@link Frame}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FrameFixture extends WindowFixture<Frame> {

  /**
   * Creates a new </code>{@link FrameFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param name the name of the <code>Frame</code> to find.
   */
  public FrameFixture(String name) {
    super(name, Frame.class);
  }

  /**
   * Creates a new </code>{@link FrameFixture}</code>.
   * @param robot performs user events on the given window and verifies expected output.
   * @param name the name of the <code>Frame</code> to find using the given <code>RobotFixture</code>.
   */
  public FrameFixture(RobotFixture robot, String name) {
    super(robot, name, Frame.class);
  }

  /**
   * Creates a new </code>{@link FrameFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the <code>Frame</code> to be managed by this fixture.
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public FrameFixture(Frame target) {
    super(target);
  }

  /**
   * Creates a new </code>{@link FrameFixture}</code>.
   * @param robot performs user events on the given window and verifies expected output.
   * @param target the <code>Frame</code> to be managed by this fixture.
   */
  public FrameFixture(RobotFixture robot, Frame target) {
    super(robot, target);
  }
  
  /**
   * Shows the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture show() {
    return (FrameFixture)doShow();
  }
  
  /**
   * Shows the <code>{@link Frame}</code> managed by this fixture, resized to the given size.
   * @param size the size to resize the managed <code>Frame</code> to.
   * @return this fixture.
   */
  public final FrameFixture show(Dimension size) {
    return (FrameFixture)doShow(size);
  }

  /**
   * Simulates a user clicking the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture click() {
    return (FrameFixture)doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link Frame}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final FrameFixture click(MouseButton button) {
    return (FrameFixture)doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link Frame}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final FrameFixture click(MouseClickInfo mouseClickInfo) {
    return (FrameFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture rightClick() {
    return (FrameFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture doubleClick() {
    return (FrameFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public FrameFixture focus() {
    return (FrameFixture)doFocus();
  }

  /**
   * Simulates a user iconifying the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture iconify() {
    frameTester().iconify(target);
    robot.wait(new Condition("frame being iconified") {
      public boolean test() {
        return target.getExtendedState() == ICONIFIED;
      }
    });
    return this;
  }

  /**
   * Simulates a user deiconifying the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture deiconify() {
    frameTester().deiconify(target);
    robot.wait(new Condition("frame being deiconified") {
      public boolean test() {
        return target.getExtendedState() != ICONIFIED;
      }
    });
    return this;
  }

  /**
   * Simulates a user maximizing the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture maximize() {
    frameTester().maximize(target);
    robot.wait(new Condition("frame being maximized") {
      public boolean test() {
        return (target.getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH;
      }
    });
    return this;
  }

  /**
   * Simulates a user normalizing the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture normalize() {
    robot.invokeLater(target, new Runnable() {
      public void run() {
        target.setExtendedState(NORMAL);
        if (Bugs.hasFrameDeiconifyBug()) target.setVisible(true);
      }
    });
    robot.wait(new Condition("frame being normalized") {
      public boolean test() {
        return target.getExtendedState() == NORMAL;
      }
    });
    return this;
  }

  private FrameTester frameTester() {
    return (FrameTester)tester();
  }

  /**
   * Simulates a user resizing horizontally the <code>{@link Frame}</code> managed by this fixture.
   * @param width the width that the managed <code>Frame</code> should have after being resized.
   * @return this fixture.
   */
  public final FrameFixture resizeWidthTo(int width) {
    return (FrameFixture)doResizeWidthTo(width);
  }

  /**
   * Simulates a user resizing vertically the <code>{@link Frame}</code> managed by this fixture.
   * @param height the height that the managed <code>Frame</code> should have after being resized.
   * @return this fixture.
   */
  public final FrameFixture resizeHeightTo(int height) {
    return (FrameFixture)doResizeHeightTo(height);
  }

  /**
   * Simulates a user resizing the <code>{@link Frame}</code> managed by this fixture.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  public final FrameFixture resizeTo(Dimension size) {
    return (FrameFixture)doResizeTo(size);
  }

  /**
   * Asserts that the size of the <code>{@link Frame}</code> managed by this fixture is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of the managed <code>Frame</code> is not equal to the given size. 
   */
  public final FrameFixture requireSize(Dimension size) {
    return (FrameFixture)assertEqualSize(size);
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link Frame}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final FrameFixture pressAndReleaseKeys(int... keyCodes) {
    return (FrameFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on the <code>{@link Frame}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final FrameFixture pressKey(int keyCode) {
    return (FrameFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on the <code>{@link Frame}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final FrameFixture releaseKey(int keyCode) {
    return (FrameFixture)doReleaseKey(keyCode);
  }  

  /**
   * Asserts that the <code>{@link Frame}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Frame</code> is not visible.
   */
  public final FrameFixture requireVisible() {
    return (FrameFixture)assertVisible();
  }
  
  /**
   * Asserts that the <code>{@link Frame}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Frame</code> is visible.
   */
  public final FrameFixture requireNotVisible() {
    return (FrameFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link Frame}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Frame</code> is disabled.
   */
  public final FrameFixture requireEnabled() {
    return (FrameFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link Frame}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Frame</code> is enabled.
   */
  public final FrameFixture requireDisabled() {
    return (FrameFixture)assertDisabled();
  }
}