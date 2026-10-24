Files read: `Main.java`, `Parcel.java`, `ParcelService.java`, `Locker.java`.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---:|---|---|---|---|---|
| 1 | Code quality | `Main.java:11` | Incomplete commands may cause index or number-format exceptions because inputs are not validated. | Must fix | Validate command names, token counts, and numeric values. |
| 2 | Code quality | `Main.java:13` | Parcel sizes outside 1–3 may be accepted. | Should fix | Reject sizes outside 1–3. |
| 3 | Code quality | `ParcelService.java:42` | Parcel codes are compared with `==` instead of by value. | Must fix | Compare codes with `equals()`. |
| 4 | Code quality | `Main.java:8` | The `Scanner` resource is not closed. | Note | Avoid closing it if doing so would close shared `System.in`. |
| 5 | Code quality | `ParcelService.java:6` | `Date` is an unused import. | Note | Remove the unused import. |
| 6 | Code quality | `ParcelService.java:12` | `maxParcels` is never read, so the parcel limit is not enforced. | Should fix | Enforce the limit or remove the field. |
| 7 | Code quality | `ParcelService.java:76` | The `FileWriter` is not closed, so buffered data may not be flushed. | Must fix | Use try-with-resources. |
| 8 | Code quality | `ParcelService.java:80` | An `IOException` is silently ignored. | Must fix | Report or propagate the exception. |
| 9 | Code quality | `Main.java:6` | `main` handles parsing, command dispatch, object creation, and output. | Should fix | Extract command-specific operations into methods. |
| 10 | Code quality | `ParcelService.java:28, 68` | The repeated `"STORED"` string may cause inconsistent status handling if changed incorrectly. | Note | Use a shared constant or enum. |
| 11 | Code quality | `ParcelService.java:49` | Returning `0` for a missing parcel is ambiguous. | Should fix | Distinguish a missing parcel from a zero-fee pickup. |
| 12 | Cohesion/coupling/connascence | `Main.java:6` | `main` has logical cohesion because it selects between different command operations. | Should fix | Move command-specific work into separate handlers. |
| 13 | Cohesion/coupling/connascence | `ParcelService.java:64` | `printParcels` uses a flag to select between different output behaviors. | Should fix | Separate staff and public listing methods. |
| 14 | Cohesion/coupling/connascence | `ParcelService.java:24` | `ParcelService` handles parcel operations, printing, and file persistence. | Should fix | Move notification and persistence to separate classes. |
| 15 | Cohesion/coupling/connascence | `Main.java:14` | Passing a whole `Parcel` to `ParcelService` is identified as stamp coupling. | Note | Keep the current design unless a narrower interface is justified. |
| 16 | Cohesion/coupling/connascence | `Main.java:22` | `Main` accesses the locker list and calls `Locker::isFree` directly. | Should fix | Let `ParcelService` provide the free-locker count. |
| 17 | Cohesion/coupling/connascence | `ParcelService.java:26` | `ParcelService` depends on `Locker` for locker operations. | Note | Encapsulate locker assignment behavior if needed. |
| 18 | Cohesion/coupling/connascence | `ParcelService.java:28` | `ParcelService` directly accesses the public `Parcel.status` field. | Should fix | Make `status` private and provide controlled methods. |
| 19 | Cohesion/coupling/connascence | `Locker.java:25` | `Locker` depends on the `Parcel` object for its operations. | Note | Use a narrower reference only if the full object is unnecessary. |
| 20 | Cohesion/coupling/connascence | `Parcel.java:13` | The parcel classes rely on consistent status strings such as `"NEW"` and `"STORED"`. | Should fix | Represent statuses with a shared enum. |
| 21 | Cohesion/coupling/connascence | `Locker.java:5` | Multiple classes rely on integer size codes 1–3 having the same meanings. | Should fix | Use a shared enum or named constants. |
| 22 | Cohesion/coupling/connascence | `Main.java:15` | `Main` interprets a `null` locker result as meaning no locker is available. | Should fix | Return an explicit result representing success or unavailability. |
| 23 | Cohesion/coupling/connascence | `Parcel.java:7` | `status` is a non-private field. | Should fix | Make the field private and expose only the required operations. |
| 24 | Cohesion/coupling/connascence | `ParcelService.java:20` | `getLockers` returns the internal mutable locker collection. | Should fix | Return an unmodifiable view or defensive copy. |

Findings by check: Code quality — 11; Cohesion, coupling and connascence — 13.