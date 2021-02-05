package support.cps

import com.cloudbees.groovy.cps.Continuation
import com.cloudbees.groovy.cps.CpsTransformer
import com.cloudbees.groovy.cps.NonCPS
import com.cloudbees.groovy.cps.SerializableScript
import com.cloudbees.groovy.cps.WorkflowTransformed
import com.cloudbees.groovy.cps.impl.CpsCallableInvocation
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

import java.lang.annotation.Annotation
import java.lang.reflect.Method

/**
 * Utility Class to handle CPS Code Invocations
 */
class CPSUtils {

  /**
   * Methods to never treat as potentially CPS to avoid recursion
   */
  static final SKIP_METHODS = ['getClass', 'getMetaClass']

  /**
   * Invoke method on CPS Transformed object.
   *
   * NOTE, if method is not transformed, this method will throw an exception
   *
   * @param object for method to be invoked on
   * @param method name of the method to invoke
   * @param args to invoke the method with
   * @throws InvalidCPSInvocation if not a CPS Transformed method
   */
  @NonCPS
  static invokeCPSMethod(Object object, String method, Object... args)
      throws InvalidCPSInvocation {
    return invokeCPSMethodWithOptions([:], object, method, args)
  }

  /**
   * Invoke method on CPS Transformed object.
   *
   * NOTE, if method is not transformed, this method will throw an exception
   *
   * @param object for method to be invoked on
   * @param method name of the method to invoke
   * @param args to invoke the method with
   *
   * @throws InvalidCPSInvocation if not a CPS Transformed method
   * Kwargs:
   *  * maxRuntime - (default: 10000) - max run time
   *  * safe - (default: false) - if true, swallow the exception if error
   */
  @NonCPS
  static invokeCPSMethodWithOptions(Map options, Object object, String method,
                                    Object... args) throws InvalidCPSInvocation {
    Map config = [
        maxRuntime: 10000,
        safe      : false
    ] << options

    String className = MetaClassUtils.classOf(object).name

    // if CPS, make it throw exception and then invoke it
    try {
      object.metaClass.getMetaMethod(method, args).invoke(object, args)
      // if we got this far, something went wrong - CPS code throws exception!!
      throw new InvalidCPSInvocation(className, method,
          MetaClassUtils.argsToParamTypes(args))
    } catch (CpsCallableInvocation inv) {
      def invocation = inv.invoke(null, null, Continuation.HALT)
      def outcome = invocation.run(config.maxRuntime)
      return config.safe ? outcome.wrapReplay() : outcome.replay()
    }
  }

  /**
   * Invoke method on possibly CPS Transformed object.
   *
   *
   * @param object for method to be invoked on
   * @param method name of the method to invoke
   * @param args to invoke the method with
   *
   * @throws InvalidCPSInvocation if not a CPS Transformed method
   * Kwargs:
   *  * maxRuntime - (default: 10000) - max run time
   *  * safe - (default: false) - if true, swallow the exception if error
   */
  @NonCPS
  static safeInvokeMethod(Object object, String method, Object... args)
      throws InvalidCPSInvocation {
    return safeInvokeMethodWithOptions([:], object, method, args)
  }

  /**
   * Invoke method on possibly CPS Transformed object.
   *
   *
   * @param object for method to be invoked on
   * @param method name of the method to invoke
   * @param args to invoke the method with
   *
   * @throws InvalidCPSInvocation if not a CPS Transformed method
   * Kwargs:
   *  * maxRuntime - (default: 10000) - max run time
   *  * safe - (default: false) - if true, swallow the exception if error
   */
  @NonCPS
  static safeInvokeMethodWithOptions(Map options, Object object, String method, Object... args)
      throws InvalidCPSInvocation {
    //println("Attempting to invoke ${object.class.name}.${method}(...)")
    if ((method in SKIP_METHODS) || !(isCPSTransformed(object, method, args))) {
      return object.metaClass.getMetaMethod(method, args).invoke(object, args)
    }
    return invokeCPSMethodWithOptions(options, object, method, args)
  }

