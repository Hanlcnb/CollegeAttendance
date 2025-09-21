// 通用请求工具，自动添加 Authorization header
interface RequestOptions {
  url: string;
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE';
  data?: any;
  header?: Record<string, string>;
  timeout?: number;
  skipAuth?: boolean; // 是否跳过自动添加 Authorization
}

interface RequestResponse<T = any> {
  data: T;
  statusCode: number;
  header: Record<string, string>;
}

// 获取全局 token 的函数
const getToken = (): string => {
  try {
    const app = getApp();
    return app.globalData?.token || '';
  } catch (error) {
    console.warn('获取 token 失败:', error);
    return '';
  }
};

// 自动添加 Authorization header 的函数
const addAuthHeader = (options: RequestOptions): RequestOptions => {
  // 如果设置了跳过认证，直接返回原配置
  if (options.skipAuth) {
    return options;
  }

  const token = getToken();
  if (!token) {
    console.warn('未找到 token，请求将不包含 Authorization header');
    return options;
  }

  // 合并 header，确保 Authorization 不被覆盖
  const header = {
    ...options.header,
    'Authorization': `Bearer ${token}`
  };

  return {
    ...options,
    header
  };
};

// 封装的请求函数，自动添加 Authorization header
export const request = <T = any>(options: RequestOptions): Promise<RequestResponse<T>> => {
  // 自动添加 Authorization header
  const optionsWithAuth = addAuthHeader(options);

  return new Promise((resolve, reject) => {
    wx.request({
      ...optionsWithAuth,
      success: (res) => {
        resolve({
          data: res.data as T,
          statusCode: res.statusCode,
          header: res.header
        });
      },
      fail: reject
    });
  });
};

// 便捷方法
export const get = <T = any>(url: string, data?: any, options?: Partial<RequestOptions>): Promise<RequestResponse<T>> => {
  return request<T>({
    url,
    method: 'GET',
    data,
    ...options
  });
};

export const post = <T = any>(url: string, data?: any, options?: Partial<RequestOptions>): Promise<RequestResponse<T>> => {
  return request<T>({
    url,
    method: 'POST',
    data,
    ...options
  });
};

export const put = <T = any>(url: string, data?: any, options?: Partial<RequestOptions>): Promise<RequestResponse<T>> => {
  return request<T>({
    url,
    method: 'PUT',
    data,
    ...options
  });
};

export const del = <T = any>(url: string, data?: any, options?: Partial<RequestOptions>): Promise<RequestResponse<T>> => {
  return request<T>({
    url,
    method: 'DELETE',
    data,
    ...options
  });
};
