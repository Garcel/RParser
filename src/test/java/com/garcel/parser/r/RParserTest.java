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
import com.garcel.parser.r.autogen.TokenMgrError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * RParserTest.java -
 *
 * @author : José Antonio Garcel
 */
public class RParserTest {

  @DisplayName("Should parse")
  @ParameterizedTest(name = "{index} => Test {0}")
  @MethodSource("shouldParseTestCodeProvider")
  @SuppressWarnings("unused")
  public void testShouldParse(String name, String testCode) throws ParseException {
    test(testCode);
  }

  @DisplayName("Should NOT parse")
  @ParameterizedTest(name = "{index} => Test {0}")
  @MethodSource("shouldNotParseTestCodeProvider")
  @SuppressWarnings("unused")
  public void testShouldNotParse(String name, String testCode) {
    assertThrows(TokenMgrError.class, () -> test(testCode));
  }

  // IMPLEMENTATION
  private void test(String testCode) throws ParseException {
    try (InputStream inputStream = new ByteArrayInputStream(testCode.getBytes())) {
      R parser = new R(inputStream);
      parser.parse();

    } catch (IOException e) {
      // ignore
    }
  }

  private static Stream<Arguments> shouldParseTestCodeProvider() {
    return Stream.of(
      //<editor-fold desc="Assignments">
      Arguments.of("equal assignment", "foo = bar;"),
      Arguments.of("equal assignment (2)", "foo := bar;"),
      Arguments.of("left assignment", "foo <- bar;"),
      Arguments.of("right assignment", "bar -> foo;"),
      //</editor-fold>
      //<editor-fold desc="Comments">
      Arguments.of("comment", "# I'm a comment"),
      //</editor-fold>
      //<editor-fold desc="Calls">
      Arguments.of("call", "foo(1,2,3,4)"),
      Arguments.of("call (2)", "foo(0, 1^(2:3))"),
      Arguments.of("call (3)", "foo(, 0, 1)"),
      Arguments.of("call (4)", "foo(\"<\" = , \">\" = , \"==\" = , \"!=\" = , \">=\" = TRUE, FALSE)"),
      Arguments.of("call (5)", "foo(, 0, 1)[1L:bar, ]"),
      Arguments.of("call (6)", "foo(, , )"),
      Arguments.of("call multiple lines", "foo(0,\n1)"),
      Arguments.of("call multiple lines (2)", "foo() {\n" +
        "  list(" +
        "    a,\n" +
        "    b\n" +
        "  )" +
        "}"),
      //</editor-fold>
      //<editor-fold desc="Constants">
      Arguments.of("complex constant", "2i"),
      Arguments.of("float constant", "1e2L"),
      Arguments.of("hexadecimal constant", "0x10L"),
      Arguments.of("infinity constant", "Inf"),
      Arguments.of("integer constant", "12345L"),
      Arguments.of("not a number constant", "NaN"),
      Arguments.of("not available constant", "NA"),
      Arguments.of("number constant", "12345"),
      Arguments.of("null constant", "NULL"),
      Arguments.of("logical constant TRUE", "TRUE"),
      Arguments.of("logical constant FALSE", "FALSE"),
      Arguments.of("string constant double quote", "\"cat\""),
      Arguments.of("string constant single quote", "'cat'"),
      Arguments.of("string constant containing #", "\"# I'm not a comment.\""),
      Arguments.of("string constant double quote containing escaped chars", "foo(\"\\\\\", \"\\\\\\\\\")"),
      Arguments.of("string constant double quote containing nested escaped string", "foo(\"help(\\\"%s\\\")\")"),
      Arguments.of("string constant single quote containing escaped chars", "foo('\\'bar\\'')"),
      //</editor-fold>
      //<editor-fold desc="For loop">
      Arguments.of("for loop with body",
        "for(i in 1:10) {\n" +
        "  foo[i] <- bar[i]*bar[i]\n" +
        "}"),
      Arguments.of("for loop nested",
        "for(i in 1:10) {\n" +
        "  for(j in 1:10) {\n" +
        "    foo[i] <- bar[i]*bar[j]\n" +
        "  }\n" +
        "}"),
      //</editor-fold>
      //<editor-fold desc="Function">
      Arguments.of("function default values", "function(foo=bar) foo*foo"),
      Arguments.of("function default values in multiple lines", "function(foo=bar,\nfoo2=bar2) foo*foo2"),
      Arguments.of("function default values in multiple lines", "function(foo=bar,foo2\n=bar2) foo*foo2"),
      Arguments.of("function single line", "function(foo) foo*bar"),
      Arguments.of("function with block",
        "function(foo)\n" +
        "{\n" +
        "  foo();" +
        "}"),
      Arguments.of("function with block single line", "function(foo) {foo*bar}"),
      //</editor-fold>
      //<editor-fold desc="Identifiers">
      Arguments.of("identifier when contains a dot", "foo."),
      Arguments.of("identifier when contains a dot followed by a number", "foo.2"),
      Arguments.of("identifier when contains a number", "foo2"),
      Arguments.of("identifier when contains an underscore", "foo_"),
      Arguments.of("identifier when equals to dot", "."),
      Arguments.of("identifier when equals to letter", "f"),
      Arguments.of("identifier when starts with a backtick", "`if`"),
      Arguments.of("identifier when starts with a letter", "foo"),
      Arguments.of("identifier when starts with a dot", ".foo"),
      Arguments.of("identifier when starts with a dot (2)", "..foo"),
      //</editor-fold>
      //<editor-fold desc="If expression">
      Arguments.of("if without else",
        "if (foo == bar) {\n" +
        "  print(\"Well done!\");\n" +
        "}"),
      Arguments.of("if without else",
        "if (foo == bar) {\n" +
        "  foo <- bar()[0]# I'm a comment\n" +
        "  bar <- bar + 1\n" +
        "}"),
      Arguments.of("if with condition in multiple lines",
        "if (foo == bar||\n" +
        "  foo == bar + 1) {\n" +
        "  print(\"Well done!\");\n" +
        "}"),
      Arguments.of("if with condition in multiple lines (2)",
        "if (foo == bar\n" +
        "  || foo == bar + 1) {\n" +
        "  print(\"Well done!\");\n" +
        "}"),
      Arguments.of("if with else",
        "if (foo == bar) {\n" +
        "  print(\"Well done!\");\n" +
        "} else {\n" +
        "  print(\"Fail!\");\n" +
        "}"),
      //</editor-fold>
      //<editor-fold desc="Operators">
      Arguments.of("and", "foo & bar"),
      Arguments.of("and2", "foo && bar"),
      Arguments.of("division", "foo / bar"),
      Arguments.of("equality", "foo == bar"),
      Arguments.of("exponentiation", "foo^bar"),
      Arguments.of("greater equals", "foo >= bar"),
      Arguments.of("greater than", "foo > bar"),
      Arguments.of("integer division", "foo %/% bar"),
      Arguments.of("Kronecker product", "foo %x% bar"),
      Arguments.of("matrix product", "foo %*% bar"),
      Arguments.of("matching operator", "foo %in% bar"),
      Arguments.of("minus", "foo - bar"),
      Arguments.of("multiplication", "foo * bar"),
      Arguments.of("module", "foo %% bar"),
      Arguments.of("not", "!foo"),
      Arguments.of("not equals", "foo != bar"),
      Arguments.of("list subset", "foo$bar"),
      Arguments.of("lower equals", "foo <= bar"),
      Arguments.of("lower than", "foo < bar"),
      Arguments.of("or", "foo | bar"),
      Arguments.of("or2", "foo || bar"),
      Arguments.of("outer product", "foo %o% bar"),
      Arguments.of("plus", "foo + bar"),
      Arguments.of("sequence", "foo : bar"),
      Arguments.of("tilde", "foo ~ bar"),
      Arguments.of("special operator", "foo %customOp% bar"),
      Arguments.of("special operator (2)", "foo %||% bar"),
      //</editor-fold>
      //<editor-fold desc="Other">
      Arguments.of("double brackets expression", "foo[[\"bar\"]]"),
      Arguments.of("expression", "1/foo\nfoo"),
      Arguments.of("form feed char", "\f"),
      Arguments.of("single brackets expression", "foo[,,bar]"),
      //</editor-fold>
      //<editor-fold desc="Repeat loop">
      Arguments.of("repeat loop",
        "repeat {\n" +
        "  foo <- foo + 1\n" +
        "  break\n" +
        "}"),
      //</editor-fold>
      //<editor-fold desc="While loop">
      Arguments.of("while loop with body",
        "while(foo < bar) {\n" +
        "  foo <- foo + 1\n" +
        "}"),
      Arguments.of("while loop nested",
        "while(foo < bar) {\n" +
        "  while(foo + 1 < bar) {\n" +
        "    foo <- foo + 1\n" +
        "  }\n" +
        "}")
      //</editor-fold>
    );
  }

  private static Stream<Arguments> shouldNotParseTestCodeProvider() {
    return Stream.of(
      //<editor-fold desc="Constants">
      Arguments.of("complex constant with L suffix", "2iL"),
      //</editor-fold>
      //<editor-fold desc="Identifiers">
      Arguments.of("identifier when starts with a digit", "2foo"),
      Arguments.of("identifier when starts with a dot followed by a number", ".2foo"),
      Arguments.of("identifier when starts with an underscore", "_foo")
      //</editor-fold>
    );
  }
}