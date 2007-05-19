/*
 * Created on May 19, 2007
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
package org.fest.swing.testng;

import org.testng.IClass;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

/**
 * Understands an <code>{@link ITestResult}</code> stub for testing purposes.
 *
 * @author Alex Ruiz
 */
public class TestResultStub implements ITestResult {

  private static final long serialVersionUID = 1L;
  
  private IClass testClass;
  private ITestNGMethod method;

  /** @see org.testng.ITestResult#getEndMillis() */
  public long getEndMillis() {
    return 0;
  }

  /** @see org.testng.ITestResult#getHost() */
  public String getHost() {
    return null;
  }

  /** @see org.testng.ITestResult#getMethod() */
  public ITestNGMethod getMethod() {
    return method;
  }

  public void setMethod(ITestNGMethod method) {
    this.method = method;
  }

  /** @see org.testng.ITestResult#getName() */
  public String getName() {
    return null;
  }

  /** @see org.testng.ITestResult#getParameters() */
  public Object[] getParameters() {
    return null;
  }

  /** @see org.testng.ITestResult#getStartMillis() */
  public long getStartMillis() {
    return 0;
  }

  /** @see org.testng.ITestResult#getStatus() */
  public int getStatus() {
    return 0;
  }

  /** @see org.testng.ITestResult#getTestClass() */
  public IClass getTestClass() {
    return testClass;
  }

  public void setTestClass(IClass testClass) {
    this.testClass = testClass;
  }

  /** @see org.testng.ITestResult#getThrowable() */
  public Throwable getThrowable() {
    return null;
  }

  /** @see org.testng.ITestResult#isSuccess() */
  public boolean isSuccess() {
    return false;
  }

  /** @see org.testng.ITestResult#setEndMillis(long) */
  public void setEndMillis(long millis) {}

  /** @see org.testng.ITestResult#setParameters(java.lang.Object[]) */
  public void setParameters(Object[] parameters) {}

  /** @see org.testng.ITestResult#setStatus(int) */
  public void setStatus(int status) {}

  /** @see org.testng.ITestResult#setThrowable(java.lang.Throwable) */
  public void setThrowable(Throwable throwable) {}

}
