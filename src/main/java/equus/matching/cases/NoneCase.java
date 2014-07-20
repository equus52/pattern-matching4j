package equus.matching.cases;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import equus.matching.Block;
import equus.matching.CaseBlock;
import equus.matching.CaseFunction;
import equus.matching.NothingCase;

public class NoneCase implements NothingCase<Optional<?>> {

  @Override
  public boolean match(@Nonnull Optional<?> subject) {
    return !subject.isPresent();
  }

  public static abstract class NoneCaseBlock implements CaseBlock<Optional<?>> {}

  @Override
  public NoneCaseBlock then(@Nonnull Block block) {
    return new NoneCaseBlock() {

      @Override
      public boolean match(Optional<?> subject) {
        return NoneCase.this.match(subject);
      }

      @Override
      public void accept(Optional<?> subject) {
        block.apply();
      }
    };
  }

  public static abstract class NoneCaseFunction<R> implements CaseFunction<Optional<?>, R> {}

  @Override
  public <R> NoneCaseFunction<R> thenReturn(@Nonnull Supplier<R> supplier) {
    return new NoneCaseFunction<R>() {

      @Override
      public boolean match(Optional<?> subject) {
        return NoneCase.this.match(subject);
      }

      @Override
      public R apply(Optional<?> subject) {
        return supplier.get();
      }
    };
  }

}
