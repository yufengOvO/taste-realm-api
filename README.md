# 菜谱后端

基于 Spring Boot + MyBatis Plus 的菜谱平台后端系统，支持微信小程序用户端和管理后台。

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.4.4 | 应用框架 |
| MyBatis Plus | 3.4.2 | ORM 框架 |
| MySQL | 8.0+ | 数据库 |
| Druid | 1.2.1 | 数据库连接池 |
| Kaptcha | 2.3.2 | 图形验证码 |
| FastJSON | 1.2.68 | JSON 处理 |
| Lombok | 1.18.12 | 代码简化 |
| Swagger | 3.0.0 | API 文档 |

## 项目结构

```
zhuanzhuan-dev/
├── shop-base-common/          # 公共模块（状态码、工具类）
│   └── shop-base-common/
│       └── src/main/java/edu/cdtu/
│           ├── status/        # 状态码定义
│           └── utils/         # ResultUtils、ResultVo
├── shop-base-web/             # Web 主模块
│   └── shop-base-web/
│       └── src/main/java/edu/cdtu/
│           ├── annotation/    # 自定义注解（@Auth）
│           ├── config/        # 配置类（MVC、MyBatis、验证码）
│           ├── upload/        # 文件上传
│           └── web/           # 业务模块
│               ├── goods/               # 菜品管理
│               ├── goods_category/      # 菜品分类
│               ├── goods_swiper/        # 轮播图管理
│               ├── ingredients_category/# 食材分类
│               ├── sys_menu/            # 系统菜单
│               ├── sys_user/            # 管理员用户
│               ├── user_collection/     # 用户收藏
│               ├── user_menu/           # 用户菜单关联
│               └── wx_user/             # 微信小程序用户
└── pom.xml                    # 父 POM
```

## 快速启动

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+

### 数据库配置

1. 创建数据库 `usedShop`
2. 导入数据库脚本
3. 修改 `application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/usedShop?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: root
    password: 123456
```

### 启动项目

```bash
# 克隆项目
git clone https://github.com/your-username/zhuanzhuan-dev.git
cd zhuanzhuan-dev

# 编译打包
mvn clean package

# 运行
java -jar shop-base-web/shop-base-web/target/shop-base-web.jar
```

服务默认运行在 `http://localhost:8089`

---

## API 接口文档

所有接口返回统一格式：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

---

### 1. 菜品管理 (`/api/goods`)

| # | 方法 | 路径 | 说明 | 参数 |
|---|------|------|------|------|
| 1 | POST | `/api/goods/release` | 发布新菜品 | Body: `Goods` 对象 |
| 2 | POST | `/api/goods/addIngred` | 发布菜品关联食材 | Body: `CookIngredParm` 对象 |
| 3 | GET | `/api/goods/list` | 后台分页查询菜品 | Query: `currentPage`, `pageSize`, `goodsName` |
| 4 | POST | `/api/goods/getByingred` | 通过食材ID查询菜品 | Body: `List<Long>` 食材ID数组 |
| 5 | POST | `/api/goods/delete` | 逻辑删除菜品 | Body: `{ "goodsId": 1 }` |
| 6 | POST | `/api/goods/upanddown` | 菜品上下架 | Body: `{ "goodsId": 1, "status": "0" }` |
| 7 | POST | `/api/goods/setIndex` | 设置首页推荐 | Body: `{ "goodsId": 1, "setIndex": "1" }` |
| 8 | GET | `/api/goods/getIndexList` | 小程序首页推荐列表 | Query: `currentPage`, `pageSize`, `keywords` |
| 9 | GET | `/api/goods/getcooksCategory` | 按分类查询菜品 | Query: `currentPage`, `pageSize`, `keywords`(分类名) |
| 10 | POST | `/api/goods/getcooksWiter` | 查询用户发布的菜品 | Body: `{ "currentPage": 1, "pageSize": 10, "keywords": "userId" }` |

