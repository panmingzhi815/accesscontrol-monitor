# accesscontrol-monitor
门禁监控大屏显示程序
===================================

主要用于重要岗口显示进出人员资料信息，包括大图贴、用户名、编号、地址、分组、身份证等一系列门禁事件触发后的卡片用户资料。

所有的显示都需在软件根目录下的AppConfigurator.properties文件中进行配置，详细说明如下：

1.背景图片设置：
----------------------------------- 

背景图片为当前目录下的backgroup.jpg,只需同名替换即可


2.数据库连接参数设备：
----------------------------------- 

sqlserver数据库ip地址：

sqlserver_ip=127.0.0.1

sqlserver数据库端口：

sqlserver_port=1433

sqlserver数据库名称：

sqlserver_database_name=onecard

sqlserver数据库登录名：

sqlserver_database_username=sa

sqlserver数据库登录密码：

sqlserver_database_password=1

sqlserver数据库轮询设备编码：

poll_device_identifier='d2_1','d2_2'

sqlserver数据库轮询事件类型（第一个刷卡事件在数据库中对应一个编码，1表示非法刷卡，0表示正常刷卡，2表示正常开门，3内读头正常开门，4非法开门，4内读头非法开门）:
poll_device_eventType=0,1

sqlserver数据库轮询间隔：

poll_interval=100


###3.用户大图贴的设置
----------------------------------- 

大图贴x轴坐标：

header_x=120

大图贴y轴坐标：

header_y=150

大图贴宽度：

header_width=380

大图贴高度：

header_height=580

大图贴sql数据来源：

header_sql=select headImage from CardUser where identifier = ?


###4.界面显示标签的设置（所有界面上显示的文字都属于标签，设置方法相同），现在举例在界面上显示一个大标题标签，
自定义标签名可以随意，如text1
----------------------------------- 

text1标签x轴：

text1_x=480


text1标签y轴：

text1_y=30

text1标签宽度：

text1_width=600

text1标签高度：

text1_height=100

text1标签字号大小：

text1_size=30

text1标签字体名称：

text1_family=宋体

text1标签字体颜色(该值为rgb、值，通过三个值的搭配可显示任意颜色,0 0 0 表示为黑色)：

text1_color=0 0 0

text1标签sql数据来源（大标题的显示内容只需修改下面的‘深圳市东陆高新实业有限公司’即可）：

text1_sql=select '深圳市东陆高新实业有限公司' where 'xxxx' != ?


###5.标签显示声明：
----------------------------------- 

因为标签的签名是可随意编写的，如text1,title1,label2等，所以在界面上要显示的标签设置的详细的参数后还需声明，
软件在启动时会自动加载这些标签，然后再根据标签签名来设置具体的长宽高

exist_label=text1,title1,label2


###6.标签具体显示内容的说明：
----------------------------------- 

当我们要显示一个固定的静态标签时，需设置的sql参数为 ：

select '这里是要显示的内容' where 'xxxx' != ?

当要显示一个从数据库查询的动态标签时，需设置的sql参数为：

select name from CardUser where identifier = ?

其中的name为CardUser表中的姓名，其他用户资料对应如下：

identifier－－－－－－用户编号

name－－－－－－－－－用户名

birthday－－－－－－－出生日期

sex－－－－－－－－－-姓别

email－－－－－－－－-联系邮箱

IDCard－－－－－－－－身份证号

country－－－－－－－-所属国家

nationality－－－－－-民族

mobilephone－－－－－-移动电话

fixedPhone－－－－－－固定电话

address－－－－－－－-住址

groupCodeNameJoinStr--用户组名

cardSNJoinStr---------所有卡片编码


