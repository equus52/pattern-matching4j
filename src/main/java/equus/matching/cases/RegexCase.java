package equus.matching.cases;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import equus.matching.NoConvertCase;

public class RegexCase implements NoConvertCase<String> {
  private final Pattern pattern;

  public RegexCase(@Nonnull Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public boolean match(@Nullable String subject) {
    return pattern.matcher(subject).find();
  }

}
