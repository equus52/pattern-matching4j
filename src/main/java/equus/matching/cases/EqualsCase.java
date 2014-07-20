package equus.matching.cases;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import equus.matching.NoConvertCase;

public class EqualsCase<S> implements NoConvertCase<S> {
  private final S matchValue;

  public EqualsCase(@Nonnull S matchValue) {
    this.matchValue = matchValue;
  }

  @Override
  public boolean match(@Nullable S subject) {
    return matchValue.equals(subject);
  }

}
