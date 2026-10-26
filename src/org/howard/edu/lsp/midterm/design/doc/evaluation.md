# Design Evaluation – CourseRegistration

The `CourseRegistration` class works for a single simple case, but its design breaks several of Arthur Riel's object-oriented design heuristics. Together these problems make the class fragile, hard to test, and hard to extend.

## 1. All data is public (breaks encapsulation)

`studentId`, `studentName`, `email`, `courseCode`, and `creditHours` are all `public`. This violates **Riel Heuristic 2.1: "All data should be hidden within its class."** Any other class can change these fields at any time, for example setting `creditHours` to a negative number or `email` to `null`, and nothing validates the change. The class cannot protect its own invariants. If the representation ever changes (say, storing a `Student` object instead of three strings), every class that touches those fields directly breaks.

## 2. One class does too much ("god class" / low cohesion)

The single method `registerStudent()` does six unrelated jobs:

1. calculates tuition,
2. applies a discount policy,
3. prints a confirmation,
4. saves the record to a file,
5. sends an email notification,
6. writes a log entry.

This violates **Riel Heuristic 2.8: "A class should capture one and only one key abstraction"** and **Heuristic 3.2: "Do not create god classes/objects in your system."** It also has low cohesion, against **Heuristic 4.6: "Most of the methods defined on a class should be using most of the data members most of the time."** For example, the email and logging code ignore `creditHours`, and the tuition code ignores `email`. The tuition rules, persistence, and notification code have nothing in common except that they happen at registration time. Each one is a separate reason for the class to change. A change to the tuition rate, the file format, or the email provider all mean editing the same method, which raises the risk of breaking unrelated behavior.

## 3. Mixing the student and course abstractions

Student data (`studentId`, `studentName`, `email`) and course data (`courseCode`, `creditHours`) are mixed together as loose fields in a "registration." This also goes against **Heuristic 2.8**: *Student* and *Course* are real key abstractions in the domain, but here they are not modeled as classes. As a result, a student registering for three courses must repeat their name and email three times, and the data can get out of sync.

## 4. Business rules hard-coded with magic numbers

The rate `750.0`, the threshold `12`, and the discount `0.90` are written inline. Policy and mechanism are tangled together. If the university changes its tuition rate or discount rules, someone has to find and edit this method. There is no way to swap in a different pricing policy (for example, graduate vs. undergraduate rates) without adding more conditionals.

## 5. Hard-wired dependencies on infrastructure

The class directly creates a `FileWriter("registrations.txt")`, calls `System.out` for "email," and uses `new Date()` for logging. This creates tight coupling to concrete implementations, against **Riel Heuristic 4.1: "Minimize the number of classes with which another class collaborates."** These hidden collaborators are concrete infrastructure classes, not abstractions. Consequences:

- **Extensibility:** Supporting a database or text messages means rewriting this method.
- **Testability:** You cannot unit-test the tuition calculation without also writing to a real file and printing output.
- **Correctness:** "Sending an email" is only a `println`, so no email is actually sent, but the code looks as if it is.

## 6. Poor error handling

The `catch (Exception e) { e.printStackTrace(); }` block swallows every failure. If the file write fails, the method still prints "Registration processed," and the email "confirmation" still goes out even though the registration was never saved. The writer is also not closed if `write` throws, because a try-with-resources block is not used. That is a resource leak and a correctness bug.

## 7. Behavior not kept next to the data it uses

Tuition depends on `creditHours`, which belongs to a course (or an enrollment), yet the calculation lives inside a generic `registerStudent()` method. This goes against **Riel Heuristic 2.9 ("keep related data and behavior in one place")**. The knowledge of how to price credit hours should live in a class responsible for tuition, not be buried inside a procedural script.

## Summary

`CourseRegistration` is essentially a procedural script wrapped in a class. It exposes its data, mixes several key abstractions, hard-codes policy, and couples directly to the console and file system. A better design separates these responsibilities into cohesive classes (Student, Course, Registration, TuitionCalculator, RegistrationRepository, Notifier, Logger) that collaborate through small, well-defined interfaces.
