pattern-matching4j [![Build Status](https://secure.travis-ci.org/equus52/pattern-matching4j.png)](https://travis-ci.org/equus52/pattern-matching4j)
=======

Java pattern matching library with Lambda expressions


## Usage

```java

import static equus.matching.PatternMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Pattern;

import org.junit.Test;

public class PatternMatchersTest {

  @Test
  public void match_case_value() {

    BigDecimal num = BigDecimal.ZERO;
    match(num, //
        caseValue(BigDecimal.ONE, o -> fail()), //
        caseValue(BigDecimal.ZERO, o -> assertThat(o, is(num))), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_value_return() {

    BigDecimal num = BigDecimal.ZERO;
    Optional<Integer> result = match(num, //
        caseValueReturn(BigDecimal.ONE, o -> -1), //
        caseValueReturn(BigDecimal.ZERO, o -> 0), //
        caseDefaultReturn(o -> -1));
    assertThat(result.get(), is(0));
  }

  @Test
  public void match_case_default() {

    BigDecimal num = BigDecimal.ZERO;
    match(num, //
        caseValue(BigDecimal.ONE, o -> fail()), //
        caseValue(BigDecimal.TEN, o -> fail()), //
        caseDefault(o -> assertThat(o, is(num))));
  }

  @Test
  public void match_case_default_return() {

    BigDecimal num = BigDecimal.ZERO;
    Optional<Integer> result = match(num, //
        caseValueReturn(BigDecimal.ONE, o -> -1), //
        caseValueReturn(BigDecimal.TEN, o -> -1), //
        caseDefaultReturn(o -> null));
    assertThat(result.isPresent(), is(false));
  }

  @Test
  public void match_case_class() {
    Number integer = 1;
    match(integer,//
        caseClass(Integer.class, i -> assertThat(i, is(integer))), //
        caseClass(Double.class, s -> fail()), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_class_return() {
    Number integer = 1;
    Optional<String> result = match(integer,//
        caseClassReturn(Integer.class, i -> "OK"), //
        caseClassReturn(Double.class, s -> "NG"), //
        caseDefaultReturn(o -> "NG"));
    assertThat(result.get(), is("OK"));
  }

  @Test
  public void match_case_class_boolean() {
    Number integer = 1;
    match(integer,//
        caseClass(Integer.class, i -> i == 0, i -> assertThat(i, is(integer))), //
        caseClass(Double.class, i -> i > 0, i -> fail()), //
        caseClass(Integer.class, i -> i > 0, i -> assertThat(i, is(integer))), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_class_boolean_return() {
    Number integer = 1;
    Optional<String> result = match(integer,//
        caseClassReturn(Integer.class, i -> i == 0, i -> "OK"), //
        caseClassReturn(Double.class, i -> i > 0, i -> "NG"), //
        caseClassReturn(Integer.class, i -> i > 0, i -> "OK"), //
        caseDefaultReturn(o -> "NG"));
    assertThat(result.get(), is("OK"));
  }

  @Test
  public void match_case_class_matcher() {
    Number integer = 1;
    match(integer,//
        caseClass(Integer.class, is(0), i -> assertThat(i, is(integer))), //
        caseClass(Double.class, greaterThan(0.0), s -> fail()), //
        caseClass(Integer.class, greaterThan(0), i -> assertThat(i, is(integer))), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_class_matcher_return() {
    Number integer = 1;
    Optional<String> result = match(integer,//
        caseClassReturn(Integer.class, is(0), i -> "OK"), //
        caseClassReturn(Double.class, greaterThan(0.0), i -> "NG"), //
        caseClassReturn(Integer.class, greaterThan(0), i -> "OK"), //
        caseDefaultReturn(o -> "NG"));
    assertThat(result.get(), is("OK"));
  }

  @Test
  public void match_case_null() {
    String str = null;
    match(str, //
        caseClass(String.class, s -> fail()), //
        caseNull(() -> assertThat(str, is((String) null))), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_null_return() {
    String str = null;
    Optional<Integer> result = match(str, //
        caseClassReturn(String.class, s -> -1), //
        caseNullReturn(() -> 0), //
        caseDefaultReturn(o -> -1));
    assertThat(result.get(), is(0));
  }

  @Test
  public void match_case_not_null() {
    String str = "test";
    match(str, //
        caseNull(() -> fail()), //
        caseNotNull(s -> assertThat(s, is(str))), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_not_null_return() {
    String str = "test";
    Optional<Integer> result = match(str, //
        caseNullReturn(() -> -1), //
        caseNotNullReturn(s -> 0), //
        caseDefaultReturn(o -> -1));
    assertThat(result.get(), is(0));
  }

  @Test
  public void match_case_boolean() {
    {
      int num = 5;
      match(num, //
          caseBoolean(i -> i > 5, i -> fail()), //
          caseBoolean(i -> i == 5, i -> assertThat(i, is(num))), //
          caseDefault(o -> fail()));
    }
    {
      String str = null;
      match(str, //
          caseBoolean(s -> s != null, s -> fail()), //
          caseBoolean(s -> s == null, s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
  }

  @Test
  public void match_case_boolean_return() {
    {
      int num = 5;
      Optional<Integer> result = match(num, //
          caseBooleanReturn(i -> i > 5, i -> -1), //
          caseBooleanReturn(i -> i == 5, i -> i * 0), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
    {
      String str = null;
      Optional<Integer> result = match(str, //
          caseBooleanReturn(s -> s != null, s -> -1), //
          caseBooleanReturn(s -> s == null, s -> 0), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
  }

  @Test
  public void match_case_matcher() {
    {
      String str = "test";
      match(str, //
          caseMatcher(is("test2"), s -> fail()), //
          caseMatcher(startsWith("te"), s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
    {
      String str = null;
      match(str, //
          caseMatcher(is("test2"), s -> fail()), //
          caseMatcher(is((String) null), s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
  }

  @Test
  public void match_case_matcher_return() {
    {
      String str = "test";
      Optional<Integer> result = match(str, //
          caseMatcherReturn(is("test2"), s -> -1), //
          caseMatcherReturn(startsWith("te"), s -> 0), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
    {
      String str = null;
      Optional<Integer> result = match(str, //
          caseMatcherReturn(is("test2"), s -> -1), //
          caseMatcherReturn(is((String) null), s -> 0), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
  }

  @Test
  public void match_case_regex() {
    {
      String str = "test";
      match(str, //
          caseRegex(Pattern.compile("^Te"), s -> fail()), //
          caseRegex(Pattern.compile("^te"), s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
    {
      String str = "test";
      match(str, //
          caseRegex("^Te", s -> fail()), //
          caseRegex("^te", s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
    {
      String str = "test";
      match(str, //
          caseRegex("^Te", Pattern.CASE_INSENSITIVE, s -> assertThat(s, is(str))), //
          caseRegex("^te", s -> fail()), //
          caseDefault(o -> fail()));
    }
  }

  @Test
  public void match_case_regex_return() {
    {
      String str = "test";
      Optional<Integer> result = match(str, //
          caseRegexReturn(Pattern.compile("^Te"), s -> -1), //
          caseRegexReturn(Pattern.compile("^te"), s -> 0), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
    {
      String str = "test";
      Optional<Integer> result = match(str, //
          caseRegexReturn("^Te", s -> -1), //
          caseRegexReturn("^te", s -> 0), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
    {
      String str = "test";
      Optional<Integer> result = match(str, //
          caseRegexReturn("^Te", Pattern.CASE_INSENSITIVE, s -> 0), //
          caseRegexReturn("^te", s -> -1), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
  }

  @Test
  public void match_case_disjunction() {
    {
      String str = "test";
      match(str, //
          caseValues("test1", "test2", s -> fail()), //
          caseValues("test1", "test", s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
    {
      String str = "test";
      match(str, //
          caseValues("test1", "test2", s -> fail()), //
          caseValues("test", "test2", s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
    {
      String str = "test";
      match(str, //
          caseValues("test1", "test2", "test3", s -> fail()), //
          caseValues("test1", "test2", "test", s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
  }

  @Test
  public void match_case_disjunction_return() {
    {
      String str = "test";
      Optional<Integer> result = match(str, //
          caseValuesReturn("test1", "test2", s -> -1), //
          caseValuesReturn("test1", "test", s -> 0), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
    {
      String str = "test";
      Optional<Integer> result = match(str, //
          caseValuesReturn("test1", "test2", s -> -1), //
          caseValuesReturn("test", "test2", s -> 0), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
    {
      String str = "test";
      Optional<Integer> result = match(str, //
          caseValuesReturn("test1", "test2", "test3", s -> -1), //
          caseValuesReturn("test1", "test2", "test", s -> 0), //
          caseDefaultReturn(o -> -1));
      assertThat(result.get(), is(0));
    }
  }

  @Test
  public void match_case_option() {
    String str = "test";
    Optional<String> opt = Optional.ofNullable(str);
    match(opt, //
        caseSome(s -> assertThat(s, is(str))), //
        caseNone(() -> fail()));

    Optional<String> empty = Optional.empty();
    match(empty, //
        caseSome(s -> fail()), //
        caseNone(() -> assertThat(empty.isPresent(), is(false))));
  }

  @Test
  public void match_case_option_return() {
    String str = "test";
    Optional<String> opt = Optional.ofNullable(str);
    int result1 = match(opt, //
        caseSomeReturn(s -> 0), //
        caseNoneReturn(() -> -1));
    assertThat(result1, is(0));

    Optional<String> empty = Optional.empty();
    int result2 = match(empty, //
        caseSomeReturn(s -> 0), //
        caseNoneReturn(() -> -1));
    assertThat(result2, is(-1));
  }

  @Test
  public void match_case_boolean_2() {
    {
      int num1 = 5;
      int num2 = 9;
      match(num1, num2, //
          caseBoolean(i -> i > 5, i -> i > 5, (i1, i2) -> fail()), //
          caseBoolean(i -> i == 5, i -> i == 9, (i1, i2) -> assertThat(i1, is(num1))), //
          caseDefault((i1, i2) -> fail()));
    }
    {
      int num1 = 5;
      int num2 = 9;
      match(num1, num2, //
          caseBoolean(i -> i > 5, i -> i > 5, (i1, i2) -> fail()), //
          caseBoolean((Integer i) -> i == 5).and(caseAny(Integer.class)).then((i1, i2) -> assertThat(i1, is(num1))), //
          caseDefault((i1, i2) -> fail()));
    }
  }

  @Test
  public void match_case_boolean_2_return() {
    {
      int num1 = 5;
      int num2 = 9;
      Optional<Integer> result = match(num1, num2, //
          caseBooleanReturn(i -> i > 5, i -> i > 5, (i1, i2) -> -1), //
          caseBooleanReturn(i -> i == 5, i -> i == 9, (i1, i2) -> i1 * i2 * 0), //
          caseDefaultReturn((i1, i2) -> -1));
      assertThat(result.get(), is(0));
    }
    {
      int num1 = 5;
      int num2 = 9;
      Optional<Integer> result = match(num1, num2, //
          caseBooleanReturn(i -> i > 5, i -> i > 5, (i1, i2) -> -1), //
          caseBoolean((Integer i) -> i == 5).and(caseAny(Integer.class)).thenReturn((i1, i2) -> i1 * i2 * 0), //
          caseDefaultReturn((i1, i2) -> -1));
      assertThat(result.get(), is(0));
    }
  }

  @Test
  public void match_case_value_2() {
    {
      int num1 = 5;
      int num2 = 9;
      match(num1, num2, //
          caseValue(5, 5, (i1, i2) -> fail()), //
          caseValue(5, 9, (i1, i2) -> assertThat(i1, is(num1))), //
          caseDefault((i1, i2) -> fail()));
    }
    {
      int num1 = 5;
      int num2 = 9;
      match(num1, num2, //
          caseValue(5, 5, (i1, i2) -> fail()), //
          caseValue(5).and(caseAny(Integer.class)).then((i1, i2) -> assertThat(i1, is(num1))), //
          caseDefault((i1, i2) -> fail()));
    }
  }

  @Test
  public void match_case_value_2_return() {
    {
      int num1 = 5;
      int num2 = 9;
      Optional<Integer> result = match(num1, num2, //
          caseValueReturn(5, 5, (i1, i2) -> -1), //
          caseValueReturn(5, 9, (i1, i2) -> i1 * i2 * 0), //
          caseDefaultReturn((i1, i2) -> -1));
      assertThat(result.get(), is(0));
    }
    {
      int num1 = 5;
      int num2 = 9;
      Optional<Integer> result = match(num1, num2, //
          caseValueReturn(5, 5, (i1, i2) -> -1), //
          caseValue(5).and(caseAny(Integer.class)).thenReturn((i1, i2) -> i1 * i2 * 0), //
          caseDefaultReturn((i1, i2) -> -1));
      assertThat(result.get(), is(0));
    }
  }

  @Test
  public void match_case_matcher_2() {
    {
      int num1 = 5;
      int num2 = 9;
      match(num1, num2, //
          caseMatcher(greaterThan(5), greaterThan(9), (i1, i2) -> fail()), //
          caseMatcher(is(5), greaterThan(8), (i1, i2) -> assertThat(i1, is(num1))), //
          caseDefault((i1, i2) -> fail()));
    }
    {
      int num1 = 5;
      int num2 = 9;
      match(num1, num2, //
          caseMatcher(greaterThan(5), greaterThan(9), (i1, i2) -> fail()), //
          caseMatcher(is(5)).and(caseAny(Integer.class)).then((i1, i2) -> assertThat(i1, is(num1))), //
          caseDefault((i1, i2) -> fail()));
    }
  }

  @Test
  public void match_case_matcher_2_return() {
    {
      int num1 = 5;
      int num2 = 9;
      Optional<Integer> result = match(num1, num2, //
          caseMatcherReturn(greaterThan(5), greaterThan(9), (i1, i2) -> -1), //
          caseMatcherReturn(is(5), greaterThan(8), (i1, i2) -> i1 * i2 * 0), //
          caseDefaultReturn((i1, i2) -> -1));
      assertThat(result.get(), is(0));
    }
    {
      int num1 = 5;
      int num2 = 9;
      Optional<Integer> result = match(num1, num2, //
          caseMatcherReturn(greaterThan(5), greaterThan(9), (i1, i2) -> -1), //
          caseMatcher(is(5)).and(caseAny(Integer.class)).thenReturn((i1, i2) -> i1 * i2 * 0), //
          caseDefaultReturn((i1, i2) -> -1));
      assertThat(result.get(), is(0));
    }
  }

}

```

## Installation

Gradle:

```groovy
repositories {
  maven {
    url 'http://equus52.github.io/maven/'
  }
}
dependencies {
  compile 'equus:pattern-matching4j:0.1.0'
}
```
## Build

`gradlew build`


## Requirements

* JDK 8 +

## Dependencies

* [hamcrest](https://code.google.com/p/hamcrest/)

## License

pattern-matching4j is released under the [MIT License](http://www.opensource.org/licenses/MIT).


## Donations

Your donation is great appreciated.

PayPal: stepdesign81@gmail.com