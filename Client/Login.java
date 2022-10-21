package Client;

import com.mysql.jdbc.Statement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;



public class Login extends JFrame{

    private JPanel contentPane;
    private JButton btn1,btn2,btn3;
    private JTextField userName;
    private JPasswordField password;
    private JLabel label1,label2;


    private int LOGIN_WIDTH=360;
    private int LOGIN_HEIGTH=360;

    int x = 450;//窗口默认位置
    int y = 210;

     ImageIcon bg = new ImageIcon("Client/img/start_back2.jpg");
    ImageIcon log = new ImageIcon("Client/img/log.png");
    Image log0 = log.getImage();

    ImageIcon user = new ImageIcon("Client/img/user.png");
    ImageIcon btn11 =new ImageIcon("Client/img/btn11.png");
    ImageIcon regist = new ImageIcon("Client/img/regier.png");
    ImageIcon regier11 = new ImageIcon("Client/img/regier11.png");
    ImageIcon regier1 =new ImageIcon("Client/img/regier1.png");
    ImageIcon password1 = new ImageIcon("Client/img/password.png");


    JLayeredPane layeredPane = new JLayeredPane();
    JLabel lbBg = new JLabel(bg);
    Connection conn;
    Statement stam;


    public Login(int[] a) {
        this.getLayeredPane().add(lbBg,new Integer(Integer.MIN_VALUE));
//获取frame的顶层容器,并设置为透明
//        JPanel j=(JPanel)this.getContentPane();
//        j.setOpaque(false);

        setTitle("欢乐斗地主");  //设置窗体标题
        setBounds(100, 50, LOGIN_WIDTH, LOGIN_HEIGTH	);  //设置窗体坐标以及打下
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //设置窗体可关闭
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setResizable(false);  //设置窗体大小不可以改变


        lbBg.setBounds(0, 0,LOGIN_WIDTH, LOGIN_HEIGTH);
        this.getContentPane().add(lbBg);

        //设置背景
//        ImageIcon bgImg = new ImageIcon("Client/img/bg1.png");
//        bgImg.setImage(bgImg.getImage().getScaledInstance(360,360,0));
//        JLabel label = new JLabel(bgImg);
//        label.setBounds(0,0,360,360);
//        this.getLayeredPane().add(label, new Integer(0));
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        setIconImage(log0);

            //设置窗体可见
        //设置窗体标题图标
//        setIconImage(
//                Toolkit.getDefaultToolkit().getImage(Login.class.getResource("Client/img/log.png"))
//        );
        /*
         * 添加一个面板容器到窗体中
         */
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //账号标签
        label1=new JLabel("");
        label1.setBounds(70,50, 75, 75);
        user.setImage(user.getImage().getScaledInstance(50,50,0));
        label1.setIcon(user);


        // label1.setIcon(new ImageIcon(Login.class.getResource("img/user.png")));
        contentPane.add(label1);


        //密码标签
        label2=new JLabel("");
        label2.setBounds(70, 125, 50, 50);
        password1.setImage(password1.getImage().getScaledInstance(50,50,0));
        label2.setIcon(password1);
        //label2.setIcon(new ImageIcon(Login.class.getResource("/images/psw.png")));
        contentPane.add(label2);

        //账号输入框
        userName=new JTextField();
        userName.setBounds(139, 80, 161, 25);
        contentPane.add(userName);

        //密码输入框
        password=new JPasswordField();
        password.setBounds(139, 140, 161, 25);

        contentPane.add(password);


        //按钮—登录
        btn1=new JButton("");
        btn1.setBounds(95, 210, 60, 28);
        //btn1.setIcon(new ImageIcon(Login.class.getResource("Client/img/btn1.png")));
        btn1.setIcon(btn11);
        btn1.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 0));
        btn1.setContentAreaFilled(false);
        btn1.setOpaque(false);
        btn1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==btn1) {
                    try {
                        conn=jdbcUtil.getConnection();//获取数据库连接
                        stam= (Statement) conn.createStatement();  //创建sql语句执行对象
                        //编写sql语句
                        String sql="select * from user where username='"+userName.getText()+"'  and password='"+password.getText()+"'     ";
                        //执行sql语句
                        ResultSet rs=stam.executeQuery(sql);
                        if(rs.next()) {
                            dispose();//关闭当前窗口
                            a[0]=1;
//                            try {
//                                new client();
//                            } catch (IOException ioException) {
//                                ioException.printStackTrace();
//                            } catch (InterruptedException interruptedException) {
//                                interruptedException.printStackTrace();
//                            }
                            //        try {
//            String ip = "106.13.40.227";//服务器ip
//            String ip = "192.168.43.40";//本地ip
//            InetSocketAddress socketAddress = new InetSocketAddress(ip, 8888);
//            this.socket.connect(socketAddress);
//            System.out.println("接入成功");//前端可以忽视
//            this.in = new DataInputStream(this.socket.getInputStream());
//            this.out = new DataOutputStream(this.socket.getOutputStream());
//        } catch (IOException e) {
//            System.out.println("断开连接");//前端可以忽视
//        }


//                            Player player = new Player();
//                            while (true) {
//                                player.home();
//                                player.playGame();
//                            }
                        }
                        else
                        {JOptionPane.showMessageDialog(null,
                                "您的账号不存在或者密码错误", "提示", JOptionPane.INFORMATION_MESSAGE);}

                    }catch (Exception e0) {
                        e0.printStackTrace();
                    }finally {
                        jdbcUtil.result(conn, stam);

                    }
                }
            }
        });
        contentPane.add(btn1);
        //按钮—退出
        btn2=new JButton("");
        btn2.setBounds(210, 210, 60, 28);
        btn2.setIcon(regier1);
        btn2.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 0));
        btn2.setContentAreaFilled(false);
        btn2.setOpaque(false);

        //btn2.setIcon( new ImageIcon(Login.class.getResource("/images/exit.png")));
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==btn2) {
                    dispose();
                }
            }
        });


        contentPane.add(btn2);
        //按钮-注册
        btn3=new JButton("");
        //btn3.setBounds(95,240,200, 23);
        btn3.setBounds(152, 210, 60, 28);
        regier11.setImage(regier11.getImage().getScaledInstance(56,28,0));
        btn3.setIcon(regier11);
        btn3.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 0));
        btn3.setContentAreaFilled(false);
        btn3.setOpaque(false);
        //btn3.setIcon(new ImageIcon(Login.class.getResource("/images/regier.png")));
        btn3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//        startWindow = new StartWindow(this.x, this.y);
