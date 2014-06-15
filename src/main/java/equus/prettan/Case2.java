package equus.prettan;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Case2<S1, T1, S2, T2> implements Matcher2<S1, S2> {

  private final Case<S1, T1> case1;
  private final Case<S2, T2> case2;

  public Case2(@Nonnull Case<S1, T1> case1, @Nonnull Case<S2, T2> case2) {
    this.case1 = case1;
    this.case2 = case2;
  }

  @Override
  public boolean match(@Nullable S1 subject1, @Nullable S2 subject2) {
    return case1.match(subject1) && case2.match(subject2);
  }

  public T1 convert1(S1 subject1) {
    return case1.convert(subject1);
  }

  public T2 convert2(S2 subject2) {
    return case2.convert(subject2);
  }

  public CaseBlock2<S1, S2> then(@Nonnull BiConsumer<T1, T2> consumer) {
    return new CaseBlock2<S1, S2>() {

      @Override
      public boolean match(S1 subject1, S2 subject2) {
        return Case2.this.match(subject1, subject2);
      }

      @Override
      public void accept(S1 subject1, S2 subject2) {
        consumer.accept(convert1(subject1), convert2(subject2));
      }
    };
  }

  public <R> CaseFunction2<S1, S2, R> thenReturn(@Nonnull BiFunction<T1, T2, R> function) {
    return new CaseFunction2<S1, S2, R>() {

      @Override
      public boolean match(S1 subject1, S2 subject2) {
        return Case2.this.match(subject1, subject2);
      }

      @Override
      public R apply(S1 subject1, S2 subject2) {
        return function.apply(convert1(subject1), convert2(subject2));
      }
    };
  }
}