package equus.prettan;

public interface CaseBlock<S> extends Matcher<S> {

  void accept(S subject);
}