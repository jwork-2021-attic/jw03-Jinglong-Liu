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

###但是,实际上
事情并不是想象的那么简单，以上答案看似合理，其实是不正确的(解析的过程)
测试过程中，在SteganographyClassLoader的findClass中加print,加断点，乃至全部注释掉，结果仍正常运行，这说明根本没有跳转到这个方法！
因为classpath有一个写好的BubbleSorter.java,系统会发现有这个文件，直接生成.class,并不会用图片，用自己写的findclass
事实上，经过后期检验，example中，乃至自己生成的在github上的文件，都是无法正常读取的。而通过本地绝对路径，是可以获取的
然后删掉BubbleSorter.java等java文件，无法跑通(图片错误)，经过测试，确实进入了自己写的classLoader
最后图片改成本地绝对路径，可以运行
经检验，自己生成的SelectSorter,QuickSorter图片可以读取用于排序的


###排序图片：在resources目录下
####原图
![origin](../resources/origin.jpeg)
https://github.com/jwork-2021/jw03-Jinglong-Liu/blob/main/S191220066/resources/SelectSorter.png
####快速排序
![quickSorter.png](../resources/QuickSorter.png)
https://github.com/jwork-2021/jw03-Jinglong-Liu/blob/main/S191220066/resources/QuickSorter.png
####选择排序
![selectSorter.png](../resources/SelectSorter.png)
备注：在本仓库的resources目录，github上可能无法预览

###排序视频
####选择排序 https://asciinema.org/a/ecMRHtUyq0pWY6KaEwearm70M
超链接 [selectSorter](https://asciinema.org/a/ecMRHtUyq0pWY6KaEwearm70M)
####快速排序 https://asciinema.org/a/GpUtIjdCW0P9002qpkYzrRgSK
超链接 [quickSorter](https://asciinema.org/a/GpUtIjdCW0P9002qpkYzrRgSK)
