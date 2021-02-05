import com.legrig.ClassWithSteps

/**
 * Simple echo call
 */
def call() {
  echo 'Executed'
}

/**
 * Example of using classes from library in vars
 *
 * @param errMsg
 * @return
 */
def sendError(String errMsg = 'Unknown Error') {
  def tool = new ClassWithSteps()
  tool.error(errMsg)
}
