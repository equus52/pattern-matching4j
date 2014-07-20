package equus.matching;

public interface CaseFunction<S, R> extends Matcher<S> {

  R apply(S subject);

}
