package equus.matching.cases;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import equus.matching.Block;
import equus.matching.CaseBlock;
import equus.matching.CaseFunction;
import equus.matching.NothingCase;

public class OptionalEmptyCase implements NothingCase<Optional<?>> {

  @Override
  public boolean match(@Nonnull Optional<?> subject) {
    return !subject.isPresent();
  }

  public static abstract class OptionalEmptyCaseBlock implements CaseBlock<Optional<?>> {}

  @Override
  public OptionalEmptyCaseBlock then(@Nonnull Block block) {
    return new OptionalEmptyCaseBlock() {

      @Override
      public boolean match(Optional<?> subject) {
        return OptionalEmptyCase.this.match(subject);
      }

      @Override
      public void accept(Optional<?> subject) {
        block.apply();
      }
    };
  }

  public static abstract class OptionalEmptyCaseFunction<R> implements CaseFunction<Optional<?>, R> {}

  @Override
  public <R> OptionalEmptyCaseFunction<R> then_(@Nonnull Supplier<R> supplier) {
    return new OptionalEmptyCaseFunction<R>() {

      @Override
      public boolean match(Optional<?> subject) {
        return OptionalEmptyCase.this.match(subject);
      }

      @Override
      public R apply(Optional<?> subject) {
        return supplier.get();
      }
    };
  }

}
