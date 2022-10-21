package Server;


import java.util.ArrayList;

public class Room {
    private ArrayList<Player> players = new ArrayList<>();//在房间里的玩家
    private int num;//人数
    private boolean start;//开始游戏信号
    private ArrayList<Player> readyPlayer = new ArrayList<>();//已经准备了的玩家
    static int n = 3;

     public void decrease(){
         synchronized (this){
             n--;
         }
     }

    public Room() {
        this.num = 0;
        this.start = false;
    }

    /**

     * @title: AssPlayer

     * @description: 添加加入房间的玩家
     *


     */
    public void addPlayer(Player player) {//加入玩家
        players.add(player);
        num++;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public ArrayList<Player> getReadyPlayer() {
        return readyPlayer;
    }

    /**

     * @title: addReady

     * @description: 加入已经准备的玩家
     *
     * @param player Player

     */
    public void addReady(Player player) {//加入准备玩家
        if (!readyPlayer.contains(player)) {
            readyPlayer.add(player);
            System.out.println("ready");
        }
        if (readyPlayer.size() == 3) {
            this.start = true;//三人都准备则开始信号置true
        }
    }
    /**

     * @title: removeReady

     * @description: 移除取消准备的玩家
     *
     * @param player Player

     */
    public void removeReady(Player player) {//取消准备玩家
        if (readyPlayer.remove(player))
            System.out.println("unready");
    }

    public int getNum() {
        return num;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**

     * @title: removePlayer

     * @description: 移除退出房间的玩家
     *
     * @param player Player

     */
    public void removePlayer(Player player) {//玩家退出房间
        players.remove(player);//删除房间里的玩家
        readyPlayer.remove(player);//如果退出的是已准备玩家,也删除
        num--;
    }

    public boolean isStart() {
        return start;
    }


    /**

     * @title: roomInfo

     * @description: 格式化输出其他两位置的情况
     *
     * @param player Player

     * @return String
     */
    public String roomInfo(Player player) {
        if (this.num == 1) {
            return "1020";
        } else if (this.num == 2) {
            int i = 1;
            for (Player p : players) {
                if (!p.equals(player)) {
                    if (readyPlayer.contains(p))
                        i = 2;
                }
            }
            return "1" + i + "20";
        } else {
            int[] i = {1, 1};
            int n = 0;
            for (Player p : players) {
                if (!p.equals(player)) {
                    if (readyPlayer.contains(p))
                        i[n++] = 2;
                }
            }
            return "1" + i[0] + "2" + i[1];
        }
    }
}
