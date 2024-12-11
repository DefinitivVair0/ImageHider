import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URISyntaxException;

public class ImageHider
{
    public static String path;
    public static void main(String[] args) throws IOException, URISyntaxException
    {
        path = ImageHider.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();

        encode();
        decode();
    }

    public static void encode() throws IOException
    {
        File file = new File(path + "/image.png");

        File rfile = new File(path + "/rimage.png");
        File gfile = new File(path + "/gimage.png");
        File bfile = new File(path + "/bimage.png");
        File afile = new File(path + "/aimage.png");

        BufferedImage image = ImageIO.read(file);

        File f = new File(path + "/encodedimage.png");
        ImageIO.write(alterPixel(image, rfile, gfile, bfile, afile), "PNG", f);
    }

    public static void decode() throws IOException
    {
        File file = new File(path + "/encodedimage.png");
        BufferedImage image = ImageIO.read(file);

        BufferedImage rfile = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImage gfile = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImage bfile = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImage afile = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        BufferedImage[] images = restorePixel(image, rfile, gfile, bfile, afile);

        File r = new File(path + "/rdecodedimage.png");
        ImageIO.write(images[0], "PNG", r);

        File g = new File(path + "/gdecodedimage.png");
        ImageIO.write(images[1], "PNG", g);

        File b = new File(path + "/bdecodedimage.png");
        ImageIO.write(images[2], "PNG", b);

        File a = new File(path + "/adecodedimage.png");
        ImageIO.write(images[3], "PNG", a);
    }

    public static BufferedImage alterPixel(BufferedImage image, File maskfR, File maskfG, File maskfB, File maskfA) throws IOException
    {
        BufferedImage maskR = ImageIO.read(maskfR);
        BufferedImage maskG = ImageIO.read(maskfG);
        BufferedImage maskB = ImageIO.read(maskfB);
        BufferedImage maskA = ImageIO.read(maskfA);

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x<image.getWidth();x++)
        {
            for (int y = 0; y<image.getHeight();y++)
            {
                int clr = image.getRGB(x, y);
                int alpha = (clr & 0xff000000) >> 24;
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;

                if (alpha%2==0) {alpha+=1;}
                if (red%2==0) {red+=1;}
                if (green%2==0) {green+=1;}
                if (blue%2==0) {blue+=1;}

                if (maskR.getRGB(x, y) == 0xffffffff) {red--;}
                if (maskG.getRGB(x, y) == 0xffffffff) {green--;}
                if (maskB.getRGB(x, y) == 0xffffffff) {blue--;}
                if (maskA.getRGB(x, y) == 0xffffffff) {alpha--;}

                int newPixel = 0;
                newPixel += alpha;
                newPixel = newPixel << 8;
                newPixel += red;
                newPixel = newPixel << 8;
                newPixel += green;
                newPixel = newPixel << 8;
                newPixel += blue;

                newImage.setRGB(x,y,newPixel);
            }
        }

        return newImage;
    }

    public static BufferedImage[] restorePixel(BufferedImage image, BufferedImage rimage, BufferedImage gimage, BufferedImage bimage, BufferedImage aimage)
    {
        for (int x = 0; x<image.getWidth();x++)
        {
            for (int y = 0; y<image.getHeight();y++)
            {
                int clr = image.getRGB(x, y);
                int alpha = (clr & 0xff000000) >> 24;
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;

                rimage.setRGB(x,y,0xff000000);
                gimage.setRGB(x,y,0xff000000);
                bimage.setRGB(x,y,0xff000000);
                aimage.setRGB(x,y,0xff000000);

                if (red%2==0) {rimage.setRGB(x,y, 0xffffffff);}
                if (green%2==0) {gimage.setRGB(x,y, 0xffffffff);}
                if (blue%2==0) {bimage.setRGB(x,y, 0xffffffff);}
                if (alpha%2==0) {aimage.setRGB(x,y, 0xffffffff);}
            }
        }
        return new BufferedImage[] {rimage,gimage,bimage,aimage};
    }
}