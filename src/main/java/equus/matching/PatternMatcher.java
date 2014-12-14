package equus.matching;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PatternMatcher<S> {

  private final S subject;

  public PatternMatcher(@Nullable S subject) {
    this.subject = subject;
  }

  @SafeVarargs
  public final void matches(@Nonnull CaseBlock<S>... caseBlocks) {
    for (CaseBlock<S> caseBlock : caseBlocks) {
      if (caseBlock.match(subject)) {
        caseBlock.accept(subject);
        return;
      }
    }
  }

  @SafeVarargs
  public final <R> R matches(@Nonnull CaseFunction<S, R>... caseFunctions) {
    for (CaseFunction<S, R> caseFunction : caseFunctions) {
      if (caseFunction.match(subject)) {
        return caseFunction.apply(subject);
      }
    }
    throw new MatchError(subject);
  }

}
