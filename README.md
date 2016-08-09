# 功能
      一个银行业务系统的简单实现
#背景
     5月份的时候在西安一家公司找了的一个实习，刚开始的三个星期就是一个银行业务系统的项目实战，项目其中包含了他们公司金融部门的一项重要业---ESB。当然这个项目中ESB只做了一个防串包的处理，并没有像实际业务那样做的特别复杂。三个星期过后通过了考核，项目经理就说可以留着公司继续实习，并且可以免校招。当时我还是有点纠结，如果留下来代表秋招这段艰苦的日子我就可以免去了，但是同时代表我放弃了秋招，放弃了为自己争取更好工作环境的机会，也放弃了跟更优秀的人在一起的机会。再三决定后，还是放弃留在公司，去尝试一下校招，虽然进入大公司的几率甚微，但是梦想还是要有的，万一实现了呢。发现自己扯开鸡汤了......
#项目的结构
      Teller(tomcat+servlet)+ESB+MQ+Core
      项目做好之后，Teller端，ESB端，Core端是要放在不同的服务器上面的(但是我们最终做好的并没有部署到服务器，只是在本机运行)，因此Teller端和ESB端的通信是通过建立Socket，ESB和核心端的通信是通过消息队列。
#遇到的难点
      1)一开始的时候是对这个架构不能理解。因为像ssh架构，你能很清楚的知道为什么要用它。也能很清楚的知道用了它之后有什么好处。但是对于这个架构，关于ESB和MQ这两次的出现，即不知道这两次是什么，也不知道为什么要用，用了有什么好处。所以刚开始的几天里一直都没有着手去做。而是上网搜资料，和组员交流，再就是不厌其烦的问带我们的老师。最后才算是对着个架构有了初步的认识。
      2）之后是对银行的业务流程不太清楚，最后是在老师讲解下弄明白了。
      3）Teller层负责数据的采集，但是采集后的数据要给ESB，那数据的格式是怎样的，不同业务操作不一样，如何把众多的业务数格式统一起来。最后经讨论和交流：数据格式统一采用xml格式，xml的组装和拆分均采用apache的Dom4j实现。这样格式就统一了。但是又一想，十个业务难不成有十个组装方式，突然觉得不仅麻烦也不易扩展，最后采用的解决方法就是Apache的BeanUtil工具。组装问题解决了，但是拆分业务好像必须一个业务对应一个方法。
     4）数据到达核心端时，如何去调用相应的Dao类。最后的解方法是：利用一个配置文件映射每个业务对应的不同Dao类。然后利用反射去调用对应的类。
  暂时就这这些难点吧。
#总结
     最深的感觉就是一个好的系统结构对一个系统真的很重要。在该项目中就有深刻的体会。整个项目的框架搭建起来之后，实现基本业务后在扩展一个业务真的很容易。
     不过这样结构的系统其实包含了很多的知识，只是在实习阶段老师本身没有提到。但是这个项目过后，还是发现自己的不足，大流量高并发，分布式，以及分布式系统之间的通信都是自己需要学习的。
