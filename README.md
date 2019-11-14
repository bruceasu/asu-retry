# background

clone from *https://github.com/houbb/sisyphus.git* for study.

Remove the spring because I don't like dependent on spring every components.
And also remove the interface api because it's impossible to make a multipy
implementations, there is no need to interface. Just make it a tool, but not a
framework to replace the guava or spring-retry.



# Quick Start

## Requirement

jdk1.7+

maven 3.x+

## Install
    git clone https://github.com/bruceaus/asu-retry.git
    cd asu-retry
    mvn install

## add dependency to your maven pom.xml

```xml
<plugin>
    <groupId>me.asu.retry</groupId>
    <artifactId>asu-retry</artifactId>
    <version>1.0.0</version>
</plugin>
```

## Code

```java
public void helloTest() {
    RetryExecutor<String> executor = RetryExecutor.create();
    executor.setCallable(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("called...");
                    throw new RuntimeException();
                }
            });
    executor.retryCall();
}
```

### Code Description

`RetryExecutor.create()` Create an instance of the class guide, String is to be callable i.e. retry method return type.

`callable()` The method of retries specified to be implemented.

`retryCall()` Trigger retried.

### Output

```
called...
called...
called...
```

As well as some unusual information.

## Equivalent arrangements

The above configuration actually a lot of defaults, as follows:

```java
@Test(expected = RuntimeException.class)
public void defaultConfigTest() {
    RetryExecutor<String> executor =  RetryExecutor.create();
    executor.setMaxAttempt(3);
    executor.setListener(RetryListener.NO_LISTENER);
    executor.setRecover(Recover.NO_RECOVER);
    executor.setCondition(RetryConditions.hasExceptionCause());
    executor.setRetryWaitContext(RetryWaiter.<String>retryWait(NoRetryWait.class)
                                            .getContext());
    executor.setCallable(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("called...");
                    throw new RuntimeException();
                }
            });
   executor.retryCall();
}
```

These default values ​​are configurable.



# Usage
## Configuration Overview
In order to meet more easily configured, Retryer class provides a lot of
information that can be configured.

### Default Configuration

```java
@Test(expected = RuntimeException.class)
public void defaultConfigTest() {
    RetryExecutor<String> executor =  RetryExecutor.create();
    executor.setMaxAttempt(3);
    executor.setListener(RetryListener.NO_LISTENER);
    executor.setRecover(Recover.NO_RECOVER);
    executor.setCondition(RetryConditions.hasExceptionCause());
    executor.setRetryWaitContext(RetryWaiter.<String>retryWait(NoRetryWait.class)
                                            .getContext());
    executor.setCallable(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("called...");
                    throw new RuntimeException();
                }
            });
   executor.retryCall();
}

```

And is equivalent to the following code:

```java
public void helloTest() {
    RetryExecutor<String> executor = RetryExecutor.create();
    executor.setCallable(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("called...");
                    throw new RuntimeException();
                }
            });
    executor.retryCall();
}

```



## Method Description

### setCondition

Retry trigger conditions, you can specify multiple criteria.

The default is thrown.

### setRetryWaitContext

Retry strategy to wait, you can specify more.

The default is not any waiting.

### setMaxAttempt

Specifies the maximum number of retries, including the first execution.

Default: 3 times.

### setListener

Specifies the Retry monitor implementation, the default is not listening.

### setRecover

After the completion of the retry, retry still meet the conditions, you can
specify the policy restored.

The default is not restored.

### setCallable

The method of retries to be performed.

### retryCall

Trigger retries.

# Annotation Usage

Configuration with high flexibility, but for developers to use, there would be
no comment as simple and flexible.

So also implemented based on annotation and try again.

## Design specifications

Both interfaces ensure uniformity and annotations.


## Annotations
There are two main core notes.

- Retry: For the retry configuration.
- RetryWait: It is used to specify the retry wait strategy.

# Use annotations

Well-defined annotations, be sure to have annotations related use.

On the use of annotations, mainly in two ways.

- Proxy:
- CGLIB

## RetryTemplates

### purpose

为了便于用户更加方便地使用注解，同时又不依赖 spring。

提供基于代码模式+字节码增强实现的方式。

### Example

#### Defined test methods
- MenuServiceImpl.java

```java
public class MenuServiceImpl {

    public void queryMenu(long id) {
        System.out.println("Query menu...");
        throw new RuntimeException();
    }

    @Retry
    public void queryMenuRetry(long id) {
        System.out.println("Query menu...");
        throw new RuntimeException();
    }

}
```

#### Test
- No retry annotated method

```java
@Test(expected = RuntimeException.class)
public void templateTest() {
    MenuServiceImpl menuService = RetryTemplate.createProxy(new MenuServiceImpl(),
                                                            MenuServiceImpl.class);
    menuService.queryMenu(1);
}
```

output:

```
Query menu...
```

He requested only once.

- Annotated method

```java
@Test(expected = RuntimeException.class)
public void templateRetryTest() {
   MenuServiceImpl menuService = RetryTemplate.createProxy(new MenuServiceImpl(),
                                                           MenuServiceImpl.class);
    menuService.queryMenuRetry(1);
}
```

output:

```
Query menu...
Query menu...
Query menu...
```
