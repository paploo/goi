package net.paploo.goi.common.extensions

import java.nio.file.Path

infix fun Path.append(other: Path): Path =
    this.resolve(other)