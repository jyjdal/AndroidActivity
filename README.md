# Android Activity

## 实现的功能
1. Login Activity和Main Activity的互相切换以及二者之间的双向数据传送
2. 通过点击按钮访问特定的网页
3. 应用使用自定义的标题和图片（新增）
4. 使用配置文件配置访问的网页URL，以及配置两个Activity传送页面时对应的key，便于统一管理、消除魔法值（新增）
5. 因为原有的API已经废弃（Deprecated），因此使用更新的API实现功能
6. 从Main Activity回传的数据会在Login Activity以对话框的形式显示（新增）