---

### 2. 菜品分类 (`/api/category`)

| # | 方法 | 路径 | 说明 | 参数 |
|---|------|------|------|------|
| 1 | POST | `/api/category` | 新增分类 | Body: `GoodsCategory` 对象 |
| 2 | PUT | `/api/category` | 编辑分类 | Body: `GoodsCategory` 对象 |
| 3 | DELETE | `/api/category/{categoryId}` | 删除分类 | Path: `categoryId` |
| 4 | GET | `/api/category/list` | 分页查询分类 | Query: `currentPage`, `pageSize`, `categoryName` |
| 5 | GET | `/api/category/getSelectList` | 获取分类下拉列表 | 无参数 |

---

### 3. 轮播图管理 (`/api/swiper`)

| # | 方法 | 路径 | 说明 | 参数 |
|---|------|------|------|------|
| 1 | POST | `/api/swiper/addswipr` | 新增轮播图 | Body: `GoodsSwiper` 对象 |
| 2 | GET | `/api/swiper/getList` | 后台分页查询 | Query: `currentPage`, `pageSize`, `title` |
| 3 | GET | `/api/swiper/getSwiperList` | 小程序获取启用的轮播图 | Query: `currentPage`, `pageSize`, `title` |
| 4 | POST | `/api/swiper/del` | 删除轮播图 | Body: `{ "banId": 1 }` |
| 5 | POST | `/api/swiper/stopSwiper` | 启用/停用轮播图 | Body: `{ "banId": 1, "status": "0" }` |

---

### 4. 食材分类 (`/api/ingredients`)

| # | 方法 | 路径 | 说明 | 参数 |
|---|------|------|------|------|
| 1 | POST | `/api/ingredients` | 新增食材分类 | Body: `IngredientsCategory` 对象 |
| 2 | PUT | `/api/ingredients` | 编辑食材分类 | Body: `IngredientsCategory` 对象 |
| 3 | DELETE | `/api/ingredients/{categoryId}` | 删除食材分类 | Path: `categoryId` |
| 4 | GET | `/api/ingredients/list` | 分页查询食材分类 | Query: `currentPage`, `pageSize`, `ingredName` |
| 5 | GET | `/api/ingredients/getSelectList` | 获取食材下拉列表 | 无参数 |

---

### 5. 系统菜单 (`/api/menu`)

| # | 方法 | 路径 | 说明 | 参数 |
|---|------|------|------|------|
| 1 | POST | `/api/menu` | 新增菜单 | Body: `SysMenu` 对象 |
| 2 | PUT | `/api/menu` | 编辑菜单 | Body: `SysMenu` 对象 |
| 3 | DELETE | `/api/menu/{menuId}` | 删除菜单 | Path: `menuId` |
| 4 | GET | `/api/menu/list` | 获取菜单树形列表 | 无参数 |
| 5 | GET | `/api/menu/getParent` | 获取上级菜单列表 | 无参数 |
| 6 | GET | `/api/menu/getAssignTree` | 菜单分配树形数据 | Query: `userId`, `assId` |
| 7 | POST | `/api/menu/assignSave` | 保存菜单分配 | Body: `AssignParm` 对象 (需@Auth认证) |

---

### 6. 管理员用户 (`/api/sysUser`)

| # | 方法 | 路径 | 说明 | 参数 |
|---|------|------|------|------|
| 1 | POST | `/api/sysUser` | 新增管理员 | Body: `SysUser` 对象 (密码MD5加密) |
| 2 | PUT | `/api/sysUser` | 编辑管理员 | Body: `SysUser` 对象 |
| 3 | DELETE | `/api/sysUser/{userId}` | 删除管理员 | Path: `userId` |
| 4 | GET | `/api/sysUser/getList` | 分页查询管理员 | Query: `currentPage`, `pageSize`, `nickName` |
| 5 | POST | `/api/sysUser/image` | 生成图形验证码 | 无参数 (返回Base64图片) |
| 6 | POST | `/api/sysUser/login` | 管理员登录 | Body: `{ "username": "", "password": "", "code": "" }` |
| 7 | PUT | `/api/sysUser/updatePassword` | 修改密码 | Body: `{ "userId": 1, "oldPassword": "", "password": "" }` |

