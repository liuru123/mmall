mmall_learning

商品模块：
    前台功能：
        产品搜索、动态排序列表、产品详情
    后台功能：
        商品列表、 商品搜索、图片上传
        富文本上传、商品详情、商品上下架
        增加商品、更新商品
    学习目标--
        ftp服务的对接
        SpringMVC文件上传
        流读取Properties配置文件
        抽象POJO、BO、VO对象之间的转换关系及解决思路
        joda-time快速入门
        静态块
        Mybatis-PageHelper高效准确的分页及动态排序
        Mybatis对list遍历的实现方法
        Mybatis对where语句动态拼装的几个版本演进

    门户接口：
    1.产品搜索+动态排序+分页查找
    request:categoryId,keyword,pageNum(default=1)
            pageSize(default=10) 
            orderBy(default="") 排序参数：例如price_desc,price_asc
    response:list:{}
    2.产品详情
    request:productId
    response:product{}
    
    后台接口：
    1.产品列表
        request:pageNum(default=1)
                pageSize(default=10)
        response:list{}
    2.产品搜索
        request:productName
                productId
                pageNum(default=1)
                pageSize(default=10)
        response:list{}
    3.图片上传
        request:springMVC上传图片
        response:ftp服务器上存储的图片的路径
        
    4.产品详情
          request:productId
          response:product{}
   ··· 5.产品上下架
          request:productId
                  status
          response:info(修改成功或是失败的信息）
          当然前提是有管理员的权限
   ··· 6.新增/更新产品
           request:product
           (根据id判断是新增还是更新）
           response:info(新增或者更新成功/失败的信息)
    7.富文本文件上传
            request:form表单
            response:富文本的格式
    
    
    
购物车模块：
    功能介绍---
        加入购物车 更新商品数  
        查询商品数  移除商品  
        单选/取消  全选/取消
        购物车列表
    学习目标---
        1.购物车模块的设计思想
        2.如何封装一个高复用购物车核心方法
        3.解决浮点型商业运算中丢失精度的问题
     -------------------------------------------------------------
     -   answer:利用BigDecimal的String构造器方法就可以解决丢失精度的问题
     -   BigDecimal b1 = new BigDecimal("0.05");
     -   BigDecimal b2 = new BigDecimal("0.01");
     -   System.out.println(b1.add(b2);
     -   封装BigDecimal的精度问题，就不用和数据库之中的数据进行不停地转换
     --------------------------------------------------------------
    门户接口---
        1.购物车list
            request：无参数，但需要登录状态下查看
            response:一个购物车详情的列表
        2.购物车添加add
            request：productId,count
            response:一个购物车当前详情的列表（包含limitQuantity字段
                表示限制成功或者失败)      
        3.更新购物车中某个商品的数量
            request:productId,count
            response:一个购物车当前详情的列表（包含limitQuantity字段
                                  表示限制成功或者失败) 
        4.移除购物车中的某个商品
            request:productIds(可以一起移除)
            response:同上
        5.购物车选中某商品
            request:productId
            response:同上
        6.取消选中某商品
            同上
        7.查询购物车商品数量
            request:无
            response:失败和成功返回字段
        8.购物车全选
            request:无
            response:一个购物车当前详情的列表（包含limitQuantity字段
                                          表示限制成功或者失败) 
        9.购物车取消全选
            同上
            
            
添加收货地址模块：
    功能介绍--
        添加地址   删除地址   更新地址  
        地址列表   地址分页   地址详情
        
    学习目标---
        SpringMVC 数据绑定中对象绑定
        mybatis自动生成主键、配置和使用
        如何避免横向越权漏洞的巩固
    门户接口---
        1.添加地址：
            request:地址的详情
            response:{status,msg,data}等
        2.删除地址：
            request:shippingId
            response:删除成功或者失败的msg
        3.登录状态更新地址：
            request:id,更新地址的字段
            response:更新成功或者失败的msg
        4.查看地址详情：
            request:shippingId
            response:地址详情
        5.地址列表且分页：
            reqeust:pageNum,pageSize
            response:地址列表
    
    
订单模块功能：
    学习目标：
        1.避免业务逻辑中横向纵向越权的问题  
        2.设计实用、安全、扩展性强大的常量、枚举类
        3.订单号生成规则，订单严谨性判断
        4.POJO与VO之间的实际操练
        5.Mybatis批量插入
        
        
        
       功能介绍：
           ---门户接口---
                1.create(创建订单)
                    将收货地址的id传入将购物车中的东西全部入到order
                    库和order_item库中，清空购物车，减掉product库
                    的库存
                2.select(获取订单的商品详情)
                    request不需要，只需要传入userId就可以获取订单详情
                    将image_host传入与product_image拼接成商品图片
                3.list(订单列表页)
                    获取订单列表的展示，将获取到的订单详情放在VO类中进行
                    展示
                4.detail(订单详情)
                    orderNo传入，获取订单的所有明细，是一个VO（多个VO组装而成)
                
                5.cancel(取消订单)
                    orderNo传入，取消订单，成功返回0,失败分为，订单不存在
                    和订单已付款不可取消
                 
            ---后台接口---
                1.list(订单列表页）
                    无需和userId关联
                    
                2.按订单号查询
                    分页展示
                    
                3.订单详情
                
                4.订单发货
                    orderNo
                    判断订单状态是否是已发货，是才可发货
                    
        
          
        