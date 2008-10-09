package org.fest.swing.query;

import java.awt.Component;
import java.awt.Font;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the font of a
 * <code>{@link Component}</code>.
 * @see Component#getFont()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final public class ComponentFontQuery {

  /**
   * Returns the font of the given <code>{@link Component}</code>. This action is executed in the event dispatch
   * thread.
   * @param component the given <code>Component</code>.
   * @return the font of the given <code>Component</code>.
   * @see Component#getFont()
   */
  public static Font fontOf(final Component component) {
    return execute(new GuiQuery<Font>() {
      protected Font executeInEDT() {
        return component.getFont();
      }
    });
  }

  private ComponentFontQuery() {}
}