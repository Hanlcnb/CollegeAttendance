package com.hanlc.graduationproject.controller;

import com.hanlc.graduationproject.common.DetectFace;
import com.hanlc.graduationproject.common.Result;
import com.tencentcloudapi.common.profile.Region;
import com.tencentcloudapi.iai.v20200303.models.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wechat")
@RequiredArgsConstructor
public class FaceController {

    private final DetectFace detectFace;

    @PostMapping("/person")
    public Result<CreatePersonResponse> createPerson(
            @RequestParam String personId,
            @RequestParam String imageBase64,
            @RequestParam long gender,
            @RequestParam String personName) {
        return Result.success(detectFace.createPerson(personId, imageBase64, gender, personName));
    }

    @PostMapping("/face")
    public Result<CreateFaceResponse> createFace(
            @RequestParam String personId,
            @RequestParam String[] imageBase64) {
        return Result.success(detectFace.createFace(personId, imageBase64));
    }

    @GetMapping("/person/{personId}")
    public Result<GetPersonBaseInfoResponse> getPersonBaseInfo(
            @PathVariable String personId) {
        return Result.success(detectFace.getPersonBaseInfo(personId));
    }

    @DeleteMapping("/face/{personId}")
    public Result<DeleteFaceResponse> deleteFace(
            @PathVariable String personId) {
        return Result.success(detectFace.deleteFace(personId));
    }

    @Data
    public static class VerifyRequest {
        private String imageBase64;
        private String personId;
    }

    @PostMapping("/verify")
    public Result<VerifyPersonResponse> verifyPerson(@RequestBody VerifyRequest request) {
        return Result.success(detectFace.verifyPerson(request.getImageBase64(), request.getPersonId()));
    }
} 