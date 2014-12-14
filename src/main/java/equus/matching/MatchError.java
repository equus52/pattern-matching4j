package equus.matching;

@SuppressWarnings("serial")
public class MatchError extends RuntimeException {

  public MatchError(Object... subjects) {
    super(String.format("%s does not match with any conditions.", subjects));
  }

}
