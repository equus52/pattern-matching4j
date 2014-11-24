package equus.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.hamcrest.Matcher;

import equus.matching.cases.AnyCase;
import equus.matching.cases.ClassCase;
import equus.matching.cases.DisjunctionCase;
import equus.matching.cases.EqualsCase;
import equus.matching.cases.MatcherCase;
import equus.matching.cases.NoneCase;
import equus.matching.cases.NoneCase.NoneCaseBlock;
import equus.matching.cases.NoneCase.NoneCaseFunction;
import equus.matching.cases.NotNullCase;
import equus.matching.cases.NullCase;
import equus.matching.cases.PredicateCase;
import equus.matching.cases.RegexCase;
import equus.matching.cases.SomeCase;
import equus.matching.cases.SomeCase.SomeCaseBlock;
import equus.matching.cases.SomeCase.SomeCaseFunction;

public final class PatternMatchers {

  private PatternMatchers() {}

  public static <S> PatternMatcher<S> subject(@Nullable S subject) {
    return new PatternMatcher<>(subject);
  }

  @SafeVarargs
  public static <S> void match(@Nullable S subject, @Nonnull CaseBlock<S>... caseBlocks) {
    subject(subject).matches(caseBlocks);
  }

  @SafeVarargs
  public static <S, R> Optional<R> match(@Nullable S subject, @Nonnull CaseFunction<S, R>... caseFunctions) {
    return subject(subject).matches(caseFunctions);
  }

  public static <S, T extends S> void instanceOf(@Nullable S subject, @Nonnull Class<T> matchClass,
      @Nonnull Consumer<T> consumer) {
    subject(subject).matches(caseClass(matchClass, consumer));
  }

  public static <S, T extends S, R> Optional<R> instanceOf(@Nullable S subject, @Nonnull Class<T> matchClass,
      @Nonnull Function<T, R> function) {
    return subject(subject).matches(caseClassReturn(matchClass, function));
  }

  public static <S> EqualsCase<S> caseValue(S value) {
    return new EqualsCase<>(value);
  }

  public static <S> CaseBlock<S> caseValue(@Nonnull S value, @Nonnull Consumer<S> consumer) {
    return caseValue(value).then(consumer);
  }

  public static <S, R> CaseFunction<S, R> caseValueReturn(@Nonnull S value, @Nonnull Function<S, R> function) {
    return caseValue(value).thenReturn(function);
  }

  public static <S> AnyCase<S> caseDefault() {
    return new AnyCase<S>();
  }

  public static <S> AnyCase<S> caseAny(Class<S> clazz) {
    return new AnyCase<>(clazz);
  }

  public static <S> CaseBlock<S> caseDefault(@Nonnull Consumer<S> consumer) {
    return PatternMatchers.<S> caseDefault().then(consumer);
  }

  public static <S, R> CaseFunction<S, R> caseDefaultReturn(@Nonnull Function<S, R> function) {
    return PatternMatchers.<S> caseDefault().thenReturn(function);
  }

  public static <S> NullCase<S> caseNull() {
    return new NullCase<S>();
  }

  public static CaseBlock<Object> caseNull(@Nonnull Block block) {
    return caseNull().then(block);
  }

  public static <R> CaseFunction<Object, R> caseNullReturn(@Nonnull Supplier<R> supplier) {
    return caseNull().thenReturn(supplier);
  }

  public static <S> NotNullCase<S> caseNotNull() {
    return new NotNullCase<S>();
  }

  public static <S> NotNullCase<S> caseNotNull(Class<S> clazz) {
    return new NotNullCase<>(clazz);
  }

  public static <S> CaseBlock<S> caseNotNull(@Nonnull Consumer<S> consumer) {
    return PatternMatchers.<S> caseNotNull().then(consumer);
  }

  public static <S, R> CaseFunction<S, R> caseNotNullReturn(@Nonnull Function<S, R> function) {
    return PatternMatchers.<S> caseNotNull().thenReturn(function);
  }

  public static <S> PredicateCase<S> caseBoolean(Predicate<S> predicate) {
    return new PredicateCase<S>(predicate);
  }

  public static <S> CaseBlock<S> caseBoolean(@Nonnull Predicate<S> predicate, @Nonnull Consumer<S> consumer) {
    return caseBoolean(predicate).then(consumer);
  }

  public static <S, R> CaseFunction<S, R> caseBooleanReturn(@Nonnull Predicate<S> predicate,
      @Nonnull Function<S, R> function) {
    return caseBoolean(predicate).thenReturn(function);
  }

  public static <S> MatcherCase<S> caseMatcher(Matcher<S> matcher) {
    return new MatcherCase<S>(matcher);
  }

  public static <S> CaseBlock<S> caseMatcher(@Nonnull Matcher<S> matcher, @Nonnull Consumer<S> consumer) {
    return caseMatcher(matcher).then(consumer);
  }

  public static <S, R> CaseFunction<S, R> caseMatcherReturn(@Nonnull Matcher<S> matcher,
      @Nonnull Function<S, R> function) {
    return caseMatcher(matcher).thenReturn(function);
  }

