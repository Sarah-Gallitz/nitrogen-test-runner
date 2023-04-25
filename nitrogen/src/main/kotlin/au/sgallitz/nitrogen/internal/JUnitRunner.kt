package au.sgallitz.nitrogen.internal

import org.junit.runner.Runner
import org.junit.runners.model.InitializationError

@Throws(InitializationError::class)
internal fun Class<out Runner>.createInstance(testClass: Class<*>): Runner {
    val constructor = try {
        this.getConstructor(Class::class.java)
    } catch (e: Throwable) {
        throw InitializationError(
            "Test runner ${this.simpleName} must have a constructor that take Class<*>"
        )
    }

    val runner = try {
        constructor.newInstance(testClass)
    } catch (e: Throwable) {
        throw InitializationError("Failed to instantiate test runner ${this.simpleName}")
    }

    return runner
}
