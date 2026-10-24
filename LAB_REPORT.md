# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/Onwara-won/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | Eclipse Temurin 25.0.4.1 LTS (OpenJDK) |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code 1.142.0 |
| SonarQube for IDE | 6.1.1 |
| AI tool and exact model | GitHub Copilot Chat in VS Code, gpt-6-luna|

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: b8a07b4

### Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
|---:|---|---|---|
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 5 | java:S2143 | ParcelService.java:6 | Use classes from java.time instead of java.util.Date. |
| 6 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 7 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 8 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 9 | java:S4973 | ParcelService.java:42 | Strings and boxed types should be compared using equals(). |
| 10 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 11 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 12 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 13 | java:S108 | ParcelService.java:80 | Either remove or fill this block of code. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 20 October 2026 |
| AI tool and exact model | GitHub Copilot Chat in VS Code, gpt-6-luna |
| Commit reviewed | b8a07b4 |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Opened the repository folder and read the four Java files under parcelpoint/src/main/java/parcelpoint |
| Output file in the reviews folder | reviews/parcelpoint-review.md |

### Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
|---:|---|---|---|---|---|
| 1 | Main.java:9, 15, 17, 22 | Direct use of `System.out` instead of a logger | SonarQube | `java:S106` | Yes: These lines use `System.out.println`, although this may be acceptable for a simple command-line program. |
| 2 | ParcelService.java:30, 67, 69 | Direct use of `System.out` in the service | SonarQube | `java:S106` | Yes: Printing directly from the service mixes application logic with output handling. |
| 3 | ParcelService.java:6 | Unused `java.util.Date` import and legacy Date API | Both | `java:S1128`, `java:S2143` | Yes: The import is unused, and SonarQube also flags the legacy Date API. |
| 4 | ParcelService.java:12 | `maxParcels` is never used to enforce a parcel limit | Both | `java:S1068` | Yes: The field is never read, so it does not limit the number of parcels. |
| 5 | ParcelService.java:42 | Parcel codes are compared using `==` | Both | `java:S4973` | Yes: `==` compares String references and may fail to match identical parcel codes. |
| 6 | ParcelService.java:76 | `FileWriter` is never closed | SonarQube | `java:S2095` | Yes: The writer is not closed, so buffered data may not be flushed reliably. |
| 7 | ParcelService.java:80 | Empty `catch` block silently ignores `IOException` | SonarQube | `java:S108` | Yes: The empty catch block hides file-saving errors. |
| 8 | Main.java:11–13 | Command arguments and numbers are not validated | AI | Code quality (probable bug) | Yes: Missing command arguments may cause an array-index error. |
| 9 | Main.java:13 | Parcel size is not validated to be between 1 and 3 | AI | Code quality | Yes: Invalid sizes such as `0` or `9` may be accepted. |
| 10 | Main.java:8 | `Scanner` is never closed | AI | Resource management | No: The Scanner is used for console input in a short-lived program, so the practical impact is limited. |
| 11 | Main.java:6 | `main` handles parsing, dispatch, object creation, and output | AI | Method cohesion: logical | Yes: The method handles several responsibilities that could be separated. |
| 12 | ParcelService.java:49 | Returning `0` for a missing parcel is ambiguous | AI | Code quality | Yes: The caller cannot distinguish a failed pickup from a successful pickup with no fee. |
| 13 | ParcelService.java:64 | `printParcels(boolean forStaff)` handles different outputs | AI | Method cohesion: logical | Yes: The boolean flag makes one method handle two different listing behaviors. |
| 14 | ParcelService.java:24 | `storeParcel` handles storage, SMS output, and file saving | AI | Class cohesion (weak) | Yes: The method combines storage, notification, and persistence responsibilities. |
| 15 | Main.java:22 | `Main` retrieves and filters the locker list itself | AI | Coupling (reach-through) | Yes: `Main` depends on the locker collection's details instead of delegating the filtering to the service. |
| 16 | ParcelService.java:20 | `getLockers` returns the internal locker list | AI | Encapsulation | Yes: Callers can modify the service's internal list directly. |
| 17 | Parcel.java:7; ParcelService.java:28, 44 | The public `status` field is accessed directly | AI | Content coupling; non-private field | Yes: Direct field access exposes the parcel's internal state. |
| 18 | Parcel.java:13; ParcelService.java:28, 44, 68 | Status strings must match across classes | AI | Connascence of convention | Yes: A typo in a repeated status string may cause incorrect behavior. |
| 19 | Locker.java:5; Parcel.java:6; ParcelService.java:56–58 | Size codes must have consistent meanings | AI | Connascence of convention | Yes: Inconsistent use of numeric size codes may cause incorrect behavior. |
| 20 | Main.java:15 | `Main` relies on `null` to indicate no free locker | AI | Connascence of convention | Yes: The caller must know that `null` means no locker is available. |
| 21 | Main.java:14 | `Main` passes an entire `Parcel` object to `ParcelService` | AI | Stamp coupling | No: Passing a `Parcel` is reasonable because the service needs its related attributes. |
| 22 | ParcelService.java:26; Locker.java:25 | `ParcelService` depends on `Locker`, and `Locker` depends on `Parcel` | AI | Stamp coupling | No: These dependencies are reasonable for a parcel-locker system and do not prove a design defect. |

**2.1 Name two problems that only the AI review found.**

