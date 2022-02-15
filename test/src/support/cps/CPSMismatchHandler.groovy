package support.cps

import javax.annotation.CheckForNull

/**
 * This code was taken from the workflow-cps plugin. The extension makes methods
 * private, not allowing the, to be used directly.
 */
class CPSMismatchHandler {

  /**
   * Generate Mismatch Message
   *
   * @param expectedReceiverClassName
   * @param expectedMethodName
   * @param actualReceiverClassName
   * @param actualMethodName
   * @return
   */
  static String mismatchMessage(@CheckForNull String expectedReceiverClassName,
                                String expectedMethodName,
                                @CheckForNull String actualReceiverClassName,
                                String actualMethodName) {
    StringBuilder b = new StringBuilder('expected to call ')
    if (expectedReceiverClassName != null) {
      b.append(expectedReceiverClassName).append('.')
    }
    b.append(expectedMethodName).append(' but wound up catching ')
    if (actualReceiverClassName != null) {
      b.append(actualReceiverClassName).append('.')
    }
    b.append(actualMethodName)
    //b.append('; see: https://jenkins.io/redirect/pipeline-cps-method-mismatches/')
    return b.toString()
  }

  /**
   * Handle Mismatch between expected and actual methods
   *
   * @param expectedReceiver
   * @param expectedMethodName
   * @param actualReceiver
   * @param actualMethodName
   */
  static void handleMismatch(Object expectedReceiver,
                             String expectedMethodName,
                             Object actualReceiver,
                             String actualMethodName) {
    //Class receiverClass = expectedReceiver.getClass()
    String mismatchMessage = mismatchMessage(className(expectedReceiver),
        expectedMethodName, className(actualReceiver), actualMethodName)
    throw new IllegalStateException(mismatchMessage)
  }

  /**
   * Get a class name as string
   * @param receiver
   * @return
   */
  @SuppressWarnings(['UnnecessaryGetter'])
  static @CheckForNull
  String className(@CheckForNull Object receiver) {
    if (receiver == null) {
      return null
    }
    if (receiver instanceof Class) {
      return ((Class) receiver).getName()
    }
    return receiver.getClass().getName()
  }
}
