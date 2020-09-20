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

import java.util.Arrays;

import static com.garcel.parser.r.autogen.RConstants.EOL;

/**
 * RAbstractParser.java - Abstract parser class defining utilities to be used in the parsing process.
 *
 * @author : José Antonio Garcel
 */
public abstract class RAbstractParser {

  /**
   * Returns the matched {@link Token} at the given position, or null when the EOF has already been reached at the given
   * position.
   *
   * @param position the position of the token that is expected to be matched.
   * @return the matched token at the given position.
   */
  public abstract Token getToken(int position);

  /**
   * Returns true when the next matched {@link Token} equals one of the given token kinds. Else return false.
   *
   * @param kinds the set of kinds that are expected to be matched at the next token position.
   * @return true when the next matched {@link Token} equals one of the given token kinds.
   */
  protected boolean isToken(int ...kinds) {
    return isTokenAtPosition(1, kinds);
  }

  /**
   * Returns true when the matched {@link Token} at the given position equals one of the given token kinds.
   * Else return false.
   *
   * @param position the position of the token that is expected to be matched.
   * @param kinds the set of kinds that are expected to be matched at the next given position.
   * @return true when the next matched {@link Token} equals one of the given token kinds.
   */
  protected boolean isTokenAtPosition(int position, int ...kinds) {
    Token token = getToken(position);
    return token != null && Arrays.stream(kinds).anyMatch(kind -> kind == token.kind);
  }

  /**
   * Return true when a new line special token follows the last matched token.
   * Else return false.
   *
   * @return true when a new line special token follows the last matched token.
   */
  protected boolean isNewLine() {
    Token current = getToken(1);
    if (current == null) return false;

    Token specialToken = current.specialToken;
    while (specialToken != null) {  // check all linked special tokens, not just the first one
      if (specialToken.kind == EOL) return true;

      specialToken = specialToken.specialToken;
    }

    return false;
  }
}
