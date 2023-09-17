import java.util.ArrayList;
import java.util.Objects;

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
        if(list.size() % 2 == 0){
            return (list.get(list.size() / 2) + list.get((list.size() / 2) - 1)) / 2;
        } else {
            return list.get(list.size() / 2);
        }
    }

    public static int getListMode(ArrayList<Integer> list){
        int mode = 0;
        int maxCount = 0;
        for (int i = 0; i < list.size(); i++) {
            int count = 0;
            for (Integer integer : list) {
                if (Objects.equals(integer, list.get(i))) count++;
            }
            if(count > maxCount){
                maxCount = count;
                mode = list.get(i);
            }
        }
        return mode;
    }
    public static float getListTotal(ArrayList<Integer> list) {
        float total = 0;
        for(float d : list) {total += d;}
        return total;
    }
    public static float getListAverage(ArrayList<Integer> list) {
        return (getListTotal(list) / list.size());
    }

    public static void printList(ArrayList<Integer> list){
        StringBuilder output = new StringBuilder();
        for (Integer integer : list) {
            output.append(integer).append(", ");
        }
        System.out.println(output);
    }
}
