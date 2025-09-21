// Mock 数据接口定义
export interface MockResponse<T> {
  code: number;
  data: T;
  message: string;
}

// 模拟成功响应
export const mockSuccess = <T>(data: T): MockResponse<T> => ({
  code: 200,
  data,
  message: 'success'
});

// 模拟错误响应
export const mockError = (message: string): MockResponse<null> => ({
  code: 500,
  data: null,
  message
});

// 模拟延迟响应
export const mockDelay = <T>(data: T, delay: number = 1000): Promise<MockResponse<T>> => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(mockSuccess(data));
    }, delay);
  });
};

// 模拟分页数据
export interface PaginationData<T> {
  list: T[];
  total: number;
  page: number;
  pageSize: number;
}

// 模拟分页响应
export const mockPagination = <T>(
  list: T[],
  total: number,
  page: number = 1,
  pageSize: number = 10
): MockResponse<PaginationData<T>> => {
  return mockSuccess({
    list,
    total,
    page,
    pageSize
  });
}; 