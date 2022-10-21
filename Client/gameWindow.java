package Client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class gameWindow {
    Player player;
    ArrayList<Integer> card = new ArrayList<>();
    JLayeredPane layeredPane = new JLayeredPane();
    JFrame frame = new JFrame("游戏界面");
    ImageIcon bg = new ImageIcon("Client/img/gameBackground/BG8.png");
    JPanel back;//背景
    JPanel front = new JPanel();//按钮之类
    ImageIcon _bujiao = new ImageIcon("Client/img/0.png");
    ImageIcon _one = new ImageIcon("Client/img/1.png");
    ImageIcon _two = new ImageIcon("Client/img/2.png");
    ImageIcon _three = new ImageIcon("Client/img/3.png");
    JButton bujiao = new JButton();
    JButton one = new JButton();
    JButton two = new JButton();
    JButton three = new JButton();
    JButton chupai = new JButton();
    JButton buchu = new JButton();
    JLabel notice = new JLabel("", JLabel.CENTER);
    ImageIcon GG = new ImageIcon("Client/img/characters/Ch1.png");
    ImageIcon GG_lord1 = new ImageIcon("Client/img/characters/Ch1_Lord1.png");
    ImageIcon GG_lord2 = new ImageIcon("Client/img/characters/Ch1_Lord2.png");
    ImageIcon chupai_btn = new ImageIcon("Client/img/chupai.png");
    ImageIcon chupai_false_btn = new ImageIcon("Client/img/chupai_false.png");
    ImageIcon buchu_btn = new ImageIcon("Client/img/buchu.png");
    ImageIcon log = new ImageIcon("Client/img/log.png");//
    Image log0 = log.getImage();
    JLabel player1 = new JLabel(GG);
    JLabel player2 = new JLabel(GG);
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JLabel player1_num = new JLabel();//玩家手牌数,和玩家抢的点数
    JLabel player2_num = new JLabel("", SwingConstants.LEFT);
    ImageIcon poker_back = new ImageIcon("Client/img/poker/poker_back.png");
    JPanel dipai = new JPanel();
    JLabel dipai_1 = new JLabel("", JLabel.CENTER);
    JLabel dipai_2 = new JLabel("", JLabel.CENTER);
    JLabel dipai_3 = new JLabel("", JLabel.CENTER);
    JPanel btn_group = new JPanel();//抢点数按钮
    JPanel btn_group2 = new JPanel();//出牌按钮
    Font font = new Font("宋体", Font.BOLD, 20);
    Font font2 = new Font("宋体", Font.BOLD, 16);
    private String player1_id = "-1";
    private String player2_id = "-1";
    private int player1_deckNum;
    private int player2_deckNum;
    int x = 450;
    int y = 210;
    int width = 650;
    int height = 450;
    private String qiangdizhu = "-1";
    private Boolean isOutCards = false;
    ImageIcon[] images = new ImageIcon[15];//卡牌图片
    ImageIcon[] smallImages = new ImageIcon[15];//小图片
    private int last_cardNum = 0;//出牌区上次的卡牌数
    private int last_haveCards = 0;//手牌区上次手牌数量

    //BGM
    private static Clip bgm;
    private static AudioInputStream ais;

//    public static void play()
//    {
//        try {
//            bgm = AudioSystem.getClip();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
//        InputStream is = gameWindow.class.getClassLoader().getResourceAsStream("sound/BGM.mp3");
//        if(is != null){
//            try {
//                ais = AudioSystem.getAudioInputStream(is);
//            } catch (UnsupportedAudioFileException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            bgm.open(ais);
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        bgm.start();
//        bgm.loop(Clip.LOOP_CONTINUOUSLY);
//    }

//    //背景音乐播放器
//    private AudioStream as;
//
//    {
//        try {
//            as = new AudioStream(new URL("Client/sound/BGM.mp3").openStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    ;
//    ContinuousAudioDataStream cads;

//    //循环播放器
//    public void continuousStart() {
//        // Create AudioData source.
//        AudioData data = null;
//        try {
//            data = as.getData();  //getData()函数不支持 mid的音频文件
//        } catch(IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        // Create ContinuousAudioDataStream.
//        cads = new ContinuousAudioDataStream(data);
//
//        // Play audio.
//        AudioPlayer.player.start(cads);
//    }



    /**
     * @param x      int 初始化窗口横坐标
     * @param y      int 初始化窗口纵坐标
     * @param player Player 玩家实例
     */
    public gameWindow(int x, int y, Player player) throws IOException {
        this.player = player;
        this.x = x;
        this.y = y;

        new MusicPlayer("music/longBGM.wav");

//        MusicPlay myMusicPlay = new MusicPlay(MusicPlay.class.getResource("/sound/BGM.mp3"));
//        myMusicPlay.continuousStart();

//        //设置背景
//        back = (JPanel) frame.getContentPane();
//        bg.setImage(bg.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
//        JLabel b = new JLabel(bg);
//        back.setOpaque(false);
//        back.add(b);
//        layeredPane.add(back, JLayeredPane.DEFAULT_LAYER);

//        try {
//            URL url = new URL("Client/sound/BGM.mp3");
//            try {
//                as = new AudioStream(url.openStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }


        this.frame.setIconImage(log0);

        notice.setForeground(Color.white);
        notice.setFont(font);
        player1_num.setForeground(Color.white);
        player2_num.setForeground(Color.white);
        player1_num.setFont(font2);
        player2_num.setFont(font2);
        chupai.setContentAreaFilled(false);
        buchu.setContentAreaFilled(false);
        chupai.setBorder(null);
        buchu.setBorder(null);
//        chupai_btn.setImage(chupai_btn.getImage().getScaledInstance(80,35,Image.SCALE_DEFAULT));
//        chupai_false_btn.setImage(chupai_false_btn.getImage().getScaledInstance(80,35,Image.SCALE_DEFAULT));
//        buchu_btn.setImage(buchu_btn.getImage().getScaledInstance(80,35,Image.SCALE_DEFAULT));
        buchu.setIcon(buchu_btn);
        chupai.setIcon(chupai_false_btn);
        chupai.addActionListener(e -> {
            isOutCards = true;
            front.remove(btn_group2);
        });
        buchu.addActionListener(e -> {
            if (CompareCard.getInstance().getPlayer().equals("m")) {
                JOptionPane.showMessageDialog(null, "必须出牌");
            } else {
                for (Integer i : card) {
                    labels[i].setLocation(labels[i].getX(), labels[i].getY() + 20);
                }
                card.clear();
                //new MusicPlayer("music/Man_buyao1.wav");
                isOutCards = true;
                front.remove(btn_group2);
            }
        });
        btn_group2.setOpaque(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null, "请完成这一局游戏");

            }
        });
        images[0] = new ImageIcon("Client/img/poker/A.jpg");
        images[1] = new ImageIcon("Client/img/poker/2.jpg");
        images[2] = new ImageIcon("Client/img/poker/3.jpg");
        images[3] = new ImageIcon("Client/img/poker/4.jpg");
        images[4] = new ImageIcon("Client/img/poker/5.jpg");
        images[5] = new ImageIcon("Client/img/poker/6.jpg");
        images[6] = new ImageIcon("Client/img/poker/7.jpg");
        images[7] = new ImageIcon("Client/img/poker/8.jpg");
        images[8] = new ImageIcon("Client/img/poker/9.jpg");
        images[9] = new ImageIcon("Client/img/poker/10.jpg");
        images[10] = new ImageIcon("Client/img/poker/J.jpg");
        images[11] = new ImageIcon("Client/img/poker/Q.jpg");
        images[12] = new ImageIcon("Client/img/poker/K.jpg");
        images[13] = new ImageIcon("Client/img/poker/big.jpg");
        images[14] = new ImageIcon("Client/img/poker/small.jpg");
        for (int i = 0; i < 15; i++) {
            smallImages[i] = new ImageIcon();
            smallImages[i].setImage(images[i].getImage().getScaledInstance(images[i].getIconWidth() * 3 / 4,
                    images[i].getIconHeight() * 3 / 4, Image.SCALE_FAST));
        }
        dipai.setLayout(new GridLayout(1, 11));
        for (int i = 0; i < 4; i++) {
            dipai.add(new JLabel());
        }
        dipai.add(dipai_1);
        dipai.add(dipai_2);
        dipai.add(dipai_3);
        for (int i = 0; i < 4; i++) {
            dipai.add(new JLabel());
        }
        dipai_1.setIcon(poker_back);
        dipai_2.setIcon(poker_back);
        dipai_3.setIcon(poker_back);
        dipai.setOpaque(false);
    }

    /**
     * 显示窗口
     */
    public void showGame() {
        this.frame.setIconImage(log0);
        back = (JPanel) frame.getContentPane();
        bg.setImage(bg.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel b = new JLabel(bg);
        btn_group2.add(chupai);
        btn_group2.add(buchu);
        back.setOpaque(false);
        back.add(b);
//        p1.setLayout(new GridLayout(2, 1));
        p1.add(player1);
        p1.add(player1_num);
//        p2.setLayout(new GridLayout(2, 1));
        p2.add(player2_num);
        p2.add(player2);
        p1.setOpaque(false);
        p2.setOpaque(false);
        layeredPane.add(back, JLayeredPane.DEFAULT_LAYER);
        frame.setBounds(x, y, width, height);
        front.setLayout(new BorderLayout());
        front.setBounds(0, 0, width, height);
        front.setBorder(BorderFactory.createEmptyBorder(0, 10, 160, 30));
        front.add(p1, BorderLayout.WEST);
        front.add(p2, BorderLayout.EAST);
        bujiao.addActionListener(e -> {
            this.qiangdizhu = "0";

        });
        one.addActionListener(e -> this.qiangdizhu = "1");
        two.addActionListener(e -> this.qiangdizhu = "2");
        three.addActionListener(e -> this.qiangdizhu = "3");
        btn_group.setOpaque(false);
        front.add(btn_group, BorderLayout.SOUTH);
        front.add(dipai, BorderLayout.NORTH);
        front.setOpaque(false);
        front.add(notice, BorderLayout.CENTER);
        layeredPane.add(front, JLayeredPane.MODAL_LAYER);
        notice.setText("等待其他玩家选择点数");
        //play();
        frame.setResizable(true);
        frame.setLayeredPane(layeredPane);
        frame.setVisible(true);

    }

    /**
     * @param dianshu
     * @return String
     * @throws InterruptedException 抢地主的前端api
     */
    public String returnDianshu(String dianshu) throws InterruptedException {
        qiangdizhu = "-1";
        notice.setText("请选择你的点数");
        String[] str = dianshu.split("");
        List<String> list = Arrays.asList(str);
        ArrayList<String> arr = new ArrayList<>(list);
        bujiao.setContentAreaFilled(false);
        bujiao.setBorder(null);
        bujiao.setIcon(_bujiao);
        btn_group.add(bujiao);
        if (arr.contains("1")) {
            one.setContentAreaFilled(false);
            one.setBorder(null);
            one.setIcon(_one);
            btn_group.add(one);
        }
        if (arr.contains("2")) {
            two.setContentAreaFilled(false);
            two.setBorder(null);
            two.setIcon(_two);
            btn_group.add(two);
        }
        if (arr.contains("3")) {
            three.setContentAreaFilled(false);
            three.setBorder(null);
            three.setIcon(_three);
            btn_group.add(three);
        }
        frame.invalidate();
        frame.repaint();
        frame.setVisible(true);
        while (this.qiangdizhu.equals("-1")) {
            sleep(10);
        }
        front.remove(btn_group);
        btn_group.remove(bujiao);
        btn_group.remove(one);
        btn_group.remove(two);
        btn_group.remove(three);
        notice.setText("等待其他玩家选择点数");
        return this.qiangdizhu;
    }

    /**
     * 加载底牌的api
     *
     * @param s
     */
    public void addLord(String s) {
        this.frame.setIconImage(log0);
        notice.setText("");
        String[] str = s.split("");
        ///>>>>>>>>>>>>>>  dipai.setText(str[1] + str[2] + str[3]);设置底牌
        addIcon(dipai_1, str[1], this.smallImages);
        addIcon(dipai_2, str[2], this.smallImages);
        addIcon(dipai_3, str[3], this.smallImages);
        if (str[0].equals(player1_id)) {
            player1.setIcon(GG_lord1);
            this.player1_deckNum = 20;
            this.player2_deckNum = 17;
        }
        if (str[0].equals(player2_id)) {
            player2.setIcon(GG_lord2);
            this.player1_deckNum = 17;
            this.player2_deckNum = 20;
        }
        player1_num.setText(player1_deckNum + "张");
        player2_num.setText(player2_deckNum + "张");
    }

    /**
     * 地主给自己加底牌的api
     *
     * @param s
     */
    public void addSelf(String s) {
        this.frame.setIconImage(log0);
        notice.setText("");
        ImageIcon lord = new ImageIcon("Client/img/lord.png");
        JLabel l = new JLabel(lord);
//       >>>>>>>>> dipai.setText(s);设置底牌
        String[] str = s.split("");
        addIcon(dipai_1, str[0], this.smallImages);
        addIcon(dipai_2, str[1], this.smallImages);
        addIcon(dipai_3, str[2], this.smallImages);
        l.setBounds(0, height - lord.getIconHeight() - 50, lord.getIconWidth(), lord.getIconHeight());
        l.setOpaque(false);
        layeredPane.add(l);
        layeredPane.setLayer(l, 201);
        //////////
        player1_deckNum = 17;
        player2_deckNum = 17;
        player1_num.setText(player1_deckNum + "张");
        player2_num.setText(player2_deckNum + "张");

    }

    /**
     * 更新记录玩家编号
     *
     * @param dianshu
     */
    public void refresh(String dianshu) {
        this.frame.setIconImage(log0);
        String[] str = dianshu.split("");
        if (this.player1_id.equals("-1")) {
            this.player1_id = str[0];
            if (str[1].equals("0"))
                player1_num.setText("不叫");
            else if (str[1].equals("1"))
                player1_num.setText("1分");
            else if (str[1].equals("2"))
                player1_num.setText("2分");
            else
                player1_num.setText("3分");
            return;
        }
        this.player2_id = str[0];
        if (str[1].equals("0"))
            player2_num.setText("不叫");
        else if (str[1].equals("1"))
            player2_num.setText("1分");
        else if (str[1].equals("2"))
            player2_num.setText("2分");
        else
            player2_num.setText("3分");
    }

    /**
     * 地主胜利画面api
     *
     * @throws InterruptedException
     */
    public void showLordWin() throws InterruptedException {
        this.frame.setIconImage(log0);
        ImageIcon lordWin = new ImageIcon("Client/img/lord_win.png");
        JLabel label = new JLabel(lordWin);
        label.setBounds(width / 2 - lordWin.getIconWidth() / 2, height / 2 - lordWin.getIconHeight() / 2, lordWin.getIconWidth(), lordWin.getIconHeight());
        layeredPane.add(label);
        layeredPane.setLayer(label, 290);
        sleep(3000);
    }

    /**
     * 地主失败画面
     *
     * @throws InterruptedException
     */
    public void showLordLose() throws InterruptedException {
        this.frame.setIconImage(log0);
        ImageIcon lordLose = new ImageIcon("Client/img/lord_lose.png");
        JLabel label = new JLabel(lordLose);
        label.setBounds(width / 2 - lordLose.getIconWidth() / 2, height / 2 - lordLose.getIconHeight() / 2, lordLose.getIconWidth(), lordLose.getIconHeight());
        layeredPane.add(label);
        layeredPane.setLayer(label, 290);
        sleep(3000);
    }

    /**
     * 农民胜利画面
     *
     * @throws InterruptedException
     */
    public void showFarmerWin() throws InterruptedException {
        this.frame.setIconImage(log0);
        ImageIcon farmerWin = new ImageIcon("Client/img/farmer_win.png");
        JLabel label = new JLabel(farmerWin);
        label.setBounds(width / 2 - farmerWin.getIconWidth() / 2, height / 2 - farmerWin.getIconHeight() / 2, farmerWin.getIconWidth(), farmerWin.getIconHeight());
        layeredPane.add(label);
        layeredPane.setLayer(label, 290);
        sleep(3000);
    }

    /**
     * 农民失败画面
     *
     * @throws InterruptedException
     */
    public void showFarmerLose() throws InterruptedException {
        this.frame.setIconImage(log0);
        ImageIcon farmerLose = new ImageIcon("Client/img/farmer_lose.png");
        JLabel label = new JLabel(farmerLose);
        label.setBounds(width / 2 - farmerLose.getIconWidth() / 2, height / 2 - farmerLose.getIconHeight() / 2, farmerLose.getIconWidth(), farmerLose.getIconHeight());
        layeredPane.add(label);
        layeredPane.setLayer(label, 290);
        sleep(3000);
    }

    public String outCards() throws InterruptedException {


        chupai.setEnabled(false);
        this.front.add(btn_group2, BorderLayout.SOUTH);
        frame.validate();
        while (!this.isOutCards) {
            sleep(10);
        }
        this.isOutCards = false;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.card == null)
            return "";
        for (Integer i : this.card) {
            stringBuilder.append(player.getDeck().get(i));
        }
        card.clear();
        for (JLabel label : labels) {
            layeredPane.remove(label);
        }


        frame.invalidate();
        frame.repaint();
        frame.setVisible(true);
        return stringBuilder.toString();
    }

    JLabel[] labels = new JLabel[20];

    public void printCards() {
        this.frame.setIconImage(log0);
        for (int i = 0; i < last_haveCards; i++) {
            layeredPane.remove(labels[i]);
        }
        this.last_haveCards = player.getDeck().size();
        for (int i = 0; i < 20; i++) {
            JLabel l = new JLabel();
            labels[i] = l;
        }
        int w = images[0].getIconWidth();
        int h = images[0].getIconWidth();
        int num = player.getDeck().size();
        int total_w = (num * w + 2 * w) / 3;
        int floor = 250;
        for (int i = 0; i < num; i++) {
            addIcon(labels[i], player.getDeck().get(i), this.images);
            labels[i].setBounds(width / 2 - total_w / 2 + i * w / 3, height / 2 + 70, w, h + 30);
            layeredPane.add(labels[i]);
            int finalI = i;
            labels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!card.contains(finalI)) {
                        card.add(finalI);
                        labels[finalI].setLocation(labels[finalI].getX(), labels[finalI].getY() - 20);
                    } else {
                        card.remove((Integer) finalI);
                        labels[finalI].setLocation(labels[finalI].getX(), labels[finalI].getY() + 20);
                    }
                    CompareCard compareCard = CompareCard.getInstance();
                    StringBuilder stringBuilder = new StringBuilder();
                    if (card.size() == 0) {
                        chupai.setIcon(chupai_false_btn);
                        chupai.setEnabled(false);
                    } else {
                        for (Integer i : card) {
                            String s = player.getDeck().get(i);
                            stringBuilder.append(s);
                        }
                        if (compareCard.compare(stringBuilder.toString())) {
                            chupai.setIcon(chupai_btn);
                            chupai.setEnabled(true);
                        } else {
                            chupai.setIcon(chupai_false_btn);
                            chupai.setEnabled(false);
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            layeredPane.setLayer(labels[i], floor++);
            frame.invalidate();
            frame.repaint();
            frame.setVisible(true);
        }
    }

    JLabel[] playedCards = new JLabel[20];

    public void printPlayedCards(String s) throws InterruptedException {
        this.frame.setIconImage(log0);
        String[] str = s.split("");
        if (str.length == 1) {
            pass(s);
            return;
        }
        for (int i = 0; i < this.last_cardNum; i++) {
            layeredPane.remove(playedCards[i]);
        }
        String p = str[0];
        List<String> list = Arrays.asList(s.substring(1, str.length).split(""));
        ArrayList<String> cards = new ArrayList<>(list);
        int bias = 130;
        int w = images[0].getIconWidth() * 3 / 4;
        int h = images[0].getIconWidth() * 3 / 4;
        int num = cards.size();
        System.out.println(num);//debug
        this.last_cardNum = num;
        int total_w = (num * w + 2 * w) / 3;
        int floor = 270;
        for (int i = 0; i < 20; i++) {
            playedCards[i] = new JLabel();
        }
        for (int i = 0; i < num; i++) {
            addIcon(playedCards[i], cards.get(i), this.smallImages);
            if (p.equals("m")) {
                playedCards[i].setBounds(width / 2 - total_w / 2 + i * w / 3, height / 2 - 65, w, h + 30);




            }
            else if (p.equals(player1_id)) {
                playedCards[i].setBounds(bias + i * w / 3, height / 2 - 65, w, h + 30);
                player1_deckNum -= 1;
                player1_num.setText(player1_deckNum + "张");

                //音效
//                double rm = (int)(3*Math.random());
//                if (rm==0)new MusicPlayer("music/Man_dani1.wav");
//                else if (rm==1)new MusicPlayer("music/Man_dani2.wav");
//                else if (rm==2)new MusicPlayer("music/Man_dani3.wav");


            } else {
                playedCards[i].setBounds(width - bias - total_w + i * w / 3, height / 2 - 65, w, h + 30);
                player2_deckNum -= 1;
                player2_num.setText(player2_deckNum + "张");

//                double rm = (int)(3*Math.random());
//                if (rm==0)new MusicPlayer("music/Man_dani1.wav");
//                else if (rm==1)new MusicPlayer("music/Man_dani2.wav");
//                else if (rm==2)new MusicPlayer("music/Man_dani3.wav");


            }
            layeredPane.add(playedCards[i]);
            layeredPane.setLayer(playedCards[i], floor++);
        }
        frame.invalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public void pass(String s) throws InterruptedException {
        this.frame.setIconImage(log0);
        if (s.equals(player1_id)) {
            player1_num.setIcon(new ImageIcon("Client/img/pass.png"));
            sleep(1000);
            player1_num.setIcon(null);
        } else if (s.equals(player2_id)) {
            player2_num.setIcon(new ImageIcon("Client/img/pass.png"));
            sleep(1000);
            player2_num.setIcon(null);
        }
        new MusicPlayer("music/Man_buyao4.wav");

    }

    //根据牌添加图标
    public void addIcon(JLabel label, String card, ImageIcon[] images) {
        this.frame.setIconImage(log0);
        if (card.equals("A"))
            label.setIcon(images[0]);
        else if (card.equals("2"))
            label.setIcon(images[1]);
        else if (card.equals("3"))
            label.setIcon(images[2]);
        else if (card.equals("4"))
            label.setIcon(images[3]);
        else if (card.equals("5"))
            label.setIcon(images[4]);
        else if (card.equals("6"))
            label.setIcon(images[5]);
        else if (card.equals("7"))
            label.setIcon(images[6]);
        else if (card.equals("8"))
            label.setIcon(images[7]);
        else if (card.equals("9"))
            label.setIcon(images[8]);
        else if (card.equals("X"))
            label.setIcon(images[9]);
        else if (card.equals("J"))
            label.setIcon(images[10]);
        else if (card.equals("Q"))
            label.setIcon(images[11]);
        else if (card.equals("K"))
            label.setIcon(images[12]);
        else if (card.equals("w"))
            label.setIcon(images[13]);
        else
            label.setIcon(images[14]);
    }

    public void close()
    {
        //this.continuousStop();
        frame.setVisible(false);
    }

    public int[] getLocate() {
        this.frame.setIconImage(log0);
        Rectangle locate = this.frame.getBounds();
        int[] point = new int[2];
        point[0] = locate.x;
        point[1] = locate.y;
        return point;
    }
}
