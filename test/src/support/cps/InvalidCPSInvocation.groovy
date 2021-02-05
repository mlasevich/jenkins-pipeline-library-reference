package support.cps

class InvalidCPSInvocation extends Exception {

  final String className
  final String methodName
  final Object[] params

  InvalidCPSInvocation(String className, String methodName, Object... params) {
    super(
        "ERROR: ${className}.${methodName}(${params}) did not appear to be CPS transformed")
    this.className = className
    this.methodName = methodName
    this.params = params
  }
}
