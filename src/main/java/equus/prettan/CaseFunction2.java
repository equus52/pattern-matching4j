package equus.prettan;

public interface CaseFunction2<S1, S2, R> extends Matcher2<S1, S2> {

  R apply(S1 subject1, S2 subject2);

}
