# Security policy

## Reporting a vulnerability

Please report vulnerabilities privately through GitHub's
[private vulnerability reporting](https://github.com/Ourea-Technologies/ensemble-weather-java/security/advisories/new)
for this repository. **Do not open a public issue**, pull request or
discussion for a security problem.

Include what you found, how to reproduce it, and the SDK version
(`EnsembleWeatherClient.VERSION`). We aim to acknowledge a report within three
working days and will keep you informed until it is fixed and released. We
credit reporters who want it.

## Supported versions

Security fixes go into the latest 0.x release.

| Version | Supported |
| ------- | --------- |
| 0.1.x   | yes       |

## What the SDK guarantees

These are covered by tests (`ClientTest`, `JsonTest`, `HttpClientTransportTest`
and the shared vectors):

- the API key never appears in exception messages, `toString()` of the client,
  its builder or a request, or the SDK's own log lines (a server message that
  echoes it is redacted), and it is never sent as a query parameter;
- cache keys hold a SHA-256 hash of the key, never the key;
- the key is never sent to another origin (host, scheme or port) on a redirect;
- TLS verification is never disabled, and there is no option to disable it;
- a response body over 64 MiB is an error, never an out-of-memory;
- JSON is parsed by jackson-core's streaming parser into plain data only (no
  data binding, so no JSON can name a class to instantiate); nesting depth and
  number and string lengths are bounded, and duplicate keys are refused.

One caveat outside the SDK's control: the JDK's own HTTP client debug logging
(`-Djdk.httpclient.HttpClient.log=headers` or `=all`) prints request headers,
including `X-API-Key`. The SDK logs a warning when it sees that property set
with a key configured; do not enable it where logs are kept.

Issues in the Ourea API itself (not this SDK) can be reported the same way.
