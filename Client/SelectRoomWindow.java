package Client;

import javax.swing.*;
import java.awt.*;

public class SelectRoomWindow {
    JFrame frame = new JFrame("游戏大厅");
    ImageIcon bg = new ImageIcon("Client/img/room.jpg");
    JLabel back = new JLabel(bg);
    JLayeredPane layeredPane = new JLayeredPane();
    ImageIcon log = new ImageIcon("Client/img/log.png");
    ImageIcon home = new ImageIcon("Client/img/room.png");
    Image log0 = log.getImage();
    JPanel ground;
    JPanel p = new JPanel();
    JButton[] b = {new JButton("0号房间"),new JButton("1号房间"),new JButton("2号房间"),new JButton("3号房间"),new JButton("4号房间"),new JButton("5号房间"),new JButton("6号房间"),
            new JButton("7号房间"),new JButton("8号房间"),new JButton("9号房间"),new JButton("10号房间"),new JButton("11号房间")};
    JLabel[] roomNumLable=new JLabel[12];
    JLabel head=new JLabel("游戏大厅",JLabel.CENTER);
    Font font = new Font("宋体",Font.BOLD,20);
    Font font2 = new Font("宋体",Font.BOLD,15);
    int x = 450;
    int y = 210;
    int width = 650;
    int height = 450;

    public SelectRoomWindow(int x,int y){
        this.frame.setIconImage(log0);
        this.x = x;
        this.y = y;
    }

    public void showSelectRoom(Player player){

        home.setImage(home.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));

        this.frame.setIconImage(log0);
        bg.setImage(bg.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
        ground = (JPanel) frame.getContentPane();
        ground.add(back);
        layeredPane.add(ground,JLayeredPane.DEFAULT_LAYER);
        p.setOpaque(false);
        p.setBounds(0,0,width,height);
        layeredPane.add(p,JLayeredPane.MODAL_LAYER);
        head.setFont(new Font("宋体", Font.BOLD, 20));
        for(int i=0;i<12;i++){
            roomNumLable[i]=new JLabel("",JLabel.CENTER);
            b[i].setContentAreaFilled(false);//设置按钮透明
            b[i].setBorder(null);//取消边框
            b[i].setIcon(home);
            b[i].setBounds(b[i].getX(),b[i].getY(),30,30);
            b[i].setText("");
        }
        b[0].addActionListener(e -> player.setSelectRoom("0"));
        b[1].addActionListener(e -> player.setSelectRoom("1"));
        b[2].addActionListener(e -> player.setSelectRoom("2"));
        b[3].addActionListener(e -> player.setSelectRoom("3"));
        b[4].addActionListener(e -> player.setSelectRoom("4"));
        b[5].addActionListener(e -> player.setSelectRoom("5"));
        b[6].addActionListener(e -> player.setSelectRoom("6"));
        b[7].addActionListener(e -> player.setSelectRoom("7"));
        b[8].addActionListener(e -> player.setSelectRoom("8"));
        b[9].addActionListener(e -> player.setSelectRoom("9"));
        b[10].addActionListener(e -> player.setSelectRoom("10"));
        b[11].addActionListener(e -> player.setSelectRoom("11"));

        p.setBorder(BorderFactory.createEmptyBorder(20,20,26,25));
        p.setLayout(new GridLayout(6,4,40,0));
        for(int i=0;i<4;i++){
            p.add(b[i]);
            b[i].setFont(font);
            roomNumLable[i].setForeground(Color.WHITE);
        }
        for(int i=0;i<4;i++){
            p.add(roomNumLable[i]);
            roomNumLable[i].setFont(font2);

        }
        for(int i=4;i<8;i++){
            p.add(b[i]);
            b[i].setFont(font);
            roomNumLable[i].setForeground(Color.WHITE);
        }
        for(int i=4;i<8;i++){
            p.add(roomNumLable[i]);
            roomNumLable[i].setFont(font2);

        }
        for(int i=8;i<12;i++){
            p.add(b[i]);
            b[i].setFont(font);
        }
        for(int i=8;i<12;i++){
            p.add(roomNumLable[i]);
            roomNumLable[i].setFont(font2);
            roomNumLable[i].setForeground(Color.WHITE);
        }
        frame.setLayeredPane(layeredPane);
        frame.setBounds(x,y,width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public void close(){
        frame.setVisible(false);
    }

    public void refresh(String s){
        this.frame.setIconImage(log0);
        String[] info = s.split(",");
        String[] room_name = {"南五舍","铁十舍","二综楼","知新馆","世纪楼","毓秀楼","游泳馆","瞻衡斋","逸舟亭","知秋苑","贤良阁","珈瑜堂"};
        for (int i = 0;i < 12;i++){
            this.roomNumLable[i].setText("<html>&nbsp" + room_name[i] + "<br>已有" + info[i] + "个玩家</html>");
        }
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
