import { mockSuccess, mockError, mockDelay, MockResponse } from './index';

// 定义请求配置接口
interface RequestOptions {
  url: string;
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE';
  data?: any;
  header?: Record<string, string>;
  timeout?: number;
}

// 定义请求响应接口
interface RequestResponse<T = any> {
  data: MockResponse<T>;
  statusCode: number;
  header: Record<string, string>;
}

// 定义模拟数据的类型
interface UserInfo {
  id: number;
  name: string;
  avatar: string;
}

interface TodoList {
  list: Array<{
    id: number;
    title: string;
    completed: boolean;
  }>;
  total: number;
}

// 模拟请求处理函数
export const mockRequest = async <T>(options: RequestOptions): Promise<RequestResponse<T>> => {
  const { url, method = 'GET', data, timeout = 1000 } = options;

  // 模拟网络延迟
  await new Promise(resolve => setTimeout(resolve, timeout));

  // 根据不同的 URL 和 method 返回不同的模拟数据
  switch (url) {
    case '/api/user/info': {
      const response = {
        data: mockSuccess<UserInfo>({
          id: 1,
          name: '测试用户',
          avatar: 'https://example.com/avatar.png'
        }),
        statusCode: 200,
        header: { 'content-type': 'application/json' }
      };
      return response as unknown as RequestResponse<T>;
    }

    case '/api/todo/list': {
      const response = {
        data: mockSuccess<TodoList>({
          list: [
            { id: 1, title: '完成项目', completed: false },
            { id: 2, title: '写文档', completed: true }
          ],
          total: 2
        }),
        statusCode: 200,
        header: { 'content-type': 'application/json' }
      };
      return response as unknown as RequestResponse<T>;
    }

    case '/api/error': {
      const response = {
        data: mockError('模拟错误响应'),
        statusCode: 500,
        header: { 'content-type': 'application/json' }
      };
      return response as unknown as RequestResponse<T>;
    }

    default: {
      const response = {
        data: mockError('接口不存在'),
        statusCode: 404,
        header: { 'content-type': 'application/json' }
      };
      return response as unknown as RequestResponse<T>;
    }
  }
};

// 封装 wx.request 的模拟函数
export const request = <T>(options: RequestOptions): Promise<RequestResponse<T>> => {
  // 在开发环境下使用 mock 数据
  const accountInfo = wx.getAccountInfoSync();
  if (accountInfo.miniProgram.envVersion === 'develop') {
    return mockRequest<T>(options);
  }

  // 在生产环境下使用真实的 wx.request
  return new Promise((resolve, reject) => {
    wx.request({
      ...options,
      success: (res) => {
        resolve({
          data: res.data as MockResponse<T>,
          statusCode: res.statusCode,
          header: res.header
        });
      },
      fail: reject
    });
  });
}; 