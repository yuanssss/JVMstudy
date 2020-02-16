# JVM学习笔记

## 类加载

 在java代码中，类型的加载、连接和初始化过程中是在程序运行的过程中完成的：

- 加载：根据类的全限定类名查找并加载类的二进制数据

- 连接
  - 验证：确保被加载的类的正确性
  - 准备：为类的**静态变量**分配内存，并将其初始化为默认值，比如int类型默认值为0.
  - 解析：在类的常量池中寻找类、接口、字段和方法的符号引用，把类中的符号引用转为直接引用

- 初始化：为类的**静态变量**赋与正确的初始值

  

类的实例化：

 为新的对象分配内存

为实例变量赋默认值

为实例变量赋与正确的初始值

在如下情况下，java虚拟机将结束生命周期：

- 执行了System.exit（）方法

- 程序正常之行结束
- 程序在执行过程中遇到异常或错误而异常终止
- 操作系统错误导致java虚拟机终止

java程序对于类的使用方式分为两种：

- 主动使用
  - 创建类的实例
  
  - 访问每个类或接口的静态变量，或者对该静态变量赋值
  
  - 调用类的静态方法 
  
  - 反射（如Class.forName）
  
  - 初始化一个类的子类（参考MyTest1）
  
  - Java虚拟机启动时被标明为启动类的类(XXX.java)
  
  - JDK1.7开始提供的动态语言支持：
  
    java.lang.invoke.MethodHandle实例的解析结果REF_getStatic, REF_putStatic,REF_invokeStatic句柄对应的类没有初始化，则初始化 
  
- 被动使用

  - 出了上面的七种情况，其他的都为被动使用，不会导致类的初始化

所有的java虚拟机实现必须在每个类或接口被java“**首次主动使用**”时才初始化他们

### 类的加载

- 类的加载是指将类的.class文件中的二进制数据读入到内存 中，将其放在运行时数据区的方法区内，然后在内存中创建一个java.lang.Class对象（规范并未说明Class对象位于哪里，HotSpot虚拟机将其放在方法区中）用来封装类在方法区内的数据结构。
- 加载.class文件的方式
  - 从本地系统中直接加载
  - 通过网络下载.class文件
  - 从zip，jar等归档文件中加载.class文件
  - 将Java源文件动态编译为.class文件
- 有两种类型的类加载器
  - Java虚拟机自带的加载器
    - 根类加载器（Bootstrap）
    - 扩展类加载器（Extension）
    - 系统（应用）类加载器（System）
  - 用户自定义的类加载器
    - java.lang.ClassLoader的子类
    - 用户定制类的加载方式
- 类加载器并不需要等到某个类被“首次使用”时再加载它
- JVM规范允许类加载器在预料某个类将要被使用时就预先加载它，如果在预先加载的过程中遇到了.class文件缺失或存在错误，类加载器必须在程序首次主动使用时才报错（LinkageError错误）。如果一直没有被主动使用那么类加载器就不会报错。

### 类的验证

- 类被加载后，就进入连接阶段。连接就是将已经读入到内存的类的二进制数据合并到虚拟机的运行时环境中去。

### 类的初始化

- 静态变量初始化有两个途径
  - 在声明处初始化
  - 在静态代码块中初始化 
- 类的初始化步骤
  - 假如这个类还没有被加载和连接，那就先进行加载和连接
  - 假如类存在直接父类，并且这个父类还没有被初始化，那就先初始化直接父类
  - 假如类中存在初始化语句，那就依次执行这些初始化语句
- 类的初始化时机
  - 主动使用（七种）
  - 当Java虚拟机初始化一个类时，要求它的所有父类都已经被初始化，但是这条规则并不适用于接口。只有程序首次使用特定接口的静态变量时，才会导致接口的初始化。
  - 调用ClassLoader的loadClass方法加载一个类，并不是对类的主动使用，不会导致类的初始化

### 类加载器的父亲（双亲）委托机制

- 在父亲委托机制中，各个加载器按照父子关系形成树形结构，除了根类加载器之外，其余的类加载器都有且只有一个父加载器
- ![](/Users/ys/Library/Application Support/typora-user-images/截屏2020-01-25下午3.08.37.png)