  public static <S, T extends S> ClassCase<S, T> caseClass(Class<T> matchClass) {
    return new ClassCase<S, T>(matchClass);
  }

  public static <S, T extends S> CaseBlock<S> caseClass(@Nonnull Class<T> matchClass, @Nonnull Consumer<T> consumer) {
    return PatternMatchers.<S, T> caseClass(matchClass).then(consumer);
  }

  public static <S, T extends S, R> CaseFunction<S, R> caseClassReturn(@Nonnull Class<T> matchClass,
      @Nonnull Function<T, R> function) {
    return PatternMatchers.<S, T> caseClass(matchClass).thenReturn(function);
  }

  public static <S, T extends S> CaseBlock<S> caseClass(@Nonnull Class<T> matchClass, @Nonnull Predicate<T> predicate,
      @Nonnull Consumer<T> consumer) {
    return PatternMatchers.<S, T> caseClass(matchClass).with(predicate).then(consumer);
  }

  public static <S, T extends S, R> CaseFunction<S, R> caseClassReturn(@Nonnull Class<T> matchClass,
      @Nonnull Predicate<T> predicate, @Nonnull Function<T, R> function) {
    return PatternMatchers.<S, T> caseClass(matchClass).with(predicate).thenReturn(function);
  }

  public static <S, T extends S> CaseBlock<S> caseClass(@Nonnull Class<T> matchClass, @Nonnull Matcher<T> matcher,
      @Nonnull Consumer<T> consumer) {
    return PatternMatchers.<S, T> caseClass(matchClass).with(matcher).then(consumer);
  }

  public static <S, T extends S, R> CaseFunction<S, R> caseClassReturn(@Nonnull Class<T> matchClass,
      @Nonnull Matcher<T> matcher, @Nonnull Function<T, R> function) {
    return PatternMatchers.<S, T> caseClass(matchClass).with(matcher).thenReturn(function);
  }

  public static RegexCase caseRegex(Pattern pattern) {
    return new RegexCase(pattern);
  }

  public static CaseBlock<String> caseRegex(@Nonnull Pattern pattern, @Nonnull Consumer<String> consumer) {
    return caseRegex(pattern).then(consumer);
  }

  public static <R> CaseFunction<String, R> caseRegexReturn(@Nonnull Pattern pattern,
      @Nonnull Function<String, R> function) {
    return caseRegex(pattern).thenReturn(function);
  }

  public static CaseBlock<String> caseRegex(@Nonnull String regex, @Nonnull Consumer<String> consumer) {
    return caseRegex(Pattern.compile(regex)).then(consumer);
  }

  public static <R> CaseFunction<String, R> caseRegexReturn(@Nonnull String regex, @Nonnull Function<String, R> function) {
    return caseRegex(Pattern.compile(regex)).thenReturn(function);
  }

  public static CaseBlock<String> caseRegex(@Nonnull String regex, int flags, @Nonnull Consumer<String> consumer) {
    return caseRegex(Pattern.compile(regex, flags)).then(consumer);
  }

  public static <R> CaseFunction<String, R> caseRegexReturn(@Nonnull String regex, int flags,
      @Nonnull Function<String, R> function) {
    return caseRegex(Pattern.compile(regex, flags)).thenReturn(function);
  }

  public static <S> DisjunctionCase<S> caseValues(List<S> values) {
    return new DisjunctionCase<S>(values);
  }

  public static <S> CaseBlock<S> caseValues(@Nonnull S value1, @Nonnull S value2, @Nonnull Consumer<S> consumer) {
    List<S> values = new ArrayList<>();
    values.add(value1);
    values.add(value2);
    return caseValues(values).then(consumer);
  }

  public static <S> CaseBlock<S> caseValues(@Nonnull S value1, @Nonnull S value2, @Nonnull S value3,
      @Nonnull Consumer<S> consumer) {
    List<S> values = new ArrayList<>();
    values.add(value1);
    values.add(value2);
    values.add(value3);
    return caseValues(values).then(consumer);
  }

  public static <S, R> CaseFunction<S, R> caseValuesReturn(@Nonnull S value1, @Nonnull S value2,
      @Nonnull Function<S, R> function) {
    List<S> values = new ArrayList<>();
    values.add(value1);
    values.add(value2);
    return caseValues(values).thenReturn(function);
  }

  public static <S, R> CaseFunction<S, R> caseValuesReturn(@Nonnull S value1, @Nonnull S value2, @Nonnull S value3,
      @Nonnull Function<S, R> function) {
    List<S> values = new ArrayList<>();
    values.add(value1);
    values.add(value2);
    values.add(value3);
    return caseValues(values).thenReturn(function);
  }

  public static <T> OptionalMatcher<T> subject(@Nonnull Optional<T> subject) {
    return new OptionalMatcher<>(subject);
  }

