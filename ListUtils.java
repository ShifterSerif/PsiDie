import java.util.ArrayList;

public class ListUtils {
    public static void winsorizeList(ArrayList<Integer> list, double trimPercent){
        int trim = (int) (list.size() * (trimPercent / 100));
        for (int i = 0; i < trim; i++) {
            if(i < trim / 10) {
                list.set(i, list.get(trim));
            }
            //list.set(i, list.get(trim));
            list.set(list.size() - 1 - i, list.get(list.size() - 1 - trim));
        }
    }
    public static void trimList(ArrayList<Integer> list, double trimPercent) {
        int trim = (int) (list.size() * (trimPercent / 100));
        for (int i = 0; i < trim; i++) {
            if(i < trim / 10) {
                list.remove(0);
            }
            //list.remove(0);
            list.remove(list.size() - 1);
        }
    }

    public static int getListMedian(ArrayList<Integer> list){
        if (list.size() % 2 == 0){
            return (list.get(list.size() / 2) + list.get((list.size() / 2) - 1)) / 2;
        } else {
            return list.get(list.size() / 2);
        }
    }
    public static double getListTotal(ArrayList<Integer> list) {
        double total = 0;
        for (double d : list) {
            total += d;
        }
        return total;
    }
    public static double getListAverage(ArrayList<Integer> list) {return getListTotal(list) / list.size();}
}
