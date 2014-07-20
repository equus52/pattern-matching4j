package equus.matching;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

public interface Case<S, T> extends Matcher<S> {

  T convert(S subject);

  default CaseBlock<S> then(@Nonnull Consumer<T> consumer) {
    return new CaseBlock<S>() {

      @Override
      public boolean match(S subject) {
        return Case.this.match(subject);
      }

      @Override
      public void accept(S subject) {
        consumer.accept(convert(subject));
      }
    };
  }

  default <R> CaseFunction<S, R> thenReturn(@Nonnull Function<T, R> function) {
    return new CaseFunction<S, R>() {

      @Override
      public boolean match(S subject) {
        return Case.this.match(subject);
      }

      @Override
      public R apply(S subject) {
        return function.apply(convert(subject));
      }
    };
  }

  default <S2, T2> Case2<S, T, S2, T2> and(Case<S2, T2> case2) {
    return new Case2<>(this, case2);
  }
}