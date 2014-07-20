package equus.matching.cases;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import equus.matching.NoConvertCase;

public class DisjunctionCase<S> implements NoConvertCase<S> {

  private final List<S> matchValues;

  public DisjunctionCase(@Nonnull List<S> matchValues) {
    this.matchValues = matchValues;
  }

  @Override
  public boolean match(@Nullable S subject) {
    for (S matchValue : matchValues) {
      if (matchValue.equals(subject)) {
        return true;
      }
    }
    return false;
  }

}
