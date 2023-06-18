# TypeScript

## 接口: `interface`

**接口**：一系列抽象的方法的声明；是一种规范。

```typescript
// 定义接口
interface IPerson {
    name: string;
    age: number;
}

// 使用接口
let tom: IPerson = {
    name: 'tom',
    age: 10
}
```

* `?` 用于定义可选属性

```typescript
// name 为可选属性
interface IPerson {
    name?: string;
    age: number;
}

let cat:IPerson = {
    age: 10
};
let dog:IPerson = {
    name:"Dogger",
    age: 11
}
```

## 类型别名: `type`

类型别名用来给一个类型起一个新的名字；
类型别名 `type` 和接口 `interface` 都可以描述一个对象或函数；
不同点是类型别名 `type` 可以声明**基本类型别名**、**联合类型别名**、**元组类型别名**等。


* `？` 用于定义可选属性

## `typeof` 与 `keof`

* `typeof` 获取变量或对象的类型
```typescript
// typeof
type Tm = typeof toms;
let tms: Tm = {
    name: 'tms',
    age: 18
}
console.log(tms)
const person = {
    name: 'zs',
    age: 9
}
type Pes = typeof person;
let pes: Pes = {
    name: 'pes',
    age: 20
}
console.log(pes)
```

* `keyof` 获取对象类型的属性名称

如果需要获取变量的属性名称，首先通过 `typeof` 获取的对象的类型，然后通过 `keyof` 获取属性列表

```typescript
/**
 * Make all properties in T optional
 */
type Partial<T> = {
    [P in keyof T]?: T[P];
};

/**
 * Make all properties in T required
 */
type Required<T> = {
    [P in keyof T]-?: T[P];
};
```

## 泛型

泛型：是在编译期间不确定方法的类型，在方法调用时，由程序员指定泛型具体类型类型。

泛型作用：通常解决类、接口、方法的复用，以及对非特定数据的支持。

```typescript
function getProperty<T,K extends keyof T>(obj:T,key:K) {
    return obj[key];
}
const psn = {
    name:'lili',
    age:22
}
console.log(getProperty(psn, 'name'))
console.log(getProperty(psn, 'age'))
```

