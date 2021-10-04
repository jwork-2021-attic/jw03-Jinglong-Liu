###过程理解
一、将类字节码传入图片
```java
public static void getSteganography(String classSource, String originImage) throws IOException {
    1'String className = classSource.substring(0, classSource.indexOf(".")).replace("/", ".");
    2'SteganographyFactory.compile(classSource);
    3'BufferedImage image = ImageIO.read(new File(originImage));
    4'SteganographyEncoder steganographyEncoder = new SteganographyEncoder(image);

    5'BufferedImage encodedImage = steganographyEncoder.encodeFile(new File(classSource.replace("java", "class")));
    6'ImageIO.write(encodedImage, "png", new File(className+".png"));
}
```
1、获得类名
2、对给定的java文件，用getSystemJavaCompiler编译生成.class文件
3-4、读取存储原图
5、encode,对java文件对应的.class文件编码，生成新图片
6、保存经过编码后的图片
二、解析图片字节码
```java
1'SteganographyClassLoader loader = new SteganographyClassLoader(
                new URL("https://cdn.njuics.cn/example.BubbleSorter.png"));

2'Class c = loader.loadClass("example.BubbleSorter");

3'Sorter sorter = (Sorter) c.newInstance();
```
1，SteganographyClassLoader构造函数，传入的一个图片的url，保存在loader对象中
2，loaderClass,在确认没有被load过的情况下，最终调用的是`c = findClass(name);`
而example中的SteganographyClassLoader重写的findClass方法
```java
BufferedImage img = ImageIO.read(url);//将存在loader中的url读取为图片
SteganographyEncoder encoder = new SteganographyEncoder(img);
byte[] bytes = encoder.decodeByteArray();//加密解密最终变成byte[]
return this.defineClass(name, bytes, 0, bytes.length);
```
下面讨论defineClass
在classLoader的defineClass的注释中写道

Converts an array of bytes into an instance of class {@code Class},* with a given {@code ProtectionDomain}
也即将传入的byte[] 转换为一个给定类的实例
所以`Class c = loader.loadClass("example.BubbleSorter");`这一句是对图片解码，得到一个Class<?>的实例
3、将Class类型转换为Sorter类型，完成解析还原全过程

###排序图片
####原图
![origin](../resources/origin.jpeg)
####快速排序
![quickSorter.png](../resources/QuickSorter.png)
####选择排序
![selectSorter.png](../resources/SelectSorter.png)
备注：在本仓库的resources目录，github上可能无法预览

###排序视频
####选择排序 https://asciinema.org/a/ecMRHtUyq0pWY6KaEwearm70M
超链接 [selectSorter](https://asciinema.org/a/ecMRHtUyq0pWY6KaEwearm70M)
####快速排序 https://asciinema.org/a/GpUtIjdCW0P9002qpkYzrRgSK
超链接 [quickSorter](https://asciinema.org/a/GpUtIjdCW0P9002qpkYzrRgSK)
