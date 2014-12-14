package equus.matching;

import java.util.Optional;

import javax.annotation.Nonnull;

import equus.matching.cases.OptionalEmptyCase.OptionalEmptyCaseBlock;
import equus.matching.cases.OptionalEmptyCase.OptionalEmptyCaseFunction;
import equus.matching.cases.OptionalPresentCase.OptionalPresentCaseBlock;
import equus.matching.cases.OptionalPresentCase.OptionalPresentCaseFunction;

public class OptionalMatcher<T> {

  private final Optional<T> subject;

  public OptionalMatcher(@Nonnull Optional<T> subject) {
    this.subject = subject;
  }

  public void matches(@Nonnull OptionalPresentCaseBlock<T> someCaseBlock, OptionalEmptyCaseBlock noneCaseBlock) {
    if (someCaseBlock.match(subject)) {
      someCaseBlock.accept(subject);
      return;
    }
    noneCaseBlock.accept(subject);
  }

  public <R> R matches(@Nonnull OptionalPresentCaseFunction<T, R> someCaseFunction, @Nonnull OptionalEmptyCaseFunction<R> noneCaseFunction) {
    if (someCaseFunction.match(subject)) {
      return someCaseFunction.apply(subject);
    }
    return noneCaseFunction.apply(subject);
  }
}
