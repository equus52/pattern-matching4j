package equus.prettan.cases;

import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.hamcrest.Matcher;

import equus.prettan.Case;

public class ClassCase<S, T extends S> implements Case<S, T> {
  private final Class<T> matchClass;
  private Predicate<T> predicate;
  private Matcher<T> matcher;

  public ClassCase(@Nonnull Class<T> matchClass) {
    this.matchClass = matchClass;
  }

  @Override
  public boolean match(@Nullable S subject) {
    if (!matchClass.isInstance(subject)) {
      return false;
    }
    T converted = convert(subject);
    if (predicate != null && !predicate.test(converted)) {
      return false;
    }
    if (matcher != null && !matcher.matches(converted)) {
      return false;
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T convert(S subject) {
    return (T) subject;
  }

  public ClassCase<S, T> with(@Nonnull Predicate<T> predicate) {
    this.predicate = predicate;
    return this;
  }

  public ClassCase<S, T> with(@Nonnull Matcher<T> matcher) {
    this.matcher = matcher;
    return this;
  }
}
