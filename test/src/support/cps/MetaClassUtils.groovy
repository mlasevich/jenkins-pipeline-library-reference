package support.cps

import com.cloudbees.groovy.cps.NonCPS
import org.codehaus.groovy.reflection.CachedMethod

import javax.annotation.Nullable
import java.lang.reflect.Method

/**
 * Utils for dealing with MetaClass related stuff
 *
 * Mostly copied from Spock
 */
class MetaClassUtils {

  /**
   * Convert argument list to a list of parameter types (class names)
   *
   * NOTE, this is not same as resolving canonical signature of a method
   *
   * @param args list
   * @return list of Classes for each arg
   */
  @NonCPS
  static Class[] argsToParamTypes(Object... args) {
    // Must use getClass() to handle maps
    return (args*.getClass()).toArray(new Class[0])
  }

  @Nullable
  @NonCPS
  static Method toMethod(@Nullable MetaMethod metaMethod) {
    CachedMethod cachedMethod = asInstance(metaMethod, CachedMethod)
    return cachedMethod == null ? null : cachedMethod.cachedMethod
  }

  /**
   * Cast wrapper
   *
   * @param obj
   * @param type
   * @return
   */
  @SuppressWarnings('unchecked')
  @NonCPS
  static @Nullable
  <T> T asInstance(Object obj, Class<T> type) {
    return type.isInstance(obj) ? (T) obj : null
  }

  /**
   * Wrapper for safely getting class of object without having to suppress unnecessary
   * getters everywhere.
   *
   * We use `getClass()` instead of .class in case object is hijacking properties (like
   * for example Map class)
   *
   * @param object
   * @return
   */
  @SuppressWarnings('UnnecessaryGetter')
  @NonCPS
  static Class classOf(Object object) {
    return object?.getClass()
  }

  /**
   * Wrapper for safely getting metaclass of object without having to suppress unnecessary
   * getters everywhere.
   *
   * We use `getClass()` instead of .class in case object is hijacking properties (like
   * for example Map class)
   *
   * @param object
   * @return
   */
  @SuppressWarnings('UnnecessaryGetter')
  @NonCPS
  static MetaClass metaClassOf(Object object) {
    return object?.getMetaClass()
  }
}
