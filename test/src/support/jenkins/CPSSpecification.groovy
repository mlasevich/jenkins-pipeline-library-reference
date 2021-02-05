package support.jenkins

import support.cps.CPSUtils
import support.cps.InvalidCPSInvocation
import spock.lang.Specification

/**
 * Convenience Wrapper for CPS specs
 */
abstract class CPSSpecification extends Specification {

  //Subject Under Test instance
  protected sut

  /**
   * (Required) testSubjectClass
   */
  abstract Class getTestSubjectClass()

  /**
   * Optional default parameters for sut constructor
   */
  List<Object> getDefaultParameters() {
    return []
  }

  def setup() {
    sut = GroovySpy(testSubjectClass, constructorArgs: defaultParameters)
  }

  /**
   * Wrapper for CPSUtils.asCPSScript(String body) that invokes script with current package imported
   *
   * @param body
   * @return
   */
  def asCPSScript(String body) {
    return asCPSScript([:], body)
  }

  /**
   * Wrapper for CPSUtils.asCPSScript that invokes script with current package imported
   *
   * @param kwargs
   * @param body
   * @return
   */
  def asCPSScript(Map kwargs, String body) {
    Map args = [starImports: []] << kwargs
    args.bindings = args.bindings ?: [:]
    args.bindings.sut = args.bindings.sut ?: sut
    args.starImports = args.starImports ?: []
    args.starImports += [this.class.package?.name]
    CPSUtils.asCPSScript(args, body)
  }

  /**
   * Proxy for CPSUtils.invokeCPSMethod()
   *
   * @param object
   * @param method
   * @param args
   * @return
   * @throws InvalidCPSInvocation
   */
  def invokeCPSMethod(Object object, String method, Object... args)
      throws InvalidCPSInvocation {
    return invokeCPSMethodWithOptions([:], object, method, args)
  }

  /**
   * Proxy for CPSUtils.invokeCPSMethodWithOptions()
   * @param options
   * @param object
   * @param method
   * @param args
   * @return
   * @throws InvalidCPSInvocation
   */
  def invokeCPSMethodWithOptions(Map options, Object object, String method,
                                 Object... args) throws InvalidCPSInvocation {
    CPSUtils.invokeCPSMethodWithOptions(options, object, method, args)
  }
}
