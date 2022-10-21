package Client;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;


public class Player {
    private DataInputStream in;//输入流
    private DataOutputStream out;//输出流
    private Socket socket = new Socket();//客户端的套接字
    private ArrayList<String> deck = new ArrayList<>();//自己的牌
    private String serverMessage;//服务器消息
    private boolean isLord;//自己是不是地主
    private String whoIsLord;//谁是地主
    private String state;//准备状态
    private boolean isInGame;//判断是否从游戏里出来
    private String roomInfo;//房间内玩家准备情况
    private String roomNum;//大厅里房间人数情况
    private String selectRoom;//玩家房间选择

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    StartWindow startWindow;
    RoomWindow roomWindow;
    gameWindow gameWindow;
    private boolean inStartWindow = true;//开始页面
    int x = 450;//窗口默认位置
    int y = 210;

    public void refreshWindowLocate(int[] point) {
        if (point[0] != this.x)
            this.x = point[0];
        if (point[1] != this.y)
            this.y = point[1];
    }

    public void setInStartWindow(boolean inStartWindow) {
        this.inStartWindow = inStartWindow;
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public Player() {
        try {
            String ip = "59.110.61.120";//服务器ip
            //String ip = "192.168.43.11";//本地ip
            //String ip ="10.36.154.108";
            InetSocketAddress socketAddress = new InetSocketAddress(ip, 8888);
            this.socket.connect(socketAddress);
            System.out.println("接入成功");//前端可以忽视
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("断开连接");//前端可以忽视
        }
        this.isInGame = false;
        this.selectRoom = "-1";
    }
//<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //游戏选择房间

    /**
     * @return 没有返回值
     * @title: home
     * @description: 选择房间方法, 玩家选择房间, 如果人满, 窗口提示full, 能进入显示ok
     */
    public void home() throws IOException {

//                try {
//            //String ip = "106.13.40.227";//服务器ip
//            String ip = "192.168.43.40";//本地ip
//            InetSocketAddress socketAddress = new InetSocketAddress(ip, 8888);//8888自动
//            this.socket.connect(socketAddress);
//            System.out.println("接入成功");//前端可以忽视
//            this.in = new DataInputStream(this.socket.getInputStream());
//            this.out = new DataOutputStream(this.socket.getOutputStream());
//        } catch (IOException e) {
//            System.out.println("断开连接");//前端可以忽视
//        }

        startWindow = new StartWindow(this.x, this.y);
        startWindow.show(this);
        while (this.inStartWindow) {//这循环里什么都不放的话会有bug
            try {
                refreshWindowLocate(startWindow.getLocate());//更新窗口位置
                sleep(10);
            } catch (Exception e) {
                System.exit(0);
            }
            ;
        }
        startWindow.close();
        while (true) {
            if (!isInGame) {
                System.out.println("大厅");//前端直接显示大厅
                System.out.println("选择房间");
                this.selectRoom = "-1";
                SelectRoomWindow selectRoomWindow = new SelectRoomWindow(this.x, this.y);
                selectRoomWindow.showSelectRoom(this);//调试选择房间窗口
                serverMessage = in.readUTF();
                out.writeUTF(this.selectRoom);
                selectRoomWindow.refresh(serverMessage);
                while (true) {
                    refreshWindowLocate(selectRoomWindow.getLocate());//更新位置
                    serverMessage = in.readUTF();//服务器发过来的房间信息
                    if (serverMessage.equals("full")) {//前端弹出提示
                        System.out.println("房间人数已满");
                        JOptionPane.showMessageDialog(null, "房间人数已满");
                        this.selectRoom = "-1";
                    }
                    if (serverMessage.equals("ok")) {//前端在后面显示进入房间
                        out.writeUTF("1");
                        selectRoomWindow.close();//关闭选择房间窗口
                        break;
                    }
                    storeRoomNum(serverMessage, selectRoomWindow);//储存大厅房间状态(人数)
                    out.writeUTF(this.selectRoom);

                }
            }
            if (gameReady())//游戏准备环节
                break;
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return 如果返回true表示开始游戏, false表示退出房间, 返回大厅选择房间
     * @title: gameReady 玩家在房间里准备环节
     * @description: player属性state, isInGame,
     * state为玩家状态,默认unready,ready为准备,quit为退出房间,不断传回服务器
     */
    public boolean gameReady() throws IOException {
        roomWindow = new RoomWindow(this.x, this.y);
        roomWindow.show(this);
        this.isInGame = false;//退出游戏后返回准备
        // 阶段,可以退出房间到大厅或继续准备
        this.state = "unready";//按钮切换ready,unready,quit
        String message;
        while (true) {
            refreshWindowLocate(roomWindow.getLocate());//更新位置
            message = in.readUTF();
            if (message.startsWith("ready?")) {//例1020  0没人 1有 2准备
                String[] s = message.split("\\?");
                storeRoomInfo(s[1], roomWindow);//储存房间状态,并修改
                out.writeUTF(state);
            }
            if (message.equals("start")) {
                out.writeUTF("start");
                System.out.println("start");
                roomWindow.close();
                return true;
            }
            if (message.equals("quit")) {
                out.writeUTF("quit");
                System.out.println("退出房间");

                roomWindow.close();//关闭调试窗口
                return false;
            }
        }
    }

    /**
     * @return 无
     * @title: playGame 玩家游戏环节
     * @description: 玩家进行抢地主, 出牌阶段
     */
    //游戏局内
    public void playGame() throws IOException, InterruptedException {
        do {
            this.isInGame = true;//游戏局内,设为true,游戏单局结束后直接到准备阶段
            //获取手牌
            dealCards();
        } while (!qiangdizhu());//抢地主环节
        //开始出牌
        gameRound();
    }

    /**
     * @return 无
     * @title: dealCards
     * @description: 玩家游戏环节里获取手牌方法
     */
    //获取手牌
    public void dealCards() throws IOException {
        String str = message();//服务器传过来的开始游戏信息
        System.out.println(str);
        List<String> result = Arrays.asList(message().split(""));//message里是服务器传过来的手牌信息
        deck = new ArrayList<>(result);//手牌
        sortDeck();//排序
        //获取自己的牌
        System.out.println("你的牌为\n" + deck.toString());//前端显示手牌
    }

    /**
     * @return Boolean true表示地主产生,进入出牌环节;false表示都不叫地主,重新发牌
     * @title: qiangdizhu
     * @description: 玩家游戏环节里抢地主方法
     */
    public boolean qiangdizhu() throws IOException, InterruptedException {
        System.out.println("抢地主");//前端显示抢地主
        gameWindow = new gameWindow(this.x, this.y, this);
        gameWindow.showGame();
        gameWindow.printCards();
        int bujiao = 0;
        for (int i = 0; i < 3; i++) {
            refreshWindowLocate(gameWindow.getLocate());
            serverMessage = message();
            if (serverMessage.equals("抢地主")) {
                serverMessage = in.readUTF();
                String s = gameWindow.returnDianshu(serverMessage);
                //前端改成按钮传入,需要设置阻塞,建议在前端方法里写入while死循环,选择了才返回,选择抢地主的点数
                if (s.equals("0")) {//后面的通过scanner方法读入的都需要阻塞,都建议使用while死循环
                    bujiao++;
                }
                out.writeUTF(s);
                System.out.println("你抢了" + s);//
            } else {
                gameWindow.refresh(serverMessage);//显示其他玩家抢的点数
                if (serverMessage.charAt(1) == '0') {
                    bujiao++;
                }
            }
        }
        if (bujiao == 3) {//三人都不叫,返回false ,重新发牌
            gameWindow.close();
            return false;
        }
        serverMessage = message();
        whoIsLord = serverMessage;//储存地主信息
        if (serverMessage.equals("you")) {
            System.out.println("你是地主");//前端显示自己为地主
            isLord = true;
            //添加地主三张牌
            String c;
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                c = message();
                deck.add(c);
                s.append(c);
            }
            gameWindow.addSelf(s.toString());
            //前端地主显示三张地主牌传入builder.toString
        } else {
            System.out.println(serverMessage);//显示地主,加上地主的三张牌 例2AAA,前端显示
            isLord = false;
            //服务器发送地主的三张牌
            //界面显示地主牌
            whoIsLord = serverMessage.charAt(0) + "";
            gameWindow.addLord(serverMessage);//前端更新
        }
        sortDeck();
        gameWindow.printCards();//显示手牌
        System.out.println("你的手牌为" + deck.toString());//显示自己的手牌
        return true;
    }

    /**
     * @return 无
     * @title: gameRound
     * @description: 玩家游戏环节里出牌循环方法, 比较上家牌, 能吃则出牌, 不能在重新选择
     */
    public void gameRound() throws IOException, InterruptedException {
        while (true) {
            serverMessage = in.readUTF();
            if (serverMessage.equals("请你出牌")) {
                System.out.println("请你出牌");//前端提示玩家出牌
                String str;
//                str = client.scanner.nextLine();//测试,记得删除
//                while (true) {//测试可把while注释,上面取消注释,可以直接把牌出完
//                    str = client.scanner.nextLine();//前端设置为while死循环,可以将这个循环放到界面方法里面
//                    if (str.equals("")) {
//                        if (CompareCard.getInstance().getPlayer().equals("m")) {
//                            System.out.println("必须出牌");//前端提示玩家必须出牌
//                            continue;
//                        } else
//                            break;
//                    }
//                    if (CompareCard.getInstance().compare(str))
//                        break;
//                    System.out.println("吃不了,请重新出牌");//这个可以设置成出牌按钮灰色
//                }
                //出牌
                str = gameWindow.outCards();
                out.writeUTF(str);//str表示出的牌,
                String[] s = str.split("");
                for (String s1 : s) {
                    deck.remove(s1);
                }
                gameWindow.printCards();
                System.out.println("你出了" + str + "牌");//前端显示玩家出的牌
                gameWindow.printPlayedCards("m" + str);//显示出牌
                CompareCard.getInstance().setCompareCard("m" + str);//出牌记录下来
                System.out.println("你现在的牌为" + deck.toString());
            } else if (serverMessage.equals("游戏结束")) {//服务器返回结束信号
                out.writeUTF("1");
                serverMessage = message();
                if (serverMessage.equals(whoIsLord)) {
                    if (isLord) {
                        System.out.println("地主赢了");//前端
                        gameWindow.showLordWin();
                    } else {
                        System.out.println("农民输了");
                        gameWindow.showFarmerLose();
                    }
                } else {
                    if (isLord) {
                        System.out.println("地主输了");
                        gameWindow.showLordLose();
                    } else {
                        System.out.println("农民赢了");
                        gameWindow.showFarmerWin();
                    }
                }
                gameWindow.close();
                break;
            } else {
                CompareCard.getInstance().setCompareCard(serverMessage);//记录其他玩家出牌
                gameWindow.printPlayedCards(serverMessage);
                System.out.println(serverMessage);//前端处理其他玩家出牌,第一个为玩家序号,后面是玩家出的牌
                out.writeUTF("1");
            }
        }
    }

    /**
     * @return String 服务器发送的消息
     * @title: message
     * @description: 封装的客户端接收服务器的消息
     */
    public String message() throws IOException {
        String str = this.in.readUTF();
        this.out.writeUTF("message");
        return str;
    }

    /**
     * @title: comparator
     * @description: 自定义的斗地主卡牌排序的比较器
     */
    //自定义比较大小
    public static Comparator<String> comparator = (s1, s2) -> {
        if (s1.equals("3")) {
            if (s2.equals("3"))
                return 0;
            else
                return -1;
        } else if (s1.equals("4")) {
            if (s2.equals("3")) {
                return 1;
            } else if (s2.equals("4"))
                return 0;
            else {
                return -1;
            }
        } else if (s1.equals("5")) {
            if (s2.equals("3") || s2.equals("4")) {
                return 1;
            } else if (s2.equals("5"))
                return 0;
            else {
                return -1;
            }
        } else if (s1.equals("6")) {
            if (s2.equals("3") || s2.equals("4") || s2.equals("5")) {
                return 1;
            } else if (s2.equals("6"))
                return 0;
            else {
                return -1;
            }
        } else if (s1.equals("7")) {
            if (s2.equals("3") || s2.equals("4") || s2.equals("5") || s2.equals("6")) {
                return 1;
            } else if (s2.equals("7"))
                return 0;
            else {
                return -1;
            }
        } else if (s1.equals("8")) {
            if (s2.equals("3") || s2.equals("4") || s2.equals("5") || s2.equals("6") || s2.equals("7")) {
                return 1;
            } else if (s2.equals("8"))
                return 0;
            else {
                return -1;
            }
        } else if (s1.equals("9")) {
            if (s2.equals("3") || s2.equals("4") || s2.equals("5") ||
                    s2.equals("6") || s2.equals("7") || s2.equals("8")) {
                return 1;
            } else if (s2.equals("9"))
                return 0;
            else {
                return -1;
            }
        } else if (s1.equals("X")) {
            if (s2.equals("3") || s2.equals("4") || s2.equals("5") ||
                    s2.equals("6") || s2.equals("7") || s2.equals("8") || s2.equals("9")) {
                return 1;
            } else if (s2.equals("X"))
                return 0;
            else {
                return -1;
            }
        } else if (s1.equals("J")) {
            if (s2.equals("W") || s2.equals("w") || s2.equals("2") ||
                    s2.equals("A") || s2.equals("K") || s2.equals("Q")) {
                return -1;
            } else if (s2.equals("J"))
                return 0;
            else {
                return 1;
            }
        } else if (s1.equals("Q")) {
            if (s2.equals("W") || s2.equals("w") || s2.equals("2") ||
                    s2.equals("A") || s2.equals("K")) {
                return -1;
            } else if (s2.equals("Q"))
                return 0;
            else {
                return 1;
            }
        } else if (s1.equals("K")) {
            if (s2.equals("W") || s2.equals("w") || s2.equals("2") ||
                    s2.equals("A")) {
                return -1;
            } else if (s2.equals("K"))
                return 0;
            else {
                return 1;
            }
        } else if (s1.equals("A")) {
            if (s2.equals("W") || s2.equals("w") || s2.equals("2")) {
                return -1;
            } else if (s2.equals("A"))
                return 0;
            else {
                return 1;
            }
        } else if (s1.equals("2")) {
            if (s2.equals("W") || s2.equals("w")) {
                return -1;
            } else if (s2.equals("2"))
                return 0;
            else {
                return 1;
            }
        } else if (s1.equals("w")) {
            if (s2.equals("W")) {
                return -1;
            } else {
                return 1;
            }
        } else if (s1.equals("W")) {
            return 1;
        }
        return 0;
    };
    //排序

    /**
     * @return 无
     * @title: sortDeck
     * @description: 排序玩家的手牌deck
     */
    public void sortDeck() {
        deck.sort(comparator);
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public ArrayList<String> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<String> deck) {
        this.deck = deck;
    }

    /**
     * @return 无
     * @title: storeRoomInfo
     * @description: 储存大厅房间人数情况, 如果变化则更新
     */
    public void storeRoomInfo(String info, RoomWindow w) {
        String[] str = info.split("");//四位数字,每两位代表一个玩家,例1021代表1没人,2进房间未准备
        if (!info.equals(this.roomInfo)) {//0:没人,1:进房间,2:已准备
            System.out.println(info);//前端,更新玩家准备情况
            w.refresh(info);
            this.roomInfo = info;
        }
    }

    public void setSelectRoom(String selectRoom) {
        this.selectRoom = selectRoom;
    }

    /**
     * @return 无
     * @title: storeRoomNum
     * @description: 储存房间玩家情况, 如果有变化则更新
     */
    public void storeRoomNum(String roomNum, SelectRoomWindow w) {
        if (!roomNum.equals(this.roomNum) && !roomNum.equals("full")) {
            System.out.println(roomNum);//前端,对接更新房间信息
            w.refresh(roomNum);
            this.roomNum = roomNum;
        }
    }

}
