package win.panyong.utils;

import java.util.ArrayList;
import java.util.List;

public class DeleteMe {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }};
        for (Integer integer : list) {
            if (integer == 4) {
                list.remove(integer);
            }
        }
        System.out.println(list);
    }
}
