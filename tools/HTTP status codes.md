# List HTTP status codes _HTTP 状态码列表_

HTTP 状态码(RFC 7231)由服务器为响应客户端向服务器发出的请求而发出。

状态码的第一个数字制定了五个标准响应类别之一。

## `1xx` informational response 

> the request was received, continuing process **请求被接收，继续处理**

信息响应表明已被接收和理解。
它是在请求处理继续期间临时发出的，它提醒客户端等待最终响应。

* `100` Continue
* `101` Switching Protocols
* `102` Processing (WebDAV; RFC 2518)
* `103` Early Hints (RFC 8297)

## `2xx` successful

> the request was successfully received, understood, and accepted **请求被成功地接收、理解和接受**

* `200` OK
* `201` Created
* `202` Accepted
* `203` Non-Authoritative Information (since HTTP/1.1)
* `204` No Content
* `205` Reset Content
* `206` Partial Content (RFC 7233)
* `207` Multi-Status (WebDAV; RFC 4918)
* `208` Already Reported (WebDAV; RFC 5842)
* `226` IM Used (RFC 3229)

## `3xx` redirection

> further action needs to be taken in order to complete the request **为了完成请求需要采取进一步的行动**

* `300` Multiple Choices
* `301` Moved Permanently
* `302` Found (Previously "Moved temporarily")
* `303` See Other (Since HTTP/1.1)
* `304` Not Modified (RFC 7232)
* `305` Use Proxy (since HTTP/1.1)
* `306` Switch Proxy
* `307` Temporary Redirect (Since HTTP/1.1)
* `308` Permanent Redirect (RFC 7538)

## `4xx` client error

> the request contains bad syntax or cannot be fulfilled **请求包含错误的语法或无法实现**

* `400` Bad Request
* `401` Unauthorized (RFC 7235)
* `402` Payment Required
* `403` Forbidden
* `404` Not Found
* `405` Method Not Allowed
* `406` Not Acceptable
* `407` Proxy Authentication Required (RFC 7235)
* `408` Request Timeout
* `409` Conflict
* `410` Gone
* `411` Length Required
* `412` Precondition Failed (RFC 7232)
* `413` Payload Too Large (RFC 7231)
* `414` URI Too Long (RFC 7231)
* `415` Unsupported Media Type (RFC 7233)
* `416` Range Not Satisfiable (RFC 7233)
* `417` Expectation Failed
* `418` I'm a teapot (RFC 2324, RFC 7168)
* `421`
* `422`
* `423`
* `424`
* `425`
* `426`
* `428`
* `429`
* `431`
* `451`

## `5xx` server error

> the server failed to fulfil an apparently valid request **服务器未能满足一个明显的请求**