The AI found that printParcels(boolean forStaff) handles two different behaviors (Table 2, row 13). It also found that returning 0 for a missing parcel makes failure indistinguishable from a free pickup (row 12). SonarQube did not report these issues.

**2.2 Name one problem that only SonarQube found, or write none.**

SonarQube found the direct use of System.out (java:S106) at seven locations in Main.java and ParcelService.java (Table 2, rows 1–2), which the AI did not report.

**2.3 Name one AI finding that was wrong or too vague, and explain why.**

The AI's stamp coupling finding between Main and ParcelService (Table 2, row 21) is questionable because the service needs several attributes of Parcel, making it reasonable to pass the whole object. I checked the finding by opening the specified file and reading the code at that location.

## Task 3. Fix and check

### Table 3. What You Did with Each SonarQube Issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
|---:|---|---|
| 1 | Kept | `System.out` is used for intentional user-facing instructions and command results in this command-line program. |
| 2 | Fixed | `a42db6a` |
| 3 | Fixed | `a42db6a` |
| 4 | Fixed | `a42db6a` |
| 5 | Fixed | `a42db6a` |
| 6 | Fixed | `a42db6a` |
| 7 | Fixed | `a42db6a` |
| 8 | Fixed | `a42db6a` |
| 9 | Fixed | `a42db6a` |
| 10 | Fixed | `a42db6a` |
| 11 | Fixed | `a42db6a` |
| 12 | Fixed | `a42db6a` |
| 13 | Fixed | `a42db6a` |

Number of SonarQube issues that remain after your fixes: 4

### The Two Design Problems That You Fixed

| Design problem (course term) | What you changed | Commit |
|---|---|---|
| Weak class cohesion | Moved console output from `ParcelService` to the command-line entry point. | `a42db6a` |
| Connascence of convention | Replaced shared numeric size codes with the `ParcelSize` enum, which defines size mappings and fee rates. | `a42db6a` |

### Output of the Program After Your Fixes

Commands: `store P1 6501 2`, `pickup P1 5`, and `staff`.

```text
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
SMS to 6501: parcel P1 is in locker 2
Locker 2
Fee: 30.0
P1 6501 PICKED_UP
Free lockers: 3
```

Content of parcels.csv: P1,PICKED_UP

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 24 October 2026 | 24 October 2026 |
| AI tool and exact model | GitHub Copilot Chat in VS Code, gpt-6-luna | GitHub Copilot Chat in VS Code, gpt-6-luna |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B, as given | Prompt C, as given |
| Builds with `mvn package` (yes or no) | Yes | Yes |
| Number of classes, interfaces, enums and records | 2 classes | 5 classes, 1 interface, 2 enums, 2 records (10 total) |
| SonarQube issues, total | 21 reported | 8 reported |
| SonarQube issues, by rule | `java:S106` ("Replace this use of System.out/System.err by a logger"): 16 issues. Unused `args` parameter: 1 issue. Member visibility ("Make this member protected"): 1 issue. Nested try block: 1 issue. Unnamed catch parameters: 2 issues. The Problems output did not expose rule keys for the latter four diagnostics. | `java:S106` ("Replace this use of System.out/System.err by a logger"): 7 issues. Unused `args` parameter: 1 issue. The Problems output did not expose the rule key for the unused-parameter diagnostic. |
| AI review findings in Check 1 | 3 | 0 |
| AI review findings in Check 2 | 6 | 8 |
| Review output file in the reviews folder | [reviews/bikeshare-poor-review.md](reviews/bikeshare-poor-review.md) | [reviews/bikeshare-good-review.md](reviews/bikeshare-good-review.md) |

**4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.**

SonarQube did not report a rule explicitly about cohesion or coupling. It did flag the public `BIKES` member in `Main.java:17` with the message, “Make this member protected.” This is a visibility and encapsulation issue that can contribute to coupling, but it is not itself a cohesion- or coupling-specific finding.

**4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.**

Yes. The poor version exposes the mutable bike inventory and bike state through public fields (`Main.java:17, 185-189`), while the good version keeps its fields private.

**4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?**

I would extend bikeshare-good. I could add `VISITOR(4)` to `MemberType`, which keeps the visitor's fee rule with the member type and avoids adding another integer code and conditional fee rule to shared logic.

## Task 5. Reflection

**5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.**

SonarQube uses static-analysis rules to flag code issues, such as comparing strings using `==` and leaving a `FileWriter` unclosed in ParcelPoint. It does not directly evaluate design quality, and it missed the weak cohesion and publicly shared inventory in the poor BikeShare version.

**5.2 When should you not trust the AI review? Give an example from this lab.**

Check AI findings against the code and requirements before accepting them. For example, the review identified passing a `Ride` object to `RideRepository.save()` as stamp coupling, even though the repository needs the ride data.

**5.3 What will you do differently in Project Phase 2 after this lab?**

In Project Phase 2, I will separate responsibilities, encapsulate state, and use enums instead of shared numeric codes. I will run tests and static analysis, and verify AI suggestions before acting on them.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
|---|---|---|---|
| GitHub Copilot Chat in VS Code, [exact model name] | Review the BikeShare versions and help complete the lab report. | Drafted `reviews/bikeshare-poor-review.md` and `reviews/bikeshare-good-review.md`, and helped fill in the Task 4 results. | Checked findings against the Java source code and line numbers, recounted findings in the review tables, ran `mvn package` for both versions, and checked SonarQube for IDE diagnostics. |