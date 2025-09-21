package com.hanlc.attendence.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class QRCodeService {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeService.class);

    @Autowired
    private ResourceLoader resourceLoader;

    public String generateQRCode(String content, int width, int height, OutputStream outputStream)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        // 将 BitMatrix 转换为 BufferedImage
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // 将 BufferedImage 转换为字节数组
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byte[] qrcodeBytes = byteArrayOutputStream.toByteArray();

        // 将二维码保存到静态资源目录并获取 URL
        String imageUrl = saveQrcodeToStatic(qrcodeBytes);
        return imageUrl;
    }

    public String saveQrcodeToStatic(byte[] qrcodeBytes) throws IOException {
        String filename = UUID.randomUUID().toString() + ".png";
        // 获取项目根目录
        String projectRoot = System.getProperty("user.dir");
        // 构建二维码保存路径
        String qrcodeDir = projectRoot + "/src/main/resources/static/qrcode";
        File directory = new File(qrcodeDir);
        
        // 如果目录不存在，创建目录
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // 保存文件
        Path filePath = Paths.get(qrcodeDir, filename);
        Files.write(filePath, qrcodeBytes);
        
        // 返回相对URL路径
        return "/qrcode/" + filename;
    }
}