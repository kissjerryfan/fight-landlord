package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RoomWindow {
    private boolean isReady = false;
    JFrame frame = new JFrame("游戏房间");
    JPanel p = new JPanel();
    JLayeredPane layeredPane = new JLayeredPane();
    ImageIcon GG = new ImageIcon("Client/img/characters/Ch1.png");
    ImageIcon GG_ready1 = new ImageIcon("Client/img/characters/Ch1_ready1.png");
    ImageIcon GG_ready2 = new ImageIcon("Client/img/characters/Ch1_ready2.png");
    ImageIcon noPerson = new ImageIcon("Client/img/defaultHeader.png");
    ImageIcon desk = new ImageIcon("Client/img/gameBackground/BG8.png");
    ImageIcon unready = new ImageIcon("Client/img/unready.png");
    ImageIcon ready = new ImageIcon("Client/img/ready.png");
    ImageIcon quit =  new ImageIcon("Client/img/quit.png");
    JLabel bg = new JLabel(desk);
    JPanel ground;
    JButton b_ready = new JButton(ready);
    JButton b_exit = new JButton(quit);
    JLabel player1 = new JLabel(noPerson);
    JLabel player2 = new JLabel(noPerson);
    JPanel btn_group = new JPanel();
    ImageIcon log = new ImageIcon("Client/img/log.png");
    Image log0 = log.getImage();

    int x = 450;
    int y = 210;
    int width = 650;
    int height = 450;

    public RoomWindow(int x, int y){
        this.x = x;
        this.y = y;
    }


    public void show(Player player){
        this.frame.setIconImage(log0);
        ready.setImage(ready.getImage().getScaledInstance(100,50,Image.SCALE_DEFAULT));
        unready.setImage(unready.getImage().getScaledInstance(100,50,Image.SCALE_DEFAULT));
        quit.setImage(quit.getImage().getScaledInstance(100,50,Image.SCALE_DEFAULT));
        desk.setImage(desk.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
        ground = (JPanel) frame.getContentPane();
        ground.add(bg);
        layeredPane.add(ground,JLayeredPane.DEFAULT_LAYER);
        p.setBorder(BorderFactory.createEmptyBorder(0,10,20,30));
        p.setLayout(new BorderLayout());
        p.setBounds(0,0,this.width,this.height);
        btn_group.add(b_ready);
        btn_group.add(b_exit);
        b_ready.setContentAreaFilled(false);
        b_ready.setBorder(null);
        b_exit.setContentAreaFilled(false);
        b_exit.setBorder(null);
        btn_group.setOpaque(false);
        p.add(btn_group,BorderLayout.NORTH);
        b_ready.addActionListener(e -> {
            if (!this.isReady){
                player.setState("ready");
                this.isReady = true;
                b_ready.setIcon(unready);
            } else {
                player.setState("unready");
                this.isReady = false;
                b_ready.setIcon(ready);
            }
        });
        b_exit.addActionListener(e -> player.setState("quit"));

        p.add(player1,BorderLayout.WEST);
        p.add(player2,BorderLayout.EAST);
        p.setOpaque(false);
        layeredPane.add(p,JLayeredPane.MODAL_LAYER);
        frame.setLayeredPane(layeredPane);
//        frame.add(p);
        frame.setBounds(x,y,width,height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                player.setState("quit");

            }
        });
    }
    public void close(){
        frame.setVisible(false);
    }
    public void refresh(String s){
        this.frame.setIconImage(log0);
        if(s.charAt(1)=='0'){
            player1.setIcon(noPerson);
        }
        else if(s.charAt(1)=='1') {
            player1.setIcon(GG);
        }
        else if(s.charAt(1)=='2')
            player1.setIcon(GG_ready1);
        if(s.charAt(3)=='0'){
            player2.setIcon(noPerson);
        }
        else if(s.charAt(3)=='1'){
            player2.setIcon(GG);
        }
        else if(s.charAt(3)=='2')
            player2.setIcon(GG_ready2);
    }

    public int[] getLocate(){
        this.frame.setIconImage(log0);
        Rectangle locate = this.frame.getBounds();
        int[] point = new int[2];
        point[0] = locate.x;
        point[1] = locate.y;
        return point;
    }

}