（loader1、loader2是自己定义的类加载器）

- 如果一个类加载器收到了类加载的请求，它首先不会自己去尝试加载这个类，而是把这个请求委派给父类加载器去完成，每一个层次的类加载器都是如此，因此所有的加载请求都应该传送到顶层的启动类加载器中，只有当父加载器反馈自己无法完成这个加载请求（它的搜索范围中没有找到所需的类）时，自己才会尝试去加载。

- 自底向上检查类是否被加载。自顶向下尝试加载类。

- 父加载器加载的类或者接口看不到子加载器加载的类或者接口，而子加载器加载的类或者接口能看到父加载器加载的类或者接口

- 命名空间：每一个类加载器都有自己的命名空间，命名空间由该类加载器及其所有父加载器所加载的类组成。

  在同一个命名空间中不会出现同名的类的情况，在不同命名空间可能会出现同名的类的情况。

- 双亲委派机制的优势：

  - **沙箱安全机制**：自己写的String.class类不会被加载，这样便可以防止核心API库被随意篡改
  - **避免类的重复加载：**当父亲已经加载了该类时，就没有必要子ClassLoader再  加载一次



### 获取类加载器的途径

![](/Users/ys/Library/Application Support/typora-user-images/截屏2020-01-30上午10.59.44.png)

### 类的卸载

当MySample类被加载、连接、初始化以后，它的生命周期就开始了。当代表MySample类的Class对象不再被引用，即不可触及时，class对象就会结束生命周期，MySample类在方法区内的数据也会被卸载，从而结束MySample类的生命周期。

由java虚拟机自带的类加载器所加载的类，在其所在的生命周期中始终不会被卸载。

----

jar hell问题：当一个类或者资源存在多个jar包的时候就是jar hell问题

### 线程上下文类加载器

```java
线程上线文类加载器（context classloader）
* 线程上下文类加载器是从jdk1.2开始启用的，通过getContextClassLoader（）和setContextClassLoader（ClassLoader c1）分别用来获取和设置上下文类加载器
* 如果没有通过setContextClassLoader（ClassLoader c1）来设置类加载器，那么将默认继承其父类的线程上下文类加载器。
* java运行时初始上下文类加载器是系统类加载器。在线程中运行的代码可以通过这个类加载器获取类和资源
* 线程上下文类加载器的重要性：
* SPI（Serivce Provider Interface）
* 父加载器可以使用当前线程Thread.currentThread().getContextClassLoader()所指定的classloader加载的类
* 这就改变了父calssLoader不能使用子classLoader或者其他没有父子关系的classLoader加载的类的情况，即破坏了双亲委托模型
*
* 在双亲委托模型下，类加载是由下至上的，即下层的类加载器会委托上层来进行加载。但是对于SPI来说，有些接口是由java核心库来提供的,因此使用的是启动类加载器，而接口的实现则是各个厂商提供的jar包如（mysql-connector-xxxx.jar）并放到classpath来实现，java的启动类加载器是不会加载不同来源的jar包，这样传统的类加载方法无法满足SPI需求。通过给当前线程提供线程上下文类加载器，就可以设置线程上下文类加载来实现对于接口类的加载。
```

```java
import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 线程的上下文类加载器的一般使用模式（获取 - 使用 - 还原）
 * ClassLoader classLoader = this.thread.getContextClassLoader();
 * try
 * {
 * Thread.currentThread().setContextClassLoader(Target1);//设置Target1我想使用的类加载器
 * myMethod();//获取Target1这个类加载器
 * }
 * finally{
 * Thread.currentThread().setContextClassLoader(classLoader);//改回原来的classLoader还原
 * }
 *
 * myMethod（）则调用了Thread.thread.getContextClassLoader();获取当前线程的类加载器来去做某些事情
 *
 * 当高层提供了统一接口让低层去实现，同时又要在高层实例化或者加载低层的类时，就必须通过线程上下文类加载器来帮助高层的classLoader
 */
public class MyTest14 {
    public static void main(String[] args) {
        Thread.currentThread().setContextClassLoader(MyTest14.class.getClassLoader().getParent());
        ServiceLoader<Driver>serviceLoader=ServiceLoader.load(Driver.class);
        Iterator<Driver>iterator=serviceLoader.iterator();
        while (iterator.hasNext())
        {
            Driver driver=iterator.next();
            System.out.println("Driver  :"+driver.getClass()+"   load  :"+driver.getClass().getClassLoader());
        }
        System.out.println("当前线程的上下文类加载器   ："+Thread.currentThread().getContextClassLoader());
        System.out.println("ServiceLoader的类加载器   ："+ServiceLoader.class.getClassLoader());
    }
}
```

