package Client;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;


public class Register  extends JFrame {

    private int LOGIN_WIDTH=360;
    private int LOGIN_HEIGTH=360;
    private JPanel contentPane;
    private JTextField userName;
    private JPasswordField password;
    private JButton btn3,btn4;
    private JLabel label3,label4;
    Connection conn;
    Statement stam;

    ImageIcon log = new ImageIcon("Client/img/log.png");
    Image log0 = log.getImage();
    ImageIcon back = new ImageIcon("Client/img/start.jpg");
    ImageIcon user = new ImageIcon("Client/img/user.png");
    ImageIcon password1 = new ImageIcon("Client/img/password.png");
    ImageIcon regier11 = new ImageIcon("Client/img/regier11.png");
    ImageIcon regier1 = new ImageIcon("Client/img/regier1.png");
    public void addMan() {

        setTitle("注册");

        setBounds(100, 50, LOGIN_WIDTH, LOGIN_HEIGTH	);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(log0);

        setVisible(true);

        //设置窗体标题图标
//        setIconImage(
//                Toolkit.getDefaultToolkit().getImage(Login.class.getResource("Client/img/log.png"))
//        );
        /**
         * 添加一个面板容器到窗体中
         */
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //账号标签
          label3=new JLabel("");
//        label3.setBounds(80,76, 54, 28);
          label3=new JLabel("");
        label3.setBounds(70,50, 75, 75);
        user.setImage(user.getImage().getScaledInstance(50,50,0));
        label3.setIcon(user);

        //ImageIcon GG = new ImageIcon("Client/img/GG.png");player1.setIcon(GG);
        //ImageIcon user = new ImageIcon("Client/img/user.png");
        //label3.setIcon(user);
        //label3.setIcon(new ImageIcon(Login.class.getResource("Client/img/user.png")));
        contentPane.add(label3);


        //密码标签
        label4=new JLabel("");
        label4.setBounds(70, 125, 50, 50);
        password1.setImage(password1.getImage().getScaledInstance(50,50,0));
        label4.setIcon(password1);
        //label2.setIcon(new ImageIcon(Login.class.getResource("/images/psw.png")));
        contentPane.add(label4);

        //label4.setIcon(new ImageIcon(Login.class.getResource("Client/img/password.png")));
        //ImageIcon password1 = new ImageIcon("Client/img/password.png") ;
        //label4.setIcon(password1);

        contentPane.add(label4);

        //账号输入框
        userName=new JTextField();
        userName.setBounds(139, 80, 161, 25);
        contentPane.add(userName);

        //密码输入框
        password=new JPasswordField();
        password.setBounds(139, 140, 161, 25);

        contentPane.add(password);

        btn3=new JButton("注   册");
        //btn3.setBounds(95, 210, 80, 23);
        btn3.setBounds(152, 210, 60, 28);
        regier11.setImage(regier11.getImage().getScaledInstance(56,28,0));
        btn3.setIcon(regier11);
        btn3.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 0));
        btn3.setContentAreaFilled(false);
        btn3.setOpaque(false);
        //btn3.setIcon(new ImageIcon(Login.class.getResource("Client/img/insist.png")));
       //ImageIcon insist = new ImageIcon("Client/img/insist.png");
       //btn3.setIcon(insist);
        btn3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getSource()==btn3) {
                    try {

                        //加载数据库驱动
                        conn=jdbcUtil.getConnection();
                        //创建执行sql语句的对象
                        stam=conn.createStatement();
                        //编写sql语句
                        String sql="insert into user values('"+userName.getText()+"','"+password.getText()+"')";
                        //执行sql语句
                        stam.execute(sql);
                        JOptionPane.showMessageDialog(null, "注册成功!");
                        dispose();  //关闭注册窗体
                         int[] a = {0};
                       new Login(a);


                    }catch (Exception e1) {
                        e1.printStackTrace();
                    }finally {
                        jdbcUtil.result(conn, stam);
                    }

                }

            }
        });

        contentPane.add(btn3);

        btn4=new JButton("");
        //btn4.setBounds(210, 210, 80, 23);
        btn4.setBounds(210, 210, 60, 28);
        btn4.setIcon(regier1);
        btn4.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 0));
        btn4.setContentAreaFilled(false);
        btn4.setOpaque(false);
        //btn4.setIcon( new ImageIcon(Login.class.getResource("Client/img/exit1.png")));
        //ImageIcon exit1 =new ImageIcon("Client/img/exit1.png");
        //btn4.setIcon(exit1);
        btn4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getSource()==btn4) {

                    dispose();
                }



            }
        });


        contentPane.add(btn4);

    }


}
