public class NaoTemEssaClasseException extends RuntimeException{

    public NaoTemEssaClasseException(String classe, String lugar){super("Não tem nenhum " + classe + lugar);}
}
