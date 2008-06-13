/*
 * Created on Aug 17, 2007
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
package org.fest.reflect.method;

/**
 * Understands the return type of the static method to invoke.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Equivalent to call 'Jedi.class.setCommonPower("Jump")'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("setCommonPower").{@link StaticName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                                 .{@link StaticParameterTypes#in(Class) in}(Jedi.class)
 *                                 .{@link Invoker#invoke(Object...) invoke}("Jump");
 *
 *   // Equivalent to call 'Jedi.class.addPadawan()'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("addPadawan").{@link StaticName#in(Class) in}(Jedi.class).{@link Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'Jedi.class.commonPowerCount()'
 *   String name = {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("commonPowerCount").{@link StaticName#withReturnType(Class) withReturnType}(String.class)
 *                                                 .{@link StaticReturnType#in(Class) in}(Jedi.class)
 *                                                 .{@link Invoker#invoke(Object...) invoke}();
 * </pre>
 * </p>
 *
 * @param <T> the generic type of the static method's return type.
 *
 * @author Alex Ruiz
 */
public class StaticReturnType<T> extends ReturnTypeTemplate<T> {
  StaticReturnType(Class<T> type, StaticName methodName) {
    super(type, methodName);
  }

  /**
   * Creates a new method invoker.
   * @param target the object containing the method to invoke.
   * @return the created method invoker.
   */
  public Invoker<T> in(Class<?> target) {
    return new Invoker<T>(methodName, target);
  }

  /**
   * Specifies the parameter types of the static method to invoke. This method call is optional if the method to invoke
   * does not take arguments.
   * @param parameterTypes the parameter types of the method to invoke.
   * @return the created parameter types holder.
   */
  public StaticParameterTypes<T> withParameterTypes(Class<?>... parameterTypes) {
    return new StaticParameterTypes<T>(parameterTypes, this);
  }
}