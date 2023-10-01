package net.paploo.goi.application

/**
 * Root interface for all "Application" instances.
 *
 * Depending on the needs, this can be anywhere from a simple function over an `Array<String>`, or
 * a full lifecycled application.
 */
interface Application<T> : suspend (T) -> Result<Unit> {
    override suspend operator fun invoke(args: T): Result<Unit>
}
