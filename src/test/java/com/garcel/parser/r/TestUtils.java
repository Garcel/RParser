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

package com.garcel.parser.r;

import com.garcel.parser.r.autogen.ParseException;
import com.garcel.parser.r.autogen.R;
import com.garcel.parser.r.node.RNode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * TestUtils.java - Common utility methods for parser tests.
 *
 * @author : José Antonio Garcel
 */
public class TestUtils {
  public static RNode test(String testCode) throws ParseException {
    RNode root = null;

    try (InputStream inputStream = new ByteArrayInputStream(testCode.getBytes())) {
      R parser = new R(inputStream);
      root = parser.parse();

    } catch (IOException e) {
      // ignore
    }

    return root;
  }
}
