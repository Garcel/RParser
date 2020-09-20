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

import com.garcel.parser.r.autogen.ParseException;
import com.garcel.parser.r.node.RNode;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static com.garcel.parser.r.TestUtils.test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * RDumperVisitorTest.java -
 *
 * @author : José Antonio Garcel
 */
public class RDumperVisitorTest {

  private static final String TEST_CODE = "foo <- function() {\n" +
    "  print(\"FOO\")\n" +
    "}\n" +
    "# Comment\n";

  private static final String EXPECTED = "Program:1:1\n" +
      " Expression:1:1\n" +
      "  Identifier:1:1\n" +
      "  Assignment:1:5\n" +
      "   Expression:1:8\n" +
      "    Function:1:8\n" +
      "     Expression:1:19\n" +
      "      Block:1:19\n" +
      "       ExpressionList:2:3\n" +
      "        Expression:2:3\n" +
      "         Identifier:2:3\n" +
      "         SubList:2:9\n" +
      "          Sub:2:9\n" +
      "           Expression:2:9\n" +
      "            Constant:2:9\n";

  @Test
  public void shouldDumpValidAST() throws ParseException {
    RNode root = test(TEST_CODE);
    OutputStream outputStream = new ByteArrayOutputStream();
    RDumperVisitor visitor = new RDumperVisitor(outputStream);
    root.jjtAccept(visitor, null);

    String result = outputStream.toString();
    assertThat(result).isEqualTo(EXPECTED);
  }
}