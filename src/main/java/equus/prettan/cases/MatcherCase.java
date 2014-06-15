package equus.prettan.cases;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.hamcrest.Matcher;

import equus.prettan.NoConvertCase;

public class MatcherCase<S> implements NoConvertCase<S> {
  private final Matcher<S> matcher;

  public MatcherCase(@Nonnull Matcher<S> matcher) {
    this.matcher = matcher;
  }

  @Override
  public boolean match(@Nullable S subject) {
    return matcher.matches(subject);
  }

}
