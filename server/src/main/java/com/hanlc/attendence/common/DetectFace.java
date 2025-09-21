package com.hanlc.attendence.common;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DetectFace {
    
    @Value("${iai.SecretId}")
    private String secretId;
    
    @Value("${iai.SecretKey}")
    private String secretKey;
    
    private static final String ENDPOINT = "iai.tencentcloudapi.com";
    private static final String GROUP_ID = "HanlProgramPerson";

    private IaiClient getClient() {
        Credential cred = new Credential(secretId, secretKey);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(ENDPOINT);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new IaiClient(cred, "ap-shanghai", clientProfile);
    }

    public CreatePersonResponse createPerson(String personId, String imageBase64, long gender, String personName) {
        try {
            IaiClient client = getClient();
            CreatePersonRequest req = new CreatePersonRequest();
            req.setGroupId(GROUP_ID);
            req.setPersonName(personName);
            req.setPersonId(personId);
            req.setGender(gender);
            req.setImage(imageBase64);
            req.setUniquePersonControl(2L);
            req.setNeedRotateDetection(1L);
            
            return client.CreatePerson(req);
        } catch (TencentCloudSDKException e) {
            log.error("创建人员失败", e);
            throw new RuntimeException("创建人员失败: " + e.getMessage());
        }
    }

    public CreateFaceResponse createFace(String personId, String[] imageBase64) {
        try {
            IaiClient client = getClient();
            CreateFaceRequest req = new CreateFaceRequest();
            req.setPersonId(personId);
            req.setImages(imageBase64);
            req.setNeedRotateDetection(1L);
            
            return client.CreateFace(req);
        } catch (TencentCloudSDKException e) {
            log.error("添加人脸失败", e);
            throw new RuntimeException("添加人脸失败: " + e.getMessage());
        }
    }

    public GetPersonBaseInfoResponse getPersonBaseInfo(String personId) {
        try {
            IaiClient client = getClient();
            GetPersonBaseInfoRequest req = new GetPersonBaseInfoRequest();
            req.setPersonId(personId);
            
            return client.GetPersonBaseInfo(req);
        } catch (TencentCloudSDKException e) {
            log.error("获取人员信息失败", e);
            throw new RuntimeException("获取人员信息失败: " + e.getMessage());
        }
    }

    public DeleteFaceResponse deleteFace(String personId) {
        try {
            IaiClient client = getClient();
            String[] faceIds = getPersonBaseInfo(personId).getFaceIds();
            DeleteFaceRequest req = new DeleteFaceRequest();
            req.setPersonId(personId);
            req.setFaceIds(faceIds);
            
            return client.DeleteFace(req);
        } catch (TencentCloudSDKException e) {
            log.error("删除人脸失败", e);
            throw new RuntimeException("删除人脸失败: " + e.getMessage());
        }
    }

    public VerifyPersonResponse verifyPerson(String imageBase64, String personId) {
        try {
            IaiClient client = getClient();
            VerifyPersonRequest req = new VerifyPersonRequest();
            req.setImage(imageBase64);
            req.setPersonId(personId);
            req.setNeedRotateDetection(1L);
            
            return client.VerifyPerson(req);
        } catch (TencentCloudSDKException e) {
            log.error("人脸验证失败", e);
            throw new RuntimeException("人脸验证失败: " + e.getMessage());
        }
    }
}
