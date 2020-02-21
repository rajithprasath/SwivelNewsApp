package  com.rajith.codetest.network

/**
 * various error status to know what happened if something goes wrong with a repository call
 */
enum class HttpResult {
    NO_CONNECTION,
    TIMEOUT,
    CLIENT_ERROR,
    BAD_RESPONSE,
    SERVER_ERROR,
    NOT_DEFINED,
}