package cn.com.ctrl.yjjy.project.rescue.trigger.tool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class JpgEdit {
    /**
     * 添加文字水印
     * @param targetImg 目标图片路径，如：C://myPictrue//
     * @param targetImg_ 目标图片路径，如：1.jpg
     * @param pressText 水印文字， 如：中国证券网
     * @param fontName 字体名称，如：宋体
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize 字体大小，单位为像素
     * @param color 字体颜色
     * @param x 水印文字偏移量
     * @param y 水印文字偏移量
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static void pressText(String targetImg, String targetImg_, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha){
        try {
            File file = new File(targetImg + targetImg_);
            Image image = ImageIO.read(file);
            //图宽度
            int width = image.getWidth(null);
            //图高度
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image,0,0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            String[] pressTexts = pressText.split(";");
            for (int i = 1;i <= pressTexts.length;i++){
                g.drawString(pressTexts[i - 1], x, y * i);//水印文件
            }
            g.dispose();
            ImageIO.write(bufferedImage, "jpg", new File("C:\\fakepath\\ttt\\"+targetImg_));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 计算文字像素长度
     * @param text
     * @return
     */
    private static int getTextLength(String text){
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            int wordLength = String.valueOf(text.charAt(i)).getBytes().length;
            if(wordLength > 1){
                length+=(wordLength-1);
            }
        }
        return length%2==0 ? length/2:length/2+1;
    }
}