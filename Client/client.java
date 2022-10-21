package Client;

import java.io.IOException;
import java.util.Scanner;

public class client {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException, InterruptedException {

        int[] a = new int[1];
        Login l = new Login(a);

        while(a[0] != 1) {

            System.out.println("a = " + a[0]);

            if (a[0] == 1) {

//                System.out.println(MusicPlay.class.getResource("/"));
//                MusicPlay myMusicPlay = new MusicPlay(MusicPlay.class.getResource("/Client/sound/BGM.wav"));
//                myMusicPlay.continuousStart();
                Player player = new Player();
                while (true) {
                    player.home();
                    player.playGame();
                }
            }
        }
    }
}
