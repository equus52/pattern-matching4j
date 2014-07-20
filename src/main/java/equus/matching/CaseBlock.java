package equus.matching;

public interface CaseBlock<S> extends Matcher<S> {

  void accept(S subject);
}