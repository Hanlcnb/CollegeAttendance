// 请求工具使用示例
// 注意：这个文件仅作为使用示例，实际使用时请删除此文件
import { request, get, post, put, del } from './request';

// 示例1: 使用通用 request 方法
export const fetchUserInfo = async (userId: string) => {
  try {
    const response = await request({
      url: '/api/user/info',
      method: 'GET',
      data: { userId }
    });
    return response.data;
  } catch (error) {
    console.error('获取用户信息失败:', error);
    throw error;
  }
};

// 示例2: 使用便捷方法
export const fetchCourseList = async () => {
  try {
    const response = await get('/api/courses/list');
    return response.data;
  } catch (error) {
    console.error('获取课程列表失败:', error);
    throw error;
  }
};

// 示例3: 发送 POST 请求
export const createMessage = async (messageData: any) => {
  try {
    const response = await post('/api/messages/create', messageData);
    return response.data;
  } catch (error) {
    console.error('创建消息失败:', error);
    throw error;
  }
};

// 示例4: 跳过自动添加 Authorization（用于登录等不需要 token 的请求）
export const login = async (loginData: any) => {
  try {
    const response = await request({
      url: '/api/login',
      method: 'POST',
      data: loginData,
      skipAuth: true // 跳过自动添加 Authorization
    });
    return response.data;
  } catch (error) {
    console.error('登录失败:', error);
    throw error;
  }
};

// 示例5: 自定义 header（会与 Authorization 合并）
export const uploadFile = async (fileData: any) => {
  try {
    const response = await request({
      url: '/api/upload',
      method: 'POST',
      data: fileData,
      header: {
        'Content-Type': 'multipart/form-data'
      }
    });
    return response.data;
  } catch (error) {
    console.error('文件上传失败:', error);
    throw error;
  }
};
