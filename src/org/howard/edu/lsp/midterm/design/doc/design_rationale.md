# Design Rationale – Evolving Requirements

## New requirements

1. Registration records can be stored in either a **local file** or a **database**, chosen by the deployment environment.
2. Confirmations can be sent by either **email** or **text message**.

## How the Part 2 design accommodates them

My Part 2 design already isolates storage in `RegistrationRepository` and notification in `ConfirmationNotifier`. `RegistrationService` only asks these classes to "save this registration" and "send a confirmation." It does not know *how* either job is done. So the new requirements only change those two cards. The rest of the system (Student, Course, Registration, TuitionCalculator, RegistrationLogger) stays the same.

## Changes to the CRC cards

**`RegistrationRepository` becomes an abstraction** (an interface or abstract class) with two concrete versions:

---

**Class:** RegistrationRepository (abstract)

**Responsibilities:**
- Define the operation for saving a registration record

**Collaborators:**
- Registration

---

**Class:** FileRegistrationRepository

**Responsibilities:**
- Save registration records to a local file
- Report file I/O failures to the caller

**Collaborators:**
- Registration

---

**Class:** DatabaseRegistrationRepository

**Responsibilities:**
- Save registration records to a database
- Report database failures to the caller

**Collaborators:**
- Registration

---

**`ConfirmationNotifier` also becomes an abstraction** with two concrete versions:

---

**Class:** ConfirmationNotifier (abstract)

**Responsibilities:**
- Define the operation for sending a registration confirmation

**Collaborators:**
- Registration
- Student

---

**Class:** EmailConfirmationNotifier

**Responsibilities:**
- Send the confirmation to the student's email address

**Collaborators:**
- Registration
- Student

---

**Class:** SmsConfirmationNotifier

**Responsibilities:**
- Send the confirmation as a text message to the student's phone number

**Collaborators:**
- Registration
- Student

---

**`Student`** gets one more responsibility: store the student's phone number (needed for text messages).

**`RegistrationService`** keeps the same responsibilities. It now receives a `RegistrationRepository` and a `ConfirmationNotifier` (for example, through its constructor) and works with them only through the abstract types. The application's startup code chooses which concrete versions to create, based on the deployment environment's configuration.

## Key responsibility decision: who chooses the storage type?

**Decision:** `RegistrationService` should **not** decide whether to use a file or a database. That choice belongs to the application's setup and configuration code, which creates the right `RegistrationRepository` and passes it into the service.

**Justification:**
- The storage type depends on the *deployment environment*, not on registration business rules. Putting an `if (environment == "production") ... else ...` check inside `RegistrationService` would bring back the same kind of conditional, tightly coupled logic that made the original `CourseRegistration` hard to maintain.
- If `RegistrationService` depends only on the abstract `RegistrationRepository`, then adding a third storage option later (such as a cloud service) needs a new subclass and a configuration change, with no edit to the service. This keeps the service cohesive (Riel 2.8: one key abstraction, the registration workflow) and reduces coupling to concrete infrastructure classes.
- It also makes `RegistrationService` easy to test, because a test can pass in a simple in-memory repository and a fake notifier.

The same reasoning applies to choosing email or text message. The notification method can be chosen from configuration, or from the student's stated preference, outside the service.
