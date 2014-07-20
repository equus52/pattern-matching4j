package equus.matching.cases;

import equus.matching.NoConvertCase;

public class AnyCase<S> implements NoConvertCase<S> {

  public AnyCase() {}

  public AnyCase(Class<S> clazz) {}

  @Override
  public boolean match(S subject) {
    return true;
  }
}
