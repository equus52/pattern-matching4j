package equus.matching;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

public interface NothingCase<S> extends Case<S, Void> {

  @Override
  default Void convert(S subject) {
    return null;
  }

  default CaseBlock<S> then(@Nonnull Block block) {
    return then(v -> block.apply());
  }

  default <R> CaseFunction<S, R> thenReturn(@Nonnull Supplier<R> supplier) {
    return thenReturn(v -> supplier.get());
  }
}