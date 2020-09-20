/*
 * Copyright 2020 by José Antonio Garcel
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

package com.garcel.parser.r.node;

import com.garcel.parser.r.autogen.R;
import com.garcel.parser.r.autogen.RTreeConstants;
import com.garcel.parser.r.autogen.SimpleNode;
import com.garcel.parser.r.autogen.Token;
import com.garcel.parser.r.visitor.RVisitor;

/**
 * RNode.java -
 *
 * @author : José Antonio Garcel
 */
public class RNode extends SimpleNode {

  public RNode(int i) {
    super(i);
  }

  public RNode(R p, int i) {
    super(p, i);
  }

  public int getBeginLine() { return jjtGetFirstToken().beginLine; }
  public int getBeginColumn() { return jjtGetFirstToken().beginColumn; }
  public String getCode() {
    String code = "";

    Token current = jjtGetFirstToken();
   do {
      code += current.image;
      current = current.next;

    } while (current.next != null && current.prev != jjtGetLastToken());

    return code;
  }

  /** Accept the visitor. **/
  public void jjtAccept(RVisitor visitor, Object data) {
    visitor.visit(this, data);
  }

  /** Accept the visitor. **/
  public Object childrenAccept(RVisitor visitor, Object data) {
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ((RNode) children[i]).jjtAccept(visitor, data);
      }
    }
    return data;
  }

  @Override
  public String toString() {
    return RTreeConstants.jjtNodeName[id] + ":" + getBeginLine() + ":" + getBeginColumn();
  }
}
