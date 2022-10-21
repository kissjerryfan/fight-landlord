package Server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**

 * @title: Server

 * @description: 服务器主线程,用于服务器启动和接受玩家接入
 *

 */
public class Server {
    public static Room[] rooms = new Room[12];
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);//服务器端口
        Socket client;
        for (int i = 0; i < 12; i++) {
            rooms[i] = new Room();
        }
        System.out.println("服务端启动");
        while (true) {
            System.out.println("等待玩家接入...");
            client = serverSocket.accept();//等待玩家接入
            System.out.println("连接到一个玩家");
            new PlayerMsg(client).start();
        }
    }
}

/**

 * @title: PlayerMsg

 * @description: 玩家和服务器交流的多线程,用于服务器和每个玩家进行交流
 *

 */
class PlayerMsg extends Thread {//一个玩家线程,用于选择房间到开始游戏之前的阶段
    Player player;//一个玩家
    Room[] rooms;//所有房间
    Room room;//选择的房间

    public PlayerMsg(Socket client) {
        this.rooms = Server.rooms;
        this.player = new Player(client);
    }
    public PlayerMsg(Player player,Room room){
        this.player = player;
        this.rooms = Server.rooms;
        this.room = room;
    }

    /**

     * @title: run

     * @description: 玩家与服务器交流的多线程
     *

     */
    @Override
    public void run() {
        try {

            do {
                if (!player.isInGame())
                    //选择房间
                    this.room = selectRoom();
            } while (!gameReady(room));//三个人都准备后break
            this.room.decrease();
            if (Room.n == 0){//由一个人的线程启动游戏线程
                Room.n = 3;
                new GameThread(room.getPlayers()).start();//开始游戏
            }
            System.out.println("start");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**

     * @title: selectRoom

     * @description: 玩家选择房间
     *

     */
    //选择房间
    public Room selectRoom() throws IOException {
        String message;
        while (true){
            player.sendMsg(getRoomNum());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            message = player.receiveMsg();
            if (!message.equals("-1")){
                int choice = Integer.parseInt(message);
                if (rooms[choice].getNum() >= 3){
                    player.sendMsg("full");//表示此房间人数已满
                    player.receiveMsg();
                } else {
                    player.sendMsg("ok");//表示可以进入此房间
                    player.receiveMsg();
                    rooms[choice].addPlayer(player);//房间记录玩家
                    player.setRoom(rooms[choice]);//记录玩家的房间
                    return rooms[choice];
                }
            }
        }
    }
    /**

     * @title: gameReady
     *
     * @description: 玩家进入房间后准备阶段的交流
     *
     * @param room Room

     * @return boolean 返回true表示三位玩家都准备了,可以开始游戏;false表示玩家退出这个房间

     */
    public boolean gameReady(Room room){
        String playerMessage;//放玩家消息
        room.setStart(false);//重置房间开始信号
        player.setInGame(false);//游戏退出后,设为不在游戏内
        while(true){//每隔0.01秒服务器向玩家询问准备状态,若3人都准备了,则房间信号置true
            if (room.isStart()) {//房间开始信号
                player.sendMsg("start");//提示玩家开始游戏
                player.receiveMsg();
                room.getReadyPlayer().clear();//开始游戏后重置准备玩家
                return true;//返回true
            }
            else
                player.sendMsg("ready?"+room.roomInfo(player));//房间准备信息
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playerMessage = player.receiveMsg();
            if (playerMessage.equals("unready"))
                room.removeReady(player);//设置房间的准备玩家的list
            if (playerMessage.equals("ready")) {
                room.addReady(player);
            }
            if (playerMessage.equals("quit")) {
                player.sendMsg("quit");
                player.receiveMsg();
                room.removePlayer(player);//房间移除玩家
                player.setRoom(null);//玩家移除房间
                System.out.println("quit");
                return false;//重新进入大厅阶段,选择房间
            }

        }
    }
    /**

     * @title: getRoomNum

     * @description: 获取所有房间人数信息
     *

     * @return String 房间人数信息

     */
    public String getRoomNum(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            str.append(rooms[i].getNum()).append(",");
        }
        return str.toString();
    }
}

/**

 * @title: GameThread
 *
 * @description: 一局游戏的多线程,用于不同玩家的游戏
 *

 */
class GameThread extends Thread {//一局游戏的多线程类
    static int homeNum = 0;
    ArrayList<Player> players;

    public GameThread(ArrayList<Player> players) {
        this.players = players;
        homeNum += 1;
        System.out.println("第" + homeNum + "局游戏开始");
    }

    @Override
    public void run() {
        Gameround game = new Gameround(players);
        game.gameStart();//开始一轮游戏
        for (Player player: players){//游戏结束后,重新开三个玩家交流线程,依靠isInGame的flag跳过选择房间阶段,
            new PlayerMsg(player,player.getRoom()).start();//直接在房间的准备阶段
        }
    }
}