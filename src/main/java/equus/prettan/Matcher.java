package equus.prettan;

import javax.annotation.Nullable;

public interface Matcher<S> {

  boolean match(@Nullable S subject);
}