## Java字节码

- 使用javap -verbose命令分析一个字节码文件时，将会分析该字节码文件的魔数、版本号、常量池、类信息、类的构造方法、类中的方法信息、类变量与成员变量等信息。
  
  - 魔数：所有的.class字节码文件的前4个字节都是魔数，魔数值的固定值为：0xCAFEBABE
  
  - 魔数之后的四个字节是版本信息，前两个字节表示minor version（次版本号），后两个字节表示major version（主版本号）。这里的版本号为00 00 00 34，换算成十进制，表示次版本号为0，主版本号为52.可以通过java -version来验证这一点
  
  - 常量池（constant pool）：紧接着主版本号之后的就是常量池入口。一个java类定义的很多信息都是由常量池来维护和描述的。可以看作是class文件的资源仓库，比如java类定义的方法和变量信息，都是存储在常量池中。常量池主要存储两类常量：字面量和符号引用。**字面量**：如文本字符串，java中声明为final的常量值等。**符号引用**：类和接口的全局限定名，字段的名称和描述符，方法的名称和描述符等。
  
  - 常量池总体结构：Java所对应的类的常量池主要由常量池数量和常量池数组两部分构成。常量池数量紧跟在版本号后面，占2个字节；常量池数组紧跟在常量池之后。常量池数组和一般数组不同的是，常量池数组中不同的元素类型、结构是不同的，长度当然也不相同；但是，每一种元素的第一个数据都是一个u1类型，该字节是个标志位，占据一个字节。JVM在解析常量池时，会根据u1类型来获取元素的具体类型。常量池容量是十进制的22，则代表常量池中有21个常量，因为容量计数是从1而不是0开始的。因为索引0也是一个常量（保留常量），只不过它不位于常量表中，这个常量就对应null值；所以，常量池的索引从1而非0开始。
  
  - <img src="/Users/ys/Library/Application Support/typora-user-images/截屏2020-01-31上午10.56.55.png" alt="截屏2020-01-31上午10.56.55" style="zoom:200%;" />
  
  - 在JVM规范中，每个变量/字段都有描述信息，描述信息主要的作用是描述字段的数据类型、方法的参数列表（包括数量、类型与顺序）与返回值。根据描述符规则，基本数据类型和代表无返回值的void类型都有一个大写字符来表示，对象类型则使用字符L加对象全限定名称来表示。为了压缩字节码文件的体积，对于基本数据类型，JVM都只使用一个大写字母来表示，如：B-byte，C-char，D-double，F-float，I-int，J-long，S-short，Z-boolean，V-void，L-对象类型，如Ljava/lang/String；
  
  - 对于数组类型来说，每一个维度使用一个前置的[来表示，如int被记录为[I，String[] []被记录为[[Ljava/lang/String；
  
  - 用描述符描述方法时，按照先参数列表，后返回值的顺序来描述。参数列表按照参数的严格顺序放在一组（）之内，如方法：
  
    String getRealnamebyIdAndNickname（int id，String name）的描述符为：（I,Ljava/lang/String;）Ljava/lang/String; < init >表示构造方法；< clinit >对静态变量的初始化
  
  - <img src="/Users/ys/Library/Application Support/typora-user-images/截屏2020-01-31下午3.41.30.png" alt="截屏2020-01-31下午3.41.30" style="zoom:250%;" />
  
  - ![截屏2020-01-31下午3.50.00](/Users/ys/Library/Application Support/typora-user-images/截屏2020-01-31下午3.50.00.png)
  
- Class字节码中有两种数据类型

  - 字节数据直接量：这是基本的数据类型。共细分为u1、u2、u4、u8四种，分别代表连续的1个字节、2个字节、4个字节、8个字节组成的整体数据。
  - 表（数组）：表是由多个基本数据或其他表，按照既定顺序组成的大的数据集合。表是由结构的，它的结构体现在：组成表的成分所在的位置和顺序都是严格定义好的。

- Access_Flag访问标志

  - 访问标志信息包括该Class文件是类还是接口，是否被定义成public，是否是abstract，如果是类，是否被声明成final。

  - ![截屏2020-01-31下午4.12.31](/Users/ys/Library/Application Support/typora-user-images/截屏2020-01-31下午4.12.31.png)

    0x0002             private

  - 0x0021:是0x0020和0x0001的并集，表示ACC_PUBLIC和ACC_SUPER。 

- 字段表集合（field_info）

  - 字段表用于描述类和接口中声明的变量。这里的字段包含了类级别变量以及实例变量，但是不包括方法内部声明的局部变量。

  - <img src="/Users/ys/Library/Application Support/typora-user-images/截屏2020-01-31下午4.25.59.png" alt="截屏2020-01-31下午4.25.59" style="zoom:200%;" />

    此表同时适用于methods（方法表）

- 方法表集合（method）

  - 方法表如上图
  - 属性表（attribute_info）
  - <img src="/Users/ys/Library/Application Support/typora-user-images/截屏2020-01-31下午4.56.31.png" alt="截屏2020-01-31下午4.56.31" style="zoom:200%;" />
  - Code attribute的作用是保存该方法的结构，如所对应的字节码
  - <img src="/Users/ys/Library/Application Support/typora-user-images/截屏2020-01-31下午5.04.37.png" alt="截屏2020-01-31下午5.04.37" style="zoom:200%;" />
  - attribute_length表示attribute所包含的字节数，不包含attribute_name_index和attribute_length字段。
  - max_stack表示这个方法运行的任何时刻所能达到的操作数栈的最大深度
  - max_locals表示方法执行期间创建的局部变量的数目，包含用来表示传入的参数的局部变量。
  - code_length表示该方法所包含的字节码的字节数以及具体的指令码
  - 具体字节码即是该方法被调用时，虚拟机所执行的字节码
  - exception_table，这里存放的是处理异常的信息
  - 每个exception_table表项由start_pc,end_pc,handler_pc,catch_type组成
  - start_pc和end_pc表示在code数组中从start_pc到end_pc处（包含start_pc,不包含end_pc）的指令抛出的异常会由这个表项来处理
  - handler_pc表示处理异常的代码的开始处。catch_type表示会被处理的异常类型，它指向常量池里的一个异常类。当catch_type为0时，表示处理所有的异常
  
- 附加属性

  - LineNumberTable：这个属性用来表示code数组中的字节码和Java代码行数之间的关系。这个属性可以用来在调试的时候定位代码执行的行数
  - ![截屏2020-02-02下午3.20.52](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-02下午3.20.52.png)
  - LocalVariableTable（局部变量表）

- 对于Java中的每一个非静态方法（实例方法）的形参，反应到字节码中都会比你看到的多一个，多的那个就是this。位于方法的第一个参数位置处；这样就可以在Java实例方法中使用this来去访问当前对象的属性和其他方法。

  这个操作是在编译过程中完成的，即由javac编译器在编译的时候将对this的访问转化成对一个普通实例方法参数的访问。在运行期间由JVM来调用实例方法时，自动向实例方法传入this参数。所以，在实例方法的局部变量表中，至少有一个指向当前对象的局部变量。

- Java字节码对于异常处理方式：

  - 统一采用异常表的形式对异常进行处理
  - 在jdk 1.4.2 之前的版本中，并不使用异常表的方式对异常进行处理，而是采用特定的指令方式
  - 当异常处理出现finally语句块时，现代化JVM采用的处理方式是将finally语句块的字节码拼接到每一个catch块的后面。换句话说程序，程序中存在多少catch块，就会在每一个catch后面重复 多少个finally语句块的字节码。

### 栈帧（stack frame）

栈帧是一种用于帮助虚拟机执行方法调用与方法执行的数据结构。

栈帧本身是一种数据结构，封装了方法的局部变量表、动态链接信息、方法的返回地址以及操作数栈等信息。

符号引用：类或方法的全限定类名。

直接引用：类似指针，指向类或者方法的全限定类名。

- 有些符号引用是在类加载阶段或者第一次使用时就会转换为直接引用，这种转换叫静态解析；

  静态解析的4种情形：

  1.静态方法

  2.父类方法

  3.构造方法

  4.私有方法（不能被重写，故不会存在多态的情况）

  以上4种类方法称作为非虚方法，他们在类加载阶段就把符号引用转换为直接引用。

- 另外一些符号引用则是在每次运行期转换为直接引用，这种转换叫做动态链接，这体现为Java的多态性。

``````java
/**
 * 方法的静态分派：
 *         Grandpa g1=new Father();
 *         g1的静态类型是Grandpa，而g1实际类型（实际指向的类型）是Father
 *         结论：变量的静态类型是不会发生变化的，而实际类型是可以发生变化的，实际类型在运行期可以确定
 */
public class MyTest4{
    //方法重载是一种静态行为，编译器就可以完全确定。Java中存在两种多态，重载和重写，重写是一种动态
    public  void test(Grandpa grandpa)
    {
        System.out.println("grandpa");
    }
    public  void test(Father father)
    {
        System.out.println("father");
    }
    public  void test(Son son)
    {
        System.out.println("son");
    }
    public static void main(String[] args) {
        Grandpa g1=new Father();
        Grandpa g2=new Son();
        MyTest4 myTest4=new MyTest4();
        myTest4.test(g1);
        myTest4.test(g2);
    }
}
class Grandpa
{}
class Father extends Grandpa
{}
class Son extends Father
{}
``````

比较方法重载（overload）和方法重写（overwrite），可以得到结论：方法重载是静态的，是编译期行为；方法重写是动态的，是运行期行为。

对于方法调用动态分派的过程，虚拟机会在类的方法区建立一个虚方法表的数据结构（virtual method table，vtable）

针对invokeinterface指令来说，虚拟机会建立一个叫做接口方法表的数据结构（interface  method table，itable）

现代JVM在执行Java代码的时候，通常都会将解释执行与编译执行二者相结合起来执行。

所谓的解释执行，就是通过解释器来读取字节码，遇到相应的指令就去执行该指令。

所谓的编译执行，就是通过即时编译器（Just In Time，JIT）将字节码转换为本地机器码来执行；现代JVM会根据代码热点来生成相应的本地机器码。

基于栈的指令集和基于寄存器的指令集之间的关系：

1.JVM执行指令时所采取的方式是基于栈的指令集。

2.基于栈的指令集主要的操作有入栈与出栈两种。

3.基于栈的指令集的优势在于它可以在 不同平台之间移植，而基于寄存器的指令集是与硬件架构紧密相关的，无法做到可移植。

4.基于栈的指令集的缺点在于完成相同的操作，指令数量要比寄存器的指令集数量要多；基于栈的指令集实在内存中完成操作，而基于寄存器指令集是直接由CPU来执行的，它是在高速缓冲区中进行执行的，速度要快很多，虽然虚拟机可以采用一些优化手段

## JVM内存空间划分

虚拟机栈：Stack Frame 栈帧

程序计数器（Program Counter）：

本地方法栈：主要用于处理本地方法

堆（Heap）:JVM管理的最大的一块内存空间，与堆相关的重要概念是垃圾收集器。现代几乎所有的垃圾收集器采用分代收集算法，堆空间也基于这一点进行划分:新生代与老年代。Eden空间，From Survivor空间与To Survivor空间。

方法区（Method Area）：存储元信息(类本身固有的信息)、常量、静态变量。

运行时常量池：方法区的一部分内容，方法区除了类的版本信息、方法、字段、接口外还有常量池。

直接内存：Driect Memory，不是JVM直接管理的区域，义Java NIO密切相关。JVM通过DirectByBuffer来操作直接内存。

关于Java对象创建的过程：

首先到常量池检查符号引用是否被加载、连接、初始化。如果没，先利用类加载器加载对象。

new关键字创建对象的3个步骤：

1.在堆内存中创建对象实例。

2.为对象的实例成员变量赋初值。

3.将对象的引用返回

指针碰撞（前提是堆中的空间通过一个指针进行分割，一侧是已经被占用的空间，另一个是未被占用的空间）

空闲列表（堆内存空间中已被使用与未被使用的空间是交织在一起的，这时虚拟机就需要通过一个列表来记录空间是可以使用的，哪些空间是已被使用的，接下来找出可以容纳下新创建的对象且未被使用的空间，在此空间存放该对象，同时还要修改列表上的记录 ）

对象在内存中的布局：

1.对象头

2.实例数据（即我们在一个类中所声明的各项信息）

3.对齐填充（可选）

引用访问对象的方式：

1.使用句柄的方式（多一个引用）

![](/Users/ys/Desktop/IMG_4992.jpeg)

2.使用直接指针的方式（直接指过去）

![IMG_4993](/Users/ys/Desktop/IMG_4993.jpeg)

设置堆的最小值-Xms5m 设置堆的最大值-Xmx1024m

形成堆的hrop文件-XX:+HeapDumpOnOutOfMemoryError

````java
package com.demo.memory;

/**
 * 死锁代码示例
 * 线程A拿到类A的锁，锁定B类的锁
 * 线程B拿到类B的锁，锁定A类的锁
 *"Thread-B":
 *   waiting to lock monitor 0x00007fdd6200fca8 (object 0x000000076aed9170, a java.lang.Class),
 *   which is held by "Thread-A"
 * "Thread-A":
 *   waiting to lock monitor 0x00007fdd6200fd58 (object 0x000000076b020bb8, a java.lang.Class),
 *   which is held by "Thread-B"
 */
public class MyTest3 {
    public static void main(String[] args) {
       new Thread(()->A.method(),"Thread-A").start();
       new Thread(()->B.method(),"Thread-B").start();
       try {
           Thread.sleep(40000);
       }
       catch (InterruptedException e)
       {e.printStackTrace();}
    }
}

class A {
    public static synchronized void method() {
        System.out.println("Method from A");
        try {
            Thread.sleep(5000);
            B.method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class B {
    public static synchronized void method() {
        System.out.println("Method from B");
        try {
            Thread.sleep(5000);
            A.method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
````

[元空间](https://www.infoq.cn/article/Java-PERMGEN-Removed/)

获取JVM进程ID：ps -ef l grep java

​                              jps -l

## JVM垃圾回收

![截屏2020-02-15下午3.23.19](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-15下午3.23.19.png)

堆：是java虚拟机管理内存中最大的一块

​        GC主要的工作区域，为了高效的GC，会把堆细分更多的子区域

![截屏2020-02-15下午3.35.06](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-15下午3.35.06.png)

垃圾判断的算法：

- 引用计数算法（Reference Counting）
  - 给对象添加一个引用计数器，当有一个地方引用它，计数器加1，当引用失效，计数器减1，任何时刻计数器为0的对象 就是不可能再被使用的
  - 引用计数算法无法解决对象循环引用的问题

- 根搜索算法（Root Tracing）
  - 在实际的生产语言中（Java、C#等），都是使用根搜索算法判定对象是否存活
  - 算法的基本思路就是通过一系列的成为“GC Roots”的点作为起始进行向下搜索，当一个对象到GC Roots没有任何引用链（Reference Chain）相连，则证明此对象是不可用的
  - 在Java语言中，GC Roots包括
    - 在VM栈（栈帧中的本地变量）中的引用
    - 方法区中的静态引用
    - JNI（即一般说的Native方法）中的引用
- 方法区
  - Java虚拟机规范表示可以不要求虚拟机在这区实现GC，因为“性价比”较低
  - 当前的商业JVM都有实现方法区的GC，主要回收两部分内容：废弃常量与无用类。
  - 类回收需要满足如下3个条件：
    - 该类所有的实例都已经被GC，也就是JVM中不存在该Class的任何实例
    - 加载该类的ClassLoader已经被GC
    - 该类对应的java.lang.Class对象没有在任何地方被引用，如不能在任何地方通过反射访问该类的方法

JVM常见的GC算法

- 标记-清除算法（Mark-Sweep）
  - 算法分为“标记”和“清除”两个阶段，首先标记出所有需要回收的对象，然后回收所有需要回收的对象
  - 缺点
    - 效率问题，标记和清理两个过程效率都不高，需要扫描所有对象。堆越大，GC越慢。
    - 空间问题，标记清理之后会产生大量不连续的内存碎片（被标记清除的对象掺杂在未被清除对象之中），空间碎片太多可能会导致后续使用中无法找到足够的连续内存而提前出发另一次的垃圾搜集动作。GC次数越多，碎片越严重。
  - ![截屏2020-02-15下午4.24.31](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-15下午4.24.31.png)
  - 上图中Runtime stack相当于根搜索算法中的Root，绿色代表被引用的，红色代表需要被回收的
  
- 标记-整理算法（Mark-Compact）
  - 标记过程仍然一样，但后续步骤不是进行直接清理，而是令所有存活的对象一端移动，然后直接清理掉这端边界以外的内存。
  - 没有内存碎片
  - 比Mark-Sweep耗费更多的时间进行compact
  
- 复制算法（Copying） 
  - 将可用内存划分为两块，每次只使用其中的一块，当半区内存用完了，仅将还存活的对象复制到另外一块上面，然后就把原来的整块内存一次性清理掉
  - 这样使得每次内存回收都是对整个半区的回收，内存分配时也就不用考虑内存碎片的问题，只需要移动堆顶指针，按顺序分配内存就可以了，实现简单，运行高效。只是这种算法的代价是将内存缩小为原来的一半，代价高昂。
  - 现在的商业虚拟机中都是用了这一种收集算法来回收***新生代***。
  - 将内存分一块较大的eden空间和2块较少的survivor空间，每次使用eden和其中的一块survivor（From survivor），当回收时将eden和survivor还存活的对象一次性拷贝到另外一块survivor空间（To survivor）上，然后清理掉eden和用过的survivor
  - Oracle Hotspot虚拟机默认eden和survivor的大小比例时8:1，也就是每次只有10%的内存时“浪费”的。
  - 复制收集算法在对象存活率高的时候，效率有所下降
  - 老年代一般不采用这种算法
  - ![截屏2020-02-15下午4.40.59](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-15下午4.40.59.png)
  - ![截屏2020-02-15下午4.41.42](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-15下午4.41.42.png)
  - 适合存活周期短的算法 
  
- 分代算法（Generational）
  - 当前商业虚拟机的垃圾收集都是采用“分代收集”（Generational Collecting）算法，根据对象不同的存活周期将直接内存划分为几块。
  - 一般是把Java堆分作新生代和老年代，这样就可以根据各个年代的特点采用最适当的收集算法，譬如新生代每次GC都有大批对象死去，只有少量存活，那就选用复制算法只需要付出少量存活对象的复制成本就可以完成收集。
  - 综合前面几种GC算法的优缺点，针对不同生命周期的对象采用不同的GC算法。
  - ![截屏2020-02-15下午4.58.53](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-15下午4.58.53.png)
  - ![截屏2020-02-15下午5.03.03](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-15下午5.03.03.png)
  - 注意：jdk1.8以后取消了永久代
  - 年轻代（Young Generation）
    - 新生成的对象都放在新生代。年轻代用复制算法进行GC（理论上。年轻代对象的生命周期非常短，所以适合复制算法）
    - 年轻代分三个区。一个Eden区，两个Survivor区。对象在Eden区生成。当Eden区满时，还存活的对象将被复制到一个Survivor区，当这个Survivor区满时，该区的存活对象将被复制到另外一个Survivor区，当第二个Survivor区满时，此时从第一个Survivor区复制过来并且还存活的对象将被复制到老年代。
    - 可以根据GC log的信息调整Eden和两个Survivor的比例。
  - 老年代（Old Generation）
    - 存放了经过一次或多次GC还存活的对象
    - 一般采用Mark-Sweep或者Mark-Compact算法进行GC
    - 有多种垃圾收集器可以选择。每种垃圾收集器可以看作一个GC算法的具体实现。可以根据具体应用的需求选用合适的垃圾收集器。

  内存分配

  - 堆上分配

    大多数在eden上分配，偶尔会直接在old上分配

    细节取决于GC的实现

  - 栈上分配

    原子类型的局部变量

  内存回收

  - GC要做的是将那些dead的对象所占用的内存回收掉
    - Hotspot认为没有引用的对象是dead的
    - Hotspot将引用分为四种：Strong、Soft、Weak、Phantom
    - Strong是默认通过Object o=new Object（）这种方式赋值引用
    - Soft、Weak、Phantom三种则是继承Referenece
  - 在Full GC时会对Reference类型的引用进行特殊处理
    - Soft：内存不够时一定会被GC、长期不用也会被GC
    - Weak：一定会被GC，当被mark为dead，会在ReferenceQueue中通知
    - Phantom：本来就没引用，当从jvm heap中释放时会通知

  GC的时机

  - 在分代模型的基础上，GC从时机上分为两种：Scavenge GC和Full GC

  - Scavenge GC（Minor GC）

    - 触发时机：新对象生成时，Eden空间满了
    - 理论上Eden区大多数对象会在Scavenge GC回收，复制算法的执行效率会很高，Scavenge GC事件比较短。

  - Full GC

    - 对整个JVM进行整理，包括Young、Old

    - 主要的触发时机：1）Old满了 2）Perm满了

      3）system.gc()

    - 效率很低，尽量减少Full GC

  垃圾收集器的“并行”和“并发”

  - 并行（Parallel）：指多个收集器的线程同时工作，但是用户线程处于等待状态
  - 并发（Concurrent）：指收集器在工作的同时，可以允许用户的线程工作
    - 并发不代表解决了GC停顿的问题，在关键的步骤还是要停顿。比如在收集器标记垃圾的时候。但是在清除垃圾的时候，用户线程可以和GC线程并发执行。

  Serial收集器

  - 单线程收集器，收集时会暂停所有工作线程（Stop The World 简称：STW），使用复制收集算法，虚拟机运行在Client模式时的默认新生代收集器。
  - 最早的收集器，单线程进行GC
  - New和Old Generation都可以使用
  - 在新生代，采用复制算法；在老年代，采用Mark-Compact算法
  - 因为是单线程GC，没有多线程切换的额外开销，简单实用
  - ![截屏2020-02-16下午6.13.33](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-16下午6.13.33.png)

  ParNew收集器

  - ParNew收集器是Serial的多线程版本，除了使用多个收集线程外，其余行为包括算法、STW、对象分配规则、回收策略等都与Serial收集器一摸一样‘
  - 对应的这种收集器是虚拟机运行在Server模式的默认新生代收集器，在单CPU的环境中，ParNew收集器并不会比Serial收集器有更好的效果
  - 可以通过-XX：ParallelGCThreads来控制GC线程数的多少。需要结合具体的CPU的个数

  Parallel Scavenge收集器

  - 是一个多线程收集器，也是使用复制算法，但是它的对象分配规则和回收策略都与ParNew收集器有所不同，它是以吞吐量最大化为目标的收集器实现（GC时间占总运行时间最小），允许较长时间的STW换取总吞吐量的最大化。

  Parallel Old

  - Parallel Scavenge在老年代的实现

  - 采用多线程，Mark-Compact算法

  - 更注重吞吐量

  - Parallel Scavenge+Parallel Old=高吞吐量

  - ![截屏2020-02-16下午6.25.44](/Users/ys/Library/Application Support/typora-user-images/截屏2020-02-16下午6.25.44.png)

    

