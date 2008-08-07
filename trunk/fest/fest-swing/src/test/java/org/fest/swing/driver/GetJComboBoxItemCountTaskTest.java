/*
 * Created on Aug 6, 2008
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

import javax.swing.JComboBox;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link GetJComboBoxItemCountTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class GetJComboBoxItemCountTaskTest {

  @Test(dataProvider = "itemCounts")
  public void shouldReturnItemCountOfJComboBox(final int itemCount) {
    final JComboBox comboBox = createMock(JComboBox.class);
    new EasyMockTemplate(comboBox) {
      protected void expectations() {
        expect(comboBox.getItemCount()).andReturn(itemCount);
      }

      protected void codeToTest() {
        assertThat(GetJComboBoxItemCountTask.itemCountOf(comboBox)).isEqualTo(itemCount);
      }
    }.run();
  }
  
  @DataProvider(name = "itemCounts") public Object[][] itemCounts() {
    return new Object[][] { { 1 }, { 6 }, { 8 }, { 26 } };
  }
}
