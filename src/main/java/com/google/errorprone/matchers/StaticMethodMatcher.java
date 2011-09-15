/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.errorprone.matchers;

import com.google.errorprone.VisitorState;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MemberSelectTree;

import static com.sun.source.tree.Tree.Kind.IDENTIFIER;

/**
 * Matches a static method expression.
 * @author alexeagle@google.com (Alex Eagle)
 */
public class StaticMethodMatcher extends Matcher<ExpressionTree> {
  private final String packageName;
  private final String className;
  private final String methodName;

  public StaticMethodMatcher(String packageName, String className, String methodName) {
    this.packageName = packageName;
    this.className = className;
    this.methodName = methodName;
  }

  @Override
  public boolean matches(ExpressionTree item, VisitorState state) {
    try {
      MemberSelectTree memberSelectTree = (MemberSelectTree) item;
      if (memberSelectTree.getExpression().getKind() == IDENTIFIER &&
          // TODO: allow fully-qualified references
          memberSelectTree.getExpression().toString().equals(className) &&
          memberSelectTree.getIdentifier().contentEquals(methodName)) {
        return true;
      }
    } catch (ClassCastException e) {
      return false;
    }
    return false;
  }
}
