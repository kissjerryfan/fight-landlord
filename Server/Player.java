package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Player {
    private ArrayList<String> playerDeck;//玩家的牌
    private DataOutputStream out;//玩家的输出流
    private DataInputStream in;//玩家的输入流
    private int qiangdizhu;//抢地主的点数
    private boolean isInGame;//玩家是否在游戏标识
    private Room room;//玩家所在的房间

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public Player(Socket socket){
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.isInGame = false;
    }

    /**

     * @title: sendMsg

     * @description: 给玩家发送消息
     *
     * @param  s String

     * @return 无

     */
    public void sendMsg(String s){
        try {
            this.out.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**

     * @title: receiveMsg

     * @description: 接受玩家发送的消息
     *

     * @return String 玩家发送的消息

     */
    public String receiveMsg(){
        try {
            return this.in.readUTF();
        } catch (IOException e) {
            System.out.println("玩家离开");
        }
        return null;
    }
    public DataInputStream getIn(){
        return in;
    }

    public DataOutputStream getOut(){
        return out;
    }

    /**

     * @title: outputPlayerDeck

     * @description: 格式化输出玩家卡牌信息
     *

     * @return String 玩家的卡牌信息

     */
    public String outputPlayerDeck(){
        StringBuilder str = new StringBuilder();
        for (String s : playerDeck){
            str.append(s);
        }
        return str.toString();
    }
    public ArrayList<String> getPlayerDeck() {
        return playerDeck;
    }

    public int getQiangdizhu() {
        return qiangdizhu;
    }

    public void setQiangdizhu(int qiangdizhu) {
        this.qiangdizhu = qiangdizhu;
    }

    public void setPlayerDeck(ArrayList<String> playerDeck) {
        this.playerDeck = playerDeck;
    }

}
