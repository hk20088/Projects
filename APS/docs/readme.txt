
● 这里记录本项目中需要注意的点，或者关键模块的逻辑，方便团队成员快速上手及维护。

一、判断支付方式模块重构   -胡起立 2017年2月17日
概述：重构了判断支付方式模块代码，用策略模式代替if else条件判断。修改符合开闭原则，方便以后扩展及维护
关键词：支付方式模块 、 策略模式、反射机制、泛型

包说明：
Ⅰ  包 com.newspace.aps.service 提供thrift接口方法，供终端调用
Ⅱ  包 com.newspace.aps.pay 提供具体的支付逻辑，包括判断支付方式，校验等模块
Ⅲ  包 com.newspace.aps.pay.strategy 提供策略模式的抽象策略类、环境类及定义了公共变量的抽象类
Ⅳ  包 com.newspace.aps.payChannel 提供每个支付渠道的逻辑代码

类说明：
Ⅰ 类 com.newspace.aps.pay.PayHandler.java 支付主操作类，提供方法pay(...)供接口调用，返回处理后的支付方式
Ⅱ 类 com.newspace.aps.payChannel.ACoinHandler.java 提供实现了A豆支付逻辑的方法，供PayHandler类调用
     备注：每种支付渠道都会有一个xxxHandler类，提供对外接口，供主操作类调用，这里不一一列举，仅以A豆为例说明
Ⅲ 类 com.newspace.aps.pay.strategy.IPayStrategy.java 抽象策略类，定义了调用者需要的方法，所有具体策略类都要实现此接口
Ⅳ 类 com.newspace.aps.pay.strategy.Context.java 环境类，提供给调用者，用来确定使用哪个具体的策略类
Ⅴ 类 com.newspace.aps.pay.strategy.Basestrategy.java 策略类基类，定义了一些公共变量 
Ⅵ 类 com.newspace.aps.payChannel.ACoinStrategy.java A豆支付的具体策略类，继承BaseStrategy类，实现IPayStrategy接口。用来具体的逻辑
    备注：每种支付渠道都会有一个xxxStrategy类，均继承BaseStrategy类，实现IPayStrategy接口，用来实现每种支付方式的具体逻辑，这里不一一列举，仅以A豆为例说明
Ⅶ 类 com.newspace.aps.pay.param.PayTypeEnum.java 枚举类，定义了每种支付方式对应的中文名称和对应的策略类的包名

PS：以上只列举出了部分关键的包和类，其它详见源码。

流程说明：
                    调用pay(...)方法                                                              调用pay(...)方法                                                         调用pay(...)方法                                                             调用pay(...)方法                                                          调用方法不定
client———————————————>PayServerImpl.java———————————————>PayHandler.java—————————————————>IPayStrategy.java————————————————>xxxStrategy.java——————————>xxxHandler.java

问题：如果新增一种支付渠道，要将该渠道的支付方式整合到项目中，该怎么做？
1、在包com.newspace.aps.payChannel下定义此支付渠道的包，比如厦门广电 ：com.newspace.aps.payChannel.xiamen，并实现具体的支付逻辑
2、在xiamen包下定义XiaMenHandler.java类，提供对外方法，实现支付的最终结果
3、在xiamen包下定义XiaMenStrategy.java类，实现IPayStrategy.java接口，重载pay(...)方法，按要求返回结果
4、在com.newspace.aps.pay.param.PayTypeEnum.java 枚举类中定义此支付方式
5、单元测试

总结：
1、用策略模式代替if else条件判断主要是为了实现设计模式中的开-闭原则，即对扩展开放，对修改关闭。这样在新增一种支付渠道时，不需要修改主接口及主操作类的任何代码，开发者只需要关注当前渠道的业务逻辑即可。
2、在主操作类PayHandler.java中，是通过反射机制动态拿到具体要调用的支付渠道。反射本身会消耗性能，如果在循环中需要动态获取的类比较多，建议将此段逻辑放到静态块中，系统启动时拿到所有的类，放到集合中， 用的时候直接从集合取即可。
 

