package equus.prettan.cases;

import javax.annotation.Nullable;

import equus.prettan.NothingCase;

public class NullCase<S> implements NothingCase<S> {

  @Override
  public boolean match(@Nullable S subject) {
    return subject == null;
  }

}
