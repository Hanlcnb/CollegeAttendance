package com.hanlc.attendence.controller;

import com.google.zxing.WriterException;
import com.hanlc.attendence.common.Result;
import com.hanlc.attendence.service.QRCodeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/wechat")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    /**
     * 根据content中的签到任务ID查询check_in_tasks表，得到该签到任务的签到任务ID
     * 再把签到任务ID放到二维码中
     */
    @GetMapping("/generateQRcode")
    public Result generateQRCode(@RequestParam("content") String content,
                                 @RequestParam(value = "width", defaultValue = "300") int width,
                                 @RequestParam(value = "height", defaultValue = "300") int height,
                                 HttpServletResponse response) throws WriterException, IOException {
        response.setContentType("image/png");
        String imageURL = qrCodeService.generateQRCode(content, width, height, response.getOutputStream());
        return Result.success("成功生成二维码图片",imageURL);
    }
}