package com.dove.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * 图像转base64工具类
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
public class ImageUtil {
    /**
     * 图片转Base64字符串
     *
     * @param imageFileName 图像文件名字
     * @return {@link String}
     */
    public static String convertImageToBase64Str(String imageFileName) {
        ByteArrayOutputStream baos = null;
        try {
            String suffix = imageFileName.substring(imageFileName.lastIndexOf(".") + 1);
            File imageFile = new File(imageFileName);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, suffix, baos);
            byte[] bytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Base64字符串转图片
     *
     * @param base64String  base64字符串
     * @param imageFileName 图像文件名字
     */
    public static void convertBase64StrToImage(String base64String, String imageFileName) {
        ByteArrayInputStream bais = null;
        try {
            String suffix = imageFileName.substring(imageFileName.lastIndexOf(".") + 1);
            byte[] bytes = Base64.getDecoder().decode(base64String);
            bais = new ByteArrayInputStream(bytes);
            BufferedImage bufferedImage = ImageIO.read(bais);
            File imageFile = new File(imageFileName);
            ImageIO.write(bufferedImage, suffix, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
