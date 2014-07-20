package equus.matching;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PatternMatcher2<S1, S2> {

  private final S1 subject1;
  private final S2 subject2;

  public PatternMatcher2(@Nullable S1 subject1, @Nullable S2 subject2) {
    this.subject1 = subject1;
    this.subject2 = subject2;
  }

  @SafeVarargs
  public final void matches(@Nonnull CaseBlock2<S1, S2>... caseBlocks) {
    for (CaseBlock2<S1, S2> caseBlock : caseBlocks) {
      if (caseBlock.match(subject1, subject2)) {
        caseBlock.accept(subject1, subject2);
        return;
      }
    }
  }

  @SafeVarargs
  public final <R> Optional<R> matches(@Nonnull CaseFunction2<S1, S2, R>... caseFunctions) {
    for (CaseFunction2<S1, S2, R> caseFunction : caseFunctions) {
      if (caseFunction.match(subject1, subject2)) {
        return Optional.ofNullable(caseFunction.apply(subject1, subject2));
      }
    }
    return Optional.empty();
  }

}
