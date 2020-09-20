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

package com.garcel.parser.r.visitor;

import com.garcel.parser.r.node.RNode;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * RDumperVisitor.java -
 *
 * @author : José Antonio Garcel
 */
public class RDumperVisitor extends RDefaultVisitor {

  private static final Logger LOGGER = Logger.getLogger(RDumperVisitor.class.getName());

  private final OutputStream outputStream;

  public RDumperVisitor(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  @Override
  public void defaultVisit(RNode node, Object data){
    dump(node);
    super.defaultVisit(node, data);
  }

  // IMPLEMENTATION
  /*
   * Dumps the node string representation to the outputStream.
   * Each line contains an indentation equals to the node depth.
   */
  private void dump(RNode node) {
    int nodeDepth = getNodeDepth(node);
    String tabs = "";
    for (int i = 0; i < nodeDepth; i++) tabs += " ";
    String nodeDump = tabs + node.toString() + "\n";

    try {
      outputStream.write(nodeDump.getBytes());

    } catch (IOException e) {
      LOGGER.warning("Error dumping node: " + e.getMessage());
    }
  }

  /*
   * Returns the node depth into the AST.
   */
  private int getNodeDepth(RNode node) {
    int depth = 0;
    RNode parent = (RNode) node.jjtGetParent();
    while (parent != null) {
      depth++;
      parent = (RNode) parent.jjtGetParent();
    }

    return depth;
  }
}
