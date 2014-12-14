package equus.matching.cases;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import equus.matching.Case;
import equus.matching.CaseBlock;
import equus.matching.CaseFunction;

public class OptionalPresentCase<T> implements Case<Optional<T>, T> {

  public OptionalPresentCase() {}

  public OptionalPresentCase(Class<T> clazz) {}

  @Override
  public boolean match(@Nonnull Optional<T> subject) {
    return subject.isPresent();
  }

  @Override
  public T convert(Optional<T> subject) {
    return subject.get();
  }

  public static abstract class OptionalPresentCaseBlock<T1> implements CaseBlock<Optional<T1>> {}

  @Override
  public OptionalPresentCaseBlock<T> then(@Nonnull Consumer<T> consumer) {
    return new OptionalPresentCaseBlock<T>() {

      @Override
      public boolean match(Optional<T> subject) {
        return OptionalPresentCase.this.match(subject);
      }

      @Override
      public void accept(Optional<T> subject) {
        consumer.accept(subject.get());
      }
    };
  }

  public static abstract class OptionalPresentCaseFunction<T1, R> implements CaseFunction<Optional<T1>, R> {}

  @Override
  public <R> OptionalPresentCaseFunction<T, R> then_(@Nonnull Function<T, R> function) {
    return new OptionalPresentCaseFunction<T, R>() {

      @Override
      public boolean match(Optional<T> subject) {
        return OptionalPresentCase.this.match(subject);
      }

      @Override
      public R apply(Optional<T> subject) {
        return function.apply(subject.get());
      }
    };
  }

}
