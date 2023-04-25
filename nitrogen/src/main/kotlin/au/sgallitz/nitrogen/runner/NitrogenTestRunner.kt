package au.sgallitz.nitrogen.runner

import androidx.test.ext.junit.runners.AndroidJUnit4
import au.sgallitz.nitrogen.TestEnvironment
import au.sgallitz.nitrogen.internal.createInstance
import kotlin.reflect.KClass
import org.junit.runner.Description
import org.junit.runner.Runner
import org.junit.runner.manipulation.Filter
import org.junit.runner.manipulation.Filterable
import org.junit.runner.manipulation.NoTestsRemainException
import org.junit.runner.manipulation.Sortable
import org.junit.runner.manipulation.Sorter
import org.junit.runner.notification.RunNotifier
import org.robolectric.RobolectricTestRunner

open class NitrogenTestRunner(
    testClass: Class<*>
) : Runner(), Filterable, Sortable {
    open val robolectricRunner: KClass<out Runner> by lazy {
        RobolectricTestRunner::class
    }
    open val instrumentationRunner: KClass<out Runner> by lazy {
        AndroidJUnit4::class
    }

    private val currentTestRunner: Runner by lazy {
        if (TestEnvironment.isRobolectric) {
            robolectricRunner.java.createInstance(testClass)
        } else {
            instrumentationRunner.java.createInstance(testClass)
        }
    }

    override fun getDescription(): Description {
        return currentTestRunner.description
    }

    override fun run(runNotifier: RunNotifier) {
        currentTestRunner.run(runNotifier)
    }

    @Throws(NoTestsRemainException::class)
    override fun filter(filter: Filter) {
        (currentTestRunner as Filterable).filter(filter)
    }

    override fun sort(sorter: Sorter) {
        (currentTestRunner as Sortable).sort(sorter)
    }
}
