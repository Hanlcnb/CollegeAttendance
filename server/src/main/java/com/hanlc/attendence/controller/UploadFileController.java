package com.hanlc.attendence.controller;

import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.config.MinioConfig;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/wechat/upload")
@RequiredArgsConstructor
public class UploadFileController {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return Result.error("文件为空");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("仅支持图片上传");
            }

            String bucket = minioConfig.getBucket();
            ensureBucket(bucket);

            String originalFilename = file.getOriginalFilename();
            log.info(originalFilename);
            String ext = "";
            if (StringUtils.hasText(originalFilename) && originalFilename.contains(".")) {
                ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
            }

            String datePath = LocalDate.now().toString();
            String md5 = DigestUtils.md5DigestAsHex(file.getBytes()).substring(0,8);
            String objectName = String.format("%s/%s_%s", datePath , md5, ext);

            try (InputStream in = file.getInputStream()) {
                PutObjectArgs args = PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        .contentType(contentType)
                        .stream(in, file.getSize(), -1)
                        .build();
                minioClient.putObject(args);
            }

            String url = buildPublicUrl(bucket, objectName);

            Map<String, Object> resp = new HashMap<>();
            resp.put("bucket", bucket);
            resp.put("object", objectName);
            resp.put("url", url);
            resp.put("size", file.getSize());
            resp.put("contentType", contentType);
            return Result.success("上传成功", resp);
        } catch (Exception e) {
            log.error("图片上传失败", e);
            return Result.error("上传失败: " + e.getMessage());
        }
    }

    private void ensureBucket(String bucket) throws Exception {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    private String buildPublicUrl(String bucket, String objectName) {
        String encoded = URLEncoder.encode(objectName, StandardCharsets.UTF_8).replace("+", "%20");
        String domain = StringUtils.hasText(minioConfig.getCustomDomain()) ? minioConfig.getCustomDomain() : null;
        if (StringUtils.hasText(domain)) {
            if (domain.endsWith("/")) {
                domain = domain.substring(0, domain.length() - 1);
            }
            return domain + "/" + bucket + "/" + encoded;
        }
        // 使用 MinIO endpoint 拼接（假设为 http(s)://host:port）
        return minioEndpointToBase(minioConfig) + "/" + bucket + "/" + encoded;
    }

    private String minioEndpointToBase(MinioConfig cfg) {
        // endpoint 可能以 http(s):// 开头，直接返回
        return cfg == null ? "" : cfg.getClass() != null ? getEndpointValue(cfg) : "";
    }

    private String getEndpointValue(MinioConfig cfg) {
        try {
            java.lang.reflect.Field f = MinioConfig.class.getDeclaredField("endpoint");
            f.setAccessible(true);
            Object v = f.get(cfg);
            return v == null ? "" : String.valueOf(v).replaceAll("/+$", "");
        } catch (Exception e) {
            return "";
        }
    }
}


