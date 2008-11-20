/*
 * Created on Feb 25, 2008
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

import java.awt.Dimension;

import javax.swing.JSlider;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.CheckThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.EventMode.ROBOT;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JSliderValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.task.ComponentSetVisibleTask.hide;
import static org.fest.swing.testing.CommonAssertions.*;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JSliderDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public abstract class JSliderDriverTestCase {

  private Robot robot;
  private MyWindow window;
  private JSliderDriver driver;
  private JSlider slider;

  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JSliderDriver(robot);
    window = MyWindow.createNew(getClass(), orientation());
    slider = window.slider;
    robot.showWindow(window);
  }

  abstract int orientation();

  JSlider slider() { return slider; }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "valueProvider")
  public void shouldSlideToValue(int value, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.slide(slider, value);
    assertThatSliderValueIsEqualTo(value);
  }

  @DataProvider(name = "valueProvider")
  public Object[][] valueProvider() {
    return new Object[][] {
        /* {  5, AWT }, */ {  5, ROBOT },
        /* { 10, AWT }, */ { 10, ROBOT },
        /* { 28, AWT }, */ { 28, ROBOT },
        /* { 20, AWT }, */ { 20, ROBOT }
    };
  }

  public void shouldThrowErrorWhenSlidingDisabledJSliderToValue() {
    disableJSlider();
    try {
      driver.slide(slider, 6);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSlidingNotShowingJSliderToValue() {
    hideWindow();
    try {
      driver.slide(slider, 6);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSlideToMaximum(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.slideToMaximum(slider);
    assertThatSliderValueIsEqualTo(maximumOf(slider));
  }

  private static int maximumOf(final JSlider slider) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return slider.getMaximum();
      }
    });
  }

  public void shouldThrowErrorWhenSlidingDisabledJSliderToMaximum() {
    disableJSlider();
    try {
      driver.slideToMaximum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }
  
  public void shouldThrowErrorWhenSlidingNotShowingJSliderToMaximum() {
    hideWindow();
    try {
      driver.slideToMaximum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSlideToMinimum(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.slideToMinimum(slider);
    assertThatSliderValueIsEqualTo(minimumOf(slider));
  }

  private static int minimumOf(final JSlider slider) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return slider.getMinimum();
      }
    });
  }
  
  public void shouldThrowErrorWhenSlidingDisabledJSliderToMinimum() {
    disableJSlider();
    try {
      driver.slideToMinimum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSlidingNotShowingJSliderToMinimum() {
    hideWindow();
    try {
      driver.slideToMinimum(slider);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  private void assertThatSliderValueIsEqualTo(int expected) {
    assertThat(valueOf(slider)).isEqualTo(expected);
  }

  public void shouldThrowErrorIfValueIsLessThanMinimum() {
    try {
      driver.slide(slider, -1);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      assertThat(expected).message().isEqualTo("Value <-1> is not within the JSlider bounds of <0> and <30>");
    }
  }

  public void shouldThrowErrorIfValueIsGreaterThanMaximum() {
    try {
      driver.slide(slider, 31);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      assertThat(expected).message().isEqualTo("Value <31> is not within the JSlider bounds of <0> and <30>");
    }
  }

  @RunsInEDT
  private void disableJSlider() {
    disable(slider);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }
  
  protected int sliderOrientation() {
    return orientationOf(slider);
  }

  private static int orientationOf(final JSlider slider) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return slider.getOrientation();
      }
    });
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JSlider slider = new JSlider();

    @RunsInEDT
    static MyWindow createNew(final Class<? extends JSliderDriverTestCase> testClass, final int orientation) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass, orientation);
        }
      });
    }

    private MyWindow(Class<? extends JSliderDriverTestCase> testClass, int orientation) {
      super(testClass);
      add(slider);
      slider.setOrientation(orientation);
      slider.setMinimum(0);
      slider.setMaximum(30);
      slider.setValue(15);
      setPreferredSize(new Dimension(300, 300));
    }
  }
}
