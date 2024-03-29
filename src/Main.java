import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static public Personagem[] jangada = new Personagem[2];
    static public ArrayList<Personagem> lado1 = new ArrayList<>();
    static public ArrayList<Personagem> lado2 = new ArrayList<>();
    static int turno = 1;
    static Canibal canibal = new Canibal();
    static Missionario missionario = new Missionario();

    public static void main(String[] args) {
        System.out.println("adicionou! 1");
        lado1.add(new Canibal());
        lado1.add(new Canibal());
        lado1.add(new Canibal());
        lado1.add(new Missionario());
        lado1.add(new Missionario());
        lado1.add(new Missionario());

        jogo();
    }

    private static int[] pegarQuantidade(int lista) {
        int[] quantidade = {0, 0};

        if (lista == 2) {
            for (Personagem personagem : lado2) {
                if (personagem != null) {
                    if (personagem instanceof Canibal) {
                        quantidade[0]++;
                    } else if (personagem instanceof Missionario) {
                        quantidade[1]++;
                    }
                }
            }
        } else if (lista == 1) {
            for (Personagem personagem : lado1) {
                if (personagem != null) {
                    if (personagem instanceof Canibal) {
                        quantidade[0]++;
                    } else if (personagem instanceof Missionario) {
                        quantidade[1]++;
                    }
                }
            }
        } else {
            for (Personagem personagem : jangada) {
                if (personagem != null) {
                    if (personagem instanceof Canibal) {
                        quantidade[0]++;
                    } else if (personagem instanceof Missionario) {
                        quantidade[1]++;
                    }
                }
            }
        }

        return quantidade;
    }

    private static void estagio() {
        int[] quantidade1 = pegarQuantidade(1), quantidade2 = pegarQuantidade(2), quantidade3 = pegarQuantidade(3);

        System.out.println("Têm " + quantidade2[0] + " canibais e " + quantidade2[1] + " missionários do lado esquerdo do rio\n" +
                "Têm " + quantidade3[0] + " canibais e " + quantidade3[1] + " missionários na jangada\n" +
                "Têm " + quantidade1[0] + " canibais e " + quantidade1[1] + " missionários do lado direito do rio\n");
    }

    private static void jogo() {
        estagio();
        String lado = "";

        if (turno % 2 == 0) {
            lado = "esquerdo";
        } else {
            lado = "direito";
        }

        System.out.println("--- Lado da jangada : " + lado);

        System.out.println("""
                1 - Colocar um missionário na jangada\s
                2 - Tirar um missionário da jangada
                3 - Colocar um canibal na jangada\s
                4 - Tirar um canibal da jangada\s
                5 - Passar a jangada para o outro lado""");
        try {
            switch (sc.nextInt()) {
                case 1 -> missionario.colocar();
                case 2 -> missionario.tirar();
                case 3 -> canibal.colocar();
                case 4 -> canibal.tirar();
                case 5 -> mandarJangada();
                default -> throw new OpcaoInvalidaException();
            }
        } catch (PerdeuException idiota) {
            System.out.println(idiota.getClass().getSimpleName() + ": " + idiota.getMessage() + "\n");
            comecarNovoJogo();
        } catch (RuntimeException idiota) {
            System.out.println(idiota.getClass().getSimpleName() + ": " + idiota.getMessage() + "\n");
        } finally {
            checarVitoria();
            jogo();
        }
    }

    public static void checarVitoria() {
        int[] quantidade2 = pegarQuantidade(2);
        int quantidadeTotal2 = quantidade2[0] + quantidade2[1];

        if (quantidadeTotal2 == 6) {
            System.out.println("Parabéns você ganhou!");
            System.out.println("Deseja jogar novamente? \n1 - Sim \n2 - Não");
            switch (sc.nextInt()) {
                case 1 -> comecarNovoJogo();
                case 2 -> System.exit(0);
                default -> throw new OpcaoInvalidaException();
            }
        }
    }

    public static void checarCondicoes() {
        int[] quantidade1 = pegarQuantidade(1), quantidade2 = pegarQuantidade(2), quantidade3 = pegarQuantidade(3);
        boolean checar[];

        if (quantidade1[0] != 0 && quantidade1[1] != 0 && quantidade1[0] > quantidade1[1]) {
            throw new PerdeuException();
        }
        if (quantidade2[0] != 0 && quantidade2[1] != 0 && quantidade2[0] > quantidade2[1]) {
            throw new PerdeuException();
        }
        if (quantidade1[0] != 0  && quantidade3[0] != 0 && quantidade3[1] != 0 && quantidade1[0] + quantidade3[0] > quantidade3[1] + quantidade1[1]) {
            throw new PerdeuException();
        }
        if (quantidade2[0] != 0  && quantidade3[0] != 0 && quantidade3[1] != 0 && quantidade2[0] + quantidade3[0] > quantidade3[1] + quantidade2[1]) {
            throw new PerdeuException();
        }

        checarVitoria();

    }

    private static void mandarJangada() {
        int quantJangada = 0;

        for (int i = 0; i < jangada.length; i++) {
            if (jangada[i] != null) {
                quantJangada++;
            }
        }

        if (quantJangada == 0) {
            throw new JangadaVaziaException();
        } else {
            checarCondicoes();
            turno++;
        }
    }

    public static void comecarNovoJogo() {
        int tamanhoLado1 = lado1.size(), tamanhoLado2 = lado2.size();

        for (int i = 0; i < tamanhoLado1; i++) {
            lado1.remove(0);
        }

        for (int i = 0; i < tamanhoLado2; i++) {
            lado2.remove(0);
        }

        Arrays.fill(jangada, null);

        lado1.add(new Canibal());
        lado1.add(new Canibal());
        lado1.add(new Canibal());
        lado1.add(new Missionario());
        lado1.add(new Missionario());
        lado1.add(new Missionario());

        turno = 1;

        jogo();
    }

    public static ArrayList<Personagem> pegarLado() {
        ArrayList<Personagem> lado;

        if (Main.turno % 2 == 0) {
            lado = Main.lado2;
        } else {
            lado = Main.lado1;
        }

        return lado;
    }

}
