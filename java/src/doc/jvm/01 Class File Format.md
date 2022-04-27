# Class File Format

内部为二进制字节流；

## 文件格式

> 数据类型： `u1` `u2` `u4` `u8` `_info`
> 
> `javap` ：jdk class 工具；
> * `-v` / `-verbose` 详细信息；

* `magic` `u4`
    
    固定内容（ ASCII 码）： `CA FE BA BE`

* `minor_version` `u2`

    class 文件小版本号；

* `major_version` `u2`

    class 文件大版本号；
    * jdk 1.7 : `0x00 33` ；
    * jdk 1.8 : `0x00 34` ；

* `constant_pool_count` `u2`

    常量池中常量的个数（最多 `2^16` 个常量）；

* `constant_pool[constant_pool_count-1]` `cp_info`

    常量池编号从 `1` 开始， `0` 保留；

    表内结构：
    * `tag` `u1`
        
        固定大小： `1 Byte`
        
        标志；
        * `7` `0x07` : `CONSTANT_Class`
        * `9` `0x09` : `CONSTANT_Fieldref`
        * `10` `0x0A` : `CONSTANT_Methodref`
        * `11` `0x0B` : `CONSTANT_InterfaceMethodref`
        * `8` `0x08` : `CONSTANT_String`
        * `3` `0x03` : `CONSTANT_Integer`
        * `4` `0x04` : `CONSTANT_Float`
        * `5` `0x05` : `CONSTANT_Long`
        * `6` `0x06` : `CONSTANT_Double`
        * `12` `0x0C` : `CONSTANT_NameAndType`
        * `1` `0x01` : `CONSTANT_Utf8`
        * `15` `0x0f` : `CONSTANT_MethodHandle`
        * `16` `0x10` : `CONSTANT_MethodType`
        * `17` `0x11` : `CONSTANT_Dynamic`
        * `18` `0x12` : `CONSTANT_InvokeDynamic`
        * `19` `0x13` : `CONSTANT_Module`
        * `20` `0x14` : `CONSTANT_Package`

    * `info[]` `u1`
  
        `info[]` 具体内容和长度要看 `tag` 标志类型决定；

* `access_flags` `u2`

    类修饰符：
    * `0x00 01` `ACC_PUBLIC` : 是否 `public` 修饰
    * `0x00 10` `ACC_FINAL` : 是否 `final` 修饰
    * `0x00 20` `ACC_SUPER` : 指明 `invokespectia` 指令使用新语义； JDK 1.0.2 之后编译出来的内容必须为 `true`
    * `0x02 00` `ACC_INTERFACE` : 是否是接口
    * `0x04 00` `ACC_ABSTRACT` : 接口或抽象类
    * `0x10 00` `ACC_SYNTHETIC` : 编译自动生成，非用户代码产生
    * `0x20 00` `ACC_ANNOTATION` :
    * `0x40 00` `ACC_ENUM` :

* `this_class` `u2`

    当前类的 class 全限定名，指向常量池中的位置；

* `super_class` `u2`

    当前类父类 `class` 全限定名，指向常量池中的位置；

* `interfaces_count` `u2`

    

* `interfaces[interfaces_count]` `u2`

    

* `fields_count` `u2`

    

* `fields[fields_count]` `field_info`

    

* `methods_count` `u2`

    

* `methods[methods_count]` `method_info`

    

* `attributes_count` `u2`

    

* `attributes[attributes_count]` `attribute_info`

    
