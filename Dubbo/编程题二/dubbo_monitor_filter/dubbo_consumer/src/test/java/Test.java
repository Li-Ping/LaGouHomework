import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:56 2020/4/15 0015
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {


        int TP90Count = (int) Math.floor(1 * 0.9);


        /*Random r = new Random();
        for (int i=0;i<100;i++){
            System.out.println(r.nextInt(100));
        }*/

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        Thread.sleep(5000);
                        System.out.println("ooo----pppp");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();*/

        final Map<String, Integer> wordCounts = new HashMap<>();
        wordCounts.put("USA", 100);
        wordCounts.put("jobs", 200);
        wordCounts.put("software", 50);
        wordCounts.put("technology", 70);
        wordCounts.put("opportunity", 200);

        final Map<String, Integer> sortedByCount3 = wordCounts.entrySet()
                .stream()
                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println(sortedByCount3);



    }
}
