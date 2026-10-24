Files read: `bikeshare-poor/src/main/java/bikeshare/Main.java`.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---:|---|---|---|---|---|
| 1 | Code quality | `Main.java:18` | The static `Scanner` wrapping standard input is never closed when the command loop ends. | Note | Close the scanner when the application exits, taking care to do so only at shutdown. |
| 2 | Code quality | `Main.java:79-117` | `finishRide` is long and handles parsing, validation, fee calculation, persistence, receipt output, and state updates with nested exception handling. | Should fix | Extract the distinct validation, fee-calculation, and ride-completion steps into small methods. |
| 3 | Code quality | `Main.java:120-137` | `showBikes` uses `availableOnly` to switch between listing all bikes and listing only available bikes. | Should fix | Split the two listing behaviors into clearly named methods or use a single method with an explicit filter. |
| 4 | Cohesion, coupling and connascence | `Main.java:28-48` | `main` has logical cohesion: a command token selects among separate operations in the switch. | Note | Keep `main` as a small dispatcher and move command handling into a dedicated menu class. |
| 5 | Cohesion, coupling and connascence | `Main.java:120-137` | `showBikes` has logical cohesion because its flag selects between two listing behaviors. | Should fix | Give the all-bike and available-bike listings separate methods. |
| 6 | Cohesion, coupling and connascence | `Main.java:13-18, 51-178` | `Main` combines command-line interaction, bike rental and return rules, inventory management, station lookup, and CSV persistence, showing weak class cohesion. | Should fix | Separate the menu, bike-share rules, and ride persistence into focused classes. |
| 7 | Cohesion, coupling and connascence | `Main.java:67-71, 93-100, 111-114, 149-152, 184-189` | `Main` directly reads and changes `Bike` fields, which is content coupling. | Should fix | Make bike state private and expose operations that preserve its invariants. |
| 8 | Cohesion, coupling and connascence | `Main.java:14-15, 56-59, 71, 100, 150, 189` | `Main` and `Bike` rely on the convention that integer `1` means student and `2` means staff; this is convention connascence. | Should fix | Replace the integer codes with a `MemberType` enum. |
| 9 | Cohesion, coupling and connascence | `Main.java:14-17, 185-189` | `STUDENT`, `STAFF`, `STATIONS`, `BIKES`, and every `Bike` field are non-private; in particular, callers can mutate the public bike list and bike state. | Should fix | Make these fields private and expose only the operations and immutable data needed by callers. |

Findings by check: Code quality — 3; Cohesion, coupling and connascence — 6.