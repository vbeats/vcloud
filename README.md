# vcloud

```bash
gradle xxx:build
```

## auth

`token` 有效期默认 `30d` 活跃超时: `7200s`

`web` 端 有效期`4h` 活跃超时: `7200s`  `refreshToken`可以重置超时时间`4h`

生产环境下 `Same-Token` 需要定时刷新 [desc](https://sa-token.cc/doc.html#/micro/same-token)

暂时没用到`same-token` , 服务间`feign`请求依然携带外部`token`

## role & permission

`super_admin` `admin` `*` `.` 保留字 , 不允许指定

## faq

- `admin` `tenant` `role` 表`id`为 `1`的记录为系统超级管理员 禁止修改
- 系统模块 `service-admin` 相关业务优化 : 查询`cache`

## todo

- [ ] [doc](https://doc.bootvue.com) 在搞
- [ ] 接口文档: 等knife4j 4.x or 用 apipost