**登录响应示例：**
```json
{
  "code": 200,
  "data": {
    "userId": 1,
    "nickName": "管理员",
    "menuList": [...],
    "codeList": [...]
  }
}
```

---

### 7. 用户收藏 (`/api/userCollection`)

| # | 方法 | 路径 | 说明 | 参数 |
|---|------|------|------|------|
| 1 | POST | `/api/userCollection/list` | 查询用户收藏列表 | Body: `userId` (Long) |
| 2 | DELETE | `/api/userCollection/del` | 删除收藏 | Body: `goodsId` (Long) |
| 3 | POST | `/api/userCollection/collect` | 收藏/取消收藏 | Body: `{ "userId": 1, "goodsId": 1 }` (需@Auth) |
| 4 | GET | `/api/userCollection/hasCollect` | 查询是否已收藏 | Query: `userId`, `goodsId` (需@Auth) |

---

### 8. 微信小程序用户 (`/api/wxUser`)

| # | 方法 | 路径 | 说明 | 参数 |
|---|------|------|------|------|
| 1 | POST | `/api/wxUser/register` | 用户注册 | Body: `WxUser` 对象 (密码MD5加密) |
| 2 | POST | `/api/wxUser/login` | 用户登录 | Body: `{ "username": "", "password": "" }` |
| 3 | GET | `/api/wxUser/list` | 后台分页查询用户 | Query: `currentPage`, `pageSize`, `phone` |
| 4 | POST | `/api/wxUser/stopUser` | 停用/启用用户 | Body: `{ "userId": 1, "status": "0" }` |
| 5 | POST | `/api/wxUser/updatePassword` | 重置密码(默认666666) | Body: `{ "userId": 1 }` |
| 6 | DELETE | `/api/wxUser/{userId}` | 删除用户 | Path: `userId` |

**登录响应示例：**
```json
{
  "code": 200,
  "data": {
    "userId": 1,
    "nickName": "用户昵称",
    "phone": "13800138000",
    "picture": "/images/avatar.jpg"
  }
}
```

---

### 9. 文件上传 (`/api/upload`)

| # | 方法 | 路径 | 说明 | 参数 |
|---|------|------|------|------|
| 1 | POST | `/api/upload/uploadImage` | 上传图片 | FormData: `file` (MultipartFile) |

**响应示例：**
```json
{
  "code": 200,
  "data": "/images/uuid-filename.jpg"
}
```

---

## 接口汇总

| 模块 | 基础路径 | 接口数量 |
|------|---------|---------|
| 菜品管理 | `/api/goods` | 10 |
| 菜品分类 | `/api/category` | 5 |
| 轮播图 | `/api/swiper` | 5 |
| 食材分类 | `/api/ingredients` | 5 |
| 系统菜单 | `/api/menu` | 7 |
| 管理员 | `/api/sysUser` | 7 |
| 用户收藏 | `/api/userCollection` | 4 |
| 小程序用户 | `/api/wxUser` | 6 |
| 文件上传 | `/api/upload` | 1 |
| **合计** | | **50** |

## 权限说明

- 带有 `@Auth` 注解的接口需要登录认证
- 管理员登录后获取菜单和权限码列表
- 超级管理员不可被分配菜单权限

## 配置说明

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| `server.port` | 服务端口 | 8089 |
| `spring.datasource.url` | 数据库连接 | jdbc:mysql://localhost:3306/usedShop |
| `web.uploadpath` | 图片上传路径 | D:/images/ |
| `spring.servlet.multipart.maxFileSize` | 最大上传文件大小 | 1024MB |

## License

MIT License
