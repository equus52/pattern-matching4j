package equus.matching.cases;

import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import equus.matching.NoConvertCase;

public class PredicateCase<S> implements NoConvertCase<S> {
  private final Predicate<S> predicate;

  public PredicateCase(@Nonnull Predicate<S> predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean match(@Nullable S subject) {
    return predicate.test(subject);
  }

}
