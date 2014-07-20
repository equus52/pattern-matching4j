package equus.matching.cases;

import javax.annotation.Nullable;

import equus.matching.NothingCase;

public class NullCase<S> implements NothingCase<S> {

  @Override
  public boolean match(@Nullable S subject) {
    return subject == null;
  }

}