  public static <T> void match(@Nonnull Optional<T> subject, @Nonnull SomeCaseBlock<T> someCaseBlock,
      @Nonnull NoneCaseBlock noneCaseBlock) {
    subject(subject).matches(someCaseBlock, noneCaseBlock);
  }

  public static <T, R> R match(@Nonnull Optional<T> subject, @Nonnull SomeCaseFunction<T, R> someCaseFunction,
      @Nonnull NoneCaseFunction<R> noneCaseFunction) {
    return subject(subject).matches(someCaseFunction, noneCaseFunction);
  }

  public static <T> void match(@Nonnull Optional<T> subject, @Nonnull Consumer<T> consumer, @Nonnull Block block) {
    subject(subject).matches(caseSome(consumer), caseNone(block));
  }

  public static <T, R> R match(@Nonnull Optional<T> subject, @Nonnull Function<T, R> function,
      @Nonnull Supplier<R> supplier) {
    return subject(subject).matches(caseSomeReturn(function), caseNoneReturn(supplier));
  }

  public static <T> SomeCase<T> caseSome() {
    return new SomeCase<T>();
  }

  public static <T> SomeCaseBlock<T> caseSome(@Nonnull Consumer<T> consumer) {
    return PatternMatchers.<T> caseSome().then(consumer);
  }

  public static <T, R> SomeCaseFunction<T, R> caseSomeReturn(@Nonnull Function<T, R> function) {
    return PatternMatchers.<T> caseSome().thenReturn(function);
  }

  public static NoneCase caseNone() {
    return new NoneCase();
  }

  public static NoneCaseBlock caseNone(@Nonnull Block block) {
    return caseNone().then(block);
  }

  public static <R> NoneCaseFunction<R> caseNoneReturn(@Nonnull Supplier<R> supplier) {
    return caseNone().thenReturn(supplier);
  }

  public static <S1, S2> PatternMatcher2<S1, S2> subject(@Nullable S1 subject1, @Nullable S2 subject2) {
    return new PatternMatcher2<S1, S2>(subject1, subject2);
  }

  @SafeVarargs
  public static <S1, S2> void match(@Nullable S1 subject1, @Nullable S2 subject2,
      @Nonnull CaseBlock2<S1, S2>... caseBlocks) {
    subject(subject1, subject2).matches(caseBlocks);
  }

  @SafeVarargs
  public static <S1, S2, R> Optional<R> match(@Nullable S1 subject1, @Nullable S2 subject2,
      @Nonnull CaseFunction2<S1, S2, R>... caseFunctions) {
    return subject(subject1, subject2).matches(caseFunctions);
  }

  public static <S1, S2> CaseBlock2<S1, S2> caseDefault(@Nonnull BiConsumer<S1, S2> consumer) {
    return PatternMatchers.<S1> caseDefault().and(PatternMatchers.<S2> caseDefault()).then(consumer);
  }

  public static <S1, S2, R> CaseFunction2<S1, S2, R> caseDefaultReturn(@Nonnull BiFunction<S1, S2, R> function) {
    return PatternMatchers.<S1> caseDefault().and(PatternMatchers.<S2> caseDefault()).thenReturn(function);
  }

  public static <S1, S2> CaseBlock2<S1, S2> caseBoolean(@Nonnull Predicate<S1> predicate1,
      @Nonnull Predicate<S2> predicate2, @Nonnull BiConsumer<S1, S2> consumer) {
    return caseBoolean(predicate1).and(caseBoolean(predicate2)).then(consumer);
  }

  public static <S1, S2, R> CaseFunction2<S1, S2, R> caseBooleanReturn(@Nonnull Predicate<S1> predicate1,
      @Nonnull Predicate<S2> predicate2, @Nonnull BiFunction<S1, S2, R> function) {
    return caseBoolean(predicate1).and(caseBoolean(predicate2)).thenReturn(function);
  }

  public static <S1, S2> CaseBlock2<S1, S2> caseValue(@Nonnull S1 value1, @Nonnull S2 value2,
      @Nonnull BiConsumer<S1, S2> consumer) {
    return caseValue(value1).and(caseValue(value2)).then(consumer);
  }

  public static <S1, S2, R> CaseFunction2<S1, S2, R> caseValueReturn(@Nonnull S1 value1, @Nonnull S2 value2,
      @Nonnull BiFunction<S1, S2, R> function) {
    return caseValue(value1).and(caseValue(value2)).thenReturn(function);
  }

  public static <S1, S2> CaseBlock2<S1, S2> caseMatcher(@Nonnull Matcher<S1> matcher1, @Nonnull Matcher<S2> matcher2,
      @Nonnull BiConsumer<S1, S2> consumer) {
    return caseMatcher(matcher1).and(caseMatcher(matcher2)).then(consumer);
  }

  public static <S1, S2, R> CaseFunction2<S1, S2, R> caseMatcherReturn(@Nonnull Matcher<S1> matcher1,
      @Nonnull Matcher<S2> matcher2, @Nonnull BiFunction<S1, S2, R> function) {
    return caseMatcher(matcher1).and(caseMatcher(matcher2)).thenReturn(function);
  }

}
