package equus.matching;

public interface NoConvertCase<S> extends Case<S, S> {

  @Override
  default S convert(S subject) {
    return subject;
  }
}
