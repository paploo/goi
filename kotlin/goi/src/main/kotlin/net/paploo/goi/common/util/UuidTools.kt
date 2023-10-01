package net.paploo.goi.common.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.UUID

/**
 * Adds UUIDv5 Support because, surprisingly, Java still hasn't implemented it
 *
 * Details Pinched from reference implementation in https://stackoverflow.com/questions/40230276/how-to-make-a-type-5-uuid-in-java
 * Kotlinified in gist https://gist.github.com/icedraco/00118b4d3c91d96d8c58e837a448f1b8
 */
object  UuidTools {

    fun createV3(
        namespace: UUID,
        name: String
    ): UUID {
        val nsBytes = toBytes(namespace)
        val nameBytes = name.toByteArray()
        return UUID.nameUUIDFromBytes(nsBytes + nameBytes)
    }

    fun createV4(): UUID = UUID.randomUUID()

    fun createV5(
        namespace: UUID,
        name: String
    ): UUID {
        val md: MessageDigest
        try {
            md = MessageDigest.getInstance("SHA-1")
        } catch (ex: NoSuchAlgorithmException) {
            throw InternalError("SHA-1 not supported", ex)
        }

        md.update(toBytes(namespace))
        md.update(name.toByteArray())
        val bytes = md.digest()
        bytes[6] = ((bytes[6].toInt() and 0x0F) or 0x50).toByte() /* clear version; set to version 5 */
        bytes[8] = ((bytes[8].toInt() and 0x3F) or 0x80).toByte() /* clear variant; set to IETF variant */
        return fromBytes(bytes)
    }

    private fun fromBytes(data: ByteArray): UUID {
        // Based on the private UUID(bytes[]) constructor
        assert(data.size >= 16)
        var msb = 0L
        var lsb = 0L
        for (i in 0..7)
            msb = msb shl 8 or (data[i].toLong() and 0xff)
        for (i in 8..15)
            lsb = lsb shl 8 or (data[i].toLong() and 0xff)
        return UUID(msb, lsb)
    }

    private fun toBytes(uuid: UUID): ByteArray {
        // inverted logic of fromBytes()
        val out = ByteArray(16)
        val msb = uuid.mostSignificantBits
        val lsb = uuid.leastSignificantBits
        for (i in 0..7)
            out[i] = (msb shr (7 - i) * 8 and 0xff).toByte()
        for (i in 8..15)
            out[i] = (lsb shr (15 - i) * 8 and 0xff).toByte()
        return out
    }

}

fun String.createUuidV5(namespace: UUID): UUID =
    UuidTools.createV5(namespace, this)

/**
 * Non-standard helper to create a UUIDv5 from iterable of strings.
 *
 * The default separator matches what was used in Goi Ruby, making this a convenient
 * default.
 */
fun Iterable<String>.createUuidV5(namespace: UUID, separator: String = "|"): UUID =
    UuidTools.createV5(namespace, joinToString(separator))