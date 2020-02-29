    //aar导入
    将aar包复制到lib目录下 
    repositories {
       flatDir {
           dirs 'libs'
       }
    }
    
    //
    sourceSets {
        main {
            jni.srcDirs = []
            jniLibs.srcDirs = ['libs']
        }
    }
    
    //导入aar
    compile(name:'leosupport_v1', ext:'aar')
    //友盟
    compile 'com.umeng.umsdk:analytics:7.5.4'
    compile 'com.umeng.umsdk:common:1.5.4'
    //LeanCloud 基础包
    compile ('cn.leancloud.android:avoscloud-sdk:4.7.10')
    //BingView
    compile "com.jakewharton:butterknife:8.8.1"
    annotationProcessor "com.jakewharton:butterknife-compiler:8.8.1"
    //Http工具
    compile 'com.zhy:okhttputils:2.6.2'

    //project
    buildscript {
        repositories {
            //友盟的包仓库
            maven { url 'https://dl.bintray.com/umsdk/release' }
            //LeanCloud的包仓库
            maven { url "http://mvn.leancloud.cn/nexus/content/repositories/public" }
        }
    }

    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
            //这里是 LeanCloud 的包仓库
            maven { url "http://mvn.leancloud.cn/nexus/content/repositories/public" }
            //友盟
            maven { url 'https://dl.bintray.com/umsdk/release' }
        }
    }


    //Other
    android = [
        compileSdkVersion       : 28,
        buildToolsVersion       : "28.0.0",
        minSdkVersion           : 19,
        targetSdkVersion        : 28,
        versionCode             : 1,
        versionName             : "1.0.0"
    ]
    v4  v7 版本"28.0.0"
        
        
        
        
    //TODO
    权限回调
    透明状态栏
