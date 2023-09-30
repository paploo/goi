package net.paploo.goi.application

import net.paploo.goi.common.extensions.flatFinally
import net.paploo.goi.common.extensions.flatMap
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Base of a more structured application with a simple lifecycle.
 *
 * @param Config The application supplied portion of the configuration.
 * @param Arguments The user supplied portion of the configuration.
 * @param Environment The environment constructed from the config and arguments.
 */
abstract class BaseApplication<Config, Arguments, Environment> : Application<Arguments> {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun invoke(args: Arguments): Result<Unit> =
        Result.success(
            config
        ).flatMap { conf ->
            logger.debug("Setting up environment")
            setupEnvironment(args, conf)
        }.flatMap { environment ->
            logger.debug("Invoking environment")
            invokeEnvironment(environment).flatFinally { teardownEnvironment(environment) }
        }.onFailure {
            logger.error("FAILURE", it)
        }.onSuccess {
            logger.info("SUCCESS")
        }

    protected abstract suspend fun invokeEnvironment(environment: Environment): Result<Unit>

    protected abstract val config: Config

    protected abstract suspend fun setupEnvironment(args: Arguments, config: Config): Result<Environment>

    protected abstract suspend fun teardownEnvironment(environment: Environment): Result<Unit>

}