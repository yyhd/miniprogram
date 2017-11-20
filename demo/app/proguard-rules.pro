-repackageclasses 'com.gameassist.plugin'

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations code/removal/simple,code/removal/advanced
-ignorewarnings                # 抑制警告

-keep public class * extends android.view.View
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View {*;}
-keep class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *;}
-keepclasseswithmembers class * {public <init>(android.content.Context, android.util.AttributeSet, int);}
-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.content.ContextWrapper
-keep public class * extends android.support.v4

#Log相关全部移除
-assumenosideeffects class android.util.Log { public *; }

-keep public class * extends com.gameassist.plugin.Plugin

-keepclassmembers class * {    native <methods>;}




