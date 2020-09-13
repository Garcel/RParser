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

import com.garcel.parser.r.autogen.Token;

/**
 * RToken.java - Token class providing a linking with the previous matched token.
 *
 * @author : José Antonio Garcel
 */
public class RToken {

  /**
   * A reference to the previous regular (non-special) token from the input
   * stream.  If this is the first token from the input stream, or if the
   * token manager, this field is set to null.
   */
  public Token prev;
}