  /**
   * Check if method `methodName` in object `object` with given a list of parameter types is
   * CPS Transformed or not
   *
   * @param object
   * @param methodName
   * @param pTypes - normalized parameter types
   * @return true if target is CPS transformed, false if not
   */
  // They are necessary
  @SuppressWarnings('UnnecessaryGetter')
  @NonCPS
  static boolean isCPSTransformedByParams(Object object, String methodName, Class... pTypes) {
    Class oClass = object?.getClass()

    Method method = oClass.getMethod(methodName, pTypes)
    // Get annotation(s)
    Annotation annotation = method?.getAnnotation(WorkflowTransformed)

    boolean isTransformed = annotation as Boolean

    //println("isCPSTransformed(${oClass.name}.${methodName}(${pTypes}) == ${isTransformed}")

    return isTransformed
  }

  /**
   * Check if method `methodName` in object `object` with given arguments is
   * CPS Transformed or not
   *
   * @param object
   * @param methodName
   * @param args
   * @return true if target is CPS transformed, false if not
   */
  @NonCPS
  @SuppressWarnings('UnnecessaryGetter')
  // They are necessary
  static boolean isCPSTransformed(Object object, String methodName, Object... args) {
    Class oClass = object?.getClass()

    // Get parameterTypes via metaClass
    MetaMethod metaMethod = oClass?.getMetaClass()?.getMetaMethod(methodName, args)

    Class[] pTypes = metaMethod?.nativeParameterTypes
    return isCPSTransformedByParams(object, methodName, pTypes)
  }

  /**
   * Generate CPS Transformed Script from String
   *
   * This is useful for generating CPS code dynamically from non-CPS code
   *
   * @param body of the script as a string
   */
  @NonCPS
  static Script cpsScript(String body) {
    return cpsScript([:], body)
  }

  /**
   * Generate CPS Transformed Script from String
   *
   * This is useful for generating CPS code dynamically from non-CPS code
   *
   * @param variables map to add to bindings
   *  * this can be a direct map of bindings, or a map like this:
   *    * bindings: map of bindings to pass to script
   *    * starImports: list of packages to add as star imports
   *    * imports: list of classes to import as strings, or as a list with alias
   * @param body of the script as a string
   */
  @NonCPS
  static Script cpsScript(Map variables, String body) {
    // Bindings for the script
    Map bindings = variables?.bindings ?: variables
    // imports for the script
    def imports = importsFromArgs(variables)

    // Compiler Configuration
    def cc = new CompilerConfiguration()
    cc.addCompilationCustomizers(imports)
    cc.addCompilationCustomizers(new CpsTransformer())

    //Set base script name
    cc.scriptBaseClass = SerializableScript.name

    // Load script
    GroovyShell csh = new GroovyShell(new Binding(bindings), cc)
    Script script = csh.parse(body.stripIndent())

    return script
  }

  /**
   * Create and Invoke CPS Script specified as a string from NonCPS context
   *
   * @param variables to bind to the script
   * @param body script body
   * @return output of the script
   */
  @NonCPS
  static asCPSScript(Map variables, String body) {
    Script cpsScript = cpsScript(variables, body)
    return invokeCPSMethod(cpsScript, 'run')
  }

  @NonCPS
  static asCPSScript(String body) {
    return asCPSScript([:], body)
  }

  /**
   * Add imports based on kwargs
   * @param importsCustomizer customizer object (if null, new one is created)
   * @param kwargs
   *    * starImports - list of package names as strings to add star imports
   *    * imports - list of classes to import. Item in a list may be:
   *      * Name of a class to import
   *      * A list of two items, [className, alias]
   * @return importsCustomizer object
   */
  @NonCPS
  static protected importsFromArgs(ImportCustomizer importsCustomizer, Map kwargs) {
    ImportCustomizer imports = importsCustomizer ?: new ImportCustomizer()

    if (kwargs) {
      List starImports = (kwargs?.starImports as List) ?: []
      imports.addStarImports(*(starImports?.
          findAll { String packageName ->
            packageName as boolean
          }))

      // add imports, if any
      kwargs?.imports?.each { importName ->
        if (importName instanceof List) {
          if (importName[0]) {
            if (importName[1]) {
              imports.addImport(importName[1], importName[0])
            }
          }
        } else {
          imports.addImports(*[importName as String])
        }
      }
    }
    return imports
  }

  @NonCPS
  static protected importsFromArgs(Map kwargs) {
    return importsFromArgs(null, kwargs)
  }

}
