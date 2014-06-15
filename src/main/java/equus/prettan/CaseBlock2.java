package equus.prettan;

public interface CaseBlock2<S1, S2> extends Matcher2<S1, S2> {

  void accept(S1 subject1, S2 subject2);
}