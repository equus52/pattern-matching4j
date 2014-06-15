package equus.prettan;

import java.util.Optional;

import javax.annotation.Nonnull;

import equus.prettan.cases.NoneCase.NoneCaseBlock;
import equus.prettan.cases.NoneCase.NoneCaseFunction;
import equus.prettan.cases.SomeCase.SomeCaseBlock;
import equus.prettan.cases.SomeCase.SomeCaseFunction;

public class OptionalMatcher<T> {

  private final Optional<T> subject;

  public OptionalMatcher(@Nonnull Optional<T> subject) {
    this.subject = subject;
  }

  public void matches(@Nonnull SomeCaseBlock<T> someCaseBlock, NoneCaseBlock noneCaseBlock) {
    if (someCaseBlock.match(subject)) {
      someCaseBlock.accept(subject);
      return;
    }
    noneCaseBlock.accept(subject);
  }

  public <R> R matches(@Nonnull SomeCaseFunction<T, R> someCaseFunction, @Nonnull NoneCaseFunction<R> noneCaseFunction) {
    if (someCaseFunction.match(subject)) {
      return someCaseFunction.apply(subject);
    }
    return noneCaseFunction.apply(subject);
  }
}
