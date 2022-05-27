public class PerdeuException extends RuntimeException{
    public PerdeuException(){
        super("Você perdeu \n Começando do 0!");
        Main.comecarNovoJogo();
    }
}
