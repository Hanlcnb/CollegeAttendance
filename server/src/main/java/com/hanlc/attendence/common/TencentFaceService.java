package com.hanlc.attendence.common;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TencentFaceService {
    
    @Value("${iai.SecretId}")
    private String secretId;
    
    @Value("${iai.SecretKey}")
    private String secretKey;
    
    private static final String ENDPOINT = "iai.tencentcloudapi.com";
    private static final String GROUP_ID = "HanlProgramPerson";
    
    /**
     * 创建人员信息
     * @param personId 人员ID
     * @param imageBase64 人脸图片Base64编码
     * @param gender 性别
     * @param personName 人员姓名
     * @return 创建结果
     */
    public CreatePersonResponse createPerson(String personId, String imageBase64, Long gender, String personName) {
        try {
            IaiClient client = createClient();
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
            log.error("创建人员信息失败", e);
            throw new RuntimeException("创建人员信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 增加人脸
     * @param personId 人员ID
     * @param imageBase64 人脸图片Base64编码数组
     * @return 创建结果
     */
    public CreateFaceResponse createFace(String personId, String[] imageBase64) {
        try {
            IaiClient client = createClient();
            CreateFaceRequest req = new CreateFaceRequest();
            req.setPersonId(personId);
            req.setImages(imageBase64);
            req.setNeedRotateDetection(1L);
            
            return client.CreateFace(req);
        } catch (TencentCloudSDKException e) {
            log.error("增加人脸失败", e);
            throw new RuntimeException("增加人脸失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取人员基础信息
     * @param personId 人员ID
     * @return 人脸ID数组
     */
    public String[] getPersonBaseInfo(String personId) {
        try {
            IaiClient client = createClient();
            GetPersonBaseInfoRequest req = new GetPersonBaseInfoRequest();
            req.setPersonId(personId);
            
            GetPersonBaseInfoResponse resp = client.GetPersonBaseInfo(req);
            return resp.getFaceIds();
        } catch (TencentCloudSDKException e) {
            log.error("获取人员基础信息失败", e);
            throw new RuntimeException("获取人员基础信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除人脸
     * @param personId 人员ID
     * @return 删除结果
     */
    public DeleteFaceResponse deleteFace(String personId) {
        try {
            IaiClient client = createClient();
            String[] faceIds = getPersonBaseInfo(personId);
            DeleteFaceRequest req = new DeleteFaceRequest();
            req.setPersonId(personId);
            req.setFaceIds(faceIds);
            
            return client.DeleteFace(req);
        } catch (TencentCloudSDKException e) {
            log.error("删除人脸失败", e);
            throw new RuntimeException("删除人脸失败: " + e.getMessage());
        }
    }
    
    /**
     * 人员验证
     * @param imageBase64 人脸图片Base64编码
     * @param personId 人员ID
     * @return 验证结果
     */
    public VerifyPersonResponse verifyPerson(String imageBase64, String personId) {
        try {
            IaiClient client = createClient();
            VerifyPersonRequest req = new VerifyPersonRequest();
            req.setImage(imageBase64);
            req.setPersonId(personId);
            req.setNeedRotateDetection(1L);
            
            return client.VerifyPerson(req);
        } catch (TencentCloudSDKException e) {
            log.error("人员验证失败", e);
            throw new RuntimeException("人员验证失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建腾讯云客户端
     */
    private IaiClient createClient() {
        Credential cred = new Credential(secretId, secretKey);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(ENDPOINT);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new IaiClient(cred, "", clientProfile);
    }
}
