import java.util.ArrayList;

public class Canibal extends Personagem{
    public Canibal() { }

    @Override
    public void colocar() {
        ArrayList<Personagem> lado = Main.pegarLado();
        int indice = -1;

        for(int i = 0; i < lado.size();i++){
            if(lado.get(i) instanceof Canibal){
                indice = i;
            }
        }

        if(indice == -1){
            throw new NaoTemEssaClasseException("canibal", " desse lado!");
        } else {
            if(Main.jangada[1] != null){
                throw new JangadaCheiaException();
            } else {
                if(Main.jangada[0] == null){
                    Main.jangada[0] = lado.get(indice);
                    lado.remove(indice);
                } else {
                    Main.jangada[1] = lado.get(indice);
                    lado.remove(indice);
                }
            }
        }
    }

    @Override
    public void tirar() {
        ArrayList<Personagem> lado = Main.pegarLado();
        int indice = -1;

        if(Main.jangada[0] == null && Main.jangada[1] == null){
            throw new JangadaVaziaException();
        } else {
            for(int i = 0; i < Main.jangada.length; i++){
                if(Main.jangada[i] instanceof Canibal){
                    indice = i;
                }
            }
            if(indice == -1){
                throw new NaoTemEssaClasseException("canibal", " na jangada");
            } else {
                Main.jangada[indice] = null;
                lado.add(new Canibal());
            }
        }

    }
}
