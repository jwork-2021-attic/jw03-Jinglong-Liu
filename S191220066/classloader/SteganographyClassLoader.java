package S191220066.classloader;

import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import S191220066.encoder.SteganographyEncoder;

import java.awt.image.BufferedImage;

public class SteganographyClassLoader extends ClassLoader {

    private URL url;

    public SteganographyClassLoader(URL imageURL) {
        super();
        this.url = imageURL;
    }

    public SteganographyClassLoader(URL imageURL, ClassLoader parent) {
        super(parent);
        this.url = imageURL;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            //System.out.print("hello\n\n");
            BufferedImage img = ImageIO.read(url);

            SteganographyEncoder encoder = new SteganographyEncoder(img);
            byte[] bytes = encoder.decodeByteArray();
            return this.defineClass(name, bytes, 0, bytes.length);

        } catch (IOException e) {
            throw new ClassNotFoundException();
        }

    }


}