//        startWindow.show(this);
//        while (this.inStartWindow) {//这循环里什么都不放的话会有bug
//            try {
//                refreshWindowLocate(startWindow.getLocate());//更新窗口位置
//                sleep(10);
//            } catch (Exception e) {
//                System.exit(0);
//            }
//            ;
//        }
                dispose();//关闭登录窗体
                new Register().addMan(); // 打开注册窗体

            }
        });

        //加入背景
        JLabel backgroundLabel = new JLabel(bg);
        layeredPane.add(backgroundLabel,Integer.MIN_VALUE);
        layeredPane.setOpaque(false);
//        JPanel jp = (JPanel) this.getContentPane();
//        jp.setOpaque(false);


        contentPane.add(btn3);
       contentPane.setOpaque(false);
//        JPanel jp = (JPanel) this.getContentPane();
//        jp.setOpaque(false);
      //add(contentPane);
        setSize(this.LOGIN_WIDTH,this.LOGIN_HEIGTH);
        setVisible(true);

    }
//    public static void main(String[] args) {
//        new Login();
//    }

}

//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
////import com.mysql.jdbc.;
///**
// * 用户登录
// * @author 大南海
// *
// */
//public class Login extends JFrame{
//
//    private JPanel contentPane;
//    private JButton btn1,btn2,btn3;
//    private JTextField userName;
//    private JPasswordField password;
//    private JLabel label1,label2;
//
//    private int LOGIN_WIDTH=360;
//    private int LOGIN_HEIGTH=350;
//
//
//    Connection conn;
//    PreparedStatement stam;
//
//    public Login() {
//
//        setTitle("欢乐斗地主");  //设置窗体标题
//        setBounds(100, 50, LOGIN_WIDTH, LOGIN_HEIGTH	);  //设置窗体坐标以及打下
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //设置窗体可关闭
//        setResizable(false);  //设置窗体大小不可以改变
//        setVisible(true);    //设置窗体可见
//        //设置窗体标题图标
////        setIconImage(
////                Toolkit.getDefaultToolkit().getImage(Login.class.getResource("Client/img/log.png"))
////        );
//        /**
//         * 添加一个面板容器到窗体中
//         */
//        contentPane = new JPanel();
//        contentPane.setBackground(Color.WHITE);
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//        //账号标签
//        label1=new JLabel("");
//        label1.setBounds(80,76, 54, 28);
//        //label1.setIcon(new ImageIcon(Login.class.getResource("Client/img/user.png")));
//        //label1.setIcon(new ImageIcon(Login.class.getResource("Client/img/user.png")));
//        contentPane.add(label1);
//
//
//        //密码标签
//        label2=new JLabel("");
//        label2.setBounds(80, 135, 54, 28);
//        //label2.setIcon(new ImageIcon(Login.class.getResource("Client/img/password.png")));
//        //label2.setIcon(new ImageIcon(Login.class.getResource("Client/img/password.png")));
//        contentPane.add(label2);
//
//        //账号输入框
//        userName=new JTextField();
//        userName.setBounds(139, 80, 161, 25);
//        contentPane.add(userName);
//
//        //密码输入框
//        password=new JPasswordField();
//        password.setBounds(139, 140, 161, 25);
//
//        contentPane.add(password);
//
//
//        //按钮—登录
//        btn1=new JButton("登   录");
//        btn1.setBounds(95, 210, 80, 23);
//        //btn1.setIcon(new ImageIcon(Login.class.getResource("Client/img/btn1.png")));
//        //btn1.setIcon(new ImageIcon(Objects.requireNonNull(Login.class.getResource("Client/img/btn1.png"))));
//        btn1.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(e.getSource()==btn1) {
//                    try {
//                        conn=jdbcUtil.getConnection();//获取数据库连接
//                        stam= (PreparedStatement) conn.createStatement();  //创建sql语句执行对象
//                        //编写sql语句
//                        String sql="select * from user where username='"+userName.getText()+"'  and password='"+password.getText()+"'     ";
//                        //执行sql语句
//                        ResultSet rs=stam.executeQuery(sql);
//                        if(rs.next()) {
//                            dispose();//关闭当前窗口
//                            client c = new client();
//                            c.Main();
//                        }
//                    }catch (Exception e0) {
//                        e0.printStackTrace();
//                    }finally {
//                        jdbcUtil.result(conn, stam);
//
//                    }
//                }
//            }
//        });
//        contentPane.add(btn1);
//        //按钮—退出
//        btn2=new JButton("退  出");
//        btn2.setBounds(210, 210, 80, 23);
//        //btn2.setIcon( new ImageIcon(Login.class.getResource("Client/img/exit1.png")));
//        //btn2.setIcon( new ImageIcon(Objects.requireNonNull(Login.class.getResource("Client/img/exit1.png"))));
//       //ImageIcon exit1 = new ImageIcon("Client/img/exit1.png");
//       //btn2.setIcon(exit1);
//        btn2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(e.getSource()==btn2) {
//                    dispose();
//                }
//            }
//        });
//
//
//        contentPane.add(btn2);
//        //按钮-注册
//        btn3=new JButton("注        册");
//        btn3.setBounds(95,240,200, 23);
//        //ImageIcon bg = new ImageIcon("Client/img/desk.jpg");
//        //btn3.setIcon(new ImageIcon(Login.class.getResource("Client/img/regier.png")));
//        //btn3.setIcon(new ImageIcon(Objects.requireNonNull(Login.class.getResource("Client/img/regier.png"))));
//        //ImageIcon regier = new ImageIcon("Client/img/regier.png");
//        //btn3.setIcon(regier);
//        btn3.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                dispose();//关闭登录窗体
//                new Register().addMan(); // 打开注册窗体
//
//            }
//        });
//        contentPane.add(btn3);
//
//    }
//
//    public static void main(String[] args) {
//        new Login();
//    }
//
//
//}