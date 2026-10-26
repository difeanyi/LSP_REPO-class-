# Improved Design – CRC Cards

The redesign splits the original `CourseRegistration` god class into cohesive classes. Each one captures a single key abstraction, hides its data, and collaborates with as few classes as possible.

---

**Class:** Student

**Responsibilities:**
- Store student information (student ID, name, email)
- Keep its data private and provide read access through getters
- Validate its own data (for example, a non-empty ID and a well-formed email)

**Collaborators:**
- None

---

**Class:** Course

**Responsibilities:**
- Store course information (course code, credit hours)
- Keep its data private and provide read access through getters
- Validate that credit hours are positive

**Collaborators:**
- None

---

**Class:** Registration

**Responsibilities:**
- Represent one student's enrollment in one course
- Store the student, the course, the calculated tuition, and the registration timestamp
- Provide registration details for confirmation messages and records

**Collaborators:**
- Student
- Course

---

**Class:** TuitionCalculator

**Responsibilities:**
- Calculate tuition from credit hours using the per-credit rate
- Apply the heavy-course-load discount (10% off at 12 or more credit hours)
- Keep tuition policy values (rate, threshold, discount) as named constants in one place

**Collaborators:**
- Course

---

**Class:** RegistrationRepository

**Responsibilities:**
- Save a registration record to persistent storage
- Report failures to the caller instead of silently ignoring them

**Collaborators:**
- Registration

---

**Class:** ConfirmationNotifier

**Responsibilities:**
- Build and send a registration confirmation to the student

**Collaborators:**
- Registration
- Student

---

**Class:** RegistrationLogger

**Responsibilities:**
- Record that a registration was processed, with a timestamp

**Collaborators:**
- Registration

---

**Class:** RegistrationService

**Responsibilities:**
- Coordinate the registration workflow: calculate tuition, create the Registration, save it, send the confirmation, and log the event
- Make sure a confirmation is sent only after the registration is saved successfully

**Collaborators:**
- Student
- Course
- TuitionCalculator
- Registration
- RegistrationRepository
- ConfirmationNotifier
- RegistrationLogger

---

## How this fixes the original problems

- **Encapsulation:** Every class keeps its data private (Riel 2.1).
- **One key abstraction per class:** Student, Course, tuition rules, storage, notification, and logging are separated (Riel 2.8, 3.2).
- **Low coupling:** Only `RegistrationService` knows the overall workflow. The other classes know nothing about each other beyond the data they need.
- **Correctness:** The service sends confirmations only after a successful save, so a failed save no longer produces a false "registered" message.
