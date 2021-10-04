package S191220066;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import S191220066.classloader.SteganographyClassLoader;

public class Scene {

    public static void main(String[] args) throws Exception {

        Line line = new Line(7);
        line.put(Gourd.ONE, 6);
        line.put(Gourd.TWO, 3);
        line.put(Gourd.THREE, 1);
        line.put(Gourd.FOUR, 5);
        line.put(Gourd.FIVE, 2);
        line.put(Gourd.SIX, 4);
        line.put(Gourd.SEVEN, 0);

        Geezer theGeezer = Geezer.getTheGeezer();
        //"https://cdn.njuics.cn/example.BubbleSorter.png"
        SteganographyClassLoader loader = new SteganographyClassLoader(
                new URL("https://github.com/jwork-2021/jw03-Jinglong-Liu/blob/main/S191220066/resources/SelectSorter.png"));

        Class c = loader.loadClass("S191220066.SelectSorter");

        Sorter sorter = (Sorter) c.newInstance();

        theGeezer.setSorter(sorter);

        String log = theGeezer.lineUp(line);

        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter("result2.txt"));
        writer.write(log);
        writer.flush();
        writer.close();

    }

}
