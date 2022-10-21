package Client;

import java.util.*;

/**

 * @title: CompareCard

 * @description: 比较出牌的类

 */
public class CompareCard {//比较牌大小
    private String player;//储存上一轮出牌的玩家序号
    private ArrayList<String> cards;//储存的待比较的牌
    private static CompareCard compareCard;//单例模式

    public static CompareCard getInstance(){
        if (compareCard == null) compareCard = new CompareCard();
        return compareCard;
    }

    public CompareCard(){
        player = "m";//地主出牌时,前面无人,所以这里设置为自己,如果不是地主,则会被之后的覆盖,若是地主,则必须出牌
        cards = new ArrayList<>();
    }

    /**

     * @title: setCompareCrad

     * @description: 保留上一轮玩家出的牌,为下家出牌比较

     * @return 无

     */
    //玩家出的牌储存
    public void setCompareCard(String in){
        String[] cards = in.split("");
        //玩家未出牌,保留上次玩家牌
        if (cards.length == 1){//只有玩家序号,所以length为一
            return;
        }
        this.cards.clear();//清除前前轮出的牌
        this.player = cards[0];//玩家序号
        this.cards.addAll(Arrays.asList(cards).subList(1, cards.length));//储存玩家出的牌
    }
    /**

     * @title: compare

     * @description: 传入一个出的卡牌,和上一轮玩家出的牌进行比较
     *
     * @param in String

     * @return Boolean true表示大过上一个玩家的牌,可以出;false表示比上一家的牌小或者不合规,不能出

     */
    public boolean compare(String in){//牌大小比较
        String[] str = in.split("");
        List<String> list = Arrays.asList(str);
        ArrayList<String> c = new ArrayList<>(list);//要出的牌
        if (this.player.equals("m")){//自己出的上一轮牌,没人吃,直接出牌
            //判断是否合法
            boolean flag = false;
            if (c.size() == 1)
                flag = true;
            else if (isABCDE(c,true))
                flag = true;
            else if (isAA(c))
                flag = true;
            else if (isBomb(c))
                flag = true;
            else if (isAAAABC(c))
                flag = true;
            else if (isAAAB(c))
                flag = true;
            else if (isAAABBB(c))
                flag = true;
            else if (isAABBCC(c))
                flag = true;
            else if (isAAABB(c))
                flag = true;
            else if (isAAABBBCCDD(c))
                flag = true;
            else if (isAAABCCCD(c))
                flag = true;
            return flag;
        } else {//吃别人的牌
            if (c.size() == 1 && this.cards.size() == 1)//单张
                return Player.comparator.compare(c.get(0), this.cards.get(0)) > 0;
            else if (c.size() != this.cards.size()){//不相同张数,如果是炸弹则可以吃,反之不行
                if (this.cards.size() == 2 && isBomb(this.cards))
                    return false;
                else return isBomb(c);
            }
            else if (isAA(c) && isAA(this.cards))//两张
                return Player.comparator.compare(c.get(0),this.cards.get(0)) > 0;
            else if (isAAA(c) && isAAA(this.cards))//三张
                return Player.comparator.compare(c.get(0),this.cards.get(0)) > 0;
            else if (isABCDE(c,true) && isABCDE(this.cards,true))//顺子
                return Player.comparator.compare(c.get(0),this.cards.get(0)) > 0;
            else if (isAAAB(c) && isAAAB(this.cards)){//三带一
                ArrayList<String> c1 = findN(c,3);
                ArrayList<String> c2 = findN(this.cards,3);
                return Player.comparator.compare(c1.get(0),c2.get(0)) > 0;
            }
            else if (isAAABB(c) && isAAABB(this.cards)){//三带一对
                ArrayList<String> c1 = findN(c,3);
                ArrayList<String> c2 = findN(this.cards,3);
                return Player.comparator.compare(c1.get(0),c2.get(0)) > 0;
            }
            else if (isAAABBB(c) && isAAABBB(this.cards)){//飞机不带
                ArrayList<String> c1 = findN(c,3);
                ArrayList<String> c2 = findN(this.cards,3);
                c1.sort(Player.comparator);
                c2.sort(Player.comparator);
                return Player.comparator.compare(c1.get(0),c2.get(0)) > 0;
            }
            else if (isAABBCC(c) && isAABBCC(this.cards)){//连对
                ArrayList<String> c1 = findN(c,2);
                ArrayList<String> c2 = findN(this.cards,2);
                c1.sort(Player.comparator);
                c2.sort(Player.comparator);
                return Player.comparator.compare(c1.get(0),c2.get(0)) > 0;
            }
            else if (isBomb(c)){//炸弹
                if (c.size() == 2)//双王,都可以吃
                    return true;
                else if (isBomb(this.cards))//都是炸弹,比较
                    return Player.comparator.compare(c.get(0),this.cards.get(0)) > 0;
                else
                    return true;//反之,炸弹直接吃
            }
            else if (isAAABCCCD(c) && isAAABCCCD(this.cards)){//飞机带单
                ArrayList<String> c1 = findN(c,3);
                ArrayList<String> c2 = findN(this.cards,3);
                c1.sort(Player.comparator);
                c2.sort(Player.comparator);
                return Player.comparator.compare(c1.get(0),c2.get(0)) > 0;
            }
            else if (isAAABBBCCDD(c) && isAAABBBCCDD(this.cards)){//飞机带双
                ArrayList<String> c1 = findN(c,3);
                ArrayList<String> c2 = findN(this.cards,3);
                c1.sort(Player.comparator);
                c2.sort(Player.comparator);
                return Player.comparator.compare(c1.get(0),c2.get(0)) > 0;
            }
            else if (isAAAABC(c) && isAAAABC(this.cards)){//四带二,可带两单,可带一对
                ArrayList<String> c1 = findN(c,4);
                ArrayList<String> c2 = findN(this.cards,4);
                return Player.comparator.compare(c1.get(0),c2.get(0)) > 0;
            }
            else
                return false;
        }
    }
    /**

     * @title: isAA

     * @description: 判断是否为一对牌
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是一对,false表示不是一对

     */
    //一对
    public boolean isAA(ArrayList<String> cards){
        return cards.size() == 2 && cards.get(0).equals(cards.get(1));
    }
    //炸弹,大小王.
    /**

     * @title: isBomb

     * @description: 判断是否为炸弹,双王
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是,false表示不是

     */
    public boolean isBomb(ArrayList<String> cards){
        HashSet<String> c = new HashSet<>(cards);
        cards.sort(Player.comparator);
        return (c.size() == 1 && cards.size() == 4) || (cards.size() == 2 && cards.get(0).equals("w") && cards.get(1).equals("W"));
    }
    /**

     * @title: isAAAB

     * @description: 判断是否为三带一,排除炸弹
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是,false表示不是

     */
    //三带一 炸弹不算
    public boolean isAAAB(ArrayList<String> cards){
        cards.sort(Player.comparator);
        if (cards.size() == 4){
            String[] c = new String[4];
            c[0] = cards.get(0);
            c[1] = cards.get(1);
            c[2] = cards.get(2);
            c[3] = cards.get(3);
            if (c[0].equals(c[1]) && c[0].equals(c[2]) && !c[0].equals(c[3])){
                return true;
            }
            else if (!c[0].equals(c[1]) && c[1].equals(c[2]) && c[1].equals(c[3])){
                return true;
            }
            else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**

     * @title: isABCDE

     * @description: 判断是否为顺子,且不能带2,大小王
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是,false表示不是

     */
    //顺子,不能有2,大小王
    public boolean isABCDE(ArrayList<String> cards,boolean shunzi){
        if (shunzi && (cards.size() < 5 || cards.size() > 12)){
            return false;
        }
        cards.sort(Player.comparator);
        String[] d = {"3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K", "A"};
        boolean flag = false;
        for (int i = 0,j = 0;i < cards.size();j++){
            if (d[j].equals(cards.get(i))){
                if (!flag && j+cards.size() > d.length)
                    return false;
                flag = true;
            }
            if (flag){
                if (d[j].equals(cards.get(i))){
                    i++;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    //三张
    /**

     * @title: isAAA

     * @description: 判断是否为三张
     *
     * @param cards ArrayList<String>
     *
     * @return Boolean true表示是,false表示不是

     */

    public boolean isAAA(ArrayList<String> cards){
        return cards.size() == 3 && cards.get(0).equals(cards.get(1)) && cards.get(0).equals(cards.get(2));
    }
    //三带一对
    /**

     * @title: isAAABB

     * @description: 判断是否为三带一对
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是,false表示不是

     */
    public boolean isAAABB(ArrayList<String> cards){
        if (cards.size() != 5){
            return false;
        }
        if (isAA(new ArrayList<>(cards.subList(0,2))) && isAAA(new ArrayList<>(cards.subList(2,5)))){
            return true;
        }
        else if (isAAA(new ArrayList<>(cards.subList(0,3))) && isAA(new ArrayList<>(cards.subList(3,5)))){
            return true;
        }
        else
            return false;
    }
    //连对
    /**

     * @title: isAABBCC

     * @description: 判断是否为连对,必须连3对以上
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是,false表示不是

     */
    public boolean isAABBCC(ArrayList<String> cards){
        int size = cards.size();
        if (size < 6)
            return false;
        ArrayList<String> card2 = findN(cards,2);
        if (card2.size()*2 != size)
            return false;
        card2.sort(Player.comparator);
        return isABCDE(card2,false);
    }
    //4带2,可以带两个单张,或两对
    /**

     * @title: isAAAABC

     * @description: 判断是否为四带二,可带两单,可带一对
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是,false表示不是

     */
    public boolean isAAAABC(ArrayList<String> cards){
        if (cards.size() == 6){//两个单张
            ArrayList<String> card4 = findN(cards,4);
            return card4.size() == 1;
        } else if (cards.size() == 8){//两对
            ArrayList<String> card4 = findN(cards,4);
            ArrayList<String> card2 = findN(cards,2);
            return card2.size() == 2 && card4.size() == 1;
        } else
            return false;
    }
    //飞机不带
    /**

     * @title: isAAABBB

     * @description: 判断是否为飞机不带
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是,false表示不是

     */
    public boolean isAAABBB(ArrayList<String> cards){
        int size = cards.size();
        if (size%3 != 0 || size < 6)
            return false;
        ArrayList<String> c = findN(cards,3);
        if (c.size() != size/3)
            return false;
        return isABCDE(c,false);
    }
    //飞机带单
    /**

     * @title: isAAABCCCD

     * @description: 判断是否为飞机带单
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是,false表示不是

     */
    public boolean isAAABCCCD(ArrayList<String> cards){
        int n = cards.size();
        if (n % 4 != 0 || n < 8){
            return false;
        }
        ArrayList<String> card3 = findN(cards,3);
        if (card3.size() != ( n/4 ))
            return false;
        return isABCDE(card3,false);
    }
    //飞机带双
    /**

     * @title: isAAABBBCCDD

     * @description: 判断是否为飞机带双
     *
     * @param cards ArrayList<String>

     * @return Boolean true表示是,false表示不是

     */
    public boolean isAAABBBCCDD(ArrayList<String> cards){
        int n = cards.size();
        if (n%5 != 0 && n < 10)
            return false;
        ArrayList<String> card3 = findN(cards,3);
        ArrayList<String> card2 = findN(cards,2);
        if (card2.size()==(n/5) && card3.size() == (n/5))
            return isABCDE(card3,false);
        return false;
    }
    //找出n张同样的牌
    /**

     * @title: findN

     * @description: 找到cards里面指定出现n次数的牌
     *
     * @param cards ArrayList<String>
     *
     * @param: int n

     * @return ArrayList String

     */
    public ArrayList<String> findN(ArrayList<String> cards,int n){
        HashMap<String,Integer> map = new HashMap<>();
        for (String s:cards){
            if (isInMap(s,map)){
                map.put(s,map.get(s)+1);
            } else {
                map.put(s,1);
            }
        }
        ArrayList<String> find = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            if (entry.getValue() == n)
                find.add(entry.getKey());
        }
        return find;
    }
    /**

     * @title: isInMap

     * @description: 判断s是否在hashmap里面
     *
     * @param s String
     *
     * @param map HashMap<String,Integer>

     * @return ArrayList String

     */
    public boolean isInMap(String s,HashMap<String,Integer> map){
        Set<String> set = map.keySet();
        return set.contains(s);
    }

    public String getPlayer() {
        return player;
    }
}
