package equus.prettan.cases;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import equus.prettan.Case;
import equus.prettan.CaseBlock;
import equus.prettan.CaseFunction;

public class SomeCase<T> implements Case<Optional<T>, T> {

  public SomeCase() {}

  public SomeCase(Class<T> clazz) {}

  @Override
  public boolean match(@Nonnull Optional<T> subject) {
    return subject.isPresent();
  }

  @Override
  public T convert(Optional<T> subject) {
    return subject.get();
  }

  public static abstract class SomeCaseBlock<T1> implements CaseBlock<Optional<T1>> {}

  @Override
  public SomeCaseBlock<T> then(@Nonnull Consumer<T> consumer) {
    return new SomeCaseBlock<T>() {

      @Override
      public boolean match(Optional<T> subject) {
        return SomeCase.this.match(subject);
      }

      @Override
      public void accept(Optional<T> subject) {
        consumer.accept(subject.get());
      }
    };
  }

  public static abstract class SomeCaseFunction<T1, R> implements CaseFunction<Optional<T1>, R> {}

  @Override
  public <R> SomeCaseFunction<T, R> thenReturn(@Nonnull Function<T, R> function) {
    return new SomeCaseFunction<T, R>() {

      @Override
      public boolean match(Optional<T> subject) {
        return SomeCase.this.match(subject);
      }

      @Override
      public R apply(Optional<T> subject) {
        return function.apply(subject.get());
      }
    };
  }

}
