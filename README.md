pattern-matching4j [![Build Status](https://secure.travis-ci.org/equus52/pattern-matching4j.png)](https://travis-ci.org/equus52/pattern-matching4j)
=======

Java pattern matching library with Lambda expressions


## Usage

```java
package equus.matching;

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
    Integer result = match(num, //
        caseValue_(BigDecimal.ONE, o -> -1), //
        caseValue_(BigDecimal.ZERO, o -> 0), //
        caseDefault_(o -> -1));
    assertThat(result, is(0));
  }

  @Test
  public void match_case_default() {

    BigDecimal num = BigDecimal.ZERO;
    match(num, //
        caseValue(BigDecimal.ONE, o -> fail()), //
        caseValue(BigDecimal.TEN, o -> fail()), //
        caseDefault(o -> assertThat(o, is(num))));
  }

  public void match_case_default_return() {

    BigDecimal num = BigDecimal.ZERO;
    Integer result = match(num, //
        caseValue_(BigDecimal.ONE, o -> -1), //
        caseValue_(BigDecimal.TEN, o -> -1), //
        caseDefault_(o -> null));
    assertThat(result, is((Integer) null));
  }

  @Test(expected = MatchError.class)
  public void match_case_MatchError() {

    BigDecimal num = BigDecimal.ZERO;
    match(num, //
        caseValue_(BigDecimal.ONE, o -> -1), //
        caseValue_(BigDecimal.TEN, o -> -1));
  }

  @Test
  public void match_case_class() {
    Number integer = 1;
    match(integer,//
        caseType(Integer.class, i -> assertThat(i, is(integer))), //
        caseType(Double.class, s -> fail()), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_class_return() {
    Number integer = 1;
    String result = match(integer,//
        caseType_(Integer.class, i -> "OK"), //
        caseType_(Double.class, s -> "NG"), //
        caseDefault_(o -> "NG"));
    assertThat(result, is("OK"));
  }

  @Test
  public void match_case_class_boolean() {
    Number integer = 1;
    match(integer,//
        caseType(Integer.class, i -> i == 0, i -> assertThat(i, is(integer))), //
        caseType(Double.class, i -> i > 0, i -> fail()), //
        caseType(Integer.class, i -> i > 0, i -> assertThat(i, is(integer))), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_class_boolean_return() {
    Number integer = 1;
    String result = match(integer,//
        caseType_(Integer.class, i -> i == 0, i -> "OK"), //
        caseType_(Double.class, i -> i > 0, i -> "NG"), //
        caseType_(Integer.class, i -> i > 0, i -> "OK"), //
        caseDefault_(o -> "NG"));
    assertThat(result, is("OK"));
  }

  @Test
  public void match_case_class_matcher() {
    Number integer = 1;
    match(integer,//
        caseType(Integer.class, is(0), i -> assertThat(i, is(integer))), //
        caseType(Double.class, greaterThan(0.0), s -> fail()), //
        caseType(Integer.class, greaterThan(0), i -> assertThat(i, is(integer))), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_class_matcher_return() {
    Number integer = 1;
    String result = match(integer,//
        caseType_(Integer.class, is(0), i -> "OK"), //
        caseType_(Double.class, greaterThan(0.0), i -> "NG"), //
        caseType_(Integer.class, greaterThan(0), i -> "OK"), //
        caseDefault_(o -> "NG"));
    assertThat(result, is("OK"));
  }

  @Test
  public void match_case_null() {
    String str = null;
    match(str, //
        caseType(String.class, s -> fail()), //
        caseNull(() -> assertThat(str, is((String) null))), //
        caseDefault(o -> fail()));
  }

  @Test
  public void match_case_null_return() {
    String str = null;
    Integer result = match(str, //
        caseType_(String.class, s -> -1), //
        caseNull_(() -> 0), //
        caseDefault_(o -> -1));
    assertThat(result, is(0));
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
    Integer result = match(str, //
        caseNull_(() -> -1), //
        caseNotNull_(s -> 0), //
        caseDefault_(o -> -1));
    assertThat(result, is(0));
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
      Integer result = match(num, //
          caseBoolean_(i -> i > 5, i -> -1), //
          caseBoolean_(i -> i == 5, i -> i * 0), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
    }
    {
      String str = null;
      Integer result = match(str, //
          caseBoolean_(s -> s != null, s -> -1), //
          caseBoolean_(s -> s == null, s -> 0), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
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
      Integer result = match(str, //
          caseMatcher_(is("test2"), s -> -1), //
          caseMatcher_(startsWith("te"), s -> 0), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
    }
    {
      String str = null;
      Integer result = match(str, //
          caseMatcher_(is("test2"), s -> -1), //
          caseMatcher_(is((String) null), s -> 0), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
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
      Integer result = match(str, //
          caseRegex_(Pattern.compile("^Te"), s -> -1), //
          caseRegex_(Pattern.compile("^te"), s -> 0), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
    }
    {
      String str = "test";
      Integer result = match(str, //
          caseRegex_("^Te", s -> -1), //
          caseRegex_("^te", s -> 0), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
    }
    {
      String str = "test";
      Integer result = match(str, //
          caseRegex_("^Te", Pattern.CASE_INSENSITIVE, s -> 0), //
          caseRegex_("^te", s -> -1), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
    }
  }

  @Test
  public void match_case_disjunction() {
    {
      String str = "test";
      match(str, //
          caseAnyValues("test1", "test2", s -> fail()), //
          caseAnyValues("test1", "test", s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
    {
      String str = "test";
      match(str, //
          caseAnyValues("test1", "test2", s -> fail()), //
          caseAnyValues("test", "test2", s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
    {
      String str = "test";
      match(str, //
          caseAnyValues("test1", "test2", "test3", s -> fail()), //
          caseAnyValues("test1", "test2", "test", s -> assertThat(s, is(str))), //
          caseDefault(o -> fail()));
    }
  }

  @Test
  public void match_case_disjunction_return() {
    {
      String str = "test";
      Integer result = match(str, //
          caseAnyValues_("test1", "test2", s -> -1), //
          caseAnyValues_("test1", "test", s -> 0), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
    }
    {
      String str = "test";
      Integer result = match(str, //
          caseAnyValues_("test1", "test2", s -> -1), //
          caseAnyValues_("test", "test2", s -> 0), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
    }
    {
      String str = "test";
      Integer result = match(str, //
          caseAnyValues_("test1", "test2", "test3", s -> -1), //
          caseAnyValues_("test1", "test2", "test", s -> 0), //
          caseDefault_(o -> -1));
      assertThat(result, is(0));
    }
  }

  @Test
  public void match_case_option() {
    String str = "test";
    Optional<String> opt = Optional.ofNullable(str);
    match(opt, //
        casePresent(s -> assertThat(s, is(str))), //
        caseEmpty(() -> fail()));

    Optional<String> empty = Optional.empty();
    match(empty, //
        casePresent(s -> fail()), //
        caseEmpty(() -> assertThat(empty.isPresent(), is(false))));
  }

  @Test
  public void match_case_option_return() {
    String str = "test";
    Optional<String> opt = Optional.ofNullable(str);
    int result1 = match(opt, //
        casePresent_(s -> 0), //
        caseEmpty_(() -> -1));
    assertThat(result1, is(0));

    Optional<String> empty = Optional.empty();
    int result2 = match(empty, //
        casePresent_(s -> 0), //
        caseEmpty_(() -> -1));
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
      Integer result = match(num1, num2, //
          caseBoolean_(i -> i > 5, i -> i > 5, (i1, i2) -> -1), //
          caseBoolean_(i -> i == 5, i -> i == 9, (i1, i2) -> i1 * i2 * 0), //
          caseDefault_((i1, i2) -> -1));
      assertThat(result, is(0));
    }
    {
      int num1 = 5;
      int num2 = 9;
      Integer result = match(num1, num2, //
          caseBoolean_(i -> i > 5, i -> i > 5, (i1, i2) -> -1), //
          caseBoolean((Integer i) -> i == 5).and(caseAny(Integer.class)).then_((i1, i2) -> i1 * i2 * 0), //
          caseDefault_((i1, i2) -> -1));
      assertThat(result, is(0));
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
      Integer result = match(num1, num2, //
          caseValue_(5, 5, (i1, i2) -> -1), //
          caseValue_(5, 9, (i1, i2) -> i1 * i2 * 0), //
          caseDefault_((i1, i2) -> -1));
      assertThat(result, is(0));
    }
    {
      int num1 = 5;
      int num2 = 9;
      Integer result = match(num1, num2, //
          caseValue_(5, 5, (i1, i2) -> -1), //
          caseValue(5).and(caseAny(Integer.class)).then_((i1, i2) -> i1 * i2 * 0), //
          caseDefault_((i1, i2) -> -1));
      assertThat(result, is(0));
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
      Integer result = match(num1, num2, //
          caseMatcher_(greaterThan(5), greaterThan(9), (i1, i2) -> -1), //
          caseMatcher_(is(5), greaterThan(8), (i1, i2) -> i1 * i2 * 0), //
          caseDefault_((i1, i2) -> -1));
      assertThat(result, is(0));
    }
    {
      int num1 = 5;
      int num2 = 9;
      Integer result = match(num1, num2, //
          caseMatcher_(greaterThan(5), greaterThan(9), (i1, i2) -> -1), //
          caseMatcher(is(5)).and(caseAny(Integer.class)).then_((i1, i2) -> i1 * i2 * 0), //
          caseDefault_((i1, i2) -> -1));
      assertThat(result, is(0));
    }
  }

}
```

## Installation

Gradle:

```groovy
repositories {
  mavenCentral()
}
dependencies {
  compile 'com.github.equus52:pattern-matching4j:0.2.0'
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
